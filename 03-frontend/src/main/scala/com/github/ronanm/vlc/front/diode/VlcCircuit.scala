package com.github.ronanm.vlc.front.diode

import autowire._
import diode.{ActionHandler, Circuit, Effect, RootModelRW}
import upickle.default._
import com.github.ronanm.vlc.front.comm.Clients._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

object VlcCircuit extends Circuit[RootModel] {

  override def initialModel = RootModel()

  private val rwPl = zoomRW(_.pl)((m, v) => m.copy(pl = v))
  private val rwStatus = zoomRW(_.vlcStatus)((m, v) => m.copy(vlcStatus = v))

  val statusHandler = new ActionHandler(rwStatus) {
    def handle = {
      case RefreshStatus => effectOnly(Effect(apiClient.vlcStatus().call().map(RefreshedStatus(_))))

      case RefreshedStatus(s) => updated(s)
    }
  }

  val rootHandler = new ActionHandler(new RootModelRW(initialModel)) {
    def handle = {
      case Log(s) =>
        println(s)
        noChange

      case Play => effectOnly(Effect(apiClient.play().call().map(plId => Log(s"Playing $plId !"))))
      case PlaySong(id) => effectOnly(Effect(apiClient.playSong(id).call().map(plId => Log(s"Playing $id"))))

      case Stop => effectOnly(Effect(apiClient.stop().call().map(_ => Log("Stoped !"))))
      case Next => effectOnly(Effect(apiClient.next().call().map(_ => Log("Nexted !"))))
      case Prev => effectOnly(Effect(apiClient.previous().call().map(_ => Log("Previsoused !"))))

      case Random => effectOnly(Effect(apiClient.randomToggle().call().map(_ => Log("Randomised !"))))

      case Seek(p) => effectOnly(Effect(apiClient.seek(p).call().map(_ => Log(s"Seeked $p % !"))))
    }
  }

  val plHandler = new ActionHandler(rwPl) {
    def handle = {
      case RefreshedPlaylist(pl) => updated(pl)
    }
  }

  override def actionHandler = foldHandlers(rootHandler, statusHandler, plHandler)
}