import Dependencies._

/* Project settings */
ThisBuild / name := "lamda"
ThisBuild / scalaVersion := "2.13.15"
ThisBuild / version := "1.0.1-SNAPSHOT"
ThisBuild / description := "Minimal functional programming library for Scala"
ThisBuild / licenses := List(("MIT", url("https://opensource.org/license/mit")))
ThisBuild / organization := "lamda"
ThisBuild / organizationName := "lamda"
ThisBuild / developers ++= List(
  Developer(
    id = "csgn",
    name = "Sergen Çepoğlu",
    email = "dev.csgn@gmail.com",
    url = url("https://github.com/csgn")
  )
)

/* Test settings */
ThisBuild / testFrameworks += new TestFramework("munit.Framework")

/* Scalafix settings */
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

/* scaladoc settings */
ThisBuild / autoAPIMappings := true
Compile / doc / scalacOptions ++= Seq(
  "-doc-title",
  (ThisBuild / name).value,
  "-project-version",
  (ThisBuild / version).value,
)

/* Publish settings */
ThisBuild / publishMavenStyle := true

/* gh-pages settings */
ThisBuild / git.remoteRepo := "git@github.com:csgn/lamda.git"

ThisBuild / scalacOptions ++= Seq(
  "-Wunused"
)

lazy val tests = project
  .in(file("tests"))
  .dependsOn(core)
  .settings(
    name := "tests",
    publish / skip := true,
    libraryDependencies ++= {
      Seq(
        munit % Test,
      )
    }
  )
  .enablePlugins(ScalafixPlugin)

lazy val core = project
  .in(file("core"))
  .settings(
    name := "core",
    moduleName := "lamda-core"
  )
  .enablePlugins(ScalafixPlugin, GhpagesPlugin, SiteScaladocPlugin)

lazy val lamda = project
  .in(file("."))
  .settings(
    name := "lamda",
  )
  .aggregate(core, tests)
