package com.github.ronanm.vlc.back

import play.api.ApplicationLoader.Context
import play.api._
import play.api.routing.Router
import com.softwaremill.macwire._
import com.github.ronanm.vlc.back.modules.{ControllerModule, ServiceModule}
import play.api.libs.ws.ahc.AhcWSComponents
import com.github.ronanm.vlc.back.services.ApiImpl
import _root_.controllers.Assets
import com.github.ronanm.vlc.shared.Api
import router.Routes
import scala.concurrent.ExecutionContext

class VlcApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {

    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment)
    }

    new VlcAppComponents(context).application
  }
}

class VlcAppComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with AhcWSComponents
  with ServiceModule
  with ControllerModule {

  lazy val assets: Assets = wire[Assets]
  lazy val prefix: String = "/"
  override lazy val router: Router = wire[Routes]

  override lazy val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  override lazy val api: Api = wire[ApiImpl]
}


