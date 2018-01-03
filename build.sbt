name := "functional_domain_model"

lazy val buildSettings = Seq(
  version := "0.0.1",
  scalaVersion := "2.11.7"
)

lazy val dependencySettings = Seq(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val allSettings = buildSettings ++ dependencySettings

//Modules
lazy val core = (project in file("core"))
  .settings(buildSettings)

lazy val domain = (project in file("domain"))
  .settings(allSettings)
  .dependsOn(core)

lazy val root = Project("functional_domain_model", file("."))
  .aggregate(core, domain)
