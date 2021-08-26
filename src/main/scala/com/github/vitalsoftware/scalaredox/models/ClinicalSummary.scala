package com.github.vitalsoftware.scalaredox.models

import com.github.vitalsoftware.macros._
import com.github.vitalsoftware.util.RobustPrimitives
import org.joda.time.DateTime
import play.api.libs.json.JodaWrites._
import play.api.libs.json.JodaReads._

import scala.collection.Seq

/**
 * //////////////////////////////////////////////////////////
 *              CLINICAL SUMMARY TRAITS
 * //////////////////////////////////////////////////////////
 */
sealed trait ClinicalSummaryLike {
  def Meta: Meta
}

sealed trait HasClinicalSummaryPatient {
  def Patient: ClinicalSummaryPatient
}

/**
 * //////////////////////////////////////////////////////////
 *              CLINICAL SUMMARY FIELDS
 * //////////////////////////////////////////////////////////
 */
/**
 * About a clinical summary demographics.
 *
 * @param FirstName Required
 * @param LastName Required
 * @param DOB Partially reliable. Patient's date of birth. In ISO 8601 format
 * @param SSN Patient SSN.
 * @param Sex Required
 * @param Address Patient's addresses.
 * @param PhoneNumber Patient's phone numbers.
 * @param EmailAddresses Patient's email addresses.
 * @param Language Patient's primary spoken language. In ISO 639-1 alpha values (e.g. 'en'). http://www.mathguide.de/info/tools/languagecode.html
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
)

object ClinicalSummaryDemographics extends RobustPrimitives

@jsonDefaults case class ClinicalSummaryPatient(
  Identifiers: Seq[Identifier],
  Demographics: Option[ClinicalSummaryDemographics] = None,
)

object ClinicalSummaryPatient extends RobustPrimitives

/**
 * About a clinical summary document
 *
 * @param ID Your application's ID for the document
 * @param Author Provider responsible for this document
 * @param Visit If the document is tied to a visit
 * @param Locale The language of the document.
 * @param Title The title of the document.
 * @param DateTime The creation/publishing date/time of the document.
 * @param Type The type of document (CCD, progress note, etc.)
 * @param TypeCode A code describing the type of document.
 */
@jsonDefaults case class ClinicalSummaryDocument(
  Author: Option[Provider] = None,
  ID: Option[String] = None,
  Locale: Option[String] = None,
  Title: Option[String] = None,
  DateTime: Option[DateTime] = None,
  Type: Option[String] = None,
  TypeCode: Option[BasicCode] = None,
  Visit: Option[VisitInfo] = None,
) extends HasVisitInfo

object ClinicalSummaryDocument extends RobustPrimitives

/**
 * Information about the clinical summary's patient and where the summary came from
 *
 * @param DirectAddressFrom The sender's Direct address, if one or both sides are using Direct messaging.
 * @param DirectAddressTo The recipient's Direct address, if one or both sides are using Direct messaging.
 * @param Document An object containing metadata about the document being pushed to the destination.
 * @param Patient Patient
 * @param PCP Provider
 */
@jsonDefaults case class ClinicalSummaryHeader(
  DirectAddressFrom: Option[String] = None,
  DirectAddressTo: Option[String] = None,
  Document: ClinicalSummaryDocument,
  Patient: ClinicalSummaryPatient,
  PCP: Provider,
) extends HasClinicalSummaryPatient

object ClinicalSummaryHeader extends RobustPrimitives

/**
 * //////////////////////////////////////////////////////////
 *              CLINICAL SUMMARY MODELS
 *
 *    A Clinical Summary represents a snapshot of the patient's chart at a moment in time. It is structured in sections,
 * each focusing on a different aspect of the patient's chart, such as allergies, immunizations, and medications.
 * The full list of sections is at the left.
 *
 *    You can obtain a Clinical Summary from an EHR via Query. You can send a Clinical Summary to an EHR via Push.
 * //////////////////////////////////////////////////////////
 */
/**
 * PATIENT QUERY MODEL
 * @param Meta Message header
 * @param Patient List of IDs and IDTypes for the patient
 * @param Location ID corresponding to the department from which to retrieved the patient chart.
 */
@jsonDefaults case class PatientQuery(
  Meta: Meta,
  Patient: ClinicalSummaryPatient,
  Location: Option[Department] = None,
) extends ClinicalSummaryLike
    with HasClinicalSummaryPatient

object PatientQuery extends RobustPrimitives

