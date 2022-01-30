# ew

[![Join the chat at https://gitter.im/Laminar_/Lobby](https://badges.gitter.im/Laminar_/Lobby.svg)](https://gitter.im/Laminar_/Lobby)
![Maven Central](https://img.shields.io/maven-central/v/com.raquo/ew_sjs1_2.13.svg)

**ew** _(pronounced as \<unfathomable disgust\>)_ is a collection of Scala.js interfaces to native JavaScript data structures such as arrays and maps that offers significant performance advantages by sacrificing Scala semantics (such as structural equality of case classes).

**ew** is very useful, but only in very limited circumstances. It is primarily intended for library authors writing internal code that will not be exposed to end users, with the public API made safe and reasonable by nothing but the authors' good understanding and mitigation of the differences in Scala and JavaScript semantics.

    "com.raquo" %%% "ew" % "<version>"  // Scala.js



## Background

When we program in [Scala.js](https://www.scala-js.org/), we are happy and chill. We don't normally need to worry about JavaScript semantics. We generally treat `js.Array` as if it's part of Scala collections library.

However, the underlying JavaScript types don't naturally know anything about Scala semantics, and reconciling that has a cost. For example, finding an index of a certain value in an array is exceptionally fast in plain JavaScript because JS arrays are heavily optimized in every browser engine. However, what if this `js.Array` contains Scala case classes? The browser engine knows nothing about Scala's `equals` method and the case classes' structural equality semantics, so on its own it would produce results that don't always make sense in Scala.

Well, that wouldn't cut it for us, Scala connoisseurs, would it? And so Scala.js takes care of this behind the scenes. You can find Scala.js' implementation of `js.Array`'s `indexOf` method in their [ArrayOps](https://github.com/scala-js/scala-js/blob/main/library/src/main/scala-new-collections/scala/scalajs/js/ArrayOps.scala) file – as you can see it has to iterate over the array using Scala code – which has more logic and compiles to JavaScript, and has no chance of being as fast as the browser engine's C++ implementation of `indexOf`.

Even so, this is all nice and well, and works just fine for most users most of the time. Nothing is ever free, and correctness-over-performance is a good default tradeoff.

**ew** is for those poor souls who need to juice more performance out of JS data structures. When using **ew**, you're only calling raw JS methods, not any Scala wrappers over them.



## Documentation

The interfaces defined in **ew** follow the corresponding native JS types very closely. We keep JS names for methods even if they go against Scala naming conventions (so we have `Array.includes`, not `Array.contains`). Since such methods lack Scala semantics, we don't want to make them look at home in Scala.

See comments in the code for any details. Don't expect a comprehensive explanation of JS semantics, it's something you need to know (or test) yourself.

See how this library is used in [Airstream](https://github.com/raquo/Airstream) and [Laminar](https://github.com/raquo/Laminar).

Note that some native JS methods have limited browser compatibility (mostly just a problem with Internet Explorer). Scaladoc should say when no version of IE supports a given method, otherwise you'll need to check the MDN links provided in scaladoc.

Note that Scala.js itself does not _always_ wrap JS types with Scala semantics. For example, `js.Array#contains` performs Scala equality checks, whereas `js.Map#contains` does not, because that's not really feasible. Of course in practice that's not a huge deal because Scala has proper Maps in its own collections library, js.Map is used mostly for interop with JS libraries and performance.

Anyway, if you need **ew**, chances are you know what you're doing, so I don't need to spend time writing more prose.



## Resources

* [Scala.js semantics](https://www.scala-js.org/doc/semantics.html)
* [Cross-Platform Language Design](https://lampwww.epfl.ch/~doeraene/thesis/) – Sébastien's Scala.js PhD thesis
* [MDN Reference](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference) – Frontend developer's best friend 



## Author

Nikita Gazarov – [@raquo](https://twitter.com/raquo)



## License

**ew** is provided under the [MIT license](https://github.com/raquo/Airstream/blob/master/LICENSE.md).

**ew** type definitions were originally derived from Scala.js code licensed under the [Apache 2.0 License](https://github.com/scala-js/scala-js/blob/main/LICENSE).

Comments that borrow from or link to MDN are derived from content created by Mozilla Contributors and are licensed under Creative Commons Attribution-ShareAlike license (CC-BY-SA), v2.5.
