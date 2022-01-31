package com.raquo.ew

import scala.scalajs.js
import scala.scalajs.js.annotation._

/**
 * To construct a new array with uninitialized elements, use the constructor
 * of this class. To construct a new array with specified elements, as if
 * you used the array literal syntax in JavaScript, use the companion object's
 * `apply` method instead.
 *
 * Note that Javascript `===` equality semantics apply. JsArray does not know
 * anything about Scala `equals` method or the case classes structural equality.
 *
 * @tparam A Type of the elements of the array
 * @constructor Creates a new array of length 0.
 */
@js.native
@JSGlobal("Array")
class JsArray[A] extends JsIterable[A] {

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

  /** Set the length of the array.
   *
   * If the new length is bigger than the old length, created slots are
   * filled with `undefined` (irrespective of the type argument `A`!).
   *
   * If the new length is smaller than the old length, the array is shrunk.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/length
   */
  def length_=(v: Int): Unit = js.native

  /** Access the element at the given index. */
  @JSBracketAccess
  def apply(index: Int): A = js.native

  /** Set the element at the given index. */
  @JSBracketAccess
  def update(index: Int, value: A): Unit = js.native

  def map[B](project: js.Function1[A, B]): JsArray[B] = js.native

  /**
   * Create a new array consisting of the elements in the this object
   * on which it is called, followed in order by, for each argument, the
   * elements of that argument
   */
  def concat[B >: A](items: JsArray[_ <: B]*): JsArray[B] = js.native

  def indexOf(item: A): Int = js.native

  /**
   * Join all elements of an array into a string.
   *
   * separator Specifies a string to separate each element of the array.
   * The separator is converted to a string if necessary. If omitted, the
   * array elements are separated with a comma.
   */
  def join(seperator: String = ","): String = js.native

  /**
   * Remove the last element from an array and returns that element.
   */
  def pop(): A = js.native

  /**
   * Mutate an array by appending the given elements and returning the new
   * length of the array.
   */
  def push(items: A*): Int = js.native

  /**
   * Reverse an array in place. The first array element
   * becomes the last and the last becomes the first.
   */
  def reverse(): JsArray[A] = js.native

  /**
   * Remove the first element from an array and return that element.
   */
  def shift(): A = js.native

  /**
   * Return a shallow copy of a portion of an array.
   */
  def slice(start: Int = 0, end: Int = Int.MaxValue): JsArray[A] = js.native

  /**
   * Sort the elements of an array in place and return the
   * array. The default sort order is lexicographic (not numeric).
   *
   * !! The sort is not stable in IE!
   *
   * If compareFn is not supplied, elements are sorted by converting them
   * to strings and comparing strings in lexicographic ("dictionary" or "telephone
   * book," not numerical) order. For example, "80" comes before "9" in
   * lexicographic order, but in a numeric sort 9 comes before 80.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/sort
   */
  def sort(compareFn: js.Function2[A, A, Int]): JsArray[A] = js.native

  /** Remove and add new elements at a given index in the array.
   *
   * This method first removes `deleteCount` elements starting from the index
   * `index`, then inserts the new elements `items` at that index.
   *
   * If `index` is negative, it is treated as that number of elements starting
   * from the end of the array.
   *
   * @param index       Index where to start changes
   * @param deleteCount Number of elements to delete from index
   * @param items       Elements to insert at index
   * @return An array of the elements that were deleted
   */
  def splice(index: Int, deleteCount: Int, items: A*): JsArray[A] = js.native

  /**
   * Add one or more elements to the beginning of the array
   * and return the new length of the array.
   */
  def unshift(items: A*): Int = js.native
}

object JsArray extends Object {

  // @TODO The `apply` method calls into js.Array because instantiating an array of
  //  one integer requires syntax like `[5]` in Javascript, and I don't know how
  //  to get Scala.js to generate such JS code aside from using js.Array.
  //    Note: `Array(5)` in JS creates an array with 5 empty slots instead of an
  //    array with an element `5` in it.

  /**
   * Creates a new array with the given items, equivalent to `[item1, item2, ...]` literal
   *
   * Note:If you want to preallocate N items, use `new JsArray(N)`
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array
   */
  def apply[A](items: A*): JsArray[A] = js.Array(items: _*).asInstanceOf[JsArray[A]]

  /**
   * Returns true if the given value is an array.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/isArray
   */
  def isArray(arg: scala.Any): Boolean = rawJsArray.isArray(arg)

  /**
   * Creates a new array from a JS iterable (array, set, map, etc.).
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/from
   */
  def from[A](iterable: JsIterable[A]): JsArray[A] = rawJsArray.from(iterable)

  /** Cast a js.Array to JsArray. It's safe because they have the same runtime representation. */
  @inline def from[A](arr: js.Array[A]): JsArray[A] = arr.asInstanceOf[JsArray[A]]

  // --

  implicit class RichJsArray[A](val arr: JsArray[A]) extends AnyVal {

    /** Note: this implementation is faster than calling into JS native `forEach`. */
    def forEach(cb: js.Function1[A, Any]): Unit = {
      var i = 0
      val len = arr.length
      while (i < len) {
        cb(arr(i))
        i += 1
      }
    }

    def asJsIterable: JsIterable[A] = arr: JsIterable[A]

    def asScalaJs: js.Array[A] = arr.asInstanceOf[js.Array[A]]
  }

  class RichScalaJsArray[A](val arr: js.Array[A]) extends AnyVal {

    def ew: JsArray[A] = from(arr)
  }

  // --

  @js.native
  @JSGlobal("Array")
  private object rawJsArray extends js.Object {

    def isArray(arg: scala.Any): Boolean = js.native

    def from[A](iterable: JsIterable[A]): JsArray[A] = js.native
  }
}
