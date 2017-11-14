import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "brandonmott",
      scalaVersion := "2.12.3",
      version      := "0.0.1"
    )),
    name := "property-testing",
    libraryDependencies ++= Seq(
      "org.typelevel"  %% "cats" % cats,
      "org.scalatest"  %% "scalatest" % scalaTest % Test,
      "org.scalacheck" %% "scalacheck" % scalaCheck % "test",
      "brandonmott"    %% "sandbox" % "0.0.1" //TODO: Remove
    ),
    resolvers ++= Seq(
      "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
    )
  )

