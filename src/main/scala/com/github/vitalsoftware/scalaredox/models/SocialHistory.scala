package com.github.vitalsoftware.scalaredox.models

import org.joda.time.DateTime
import com.github.vitalsoftware.util.JsonImplicits.jodaISO8601Format
import com.github.vitalsoftware.macros._
import com.github.vitalsoftware.util.RobustPrimitives
import play.api.libs.json.{Json, OFormat}

/**
 * @param Code A code for the observation (exercise, alcohol intake, etc.) . SNOMED CT
 * @param Value The coded observed value for the code
 * @param ValueText The observed value for the code
 */
@jsonDefaults case class SocialHistoryObservation(
  Code: Option[String] = None,
  CodeSystem: Option[String] = None,
  CodeSystemName: Option[String] = None,
  Name: Option[String] = None,
  StartDate: DateTime,
  EndDate: Option[DateTime] = None,
  Value: Option[BasicCode] = None,
  ValueText: Option[String] = None
) extends Code
    with DateRange

object SocialHistoryObservation extends RobustPrimitives

/**
 * @param StartDate When the pregnancy started. ISO 8601 Format
 * @param EndDate When the pregnancy ended. ISO 8601 Format
 * @param EstimatedDelivery Estimate delivery date if pregnancy is still active.
 */
@jsonDefaults case class Pregnancy(
  StartDate: DateTime,
  EndDate: Option[DateTime] = None,
  EstimatedDelivery: Option[String] = None
) extends DateRange

object Pregnancy extends RobustPrimitives

/**
 * @param Code A code indicating the status (current smoker, never smoker, snuff user, etc.). Contains all values descending from the SNOMED CT® 365980008 tobacco use and exposure - finding hierarchy
 * @param StartDate Start date of status
 * @param EndDate Date status ended. If this is null, the status is current.
 */
@jsonDefaults case class TobaccoUse(
  Code: Option[String] = None,
  CodeSystem: Option[String] = None,
  CodeSystemName: Option[String] = None,
  Name: Option[String] = None,
  StartDate: Option[DateTime] = None,
  EndDate: Option[DateTime] = None
) extends Code

object TobaccoUse extends RobustPrimitives

@jsonDefaults case class SocialHistory(
  Observations: Seq[SocialHistoryObservation] = Seq.empty,
  Pregnancy: Seq[Pregnancy] = Seq.empty,
  TobaccoUse: Seq[TobaccoUse] = Seq.empty
)

object SocialHistory extends RobustPrimitives{
  implicit val format: OFormat[SocialHistory] =Json.format
}

/**
 * This section contains information such as tobacco use, pregnancies, and generic social behavior observations.
 * @param SocialHistoryText Free text form of the social history summary
 * @param SocialHistory Generic observations about the patient's social hisotry that don't fall into the smoking or pregnancy categories.
 */
@jsonDefaults case class SocialHistoryMessage(
  SocialHistoryText: Option[String] = None,
  SocialHistory: SocialHistory
)

object SocialHistoryMessage extends RobustPrimitives
