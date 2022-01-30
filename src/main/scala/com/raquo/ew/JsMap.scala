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

  /** !! Not supported by IE */
  def keys(): JsIterable[K] with js.Iterator[K] = js.native

  /** !! Not supported by IE */
  def values(): JsIterable[V] with js.Iterator[V] = js.native

  /** !! Not supported by IE */
  def entries(): JsIterable[js.Tuple2[K, V]] with js.Iterator[js.Tuple2[K, V]] = js.native

  def clear(): Unit = js.native

  def delete(key: K): Boolean = js.native

  def size: Int = js.native

  def forEach(f: js.Function2[V, K, Unit]): Unit = js.native
}

object JsMap {

  /** Cast a js.Set to JsSet. It's safe because they have the same runtime representation. */
  @inline def fromScalaJs[K, V](map: js.Map[K, V]): JsMap[K, V] = map.asInstanceOf[JsMap[K, V]]

  implicit class RichJsMap[K, V](val jsMap: JsMap[K, V]) extends AnyVal {

    def asScalaJsSet: js.Map[K, V] = jsMap.asInstanceOf[js.Map[K, V]]
  }

  // #Note you need to import this for the implicit to be available
  class RichScalaJsMap[K, V](val sjsMap: js.Map[K, V]) extends AnyVal {

    def asJsMap: JsMap[K, V] = fromScalaJs(sjsMap)
  }
}
