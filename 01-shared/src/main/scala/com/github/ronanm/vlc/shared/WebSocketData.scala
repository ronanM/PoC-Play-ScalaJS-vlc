package com.github.ronanm.vlc.shared

// Les données qui remontent vers le front end.
sealed trait WebSocketData

case object Init extends WebSocketData

case object AskStatus extends WebSocketData

case class Status(vlcStatus: VlcStatus) extends WebSocketData

object WebSocketData {
  import upickle.default._

  def apply(s: String): WebSocketData = read[WebSocketData](s)

  def toStr(wsd: WebSocketData): String = write[WebSocketData](wsd)
}

