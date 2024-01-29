import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

organization := "com.github.mdcollab"

name := "scala-redox"

scalaVersion := "2.13.12"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  Resolver.jcenterRepo,
  "Typesafe Repo" at "https://repo.typesafe.com/typesafe/releases/",
  "Typesafe Repo" at "https://repo.typesafe.com/typesafe/releases/",
  "Atlassian Releases" at "https://maven.atlassian.com/public/",
)

val playJsonVersion = "2.8.0"
val playLogbackVersion = "2.8.16"
val playSpecVersion = "2.7.3"
val playVersion = "2.8.16"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % playJsonVersion,
  "com.carbonhealth.ai.x" %% "play-json-extensions" % "1.0.0",
  "com.typesafe.play" %% "play-logback" % playLogbackVersion,
  "com.typesafe.play" %% "play-json-joda" % playJsonVersion,
  "com.typesafe.play" %% "play-ahc-ws" % playVersion,
  "com.typesafe.play" %% "play-ws-standalone-json" % "2.0.5",
  "com.typesafe.akka" %% "akka-http" % "10.1.15",
  "com.github.vital-software" %% "json-annotation" % "0.6.3",
  "com.github.nscala-time" %% "nscala-time" % "2.22.0",
  "com.typesafe.play" %% "play-specs2" % playSpecVersion % Test,
)

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
publishTo := { Some("Cloudsmith API" at "https://maven.cloudsmith.io/carbon-health/scala-redox/") }

ThisBuild / scalacOptions ++= Seq("-unchecked", "-deprecation", "-Ymacro-annotations")

publishMavenStyle := true

pomIncludeRepository := { _ =>
  false
}
