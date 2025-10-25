name := "almond-x3dom-model-viewer"

organization := "com.github.dzufferey"

version := "0.2.4-SNAPSHOT"

scalaVersion := "3.3.7"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature"
)

resolvers += "jitpack" at "https://jitpack.io" // for "com.github.jupyter" % "jvm-repr"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "scalatags" % "0.13.1",
  "sh.almond" %% "jupyter-api" % "0.14.1-2"
)
