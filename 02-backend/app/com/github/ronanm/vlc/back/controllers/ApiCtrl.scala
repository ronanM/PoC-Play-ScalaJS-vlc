package com.github.ronanm.vlc.back.controllers

import java.nio.ByteBuffer

import com.github.ronanm.vlc.shared.Api
import play.api.mvc._
import upickle.default._
import scala.concurrent.ExecutionContext.Implicits.global

class ApiCtrl(apiService: Api) extends Controller {

  def autowireApi(path: String) = Action.async(parse.json) { implicit request =>
    //    println(s"Request path: $path")

    val bodyStr = request.body.toString

    //    println(s"API body: $bodyStr")

    // call Autowire route
    AutowiredServer
    .route[Api](apiService)(autowire.Core.Request(path.split("/"), upickle.default.read[Map[String, String]](bodyStr)))
    .map(Ok(_))
  }
}