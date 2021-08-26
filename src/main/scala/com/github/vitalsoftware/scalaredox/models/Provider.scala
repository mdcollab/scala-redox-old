package com.github.vitalsoftware.scalaredox.models

import com.github.vitalsoftware.macros._
import com.github.vitalsoftware.util.RobustPrimitives

import scala.collection.Seq

/**
 * Provider responsible for a document
 *
 * @param ID ID of the Provider responsible for the document. This ID is required for Inpatient Visits
 * @param IDType ID type of the ID for the Provider responsible for the document
 * @param FirstName First name of the Provider responsible for the document
 * @param LastName Last name of the Provider responsible for the document
 * @param Credentials List of credentials for the Provider responsible for the document. e.g. MD, PhD
 * @param Address Provider's address
 * @param EmailAddresses Provider's email address(es)
 * @param PhoneNumber Provider's office phone number. In E. 164 Format. (e.g. +16085551234)
 * @param Type The type of provider for this referral. One of the following: "Referring Provider", "Referred To Provider", "Other", "Patient PCP"
 * @param Location Provider's location
 */
@jsonDefaults case class Provider(
  ID: Option[String] = None,
  IDType: Option[String] = None,
  FirstName: Option[String] = None,
  LastName: Option[String] = None,
  Credentials: Seq[String] = Seq.empty,
  Address: Option[Address] = None,
  EmailAddresses: Seq[String] = Seq.empty,
  PhoneNumber: Option[PhoneNumber] = None,
  Type: Option[String] = None,
  Location: Option[CareLocation] = None,
) extends ProviderLike

object Provider extends RobustPrimitives

trait ProviderLike {
  def ID: Option[String]
  def IDType: Option[String]
  def FirstName: Option[String]
  def LastName: Option[String]
  def Type: Option[String]
  def Credentials: Seq[String]
  def Address: Option[Address]
  def Location: Option[CareLocation]
}

trait WithAddress { def Address: Option[Address] }
trait WithPhoneNumber { def PhoneNumber: Option[PhoneNumber] }
trait WithEmails { def EmailAddresses: Seq[String] }
trait WithContactDetails extends WithAddress with WithPhoneNumber with WithEmails

@jsonDefaults case class BasicPerson(
  FirstName: Option[String] = None,
  LastName: Option[String] = None,
  Address: Option[Address] = None,
  PhoneNumber: Option[PhoneNumber] = None,
  EmailAddresses: Seq[String] = Seq.empty,
  Credentials: Option[String] = None
) extends WithContactDetails

object BasicPerson extends RobustPrimitives
