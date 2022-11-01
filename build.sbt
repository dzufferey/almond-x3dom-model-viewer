name := "almond-x3dom-model-viewer"

organization := "com.github.dzufferey"

version := "0.2.4-snapshot"

scalaVersion := "3.1.3"

crossScalaVersions := Seq("2.12.17", "2.13.10", "3.1.3")

scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature"
)

resolvers += "jitpack" at "https://jitpack.io" // for "com.github.jupyter" % "jvm-repr"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "scalatags" % "0.12.0"
)

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((3, _)) => Seq(
      ("sh.almond" %% "jupyter-api" % "0.13.1").cross(CrossVersion.for3Use2_13)
    )
    case _ => Seq(
      "sh.almond" %% "jupyter-api" % "0.13.1"
    )
  }
}
