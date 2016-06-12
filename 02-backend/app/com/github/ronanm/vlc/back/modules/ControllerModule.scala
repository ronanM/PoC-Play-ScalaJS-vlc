package com.github.ronanm.vlc.back.modules

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.softwaremill.macwire._
import com.github.ronanm.vlc.back.controllers.{ApiCtrl, VlcCtrl, WebsocketCtrl}
import com.github.ronanm.vlc.shared.Api
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext

trait ControllerModule {

  // Dependencies
  implicit def executionContext: ExecutionContext

  implicit def actorSystem: ActorSystem

  implicit def materializer: Materializer

  def wsClient: WSClient

  def api: Api

  // Controllers
  lazy val apiCtrl = wire[ApiCtrl]
  lazy val vlcCtrl = wire[VlcCtrl]
  lazy val websocketCtrl = wire[WebsocketCtrl]
}
