package com.raquo.consumer

import com.raquo.ew._
import org.scalatest.funspec.AnyFunSpec

class JsIterableSpec extends AnyFunSpec {

  it("misc") {

    val x = JsArray(5, 6, 7)
    assert(x.length == 3)
    assert(x(0) == 5)
    assert(x(1) == 6)
    assert(x(2) == 7)

    var count = 0
    x.ext.forEach(item => count += item)
    assert(count == 5 + 6 + 7)
  }
}
