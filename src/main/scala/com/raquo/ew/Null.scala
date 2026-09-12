package com.raquo.ew

import scala.scalajs.js
import scala.scalajs.js.|

object Null {

  def fromUndefined[V](value: js.UndefOr[V]): V | Null = {
    value.fold[V | Null](ifEmpty = null)(identity)
  }

  /** Note: works the same if value is of type V, and not `V | Null` */
  def asUndefined[V](value: V | Null): js.UndefOr[V] = {
    if (value == null) {
      js.undefined
    } else {
      value.asInstanceOf[V]
    }
  }
}
