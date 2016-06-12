import Settings.versions
import Settings.versions._
import sbt.Keys._
import sbt.Project.projectToRef

organization := "com.github.ronanm"
name := "poc-play-scalajs-vlc"
version := "1.0-SNAPSHOT"


lazy val shared =
  (crossProject.crossType(CrossType.Pure) in file("01-shared"))
  .settings(
    scalaVersion := scalaV,
    libraryDependencies ++= Seq(
      //      "com.github.japgolly.scalacss" %%% "ext-scalatags" % scalacssV,
      "com.github.japgolly.scalacss" %%% "core" % scalacssV
    ) ++ Settings.sharedDependencies.value
  )
  .jsConfigure(_ enablePlugins ScalaJSPlay)


lazy val backend =
  (project in file("02-backend"))
  .settings(
    scalaVersion := scalaV,
    scalaJSProjects := clients,
    target := new File("/tmp/vlc-web/backend/target"), // pour preserver la durée de vie de mon SSD, /tmp est en RAM (tmpfs) ;-).
    pipelineStages := Seq(scalaJSProd, gzip),
    scalacOptions ++= Seq("-deprecation", "-Xlint", "-feature"),
    resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    libraryDependencies ++= Seq(
      jdbc,
      cache,
      ws,
      "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
      "com.vmunier" %%% "play-scalajs-scripts" % "0.3.0",
      "com.github.pathikrit" %% "better-files" % betterFilesV,
      "com.github.japgolly.scalacss" %% "ext-scalatags" % scalacssV,
      "com.github.japgolly.scalacss" %% "core" % scalacssV,
      "com.lihaoyi" %% "scalatags" % scalatagsV,
      "org.webjars" %% "webjars-play" % webjarPlayV,
      "org.webjars" % "bootstrap" % bootstrapV,
      "com.softwaremill.macwire" %% "macros" % macwireV % "provided",
      "com.softwaremill.macwire" %% "util" % macwireV
    )
  )
  .enablePlugins(PlayScala)
  .aggregate(clients.map(projectToRef): _*)
  .dependsOn(sharedJvm)


lazy val frontend =
  (project in file("03-frontend"))
  .settings(
    scalaVersion := scalaV,
    target := new File("/tmp/vlc-web/frontend/target"), // pour preserver la durée de vie de mon SSD, /tmp est en RAM (tmpfs) ;-).
    persistLauncher := true,
    persistLauncher in Test := false,
    scalacOptions ++= Seq("-deprecation", "-Xlint"),
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.0",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
      "com.lihaoyi" %%% "scalatags" % scalatagsV,
      "me.chrons" %%% "diode" % diodeV,
      "me.chrons" %%% "diode-devtools" % diodeV,
      "com.github.japgolly.scalacss" %%% "ext-scalatags" % scalacssV,
      "com.github.japgolly.scalacss" %%% "core" % scalacssV,
      "org.typelevel" %%% "cats" % catsV
    ),
    jsDependencies += "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js",
    jsDependencies += RuntimeDOM,

    // uTest settings
    libraryDependencies += "com.lihaoyi" %%% "utest" % "0.3.0" % "test",
    testFrameworks += new TestFramework("utest.runner.Framework")
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
  .dependsOn(sharedJs)

lazy val clients = Seq(frontend)
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

// Charge le projet Play au demarrage de SBT.
onLoad in Global := (Command.process("project backend", _: State)) compose (onLoad in Global).value
