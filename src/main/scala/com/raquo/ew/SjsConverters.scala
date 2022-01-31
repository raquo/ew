package com.raquo.ew

import com.raquo.ew.JsArray.RichScalaJsArray
import com.raquo.ew.JsIterable.RichScalaJsIterable
import com.raquo.ew.JsMap.RichScalaJsMap
import com.raquo.ew.JsSet.RichScalaJsSet

import scala.scalajs.js

object SjsConverters {

  implicit def enrichSjsIterable[A](arr: js.Array[A]): RichScalaJsIterable[A] = new RichScalaJsIterable(arr)

  implicit def enrichSjsArray[A](arr: js.Array[A]): RichScalaJsArray[A] = new RichScalaJsArray(arr)

  implicit def enrichSjsSet[A](set: js.Set[A]): RichScalaJsSet[A] = new RichScalaJsSet(set)

  implicit def enrichSjsMap[K, V](map: js.Map[K, V]): RichScalaJsMap[K, V] = new RichScalaJsMap(map)
}
