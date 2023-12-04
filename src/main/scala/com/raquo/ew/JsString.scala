package com.raquo.ew

import scala.scalajs.js
import scala.scalajs.js.|

// #TODO[API] Should JsString be JsIterable? Seems like it gives more room for confusion than usefulness in practice

/** JS-native operations on strings. Alternative to `JSStringOps` from Scala.js */
@js.native
trait JsString extends JsIterable[String] {

  /**
   * Returns a new string consisting of the single UTF-16 code unit located at
   * `index` in the string.
   *
   * - If index is out of range, returns an empty string.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/charAt
   */
  def charAt(index: Int): String = js.native

  /**
   * Returns an integer between 0 and 65535 representing the UTF-16 code unit
   * at the given index.
   *
   * - If `index` is out of range, charCodeAt() returns NaN.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/charCodeAt
   */
  def charCodeAt(index: Int): Double = js.native

  /**
   * Returns a non-negative integer that is the Unicode code point value at
   * the given position in the string.
   *
   * - If there is no element at `pos`, returns undefined.
   *
   * - If the element at `pos` is a UTF-16 high surrogate, returns the code
   *   point of the surrogate pair.
   *
   * - If the element at `pos` is a UTF-16 low surrogate, returns only the
   *   low surrogate code point.
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/charPointAt
   */
  def charPointAt(pos: Int): js.UndefOr[Int] = js.native

  /**
   * Returns the index of the first occurrence of the specified substring
   * at an index greater than or equal to the specified number.
   *
   * - Returns -1 if not found.
   *
   * @param fromIndex The lowest index at which the found substring can
   *                  begin, for it to be found. Defaults to 0.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/indexOf
   */
  def indexOf(searchString: String, fromIndex: Int = 0): Int = js.native

  /**
   * Returns the index of the last occurrence of the specified substring
   * at an index less than or equal to the specified number.
   *
   * - Returns -1 if not found.
   *
   * @param fromIndex The highest index at which the found substring can
   *                  begin, for it to be found. Defaults to +Infinity
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/lastIndexOf
   */
  def lastIndexOf(searchString: String, fromIndex: Int = js.native): Int = js.native

  /**
   * Return the length of the string, in UTF-16 code units.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/length
   */
  def length: Int = js.native

  /**
   * @param searchString The characters to search for.
   * @param position The position in the string at which to start searching.
   *                 Defaults to 0.
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/startsWith
   */
  def startsWith(searchString: String, position: Int = js.native): Boolean = js.native

  /**
   * @param searchString The characters to search for.
   * @param length If provided, it is used as the length of the string
   *               in which we're searching. Defaults to its real length.
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/endsWith
   */
  def endsWith(searchString: String, length: Int = js.native): Boolean = js.native

  /**
   * - If the `g` flag is used, all results matching the complete regular
   *   expression will be returned, but capturing groups will not.
   *
   * - if the `g` flag is not used, only the first complete match and its
   *   related capturing groups are returned. In this case, the returned
   *   item will have additional properties as described in the docs.
   *
   * Alternatives:
   *
   * - If you only want the first match found, you might want to use
   *   `myJsRegExp.exec(str)`
   *
   * - If you need to know if a string matches a regular expression, use
   *   `myJsRegExp.test(str)`
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/match
   */
  def `match`(regexp: js.RegExp): js.Array[String] = js.native
  def `match`(regexp: String): js.Array[String] = js.native

  /**
   * Returns the Unicode Normalization Form of the string.
   *
   * !! Not supported by IE !!
   *
   * @param form One of "NFC", "NFD", "NFKC", or "NFKD", specifying the
   *             Unicode Normalization Form
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/normalize
   */
  def normalize(form: String = "NFC"): JsString = js.native

  /**
   * Pads the current string with a given string (repeated, if needed) so that
   * the resulting string reaches a given length. The padding is applied from
   * the start of the current string.
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/padStart
   */
  def padStart(targetLength: Int, padString: String = " "): JsString = js.native

  /**
   * Pads the current string with a given string (repeated, if needed) so that
   * the resulting string reaches a given length. The padding is applied from
   * the end of the current string.
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/padEnd
   */
  def padEnd(targetLength: Int, padString: String = " "): JsString = js.native

  /**
   * Returns a string that repeats the current string `count` times
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/repeat
   */
  def repeat(count: Int): JsString = js.native

  /**
   * @param what Note: if String, only the first occurrence will be replaced.
   *             If RegExp, depends on the `g` flag.
   *
   * @param replacement Note: this is parsed for special patterns! See docs.
   *
   * !! If you don't want special patterns to be parsed out of `replacement`,
   * use the version of this method with a `replacer` function. !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/replace
   */
  def replace(what: String | js.RegExp, replacement: String): JsString = js.native

  /**
   * @param what Note: if String, only the first occurrence will be replaced.
   *             If RegExp, depends on the `g` flag.
   *
   * @param replacer Accepts the substring to be replaced, and returns what to
   *                 replace it with. Note that JS can actually provide more
   *                 data to this function, but we don't expose that. See docs.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/replace
   */
  def replace(what: String | js.RegExp, replacer: js.Function1[String, String]): JsString = js.native

