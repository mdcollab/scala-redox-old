package com.github.vitalsoftware.scalaredox.models.clinicalsummary

import com.github.vitalsoftware.scalaredox.models.Meta
import com.github.vitalsoftware.macros.jsonDefaults
import com.github.vitalsoftware.util.RobustPrimitives

/**
 * Query an organization for patient documents given the patient's Identifier(s)
 * Meta.DataModel: "ClinicalSummary"
 * Meta.EventType: "DocumentQuery"
 */
@jsonDefaults case class DocumentQuery(
  Meta: Meta,
  Patient: Option[Patient] = None,
)
object DocumentQuery extends RobustPrimitives

/**
 * Response to DocumentQuery includes document Identifiers for the patient
 * Meta.DataModel: "ClinicalSummary"
 * Meta.EventType: "DocumentQuery"
 */
@jsonDefaults case class DocumentQueryResponse(
  Meta: Meta,
  Patient: Option[Patient] = None,
  Documents: Seq[Document] = Seq.empty,
)

object DocumentQueryResponse extends RobustPrimitives
