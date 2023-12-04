package com.raquo.ew

import com.raquo.ew.JsArray.rawJsArray

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.|

/**
  * JsVector is a [[JsArray]] that pretends to be immutable by only exposing
  * immutable APIs like map / filter / etc. JS itself does not have such an
  * immutable type, so this is an extension intended for cases when you need
  * JS Arrays for efficiency, but need to communicate to yourself or your users
  * that this array should not be modified.
  *
  * Conversions to/from mutable js.Array and JsArray are available for interop,
  * but are marked "unsafe".
  *
  * Note that js.Array, JsArray, and JsVector are indistinguishable at runtime,
  * so .isInstanceOf on one of those types will succeed on any of them.
  *
  * To construct a new JsVector with uninitialized elements, use the constructor
  * of this class. To construct a new array with specified elements, as if
  * you used the array literal syntax in JavaScript, use the companion object's
  * `apply` method instead.
  *
  * Note that Javascript `===` equality semantics apply. JsVector does not know
  * anything about Scala `equals` method or the case classes structural equality.
  *
  * [[https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/length Array @ MDN]]
  *
  * @tparam A Type of the elements of the array
  * @constructor Creates a new array of length 0.
  */
@js.native
@JSGlobal("Array")
class JsVector[+A] extends JsIterable[A] {

  /**
    * Create a new array with the given length
    * (filled with `js.undefined` irrespective of the type argument `A`!).
    *
    * See companion object for more factories.
    *
    * @param arrayLength Initial length of the array.
    */
  def this(arrayLength: Int) = this()

  /** Length of the array. */
  def length: Int = js.native

  /** Access the element at the given index. */
  @JSBracketAccess
  def apply(index: Int): A = js.native

  /**
    * Create a new array populated with the results of calling a provided function
    * on every element in the calling array.
    */
  def map[B](project: js.Function1[A, B]): JsVector[B] = js.native

  @JSName("map")
  def mapWithIndex[B](project: js.Function2[A, Int, B]): JsVector[B] = js.native

  /**
    * Create a shallow copy of a portion of a given array, filtered down to just the elements
    * from the given array that pass the test implemented by the provided function.
    */
  def filter(passes: js.Function1[A, Boolean]): JsVector[A] = js.native

  @JSName("filter")
  def filterWithIndex(passes: js.Function2[A, Int, Boolean]): JsVector[A] = js.native

  /**
    * @param f (accumulator, nextValue) => nextAccumulator
    *          On first call of `f`, `accumulator` is `array[0]`, and `nextValue` is `array[1]`.
    *
    *          If array only has one item, array[0] is returned without calling `f`.
    *
    *          Note: throws exception if array is empty.
    */
  def reduce[B](f: js.Function2[B, A, B]): B = js.native

  /**
    * @param f (accumulator, nextValue) => nextAccumulator
    *          On first call of `f`, `accumulator` is `initial`, and `nextValue` is `array[0]`
    *
    *          If array is empty or only has one item, `initial` is returned without calling `f`.
    */
  def reduce[B](f: js.Function2[B, A, B], initial: B): B = js.native

  /**
    * @param f (accumulator, nextValue, nextIndex) => nextAccumulator
    *          On first call of `f`, `accumulator` is `array[0]`, and `nextValue` is `array[1]`.
    *
    *          If array only has one item, array[0] is returned without calling `f`.
    *
    *          Note: throws exception if array is empty.
    */
  @JSName("reduce")
  def reduceWithIndex[B](f: js.Function3[B, A, Int, B]): B = js.native

  /**
    * @param f (accumulator, nextValue, nextIndex) => nextAccumulator
    *          On first call of `f`, `accumulator` is `initial`, and `nextValue` is `array[0]`
    *
    *          If array is empty or only has one item, `initial` is returned without calling `f`.
    */
  @JSName("reduce")
  def reduceWithIndex[B](f: js.Function3[B, A, Int, B], initial: B): B = js.native

  /**
    * Create a new array consisting of the elements in the this object
    * on which it is called, followed in order by, for each argument, the
    * elements of that argument
    */
  def concat[B >: A](items: (JsArray[_ <: B] | JsVector[_ <: B] | js.Array[_ <: B])*): JsVector[B] = js.native

  def indexOf[AA >: A](item: AA, fromIndex: Int = 0): Int = js.native

  /**
    * Join all elements of an array into a string.
    *
    * separator specifies a string to separate each element of the array.
    * The separator is converted to a string if necessary. If omitted, the
    * array elements are separated with a comma.
    */
  def join(seperator: String = ","): String = js.native

}

object JsVector {

  /**
    * Creates a new array with the given items, equivalent to `[item1, item2, ...]` literal
    *
    * Note:If you want to preallocate N items, use `new JsArray(N)`
    *
    * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array
    */
  def apply[A](items: A*): JsVector[A] = js.Array(items: _*).asInstanceOf[JsVector[A]]

  /** Copy an array into a new vector */
  def from[A](items: JsIterable[A]): JsVector[A] = {
    rawJsArray.from(items).unsafeAsJsVector
  }

  /** Copy an array into a new vector */
  def from[A, B](items: JsIterable[A], mapFn: js.Function2[A, Int, B]): JsVector[B] = {
    rawJsArray.from(items, mapFn).unsafeAsJsVector
  }

  /** Copy an array into a new vector */
  def from[A](items: js.Iterable[A]): JsVector[A] = {
    rawJsArray.from(items).unsafeAsJsVector
  }

  /** Copy an array into a new vector */
  def from[A, B](items: js.Iterable[A], mapFn: js.Function2[A, Int, B]): JsVector[B] = {
    rawJsArray.from(items, mapFn).unsafeAsJsVector
  }

  /** Copy iterable into a new vector */
  @inline def from[A](items: Iterable[A]): JsVector[A] = {
    JsArray.from(items).unsafeAsJsVector
  }

  /** Copy an array into a new vector */
  def from[A](items: scala.Array[A]): JsVector[A] = {
    JsArray.from(items).unsafeAsJsVector
  }

  /** Make a new array out of js.UndefOr */
  def from[A](maybeItem: js.UndefOr[A])(implicit dummyImplicit: DummyImplicit): JsVector[A] = {
    JsArray.from(maybeItem).unsafeAsJsVector
  }

  // --

  implicit class RichJsVector[A](val arr: JsVector[A]) extends AnyVal {

    /** Note: the browser's native `includes` method does not work in IE11. This one does. */
    def includes(item: A, fromIndex: Int = 0): Boolean = {
      arr.indexOf(item, fromIndex) != -1
    }

    /** Note: this implementation is faster than calling into JS native `forEach`. */
    def forEach(cb: js.Function1[A, Any]): Unit = {
      var i = 0
      val len = arr.length
      while (i < len) {
        cb(arr(i))
        i += 1
      }
    }

    /** Similar to the native two-argument version of forEach */
    def forEachWithIndex(cb: js.Function2[A, Int, Any]): Unit = {
      var i = 0
      val len = arr.length
      while (i < len) {
        cb(arr(i), i)
        i += 1
      }
    }

    /**
      * Unsafe because JsVector users assume it's immutable,
      * but this can be used to mutate it.
      */
    @inline def unsafeAsScalaJs: js.Array[A] = arr.asInstanceOf[js.Array[A]]

    @inline def toList: List[A] = arr.unsafeAsScalaJs.toList
  }

}
