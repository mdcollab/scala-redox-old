package com.github.vitalsoftware.scalaredox.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.Uri.Path._
import akka.http.scaladsl.model._
import com.github.vitalsoftware.scalaredox.models._
import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus._
import play.api.libs.ws._
import play.api.libs.ws.ahc._
import play.api.libs.json._
import org.joda.time.DateTime

import scala.concurrent.{Future, SyncVar}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits._
import scala.util.{Try, Failure, Success}

/**
  * Created by apatzer on 3/20/17.
  */
class RedoxClient(conf: Config) {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  protected lazy val client = StandaloneAhcWSClient()

  private[client] lazy val apiKey = conf.as[String]("redox.apiKey")
  private[client] lazy val apiSecret = conf.as[String]("redox.secret")
  private[client] lazy val baseRestUri = Uri(conf.getOrElse[String]("redox.restApiBase", "https://api.redoxengine.com"))
  private[client] lazy val authInfo = {
    val auth = new SyncVar[Option[AuthInfo]]
    auth.put(None)
    auth
  }

  private def baseRequest(url: String) = client.url(url)
  private def baseQuery = baseRequest(baseRestUri.withPath(/("query")).toString()).withMethod("POST")
  private def basePost = baseRequest(baseRestUri.withPath(/("endpoint")).toString()).withMethod("POST")

  /** Send and receive an authorized request */
  private def sendReceive[T](request: StandaloneWSRequest)(implicit format: Reads[T]): Future[RedoxResponse[T]] = {
    for {
      auth <- authInfo.get match {
        case Some(info) => Future { info }
        case None => authorize()
      }
      response <- execute[T](request.withHeaders("Authorization" -> s"Bearer ${auth.accessToken}"))
    } yield response
  }

  /** Raw request execution */
  private def execute[T](request: StandaloneWSRequest)(implicit format: Reads[T]): Future[RedoxResponse[T]] = {
    request.execute().map {
      case r if Set[StatusCode](
        BadRequest,
        Unauthorized,
        Forbidden,
        NotFound,
        MethodNotAllowed,
        RequestedRangeNotSatisfiable
      ).contains(r.status) =>
        Try {
          r.json.asOpt[RedoxErrorResponse].map(Left(_))
        } match {
          case Success(t) => t
          case Failure(e) => Some(Left(RedoxErrorResponse.simple(e.getMessage)))
        }
      case r => r.json.asOpt[T].map(Right(_))
    }.map { response =>
      RedoxResponse[T](response.getOrElse(Left(RedoxErrorResponse.NotFound)))
    }
  }

  private def optionalQueryParam[T](el: Option[T],
                                    key: String,
                                    f: T => String = (o: T) => o.toString): Map[String, String] = {
    el.map(e => Map(key -> f(e))).getOrElse(Map.empty)
  }

  /** Authorize to Redox and save the auth tokens */
  def authorize(): Future[AuthInfo] = {
    val req = baseRequest(baseRestUri.withPath(/("auth") / "authenticate").toString())
      .withMethod("POST")
      .withBody(Map("apiKey" -> Seq(apiKey), "secret" -> Seq(apiSecret)))
    setAuth(execute[AuthInfo](req), "Cannot authenticate. Check configuration 'redox.apiKey' & 'redox.secret'")
      .map { auth =>
        refresh(auth)
        auth
      }
  }

  /** Refresh the auth token a minute before it expires */
  def refresh(auth: AuthInfo): Unit = {
    val delay = auth.expires.getMillis - DateTime.now.getMillis - 60 * 1000
    system.scheduler.scheduleOnce(delay.millis) {
      val req = baseRequest(baseRestUri.withPath(/("auth") / "refreshToken").toString())
        .withMethod("POST")
        .withBody(Map("apiKey" -> Seq(apiKey), "refreshToken" -> Seq(auth.refreshToken)))

      setAuth(sendReceive[AuthInfo](req), "Cannot refresh OAuth2 token")
    }
  }

  /** Set a thread-safe auth-token, throwing an exception if the client authorization fails */
  private def setAuth(response: Future[RedoxResponse[AuthInfo]], errMsg: String) = {
    response.map { resp =>
      resp.result.fold(
        error => throw RedoxAuthorizationException(s"$errMsg: ${error.Errors.map(_.Text).mkString(",")}"),
        t => t
      )
    }.map { auth =>
      authInfo.take()
      authInfo.put(Some(auth))
      auth
    }
  }

  def getClinicalSummary(query: ClinicalSummaryQuery): Future[RedoxResponse[ClinicalSummary]] = {
    sendReceive[ClinicalSummary](baseQuery.withBody(Json.toJson(query)))
  }

  def sendClinicalSummary(data: ClinicalSummary): Future[RedoxResponse[Meta]] = {
    sendReceive[Meta](basePost.withBody(Json.toJson(data)))
  }

}