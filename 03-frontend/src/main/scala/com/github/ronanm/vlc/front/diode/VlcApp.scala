package com.github.ronanm.vlc.front.diode

import java.nio.ByteBuffer

import autowire._
import upickle.default._
import upickle._
import com.github.ronanm.vlc.front.comm.Clients.apiClient
import com.github.ronanm.vlc.front.diode.view.{PlaylistView, StatusView, VlcMainView}
import com.github.ronanm.vlc.shared.WebSocketData.toStr
import com.github.ronanm.vlc.shared.{AskStatus, Status, WebSocketData}
import org.scalajs.dom.raw.{CloseEvent, ErrorEvent, Event, WebSocket, Element => _, MessageEvent => _, _}
import org.scalajs.dom.{MessageEvent, document, _}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import scala.util.{Failure, Success}
import scalacss.Defaults._
import scalacss.ScalatagsCss._
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._

object VlcApp extends JSApp {

  val statusView = StatusView(VlcCircuit.zoom(_.vlcStatus), VlcCircuit)
  val playlistView = PlaylistView(VlcCircuit.zoom(_.pl), VlcCircuit)
  val mainView = VlcMainView(VlcCircuit.zoom(identity), statusView, playlistView, VlcCircuit)

  @JSExport
  override def main(): Unit = {
    println(toStr(AskStatus))

    val rootAppNode = document.getElementById("app-placeholder")

    // subscribe to changes in the application model and call render when anything changes
    VlcCircuit.subscribe(VlcCircuit.zoom(identity))(_ => render(rootAppNode))

    // start the application by dispatching a Reset action
    VlcCircuit(Reset)

    //  Websocket.
    val ws = new WebSocket("ws://127.0.0.1:9000/websocket")
    ws.onmessage = { e: MessageEvent =>
      val wsd: WebSocketData = WebSocketData(e.data.toString)

      rootAppNode.appendChild(document.createTextNode(s"Websocket: $wsd"))

      wsd match {
        case Status(vlcStatus) => VlcCircuit(RefreshedStatus(vlcStatus))
      }
    }
    ws.onopen = { e: Event =>
      ws.send(toStr(AskStatus))
    }
    ws.onerror = (e: ErrorEvent) => println(s"WS Error: $e.message, $e")
    ws.onclose = (e: CloseEvent) => println(s"WS Close: $e")


    // Api call.
    apiClient.stop().call()

    apiClient.playlist().call()
    .onComplete {
      case Success(pl) => VlcCircuit(RefreshedPlaylist(pl))
    }
  }

  def render(rootNode: Element) = {
    rootNode.innerHTML = ""
    rootNode.appendChild(mainView.html.render)
  }
}