/**
 * PATIENT QUERY RESPONSE MODEL
 *
 * @param Meta Request/response clinical summary header meta-data
 * @param Header Message header
 * @param AdvanceDirectives advance directives
 * @param Allergies A code for the type of allergy intolerance this is (food, drug, etc.). [Allergy/Adverse Event Type Value Set](http://phinvads.cdc.gov/vads/ViewValueSet.action?id=7AFDBFB5-A277-DE11-9B52-0015173D1785)
 * @param Encounters A code describing the type of encounter (office visit, hospital, etc). CPT-4
 * @param FamilyHistory Each element of the FamilyHistory is one person in the patient's family.
 * @param Insurances List of insurance coverages for the patient
 * @param MedicalEquipment A list of medical equipment that the patient uses (cane, pacemakers, etc.)
 * @param Medications Array of medications
 * @param PlanOfCare Free text form of the plan of care summary
 * @param Problems An array of all of patient relevant problems, current and historical.
 * @param Procedures A general grouper for all things that CDA considers procedures, grouped into three kinds.
                    -Observations - procedures which result in new information about a patient.
                    -Procedures - procedures whose immediate and primary outcome is the alteration of the physical condition of the patient.
                    -Services (Sometimes called Acts) - procedures which cannot be classified as an observation or a procedure, such as a dressing change, feeding, or teaching.
 * @param Results Array of test results for the patient. This can include laboratory results, imaging results, and procedure Results[].
 * @param SocialHistory Generic observations about the patient's social history
 * @param VitalSigns An array of groups of vital signs. Each element represents one time period in which vitals were recorded.
 */
@jsonDefaults case class PatientQueryResponse(
  Meta: Meta,
  Header: ClinicalSummaryHeader,
  AdvanceDirectives: Seq[AdvanceDirective] = Seq.empty,
  Allergies: Seq[Allergy] = Seq.empty,
  Encounters: Seq[Encounter] = Seq.empty,
  FamilyHistory: Seq[FamilyHistory] = Seq.empty,
  Immunizations: Seq[Immunization] = Seq.empty,
  Insurances: Seq[Insurance] = Seq.empty,
  MedicalEquipment: Seq[MedicalEquipment] = Seq.empty,
  Medications: Seq[MedicationTaken] = Seq.empty,
  PlanOfCare: Option[PlanOfCare] = None,
  Problems: Seq[Problem] = Seq.empty,
  Procedures: Option[Procedures] = None,
  Results: Seq[ChartResult] = Seq.empty,
  SocialHistory: Option[SocialHistory] = None,
  VitalSigns: Seq[VitalSigns] = Seq.empty
) extends ClinicalSummaryLike

object PatientQueryResponse extends RobustPrimitives

/**
 * PATIENT PUSH MODEL
 *
 * @param Meta Request/response clinical summary header meta-data
 * @param Header Message header
 * @param AdvanceDirectives advance directives
 * @param Allergies A code for the type of allergy intolerance this is (food, drug, etc.). [Allergy/Adverse Event Type Value Set](http://phinvads.cdc.gov/vads/ViewValueSet.action?id=7AFDBFB5-A277-DE11-9B52-0015173D1785)
 * @param Encounters A code describing the type of encounter (office visit, hospital, etc). CPT-4
 * @param FamilyHistory Each element of the FamilyHistory is one person in the patient's family.
 * @param Insurances List of insurance coverages for the patient
 * @param MedicalEquipment A list of medical equipment that the patient uses (cane, pacemakers, etc.)
 * @param Medications Array of medications
 * @param PlanOfCare Free text form of the plan of care summary
 * @param Problems An array of all of patient relevant problems, current and historical.
 * @param Procedures A general grouper for all things that CDA considers procedures, grouped into three kinds.
                    -Observations - procedures which result in new information about a patient.
                    -Procedures - procedures whose immediate and primary outcome is the alteration of the physical condition of the patient.
                    -Services (Sometimes called Acts) - procedures which cannot be classified as an observation or a procedure, such as a dressing change, feeding, or teaching.
 * @param Results Array of test results for the patient. This can include laboratory results, imaging results, and procedure Results[].
 * @param SocialHistory Generic observations about the patient's social history
 * @param VitalSigns An array of groups of vital signs. Each element represents one time period in which vitals were recorded.
 */
@jsonDefaults case class PatientPush(
  Meta: Meta,
  Header: ClinicalSummaryHeader,
  AdvanceDirectives: Seq[AdvanceDirective] = Seq.empty,
  Allergies: Seq[Allergy] = Seq.empty,
  Encounters: Seq[Encounter] = Seq.empty,
  FamilyHistory: Seq[FamilyHistory] = Seq.empty,
  Immunizations: Seq[Immunization] = Seq.empty,
  Insurances: Seq[Insurance] = Seq.empty,
  MedicalEquipment: Seq[MedicalEquipment] = Seq.empty,
  Medications: Seq[MedicationTaken] = Seq.empty,
  PlanOfCare: Option[PlanOfCare] = None,
  Problems: Seq[Problem] = Seq.empty,
  Procedures: Option[Procedures] = None,
  Results: Seq[ChartResult] = Seq.empty,
  SocialHistory: Option[SocialHistory] = None,
  VitalSigns: Seq[VitalSigns] = Seq.empty
) extends ClinicalSummaryLike

object PatientPush extends RobustPrimitives
