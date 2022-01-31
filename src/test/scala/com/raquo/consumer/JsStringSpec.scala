package com.raquo.consumer

import com.raquo.ew._
import org.scalatest.funspec.AnyFunSpec

import scala.scalajs.js

class JsStringSpec extends AnyFunSpec {

  it("misc") {

    val x = "Hello".ew.repeat(3).replace("H", (s: String) => " " + s).str

    assert(x == " HelloHelloHello")

    val y = "Hello".ew.repeat(3).replace(new js.RegExp("H", "g"), (s: String) => " " + s).asScalaJs

    assert(y == " Hello Hello Hello")
  }

}
