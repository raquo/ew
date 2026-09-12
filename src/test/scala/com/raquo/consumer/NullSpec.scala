package com.raquo.consumer

import com.raquo.ew._
import org.scalatest.funspec.AnyFunSpec

import scala.scalajs.js
import scala.scalajs.js.|

class NullSpec extends AnyFunSpec {

  case class Foo(id: Int)

  it("asUndefined: AnyRefs") {

    val str = Null.asUndefined("hello")
    assert(str.isDefined)
    assert(str.get == "hello")
    val _1 = (str: js.UndefOr[String])

    val foo = Foo(1)
    val fooResult = Null.asUndefined(foo)
    assert(fooResult.isDefined)
    assert(fooResult.get eq foo)
    val _2 = (fooResult: js.UndefOr[Foo])

    val jsObj = new js.Object
    val jsObjResult = Null.asUndefined(jsObj)
    assert(jsObjResult.isDefined)
    assert(jsObjResult.get eq jsObj)

    val _3 = (jsObjResult: js.UndefOr[js.Object])
  }

  it("asUndefined: value types") {

    val _int = Null.asUndefined(1)
    val _long = Null.asUndefined(2L)
    val _double = Null.asUndefined(3.5d)
    val _bool = Null.asUndefined(true)
    val _char = Null.asUndefined('c')

    assert(_int.get == 1)
    assert(_long.get == 2L)
    assert(_double.get == 3.5d)
    assert(_bool.get)
    assert(_char.get == 'c')

    val _1 = (_int: js.UndefOr[Int])
    val _2 = (_long: js.UndefOr[Long])
    val _3 = (_double: js.UndefOr[Double])
    val _4 = (_bool: js.UndefOr[Boolean])
    val _5 = (_char: js.UndefOr[Char])
  }

  def nullAsUndefined[V](value: V): js.UndefOr[V] = {
    if (value == null)
      js.undefined
    else
      value
  }

  it("asUndefined: null values become js.undefined") {
    // #Note: Scala bug does not allow asserting with .isEmpty / .nonEmpty: https://github.com/scala/scala3/issues/27066

    val str: String = null
    val strUndef = Null.asUndefined(str)
    assert(strUndef == js.undefined)
    val _1 = (strUndef: js.UndefOr[String]) // make sure it's not js.UndefOr[Foo | Null]

    val foo: Foo = null
    val fooUndef = Null.asUndefined(foo)
    assert(fooUndef == js.undefined)
    val _2 = (fooUndef: js.UndefOr[Foo]) // make sure it's not js.UndefOr[Foo | Null]
  }

  it("fromNullable: nullable types (V | Null)") {

    // #Note: Scala bug does not allow asserting with .isEmpty / .nonEmpty: https://github.com/scala/scala3/issues/27066

    val str: String | Null = "hello"
    val strUndef: js.UndefOr[String] = Null.asUndefined(str)
    assert(strUndef.isDefined)
    assert(strUndef.get == "hello")

    val strNull: String | Null = null
    assert(Null.asUndefined(strNull) == js.undefined)

    val fooValue = Foo(1)
    val foo: Foo | Null = fooValue
    val fooResult = Null.asUndefined(foo)
    assert(fooResult.isDefined)
    assert(fooResult.get eq fooValue)
    val _ = (fooResult: js.UndefOr[Foo]) // make sure it's not js.UndefOr[Foo | Null]

    val fooNull: Foo | Null = null
    val fooUndef = Null.asUndefined(fooNull)
    assert(fooUndef == js.undefined)
    val __ = (fooUndef: js.UndefOr[Foo])
  }

}