  /**
   * @param what If js.RegExp, must be global, or this will throw.
   *
   * @param replacement Note: this is parsed for special patterns! See docs.
   *
   * !! If you don't want special patterns to be parsed out of `replacement`,
   * use the version of this method with a `replacer` function. !!
   *
   * !! Not supported by IE, not supported by MobileSafari < 13.4 !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/replaceAll
   */
  def replaceAll(what: String | js.RegExp, replacement: String): JsString = js.native

  /**
   * @param what If js.RegExp, must be global, or this will throw.
   *
   * @param replacer Accepts the substring to be replaced, and returns what to
   *                 replace it with. Note that JS can actually provide more
   *                 data to this function, but we don't expose that. See docs.
   *
   * !! Not supported by IE !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/replaceAll
   */
  def replaceAll(what: String | js.RegExp, replacer: js.Function1[String, String]): JsString = js.native

  /**
   * Returns the index of the first match between the regular expression and
   * the given string, or -1 if no match was found.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/search
   */
  def search(regexp: String | js.RegExp): Int = js.native

  /**
   * Extracts the text from the string and returns it.
   *
   * If beginIndex is negative, slice() begins extraction from the end.
   *
   * @param beginIndex The zero-based index at which to begin extraction.
   * @param endIndex The zero-based index *before* which to end extraction.
   *                 The character at this index will not be included.
   *                 If endIndex is omitted, the entire remainder of the
   *                 string is extracted.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/slice
   */
  def slice(beginIndex: Int, endIndex: Int = js.native): JsString = js.native

  /**
   * If `separator` is does not occur in str, the returned array contains one
   * element consisting of the entire string.
   *
   * If `separator` appears at the beginning (or end) of the string, it still
   * has the effect of splitting.  The result is an empty (i.e. zero length)
   * string, which appears at the first (or last) position of the returned
   * array.
   *
   * If `separator` is an empty string (""), str is converted to an array of
   * each of its UTF-16 "characters".
   *
   * @param limit Max number of substrings to be included in the array.
   *              Any leftover text is *not* included in the array at all.
   *              If limit is 0, [] is returned.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/split
   */
  def split(separator: String | js.RegExp, limit: Int = js.native): JsArray[String] = js.native

  /**
   * Returns characters from `indexStart` up to but not including `indexEnd`.
   *
   * If `indexEnd` is omitted, the entire remainder of the string is returned.
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/substring
   */
  def substr(indexStart: Int, indexEnd: Int = js.native): JsString = js.native

  /**
   * Returns a new string with the whitespace from both ends of the original
   * string removed. Whitespace in this context is all the whitespace
   * characters (space, tab, no-break space, etc.) and all the line
   * terminator characters (LF, CR, etc.).
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/trim
   */
  def trim(): JsString = js.native

  /** Like trim(), but only trims the start of the string
   *
   * !! Not supported by IE, not supported by MobileSafari < 12 !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/trimStart
   */
  def trimStart(): JsString = js.native

  /** Like trim(), but only trims the end of the string
   *
   * !! Not supported by IE, not supported by MobileSafari < 12 !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/trimEnd
   */
  def trimEnd(): JsString = js.native

  def toUpperCase(): JsString = js.native

  def toLowerCase(): JsString = js.native

  /**
   * Locale-aware upper-case
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/toLocaleUpperCase
   */
  def toLocaleUpperCase(
    locales: String | JsArray[String] | js.Array[String]
  ): JsString = js.native

  /**
   * Locale-aware lower-case
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/toLocaleLowerCase
   */
  def toLocaleLowerCase(
    locales: String | JsArray[String] | js.Array[String]
  ): JsString = js.native

  /**
   * Returns a number indicating whether a reference string comes before,
   * or after, or is the same as the given string in sort order: negative
   * number if referenceStr occurs before compareString; positive if the
   * referenceStr occurs after compareString; 0 if they are equivalent.
   *
   * `locales` and `options` customize the behavior of the function and let
   * applications specify the language whose formatting conventions should
   * be used.
   *
   * !! Not supported by Android WebView !!
   *
   * @see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/localeCompare
   */
  def localeCompare(
    compareString: String,
    locales: js.UndefOr[String | JsArray[String] | js.Array[String]] = js.undefined,
    options: js.UndefOr[js.Object] = js.undefined
  ): Int = js.native

}

object JsString {

  implicit class RichJsString(val self: JsString) extends AnyVal {

    @inline def str: String = asScalaJs

    /** Cast a JsString to scala.String. It's safe because they have the same runtime representation. */
    def asScalaJs: String = self.asInstanceOf[String]
  }

  class RichString(val str: String) extends AnyVal {

    /** Cast a scala.String JsString. It's safe because they have the same runtime representation. */
    @inline def ew: JsString = str.asInstanceOf[JsString]
  }
}
