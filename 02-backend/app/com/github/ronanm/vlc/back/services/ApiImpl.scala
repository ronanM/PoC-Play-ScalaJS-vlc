package com.github.ronanm.vlc.back.services

import java.net.URI

import play.api.libs.ws.{WSAuthScheme, WSClient, WSRequest}
import com.github.ronanm.vlc.shared.{Api, VlcSong, VlcPlaylist, VlcStatus}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try
import scala.xml.Elem

// cf. https://wiki.videolan.org/VLC_HTTP_requests/

class ApiImpl(ws: WSClient) extends Api {

  val port = 5986
  val pass = "toto"

  // Infos.
  override def vlcStatus(): Future[VlcStatus] =
    xml("status.xml")
    .map(xml => VlcStatus(
      currentPlayingID = Try((xml \ "currentplid").text.toInt).filter(_ >= 0).toOption
    ))

  override def playlist(): Future[VlcPlaylist] =
    xml("playlist.xml")
    .map(xml =>
      VlcPlaylist(
        (xml \\ "leaf").toList
        .map(leaf =>
          VlcSong(
            id = (leaf \@ "id").toInt,
            name = (leaf \@ "name"),
            path = new URI((leaf \@ "uri")).getPath,
            durationSecOpt = Some((leaf \@ "duration").toInt).filter(_ > 0)))))

  // Commands.
  override def play =
    statusCmd("pl_play").get
    .flatMap(_ => xml("status.xml"))
    .map(xml => Try((xml \ "currentplid").text.toInt).filter(_ >= 0).toOption)

  override def playSong(id: Int): Unit =
    statusCmd("pl_play")
    .withQueryString("id" -> s"$id")
    .get
    .foreach(resp => println(s"Playing: $id"))

  override def pause: Unit =
    statusCmd("pl_pause").get
    .foreach(resp => println("Pause"))

  override def stop: Unit =
    statusCmd("pl_stop").get
    .foreach(resp => println("Stop"))

  override def next(): Unit =
    statusCmd("pl_next").get
    .foreach(resp => println("Next"))

  override def previous(): Unit =
    statusCmd("pl_previous").get
    .foreach(resp => println("Next"))

  override def randomToggle(): Unit =
    statusCmd("pl_random").get
    .foreach(resp => println("Random"))

  override def seek(percent: Int): Unit =
    statusCmd("seek")
    .withQueryString("val" -> s"$percent%").get
    .foreach(resp => println("Seek"))

  private def statusCmd(c: String): WSRequest =
    cmd("status.xml").withQueryString("command" -> c)

  private def cmd(ressource: String): WSRequest =
    ws.url(s"http://localhost:$port/requests/$ressource").withAuth("", pass, WSAuthScheme.BASIC)

  private def xml(ressource: String): Future[Elem] =
    cmd(ressource).get().map(_.xml)
}
