package com.github.vitalsoftware.scalaredox.models.clinicalsummary.inbound

import com.github.vitalsoftware.macros.jsonDefaults
import com.github.vitalsoftware.scalaredox.models.{ BasicCode, BasicPerson, Code }
import com.github.vitalsoftware.util.RobustPrimitives

/**
 * Advance directive documents that the healthcare organization has on file for the patient.
 *
 * @param Type The value of the advance directive (such as 'Do not resuscitate'). SNOMED CT
 * @param StartDate Effective start date of the advance directive. ISO 8601 Format
 * @param EndDate Effective end date of the advance directive. ISO 8601 Format
 * @param ExternalReference A link to a location where the document can be accessed.
 * @param VerifiedBy A collection of people who verified the advance directive with the patient
 * @param Custodians People legally responsible for the advance directive document.
 */
@jsonDefaults case class AdvanceDirective(
  Type: BasicCode = BasicCode(),
  Code: Option[String] = None, // Todo Question: Seems to duplicate 'Type' field
  CodeSystem: Option[String] = None,
  CodeSystemName: Option[String] = None,
  Name: Option[String] = None,
  StartDate: Option[String] = None,
  EndDate: Option[String],
  ExternalReference: Option[String],
  VerifiedBy: Seq[BasicPerson] = Seq.empty, // Todo missing VerifiedBy.DateTime
  Custodians: Seq[BasicPerson] = Seq.empty
) extends Code

object AdvanceDirective extends RobustPrimitives

@jsonDefaults case class AdvanceDirectiveMessage(
  AdvanceDirectivesText: Option[String] = None,
  advanceDirectives: Seq[AdvanceDirective] = Seq.empty
)

object AdvanceDirectiveMessage extends RobustPrimitives
