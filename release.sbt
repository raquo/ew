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
    url = url("https://github.com/raquo")
  )
)

(Test / publishArtifact) := false

pomIncludeRepository := { _ => false }

sonatypeCredentialHost := "s01.oss.sonatype.org"

sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

