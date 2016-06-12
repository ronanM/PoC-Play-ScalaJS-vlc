package com.github.ronanm.vlc.front.diode

import com.github.ronanm.vlc.shared.{VlcPlaylist, VlcStatus}

case class RootModel(vlcStatus: VlcStatus = VlcStatus(),
                     pl: VlcPlaylist = VlcPlaylist())
