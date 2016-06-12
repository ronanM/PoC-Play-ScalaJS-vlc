package com.github.ronanm.vlc.shared

import scala.concurrent.Future

trait Api {

  // Infos.
  def vlcStatus(): Future[VlcStatus]

  def playlist(): Future[VlcPlaylist]

  // Commands.
  def play(): Future[Option[Int]]

  def playSong(id: Int): Unit

  def pause(): Unit

  def stop(): Unit

  def next(): Unit

  def previous(): Unit

  def randomToggle(): Unit

  def seek(percent: Int): Unit
}