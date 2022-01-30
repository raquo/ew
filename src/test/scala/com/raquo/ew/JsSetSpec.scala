package com.raquo.ew

import com.raquo.ew.SjsConverters.enrichSjsSet
import org.scalatest.funspec.AnyFunSpec

class JsSetSpec extends AnyFunSpec {

  case class Foo(id: Int)

  it("misc") {

    val foo1 = Foo(1)
    val foo1copy = Foo(1)
    val foo2 = Foo(2)

    val s = new JsSet[Foo](JsArray(foo1, foo2))
    assert(s.size == 2)

    assert(s.has(foo1))
    assert(s.has(foo2))

    // No Scala semantics
    assert(!s.has(foo1copy))

    // --

    val sjsSet = s.asScalaJsSet
    assert(sjsSet.size == 2)

    assert(sjsSet.contains(foo1))
    assert(sjsSet.contains(foo2))
    assert(!sjsSet.contains(foo1copy)) // Scala.js apparently does not have Scala semantics here either

    assert(sjsSet.asJsSet eq s)
    assert(JsSet.fromScalaJs(sjsSet) eq s)

    // --

    val vs = JsArray.from(s.values())

    vs.length == 2

    vs(0) == foo1
    vs(1) == foo2

    // --

    val ks = JsArray.from(s.keys())

    ks.length == 2

    ks(0) == foo1
    ks(1) == foo2

    s.forEach(v => assert(v.isInstanceOf[Foo]))
  }

}
