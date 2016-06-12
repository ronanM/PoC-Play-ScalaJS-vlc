import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Settings {

  object versions {

    val scalaV = "2.11.8"

    val scalatagsV = "0.5.5"
    val scalacssV = "0.4.1"
    val betterFilesV = "2.16.0"

    val autowireV = "0.2.5"
    val upickleV = "0.4.1"

    val diodeV = "0.5.2"

    val webjarPlayV = "2.5.0"
    val bootstrapV = "3.3.4"

    val catsV = "0.5.0"

    val macwireV = "2.2.0"
  }

  /**
    * These dependencies are shared between JS and JVM projects
    * the special %%% function selects the correct version for each project
    */
  val sharedDependencies = Def.setting(Seq(
    "com.lihaoyi" %%% "autowire" % versions.autowireV,
    "com.lihaoyi" %%% "upickle" % versions.upickleV))
}