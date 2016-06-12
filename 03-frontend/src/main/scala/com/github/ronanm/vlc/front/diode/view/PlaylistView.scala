package com.github.ronanm.vlc.front.diode.view

import com.github.ronanm.vlc.front.diode.{PlaySong, RefreshStatus}
import com.github.ronanm.vlc.shared.{VlcPlaylist, VlcStatus}
import diode.{Dispatcher, ModelR}

import scalatags.JsDom.all._

case class PlaylistView(plModel: ModelR[_, VlcPlaylist], dispatch: Dispatcher) {

  def html = {
    val pl = plModel.value

    div(cls := "panel panel-primary",
      div(cls := "panel-heading", "playlist"),
      div(cls := "panel-body",
        ol(
          pl.songs.map(s =>
            li(
              button(cls := "btn", "Play", onclick := { () => dispatch(PlaySong(s.id)) }),
              s"${s.name} (${s.id})")))))
  }
}

