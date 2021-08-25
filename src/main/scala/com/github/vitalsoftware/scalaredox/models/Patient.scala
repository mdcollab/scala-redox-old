package com.github.vitalsoftware.scalaredox.models

import org.joda.time.DateTime
import com.github.vitalsoftware.util.JsonImplicits.jodaISO8601Format
import com.github.vitalsoftware.macros._
import com.github.vitalsoftware.util.RobustPrimitives
import play.api.libs.json.Reads.DefaultJodaDateReads
import play.api.libs.json._

import scala.collection.Seq

/**
 * Patient identifier
 *
 * @param ID The actual identifier for the patient.
 * @param IDType An ID type associated with identifier (Medical Record Number, etc.)
 */
@jsonDefaults case class Identifier(
  ID: String,
  IDType: String
)

object Identifier extends RobustPrimitives

object RaceType extends Enumeration {
  val Asian, White, Unknown = Value
  val AmericanIndianOrAlaskanNative = Value("American Indian or Alaska Native")
  val BlackOrAfricanAmerican = Value("Black or African American")
  val NativeHawaiianOrOtherPacificIslander = Value("Native Hawaiian or Other Pacific Islander")
  val OtherRace = Value("Other Race")

  def defaultValue = Unknown

  @transient implicit lazy val jsonFormat: Format[RaceType.Value] =
    Format(Reads.enumNameReads(RaceType), Writes.enumNameWrites)
}

object SexType extends Enumeration {
  val Male, Female, Unknown, Other = Value

  @transient lazy val strictReads: Reads[SexType.Value] = Reads.enumNameReads(SexType)

  // Allow conversion of "M" and "F" into "Male" and "Female" types
  @transient implicit lazy val fuzzyReads: Reads[SexType.Value] = new Reads[SexType.Value] {
    def reads(json: JsValue) = {
      val transform = json match {
        case JsString(str) if str.equalsIgnoreCase("M") => JsString(Male.toString)
        case JsString(str) if str.equalsIgnoreCase("F") => JsString(Female.toString)
        case _                                          => json
      }
      strictReads.reads(transform)
    }
  }
  @transient implicit lazy val jsonFormat: Format[SexType.Value] = Format(fuzzyReads, Writes.enumNameWrites)

  def defaultValue: SexType.Value = Unknown
}

/**
 * About a patient.
 *
 * @param FirstName Required
 * @param LastName Required
 * @param DOB Partially reliable. Patient's date of birth. In ISO 8601 format
 * @param Sex Required
 * @param Language Patient's primary spoken language. In ISO 639-1 alpha values (e.g. 'en'). http://www.mathguide.de/info/tools/languagecode.html
 * @param Citizenship Patient's nation(s) of citizenship. *In ISO 3166 alpha 2 format (e.g. 'US').
 * @param Race List at http://phinvads.cdc.gov/vads/ViewValueSet.action?id=66D34BBC-617F-DD11-B38D-00188B398520
 * @param Ethnicity List at https://phinvads.cdc.gov/vads/ViewValueSet.action?id=35D34BBC-617F-DD11-B38D-00188B398520
 * @param Religion List at https://www.hl7.org/fhir/v3/ReligiousAffiliation/index.html
 * @param MaritalStatus List at http://www.hl7.org/FHIR/v2/0002/index.html
 */
@jsonDefaults case class ClinicalSummaryDemographics(
  FirstName: String,
  MiddleName: Option[String] = None,
  LastName: String,
  DOB: DateTime,
  SSN: Option[String] = None,
  Sex: SexType.Value = SexType.Unknown,
  Address: Option[Address] = None,
  PhoneNumber: Option[PhoneNumber] = None,
  EmailAddresses: Seq[EmailAddress] = Seq.empty,
  Language: Option[Language] = None,
  Race: Option[RaceType.Value] = None,
  Ethnicity: Option[String] = None,
  Religion: Option[String] = None,
  MaritalStatus: Option[String] = None
) extends DemographicsLike
    with WithContactDetailObjects

object ClinicalSummaryDemographics extends RobustPrimitives

@jsonDefaults case class OtherDemographics(
  FirstName: Option[String],
  MiddleName: Option[String] = None,
  LastName: Option[String],
  DOB: Option[DateTime] = None,
  SSN: Option[String] = None,
  Sex: Option[SexType.Value] = None,
  Race: Option[RaceType.Value] = None,
  IsHispanic: Option[Boolean] = None,
  MaritalStatus: Option[String] = None,
  IsDeceased: Option[Boolean] = None,
  DeathDateTime: Option[DateTime] = None,
  PhoneNumber: Option[PhoneNumber] = None,
  EmailAddresses: Seq[String] = Seq.empty,
  Language: Option[Language] = None,
  Citizenship: Seq[String] = Seq.empty, // TODO ISO 3166
  Address: Option[Address] = None,
) extends DemographicsLike
    with WithContactDetails

object OtherDemographics extends RobustPrimitives

trait DemographicsLike {
  def MiddleName: Option[String]
  def SSN: Option[String]
  def Address: Option[Address]
  def PhoneNumber: Option[PhoneNumber]
  def Language: Option[Language]
  def Race: Option[RaceType.Value]
  def MaritalStatus: Option[String]
}

/**
 * @param RelationToPatient Personal relationship to the patient. e.x. Father, Spouse
 * @param Roles E.g. "Emergency contact"
 */
@jsonDefaults case class Contact(
  FirstName: Option[String] = None,
  LastName: Option[String] = None,
  Address: Option[Address] = None,
  PhoneNumber: Option[PhoneNumber] = None,
  EmailAddresses: Seq[String] = Seq.empty,
  RelationToPatient: Option[String] = None,
  Roles: Seq[String] = Seq.empty
) extends WithContactDetails

object Contact extends RobustPrimitives

/**
 * Patient
 */
@jsonDefaults case class ClinicalSummaryPatient(
  Identifiers: Seq[Identifier],
  Demographics: Option[ClinicalSummaryDemographics] = None,
) extends PatientLike

object ClinicalSummaryPatient extends RobustPrimitives

@jsonDefaults case class Patient(
  Identifiers: Seq[Identifier],
  Demographics: Option[OtherDemographics] = None,
  Notes: Seq[String] = Seq.empty,
  Contacts: Seq[Contact] = Seq.empty,
  Guarantor: Option[Guarantor] = None,
  Insurances: Seq[Insurance] = Seq.empty,
  Diagnoses: Seq[CodesetWithName] = Seq.empty,
  Allergies: Seq[Allergy] = Seq.empty,
  PCP: Option[Provider] = None
) extends PatientLike

object Patient extends RobustPrimitives

trait PatientLike {
  def Identifiers: Seq[Identifier]
}
