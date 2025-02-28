package com.github.vitalsoftware.scalaredox.models

import java.util.UUID
import org.joda.time.DateTime
import com.github.vitalsoftware.util.JsonImplicits.jodaISO8601Format
import com.github.vitalsoftware.macros._
import com.github.vitalsoftware.util.RobustPrimitives
import play.api.libs.json.{ Format, Reads, Writes }

import scala.collection.Seq

object DataModelTypes extends Enumeration {
  val ClinicalSummary: Value = Value("Clinical Summary")
  val Claim, Device, Financial, Flowsheet, Inventory, Media, Notes, Order, PatientAdmin, PatientSearch, Referral,
    Results, Scheduling, SurgicalScheduling, Vaccination, Medications, Invalid = Value

  @transient implicit lazy val jsonFormat: Format[DataModelTypes.Value] =
    Format(Reads.enumNameReads(DataModelTypes), Writes.enumNameWrites)
}

object RedoxEventTypes extends Enumeration {
  val QueryResponse: Value = Value("Query Response")
  // format: off
  val Arrival, AvailableSlots, AvailableSlotsResponse, Booked, BookedResponse, Cancel, Delete, Deplete, Discharge,
      DocumentGet, DocumentQuery, GroupedOrders, LocationQuery,  Modification, Modify, New, NewPatient, NewUnsolicited, 
      NoShow, PatientMerge, PatientQuery, PatientQueryResponse, PatientPush, PatientUpdate, Payment, PreAdmit, Push, 
      Query, Registration, Replace, Reschedule, Response, Submission, Transaction, Transfer, Update, VisitMerge, 
      VisitQuery, VisitQueryResponse, VisitPush, VisitUpdate, Administration, Invalid = Value
  // format: on

  @transient implicit lazy val jsonFormat: Format[RedoxEventTypes.Value] =
    Format(Reads.enumNameReads(RedoxEventTypes), Writes.enumNameWrites)
}

/**
 * Request/response header meta-data
 *
 * @param DataModel Data model. E.g. Scheduling, Results
 * @param EventType Type of event. E.g. New, Update
 * @param EventDateTime DateTime of the event. ISO 8601 Format
 * @param Test Flag as a test message
 * @param Source Where the message originated. Included in messages from Redox
 * @param Destinations List of destinations to send your message to. All messages must have at least one destination. Queries accept only one destination. Required when sending data to Redox
 * @param FacilityCode Code for the facility related to the message. Only use this field if a health system indicates you should. The code is specific to the health system's EHR and might not be unique across health systems. In general, the facility fields within the data models (e.g. OrderingFacility) are more reliable and informative.
 * @param IsIncomplete Indicates that a limit was reached, and not all data was returned. If true, the sender may want to restrict the parameters of the request in order to match fewer results.
 * @param CanceledEvent Type of event being canceled. E.g. Arrival, Discharge, PreAdmit
 */
@jsonDefaults case class Meta(
  DataModel: DataModelTypes.Value,
  EventType: RedoxEventTypes.Value,
  EventDateTime: Option[DateTime] = None,
  Test: Option[Boolean] = None,
  Source: Option[SourceDestination] = None,
  Destinations: Seq[SourceDestination] = Seq.empty,
  Logs: Seq[Log] = Seq.empty,
  FacilityCode: Option[String] = None,
  IsIncomplete: Option[Boolean] = None,
  CanceledEvent: Option[RedoxEventTypes.Value] = None,
  Extensions: Option[Extension] = None,
)

object Meta extends RobustPrimitives
