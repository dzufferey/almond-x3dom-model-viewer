name := "almond-x3dom-model-viewer"

organization := "com.github.dzufferey"

version := "0.2.0"

scalaVersion := "2.13.4"

crossScalaVersions := Seq("2.12.12", "2.13.4")

scalacOptions in Compile ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature"
)

resolvers += "jitpack" at "https://jitpack.io" // for "com.github.jupyter" % "jvm-repr"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "scalatags" % "0.9.2",
  "sh.almond" %% "jupyter-api" % "0.10.9"
)
