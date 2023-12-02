# ew
[![Build status](https://github.com/raquo/ew/actions/workflows/test.yml/badge.svg)](https://github.com/raquo/ew/actions/workflows/test.yml)
[![Chat on https://discord.gg/JTrUxhq7sj](https://img.shields.io/badge/chat-on%20discord-7289da.svg)](https://discord.gg/JTrUxhq7sj)
[![Maven Central](https://img.shields.io/maven-central/v/com.raquo/ew_sjs1_3.svg)](https://search.maven.org/artifact/com.raquo/ew_sjs1_3)

**ew** _(pronounced as \<unfathomable disgust\>)_ is a collection of Scala.js interfaces to native JavaScript data structures (such as arrays and maps) that offers sometimes significant performance advantages by sacrificing Scala semantics (such as structural equality of case classes).

**ew** is very useful, but only in very limited circumstances. It is primarily intended for library authors writing internal code that will not be exposed to end users, with the public API made safe and reasonable by nothing but the authors' understanding and diligence.

    "com.raquo" %%% "ew" % "<version>"  // Scala.js



## Background

When we program in [Scala.js](https://www.scala-js.org/), we are happy and chill. We don't normally need to worry about JavaScript semantics. We generally treat `js.Array` as if it's part of Scala collections library.

However, the underlying JavaScript types don't naturally know anything about Scala semantics, and reconciling that has a cost. For example, finding an index of a certain value in an array is exceptionally fast in plain JavaScript because JS arrays are heavily optimized in every browser engine. However, what if this `js.Array` contains instances of a Scala case class? The browser engine knows nothing about Scala's `equals` method and the case classes' structural equality semantics, so on its own it would produce results that don't always make sense in Scala.

Well, that wouldn't cut it for us, Scala connoisseurs, would it? And so Scala.js takes care of this behind the scenes. You can find Scala.js' implementation of `js.Array`'s `indexOf` method in their [ArrayOps](https://github.com/scala-js/scala-js/blob/main/library/src/main/scala-new-collections/scala/scalajs/js/ArrayOps.scala) file. As you can see it has to iterate over the array to apply Scala equality checks, and that has no chance of being as fast as the browser engine's C++ implementation of `indexOf`.

Despite the inefficiency, this works just fine for most users most of the time. Nothing is ever free, and correctness-over-performance is usually a good default tradeoff.

**ew** is for the poor souls who need to juice more performance out of JS data structures. When using **ew**, you're only calling raw JS methods (or a faster equivalent, if available). You get code that's as fast as possible, but you pay for that with increased cognitive load and general annoyance.



## Documentation

Available interfaces: [String](https://github.com/raquo/ew/blob/master/src/main/scala/com/raquo/ew/JsString.scala), [Iterable](https://github.com/raquo/ew/blob/master/src/main/scala/com/raquo/ew/JsIterable.scala), [Array](https://github.com/raquo/ew/blob/master/src/main/scala/com/raquo/ew/JsArray.scala), [Set](https://github.com/raquo/ew/blob/master/src/main/scala/com/raquo/ew/JsSet.scala), [Map](https://github.com/raquo/ew/blob/master/src/main/scala/com/raquo/ew/JsMap.scala).

Also: [Vector](https://github.com/raquo/ew/blob/master/src/main/scala/com/raquo/ew/ext/JsVector.scala), and immutable version of Array.

The interfaces defined in **ew** follow the corresponding native JS types very closely. We keep JS names for methods even if they go against Scala naming conventions (so we have `Array.forEach`, not `Array.foreach`). Since such methods lack Scala semantics, we _don't_ actually want to make them look at home in Scala.

See comments in the code for any details. Don't expect a comprehensive explanation of JS semantics, it's something you need to know (or test) yourself. See also how this library is used in [Airstream](https://github.com/raquo/Airstream) and [Laminar](https://github.com/raquo/Laminar).

Import `com.raquo.ew._` to get all types and implicits. Convert Scala types to **ew** types using `ew` extension methods (or factories like `JsArray.from(scalaJsArray)`), and use the `asScalaJs` methods on `ew` types to go back. These conversions are zero-cost at runtime, they're just `.asInstanceOf`.



## Browser Compatibility

Some native JS methods have limited browser compatibility (mostly just a problem with Internet Explorer). Scaladoc should say when no version of IE supports a given method, otherwise look it up on MDN: [Array](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array#browser_compatibility), [Set](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Set#browser_compatibility), [Map](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Map#browser_compatibility).

Note that some **Scala.js** methods are not supported by all browsers either. For example, `js.Set.foreach` is implemented using JS iterators, but no version of IE supports those.


## Scala Semantics

Scala.js attempts to preserve Scala semantics for JS types, but **ew** does not. The main concern are equality checks: native JS types use only reference equality to compare objects, they're not aware of Scala's `equals` method or case classes' structural equality. Aside from the obvious effect on methods like `indexOf`, this also affects the uniqueness checks of `Set` values and `Map` keys.

Note that Scala.js itself does not _always_ wrap JS types with Scala semantics. For example, `js.Array#contains` does perform Scala equality checks, whereas `js.Map#contains` does not, because that's not really feasible. Of course in practice that's not a huge deal – Scala has proper Maps in its own collections library, so `js.Map` is used mostly for interop with JS libraries where JS values are expected (and Scala semantics are not needed).


## Performance

Some Scala.js methods are actually faster than native JS methods. For example, `js.Array.foreach` appears to be faster than native JS `Array.forEach` method (at least when called from Scala.js with a `scala.Function` callback). I'm not sure why, but probably because it's implemented using a for loop, and I guess those are faster than Javascript's native `Array.forEach` method? Because of this, **ew**'s `JsArray.forEach` method does not call into native JS forEach method, but has a loop-based implementation too. The **only** purpose of this library is performance, so we reserve the right to do such ugly things where it makes sense.

I did not check every method's performance, only the ones that I was personally [bottlenecked](https://github.com/raquo/Laminar/issues/108) by – `Array.indexOf`, and `forEach` for all types. I don't expect performance problems with other methods.

Basically, **ew** defaults to native JS implementation, unless a different implementation is known to offer an equivalent API with superior performance. It would be nice to set up proper benchmarks for every method, but I don't have time for that. I have _very_ rudimentary benchmarks in my [laminar-examples](https://github.com/raquo/laminar-examples) repo (see MiscBenchmarks file). Contributions of a better test setup are welcome!



## Resources

* [Scala.js semantics](https://www.scala-js.org/doc/semantics.html) – Differences in behaviour from Scala on JVM
* [Cross-Platform Language Design](https://lampwww.epfl.ch/~doeraene/thesis/) – Sébastien's Scala.js PhD thesis
* [MDN Reference](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference) – Frontend developer's best friend 



## Author

Nikita Gazarov – [@raquo](https://twitter.com/raquo)

Please [sponsor my open source work](https://github.com/sponsors/raquo) to make it more sustainable.



## License

**ew** is provided under the [MIT license](https://github.com/raquo/Airstream/blob/master/LICENSE.md).

**ew** type definitions were originally derived from Scala.js code licensed under the [Apache 2.0 License](https://github.com/scala-js/scala-js/blob/main/LICENSE).

Comments that borrow from or link to MDN are derived from content created by Mozilla Contributors and are licensed under Creative Commons Attribution-ShareAlike license (CC-BY-SA), v2.5.
