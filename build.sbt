name := "functional_domain_model"

lazy val buildSettings = Seq(
  version := "0.0.1",
  scalaVersion := "2.12.2"
)

lazy val dependencySettings = Seq(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val allSettings = buildSettings ++ dependencySettings

lazy val root = Project("functional_domain_model", file("."))
  .settings(allSettings)
