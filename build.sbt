version := "0.0.1"

resolvers ++= Seq(
  "CogComp Software" at "http://cogcomp.cs.illinois.edu/m2repo/"
)

val cogcompGroupId = "edu.illinois.cs.cogcomp"
val cogcompNLPVersion = "3.0.100"

libraryDependencies ++= Seq(
  cogcompGroupId %% "saul" % "0.5.7",
  cogcompGroupId % "illinois-core-utilities" % cogcompNLPVersion,
  cogcompGroupId % "illinois-corpusreaders" % cogcompNLPVersion,
  cogcompGroupId % "illinois-mention-relation" % "0.0.5-SNAPSHOT"
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.github.bhargav",
      scalaVersion := "2.11.8",
      scalacOptions ++= Seq("-unchecked", "-deprecation")
    )),
    name := "saul-relation-extraction"
  )
