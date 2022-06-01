package com.github.vitalsoftware.scalaredox.models.clinicalsummary

import com.github.vitalsoftware.scalaredox.models.Meta
import com.github.vitalsoftware.macros.jsonDefaults
import com.github.vitalsoftware.util.RobustPrimitives

/**
 * Get a single document for a patient from an organization
 * Meta.DataModel: "ClinicalSummary"
 * Meta.EventType: "DocumentGet"
 */
@jsonDefaults case class DocumentGet(
  Meta: Meta,
  Document: Option[Document] = None,
)
object DocumentGet extends RobustPrimitives

/**
 * Response for a DocumentGet request includes the document ID and the XML data
 * Meta.DataModel: "ClinicalSummary"
 * Meta.EventType: "DocumentGet"
 */
@jsonDefaults case class DocumentGetResponse(
  Meta: Meta,
  Document: Option[Document] = None, 
  Data: String,
)
object DocumentGetResponse extends RobustPrimitives
