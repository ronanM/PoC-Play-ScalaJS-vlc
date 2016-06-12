package com.github.ronanm.vlc.back.controllers

import java.nio.ByteBuffer

import upickle.default._

// TODO Utiliser JsValue au lieu de String et pickler Json de Play.
object AutowiredServer extends autowire.Server[String, Reader, Writer] {
  override def read[R: Reader](p: String): R = upickle.default.read[R](p)

  override def write[W: Writer](r: W): String = upickle.default.write[W](r)
}

