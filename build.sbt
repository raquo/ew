import VersionHelper.{versionFmt, fallbackVersion}

// Lets me depend on Maven Central artifacts immediately without waiting
resolvers ++= Resolver.sonatypeOssRepos("public")

// Makes sure to increment the version for local development
ThisBuild / version := dynverGitDescribeOutput.value
  .mkVersion(out => versionFmt(out, dynverSonatypeSnapshots.value), fallbackVersion(dynverCurrentDate.value))

ThisBuild / dynver := {
  val d = new java.util.Date
  sbtdynver.DynVer
    .getGitDescribeOutput(d)
    .mkVersion(out => versionFmt(out, dynverSonatypeSnapshots.value), fallbackVersion(d))
}

enablePlugins(ScalaJSPlugin)

libraryDependencies ++= Seq(
  "org.scalatest" %%% "scalatest" % Versions.ScalaTest % Test
)

scalaVersion := Versions.Scala_2_13

crossScalaVersions := Seq(Versions.Scala_2_12, Versions.Scala_2_13, Versions.Scala_3)

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:existentials,experimental.macros,higherKinds,implicitConversions"
)

scalacOptions ~= { options: Seq[String] =>
  options.filterNot(Set(
    "-Ywarn-value-discard",
    "-Wvalue-discard"
  ))
}

scalacOptions += {
  val localSourcesPath = baseDirectory.value.toURI
  val remoteSourcesPath = s"https://raw.githubusercontent.com/raquo/ew/${git.gitHeadCommit.value.get}/"
  val sourcesOptionName = if (scalaVersion.value.startsWith("2.")) "-P:scalajs:mapSourceURI" else "-scalajs-mapSourceURI"

  s"${sourcesOptionName}:$localSourcesPath->$remoteSourcesPath"
}

(Test / scalacOptions) ~= { options: Seq[String] =>
  options.filterNot { o =>
    o.startsWith("-Ywarn-unused") || o.startsWith("-Wunused")
  }
}

(Compile / doc / scalacOptions) ~= (_.filterNot(
  Set(
    "-deprecation",
    "-explain-types",
    "-explain",
    "-feature",
    "-language:existentials,experimental.macros,higherKinds,implicitConversions",
    "-unchecked",
    "-Xfatal-warnings",
    "-Ykind-projector",
    "-from-tasty",
    "-encoding",
    "utf8",
  )
))

(Compile / doc / scalacOptions) ++= Seq(
  "-no-link-warnings" // Suppress scaladoc "Could not find any member to link for" warnings
)

(Test / parallelExecution) := false

scalaJSUseMainModuleInitializer := true

(Compile / fastOptJS / scalaJSLinkerConfig) ~= { _.withSourceMap(false) }
