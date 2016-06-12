package com.github.ronanm.vlc.front.diode.view

import com.github.ronanm.vlc.front.diode.{Next, Random, Seek, _}
import diode.{Dispatcher, ModelR}

import scalatags.JsDom.all._

case class VlcMainView(rootModel: ModelR[_, RootModel], statusView: StatusView, playlistView: PlaylistView, dispatch: Dispatcher) {

  def html = {

    div(cls := "panel panel-primary",
      div(cls := "panel-heading", "Player"),
      div(cls := "panel-body",

        statusView.html,

        div(cls := "btn-group",
          button(cls := "btn btn-success", "Play", onclick := { () => dispatch(Play) }),
          button(cls := "btn btn-danger", "Stop", onclick := { () => dispatch(Stop) }),
          button(cls := "btn btn-default", "<< Prev", onclick := { () => dispatch(Prev) }),
          button(cls := "btn btn-default", "Next >>", onclick := { () => dispatch(Next) }),
          button(cls := "btn btn-default", "25%", onclick := { () => dispatch(Seek(25)) }),
          button(cls := "btn btn-default", "50%", onclick := { () => dispatch(Seek(50)) }),
          button(cls := "btn btn-default", "75%", onclick := { () => dispatch(Seek(75)) }),
          button(cls := "btn btn-default", "Random", onclick := { () => dispatch(Random) })),

        playlistView.html))
  }
}

