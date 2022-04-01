package com.github.vitalsoftware.scalaredox.models

@jsonDefaults case class OrganizationEntry(
    Active: Boolean,
    Name: String,
    Aliases: Seq[String] = Seq.empty,
    Identifiers: Seq[Identifier] = Seq.empty,
    Type: Option[SimpleCode] = None,
    PartOf: Option[SimpleCodeWithType] = None,
    Contacts: Seq[String] = None,
    Address: Option[Address] = None,
    Endpoints: Seq[String] = Seq.empty,
    DestinationID: Option[String] = None,
)
object OrganizationEntry extends RobustPrimitives

@jsonDefaults case class OrganizationEndpoint(
    Name: String,
    Identifiers: Seq[SimpleCodeWithType] = Seq.empty,
    ConnectionType: Option[SimpleCode] = None,
    Address: Option[String] = None,
    MIMEType: Option[String] = None,
    Attributes: Option[OrganizationAttributes] = None,
)
object OrganizationEndpoint extends RobustPrimitives

@jsonDefaults case class OrganizationAttributes(
    Transaction: Option[String] = None,
    Actor: Option[SimpleCode] = None,
    Version: Option[SimpleCode] = None,
    UseCases: Seq[SimpleCode] = Seq.empty,
    PurposeOfUse: Seq[SimpleCode] = Seq.empty,
    Roles: Seq[SimpleCode] = Seq.empty,
)
object OrganizationAttributes extends RobustPrimitives

@jsonDefaults case class OrganizationNameSearch(
    SearchType: Option[String], // exact | partOf
    Value: Option[String],
)

@jsonDefaults case class OrganizationRadiusSearch(
    ZipCode: Option[String],
    Radius: Option[String],
)

@jsonDefaults case class OrganizationPaging(
    Count: Option[Int],
    Index: Option[Int],
)
