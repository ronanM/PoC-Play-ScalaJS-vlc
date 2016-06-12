package com.github.ronanm.vlc.front.comm

import upickle.default._

import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.dom

import scala.concurrent.Future

class AjaxClient(prefix: String = "api") extends autowire.Client[String, Reader, Writer] {

  override def doCall(req: Request): Future[String] = {
    val urlToCall = s"/$prefix/${req.path.mkString("/")}"
    val dataStr = upickle.default.write(req.args)

    //    println(s"Post datas: $dataStr / ${req.args} / $urlToCall")

    dom.ext.Ajax
    .post(
      url = urlToCall,
      data = dataStr,
      headers = Map("Content-Type" -> "application/json;charset=UTF-8"))
    .map(_.responseText)
  }

  override def read[R: Reader](p: String): R = upickle.default.read[R](p)

  override def write[W: Writer](r: W): String = upickle.default.write(r)
}
