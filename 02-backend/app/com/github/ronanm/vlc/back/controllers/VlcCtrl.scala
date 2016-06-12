package com.github.ronanm.vlc.back.controllers

import play.api.mvc._

class VlcCtrl extends Controller {

  def indexPage = Action {
    Ok(com.github.ronanm.vlc.back.views.html.index())
  }
}
