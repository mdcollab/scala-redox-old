publishTo := {
  Some("Cloudsmith API" at "https://maven.cloudsmith.io/carbon-health/scala-redox/")
}
pomIncludeRepository := { x => false }

credentials += Credentials(Path.userHome / ".sbt" / ".credentials")