name := "almond-x3dom-model-viewer"

organization := "com.github.dzufferey"

version := "0.2.1"

scalaVersion := "2.13.6"

crossScalaVersions := Seq("2.12.14", "2.13.6")

scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature"
)

resolvers += "jitpack" at "https://jitpack.io" // for "com.github.jupyter" % "jvm-repr"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "scalatags" % "0.9.4",
  "sh.almond" %% "jupyter-api" % "0.11.2"
)
