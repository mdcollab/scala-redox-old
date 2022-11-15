package com.github.vitalsoftware.scalaredox.models

import com.github.vitalsoftware.macros._
import com.github.vitalsoftware.util.RobustPrimitives

/**
 * The organization or group responsible for the ongoing maintenance and access to the document
 *
 * @param Identifiers List of IDs specific to this custodian organization
 * @param Name The name of the custodian organization
 * @param Type A code describing the kind of custodian organization
 * @param Address The address information for the custodian organization
 * @param Telecom Phone, fax, email, or other communication numbers for the custodian organization
 */
@jsonDefaults case class Custodian(
  Identifiers: Seq[Identifier] = Seq.empty,
  Name: Option[String] = None,
  Type: BasicCode = BasicCode(),
  Address: Option[Address] = None,
  Telecom: Seq[Telecom] = Seq.empty,
) extends CustodianLike

object Custodian extends RobustPrimitives

trait CustodianLike {
  def Identifiers: Seq[Identifier]
  def Name: Option[String]
  def Type: BasicCode
  def Address: Option[Address]
  def Telecom: Seq[Telecom]
}
