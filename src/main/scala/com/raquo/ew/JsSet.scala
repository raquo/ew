package com.raquo.ew

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/**
 * Set objects are collections of unique values. You can iterate through the
 * elements of a set in insertion order. A value in the Set may only occur once;
 * it is unique in the Set's collection. Sets are mutable, like everything in JS
 *
 * Note that Javascript `===` equality semantics apply. JsSet does not know
 * anything about Scala `equals` method or the case classes structural equality.
 *
 *
 * The Map object holds key-value pairs and remembers the original insertion
 *  order of the keys. Any value (both objects and primitive values) may be used
 *  as either a key or a value. The Map is mutable, like everything in JS.
 *
 *  @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Map
 */
@js.native
@JSGlobal("Set")
class JsSet[A]() extends JsIterable[A] {

  /** !! Passing `iterable`  is not supported by IE - populate the Map separately if needed. */
  def this(iterable: JsIterable[A]) = this()

  /** !! Not fully supported by IE - returns js.undefined instead of Set */
  def add(value: A): JsSet[A] = js.native

  def has(value: A): Boolean = js.native

  /**
   * Alias for `values`.
   *
   * !! Not supported by IE
   */
  def keys(): JsIterable[A] with js.Iterator[A] = js.native

  /** !! Not supported by IE */
  def values(): JsIterable[A] with js.Iterator[A] = js.native

  /** !! Not supported by IE */
  //def entries(): JsIterable[js.Tuple2[A, A]] with js.Iterator[js.Tuple2[A, A]] = js.native

  def clear(): Unit = js.native

  def delete(value: A): Boolean = js.native

  def size: Int = js.native

  def forEach(f: js.Function1[A, Unit]): Unit = js.native
}

object JsSet {

  /** Cast a js.Set to JsSet. It's safe because they have the same runtime representation. */
  @inline def fromScalaJs[A](set: js.Set[A]): JsSet[A] = set.asInstanceOf[JsSet[A]]

  implicit class RichJsSet[A](val jsSet: JsSet[A]) extends AnyVal {

    def asScalaJsSet: js.Set[A] = jsSet.asInstanceOf[js.Set[A]]
  }

  // #Note you need to import this for the implicit to be available
  class RichScalaJsSet[A](val sjsSet: js.Set[A]) extends AnyVal {

    def asJsSet: JsSet[A] = fromScalaJs(sjsSet)
  }
}
