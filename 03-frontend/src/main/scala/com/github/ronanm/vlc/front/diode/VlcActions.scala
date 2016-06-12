package com.github.ronanm.vlc.front.diode

import com.github.ronanm.vlc.shared.{VlcPlaylist, VlcStatus}

sealed trait Action

case object Reset extends Action

case object RefreshStatus extends Action

case class RefreshedStatus(vlcStatus: VlcStatus) extends Action

case class RefreshedPlaylist(pl: VlcPlaylist) extends Action

case class Log(s: String) extends Action

case object Play extends Action

case class PlaySong(id: Int) extends Action

case object Stop extends Action

case object Next extends Action

case object Prev extends Action

case object Random extends Action

case class Seek(percent: Int) extends Action

