name := "ew"

normalizedName := "ew"

organization := "com.raquo"

homepage := Some(url("https://github.com/raquo/ew"))

licenses += ("MIT", url("https://github.com/raquo/ew/blob/master/LICENSE.md"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/raquo/ew"),
    "scm:git@github.com/raquo/ew.git"
  )
)

developers := List(
  Developer(
    id = "raquo",
    name = "Nikita Gazarov",
    email = "nikita@raquo.com",
    url = url("http://raquo.com")
  )
)

sonatypeProfileName := "com.raquo"

publishMavenStyle := true

(Test / publishArtifact) := false

publishTo := sonatypePublishToBundle.value

releaseCrossBuild := true

pomIncludeRepository := { _ => false }

releaseProcess := {
  import ReleaseTransformations._
  Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommandAndRemaining("+publishSigned"),
    releaseStepCommand("sonatypeBundleRelease"),
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
}

//useGpg := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

