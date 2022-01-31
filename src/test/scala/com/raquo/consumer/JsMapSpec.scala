package com.raquo.consumer

import com.raquo.ew._
import org.scalatest.funspec.AnyFunSpec

import scala.scalajs.js

class JsMapSpec extends AnyFunSpec {

  case class Foo(id: Int)

  it("misc") {

    val foo1 = Foo(1)
    val foo1copy = Foo(1)
    val foo2 = Foo(2)

    val m = new JsMap[Foo, Int](JsArray(
      js.Tuple2(foo1, 100),
      js.Tuple2(foo2, 200)
    ))
    assert(m.size == 2)

    assert(m.has(foo1))
    assert(m.has(foo2))

    assert(m.get(foo1) == js.defined(100))
    assert(m.get(foo2) == js.defined(200))

    // No Scala semantics
    assert(!m.has(foo1copy))
    assert(m.get(foo1copy) == js.undefined)

    // --

    val sjsM = m.asScalaJs
    assert(sjsM.size == 2)

    assert(sjsM.contains(foo1))
    assert(sjsM.contains(foo2))
    assert(!sjsM.contains(foo1copy)) // Scala.js apparently does not have Scala semantics here either

    assert(sjsM.ew eq m)
    assert(JsMap.from(sjsM) eq m)

    // --

    val vs = JsArray.from(m.values())

    vs.length == 2

    vs(0) == 100
    vs(1) == 200

    // --

    val ks = JsArray.from(m.keys())

    ks.length == 2

    ks(0) == foo1
    ks(1) == foo2

    // --

    val es = JsArray.from(m.iterable)

    es.length == 2

    es(0) == js.Tuple2(foo1, 100)
    es(1) == js.Tuple2(foo2, 200)

    m.forEach((v, k) => {
      assert(m.get(k) == js.defined(v))
    })
  }
}
