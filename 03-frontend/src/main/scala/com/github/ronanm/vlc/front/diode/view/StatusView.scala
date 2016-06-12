package com.github.ronanm.vlc.front.diode.view

import com.github.ronanm.vlc.front.diode.RefreshStatus
import com.github.ronanm.vlc.shared.VlcStatus
import diode.{Dispatcher, ModelR}

import scalatags.JsDom.all._

case class StatusView(statusModel: ModelR[_, VlcStatus], dispatch: Dispatcher) {
  def html = {
    val status = statusModel.value

    val playing = status.currentPlayingID.map(id => s"Current playing playlist id: $id").getOrElse("Not playing")

    div(cls := "panel panel-info",
      div(cls := "panel-heading", "Status"),
      div(cls := "panel-body",

        button(cls := "btn", "Refresh", onclick := { () => dispatch(RefreshStatus) }),

        ul(
          li(playing))))
  }
}

