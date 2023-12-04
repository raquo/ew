package com.raquo.ew

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

/**
 * The Map object holds key-value pairs and remembers the original insertion
 * order of the keys. Any value (both objects and primitive values) may be used
 * as either a key or a value. The Map is mutable, like everything in JS.
 *
 * Note that Javascript `===` equality semantics apply. JsMap does not know
 * anything about Scala `equals` method or the case classes structural equality.
 *
 * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Map
 */
@js.native
@JSGlobal("Map")
class JsMap[K, V]() extends JsIterable[js.Tuple2[K, V]] {

  /** !! Passing `iterable`  is not supported by IE - populate the Map separately if needed. */
  def this(iterable: JsIterable[js.Tuple2[K, V]]) = this()

  /** !! Not fully supported by IE - returns js.undefined instead of Map */
  def set(key: K, value: V): JsMap[K, V] = js.native

  def get(key: K): js.UndefOr[V] = js.native

  def has(key: K): Boolean = js.native

  /** !! Not supported by IE !! */
  def keys(): JsIterable[K] with js.Iterator[K] = js.native

  /** !! Not supported by IE !! */
  def values(): JsIterable[V] with js.Iterator[V] = js.native

  /** !! Not supported by IE !! */
  def entries(): JsIterable[js.Tuple2[K, V]] with js.Iterator[js.Tuple2[K, V]] = js.native

  def clear(): Unit = js.native

  def delete(key: K): Boolean = js.native

  def size: Int = js.native

  def forEach(f: js.Function2[V, K, Unit]): Unit = js.native
}

object JsMap {

  implicit class RichJsMap[K, V](val map: JsMap[K, V]) extends AnyVal {

    /** Cast a JsMap to js.Map. It's safe because they have the same runtime representation. */
    @inline def asScalaJs: js.Map[K, V] = map.asInstanceOf[js.Map[K, V]]
  }

  class RichScalaJsMap[K, V](val map: js.Map[K, V]) extends AnyVal {

    /** Cast a js.Map to JsMap. It's safe because they have the same runtime representation. */
    @inline def ew: JsMap[K, V] = map.asInstanceOf[JsMap[K, V]]
  }
}
