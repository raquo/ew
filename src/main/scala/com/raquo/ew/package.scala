package com.raquo

import com.raquo.ew.JsArray.RichScalaJsArray
import com.raquo.ew.JsIterable.RichScalaJsIterable
import com.raquo.ew.JsMap.RichScalaJsMap
import com.raquo.ew.JsSet.RichScalaJsSet
import com.raquo.ew.JsString.RichString

import scala.scalajs.js

package object ew {

  implicit def ewString(str: String): RichString = new RichString(str)

  implicit def ewIterable[A](arr: js.Array[A]): RichScalaJsIterable[A] = new RichScalaJsIterable(arr)

  implicit def ewArray[A](arr: js.Array[A]): RichScalaJsArray[A] = new RichScalaJsArray(arr)

  implicit def ewSet[A](set: js.Set[A]): RichScalaJsSet[A] = new RichScalaJsSet(set)

  implicit def ewMap[K, V](map: js.Map[K, V]): RichScalaJsMap[K, V] = new RichScalaJsMap(map)
}
