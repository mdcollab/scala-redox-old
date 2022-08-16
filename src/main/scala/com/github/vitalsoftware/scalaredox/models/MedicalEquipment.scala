package com.github.vitalsoftware.scalaredox.models

import org.joda.time.DateTime
import com.github.vitalsoftware.util.JsonImplicits.jodaISO8601Format
import com.github.vitalsoftware.macros._
import com.github.vitalsoftware.util.RobustPrimitives
import play.api.libs.json.{Json, OFormat}

/**
 * Piece of medical equipment.
 *
 * @param Status The current status of the equipment (active, completed, etc.)
 * @param StartDate When the equipment was first put into use. ISO 8601 Format
 * @param Quantity The number of products used
 * @param Product A code representing the actual product. SNOMED CT
 */
@jsonDefaults case class MedicalEquipment(
  Status: String,
  StartDate: Option[DateTime] = None,
  Quantity: Option[String] = None, // Todo Int?
  Product: BasicCode = BasicCode()
)

object MedicalEquipment extends RobustPrimitives{
  implicit val format: OFormat[MedicalEquipment] =Json.format
}

/**
 * This section lists any medical equipment that the patient uses or has been prescribed.
 * @param MedicalEquipmentText Free text form of the medical equipment summary
 * @param MedicalEquipment A list of medical equipment that the patient uses (cane, pacemakers, etc.)
 */
@jsonDefaults case class MedicalEquipmentMessage(
  MedicalEquipmentText: Option[String] = None,
  MedicalEquipment: Seq[MedicalEquipment] = Seq.empty
)

object MedicalEquipmentMessage extends RobustPrimitives
