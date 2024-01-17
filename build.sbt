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

val playJsonVersion = "2.9.4"
val playLogbackVersion = "2.8.18"
val playSpecVersion = "2.8.18"
val playVersion = "2.8.18"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % playJsonVersion,
  "ai.x" %% "play-json-extensions" % "0.42.0",
  "com.typesafe.play" %% "play-logback" % playLogbackVersion,
  "com.typesafe.play" %% "play-json-joda" % playJsonVersion,
  "com.typesafe.play" %% "play-ahc-ws" % playVersion,
  "com.typesafe.play" %% "play-ws-standalone-json" % "2.1.10",
  "com.typesafe.akka" %% "akka-http" % "10.5.0",
  "com.github.vital-software" %% "json-annotation" % "0.6.3",
  "com.github.nscala-time" %% "nscala-time" % "2.32.0",
  "com.typesafe.play" %% "play-specs2" % playSpecVersion % Test,
)

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
publishTo := { Some("Cloudsmith API" at "https://maven.cloudsmith.io/carbon-health/scala-redox/") }

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

ThisBuild / scalacOptions ++= Seq("-unchecked", "-deprecation")

publishMavenStyle := true

pomIncludeRepository := { _ =>
  false
}
