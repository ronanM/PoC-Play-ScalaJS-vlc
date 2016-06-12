package com.github.ronanm.vlc.shared

import java.net.URI

case class VlcStatus(currentPlayingID: Option[Int] = None)

case class VlcPlaylist(songs: List[VlcSong] = List.empty)

case class VlcSong(id: Int,
                   name: String,
                   path: String,
                   durationSecOpt: Option[Int])
