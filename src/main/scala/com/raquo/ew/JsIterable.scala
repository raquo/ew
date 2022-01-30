package com.raquo.ew

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
trait JsIterable[+A] extends js.Object {

  /** !! Not supported by IE! */
  @JSName(js.Symbol.iterator)
  def iterator: js.Iterator[A] = js.native
}

object JsIterable {

  implicit class RichJsIterable[A](val iterable: JsIterable[A]) extends AnyVal {
    def asScalaJsIterable: js.Iterable[A] = iterable.asInstanceOf[js.Iterable[A]]
  }

  implicit class RichScalaJsIterable[A](val iterable: js.Iterable[A]) extends AnyVal {
    def asJsIterable: JsIterable[A] = iterable.asInstanceOf[JsIterable[A]]
  }
}
