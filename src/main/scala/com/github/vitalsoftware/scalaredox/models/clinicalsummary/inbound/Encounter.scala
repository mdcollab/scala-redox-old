package com.github.vitalsoftware.scalaredox.models.clinicalsummary.inbound

import com.github.vitalsoftware.macros.jsonDefaults
import com.github.vitalsoftware.util.JsonImplicits.jodaISO8601Format
import com.github.vitalsoftware.scalaredox.models.{ Address, BasicCode, Provider }
import com.github.vitalsoftware.util.RobustPrimitives
import org.joda.time.DateTime

/**
 * Patient identifier
 *
 * @param ID The actual identifier for the patient.
 * @param IDType An ID type associated with identifier (Medical Record Number, etc.)
 */
@jsonDefaults case class Identifier(
  ID: Option[String] = None,
  IDType: Option[String] = None
)

/**
 * Location of provider or care given.
 *
 * @see https://phinvads.cdc.gov/vads/ViewCodeSystem.action?id=2.16.840.1.113883.6.259
 * Note: Seems duplicative of CareLocation, but described using the generic 'Code' object
 */
@jsonDefaults case class Location(
  Address: Option[Address] = None,
  Type: BasicCode = BasicCode(),
  Name: Option[String] = None
)

object Location extends RobustPrimitives

/**
 *
 * @param Type A code describing the type of encounter (office visit, hospital, etc). CPT-4
 * @param DateTime When the encounter took place, or alternatively when the encounter began if Encounters[].EndDateTime is present. ISO 8601 Format
 * @param EndDateTime When the encounter was completed, if available. ISO 8601 Format
 * @param Providers Providers seen
 * @param Locations The type of location where the patient was seen (Clinic, Urgent Care, Hostpital).
 * @param Diagnosis List of Diagnoses associated with the visit. SNOMED CT
 * @param ReasonForVisit The reason for the visit (usually this is what the patient reports). SNOMED CT
 */
@jsonDefaults case class Encounter(
  Identifiers: Seq[Identifier] = Seq.empty,
  Type: BasicCode = BasicCode(),
  DateTime: Option[DateTime] = None,
  EndDateTime: Option[DateTime] = None,
  Providers: Seq[Provider] = Seq.empty,
  Locations: Seq[Location] = Seq.empty,
  Diagnosis: Seq[BasicCode] = Seq.empty,
  ReasonForVisit: Seq[BasicCode] = Seq.empty
)

object Encounter extends RobustPrimitives
