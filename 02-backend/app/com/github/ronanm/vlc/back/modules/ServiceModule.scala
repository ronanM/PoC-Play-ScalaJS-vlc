package com.github.ronanm.vlc.back.modules

import com.softwaremill.macwire._
import play.api.libs.ws.WSClient
import com.github.ronanm.vlc.back.services.ApiImpl

trait ServiceModule {

  def wsClient: WSClient

  lazy val apiService = wire[ApiImpl]
}
