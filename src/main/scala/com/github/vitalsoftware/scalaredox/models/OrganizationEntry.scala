package com.github.vitalsoftware.scalaredox.models

import com.github.vitalsoftware.util.RobustPrimitives
import com.github.vitalsoftware.macros.jsonDefaults
import play.api.libs.json.{ Format, Reads, Writes }
import com.github.vitalsoftware.util.JsonImplicits.jodaISO8601Format

@jsonDefaults case class OrganizationEntry(
    Active: Boolean,
    Name: String,
    Aliases: Seq[String] = Seq.empty,
    Identifiers: Seq[Identifier] = Seq.empty,
    Type: Option[SimpleCode] = None,
    PartOf: Option[SimpleCodeWithType] = None,
    Contacts: Seq[String] = Seq.empty,
    Address: Option[Address] = None,
    Endpoints: Seq[String] = Seq.empty,
    DestinationID: Option[String] = None,
)
object OrganizationEntry extends RobustPrimitives

@jsonDefaults case class OrganizationAttributes(
    Transaction: Option[String] = None,
    Actor: Option[SimpleCode] = None,
    Version: Option[SimpleCode] = None,
    UseCases: Seq[SimpleCode] = Seq.empty,
    PurposeOfUse: Seq[SimpleCode] = Seq.empty,
    Roles: Seq[SimpleCode] = Seq.empty,
)
object OrganizationAttributes extends RobustPrimitives

@jsonDefaults case class OrganizationEndpoint(
    Name: String,
    Identifiers: Seq[SimpleCodeWithType] = Seq.empty,
    ConnectionType: Option[SimpleCode] = None,
    Address: Option[String] = None,
    MIMEType: Option[String] = None,
    Attributes: Option[OrganizationAttributes] = None,
)
object OrganizationEndpoint extends RobustPrimitives

@jsonDefaults case class OrganizationNameSearch(
    SearchType: Option[String], // exact | partOf
    Value: Option[String],
)
object OrganizationNameSearch extends RobustPrimitives

@jsonDefaults case class OrganizationRadiusSearch(
    ZipCode: Option[String],
    Radius: Option[String],
)
object OrganizationRadiusSearch extends RobustPrimitives

@jsonDefaults case class OrganizationPaging(
    Count: Option[Int],
    Index: Option[Int],
)
object OrganizationPaging extends RobustPrimitives
