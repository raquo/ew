package com.raquo.consumer

import com.raquo.ew._
import org.scalatest.funspec.AnyFunSpec

class JsVectorSpec extends AnyFunSpec {

  case class Foo(id: Int)

  val foo1 = Foo(1)
  val foo1copy = Foo(1)
  val foo2 = Foo(2)
  val foo3 = Foo(3)

  it("misc") {

    {
      val x = JsVector(5)
      assert(x.length == 1)
      assert(x(0) == 5)
    }

    {
      val x = JsVector(5, 6)
      assert(x.length == 2)
      assert(x(0) == 5)
      assert(x(1) == 6)

      val y = x.unsafeAsScalaJs
      assert(y.length == 2)
      assert(y(0) == 5)
      assert(y(1) == 6)
    }

    {
      val x = JsVector(JsVector(5, 6))
      assert(x.length == 1)
      assert(x(0)(0) == 5)
      assert(x(0)(1) == 6)
    }

    {
      val x = JsVector.from(JsArray(5, 6))
      assert(x.length == 2)
      assert(x(0) == 5)
      assert(x(1) == 6)
    }

    {
      val x = JsVector(foo1, foo2)
      assert(x.length == 2)
      assert(x(0) == foo1)
      assert(x(1) == foo2)

      val y = x.unsafeAsScalaJs
      assert(y.length == 2)
      assert(y(0) == foo1)
      assert(y(1) == foo2)

      assert(y.contains(foo1))
      assert(y.contains(foo1copy))
      assert(!y.contains(foo3)) // Scala semantics

      assert(y.unsafeAsJsVector eq x)
    }
  }
}
