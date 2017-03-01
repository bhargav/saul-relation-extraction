version := "0.0.1"

resolvers ++= Seq(
  "CogComp Software" at "http://cogcomp.cs.illinois.edu/m2repo/"
)

libraryDependencies += "edu.illinois.cs.cogcomp" %% "saul" % "0.5.7"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.github.bhargav",
      scalaVersion := "2.11.8"
    )),
    name := "saul-relation-extraction"
  )
