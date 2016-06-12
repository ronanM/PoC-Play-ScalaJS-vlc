package com.github.ronanm.vlc.back.controllers

import java.nio.ByteBuffer

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.Materializer
import play.api.libs.streams._
import play.api.libs.ws.WSClient
import play.api.mvc._
import com.github.ronanm.vlc.shared._

import scala.concurrent.duration._
import akka.pattern.pipe
import com.github.ronanm.vlc.shared
import com.github.ronanm.vlc.shared.WebSocketData.toStr

import scala.concurrent.ExecutionContext.Implicits.global

class WebsocketCtrl(serviceAPI: Api)(implicit system: ActorSystem, materializer: Materializer) {

  def socket: WebSocket = WebSocket.accept[String, String] { request =>
    println("Websocket accepting !!!!" + toStr(Status(VlcStatus(Some(42)))))

    ActorFlow.actorRef(out => MyWebSocketActor.props(out, serviceAPI))
  }
}

import akka.actor._

object MyWebSocketActor {
  def props(out: ActorRef, serviceAPI: Api) = Props(new MyWebSocketActor(out, serviceAPI))
}

class MyWebSocketActor(out: ActorRef, serviceAPI: Api) extends Actor {

  val statusRefresh = context.system.scheduler.schedule(5.seconds, 5.seconds, self, toStr(AskStatus))

  def receive = {
    case s: String =>
      WebSocketData(s) match {
        case AskStatus => serviceAPI.vlcStatus().map(s => toStr(shared.Status(s))) pipeTo out

        case msg =>
          println(s"message recu sur la websocket: $msg")
          out ! toStr(Init)
      }
  }
}
