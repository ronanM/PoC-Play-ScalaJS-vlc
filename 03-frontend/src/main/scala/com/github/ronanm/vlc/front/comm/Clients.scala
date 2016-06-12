package com.github.ronanm.vlc.front.comm

import com.github.ronanm.vlc.shared.Api

object Clients {
  val apiClient = new AjaxClient()[Api]
}
