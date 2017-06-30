import de.heikoseeberger.sbtheader.HeaderPattern

val headerMsg =  """/** This software is released under the University of Illinois/Research and Academic Use License. See
                   |  * the LICENSE file in the root folder for details. Copyright (c) 2016
                   |  *
                   |  * Developed by: The Cognitive Computations Group, University of Illinois at Urbana-Champaign
                   |  * http://cogcomp.cs.illinois.edu/
                   |  */
                   |""".stripMargin

version := "0.0.1"

resolvers ++= Seq(
  "CogComp Software" at "http://cogcomp.cs.illinois.edu/m2repo/",
  Resolver.mavenLocal
)

val cogcompGroupId = "edu.illinois.cs.cogcomp"
val cogcompNLPVersion = "3.1.1"

libraryDependencies ++= Seq(
  cogcompGroupId %% "saul" % "0.5.8-SNAPSHOT",
  cogcompGroupId % "illinois-core-utilities" % cogcompNLPVersion,
  cogcompGroupId % "illinois-corpusreaders" % cogcompNLPVersion,
  cogcompGroupId % "illinois-nlp-pipeline" % cogcompNLPVersion,
  cogcompGroupId % "illinois-mention-relation" % "0.0.5-SNAPSHOT",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.cogcomp",
      scalaVersion := "2.11.8",
      scalacOptions ++= Seq("-unchecked", "-deprecation")
    )),
    name := "saul-relation-extraction",
    headers := Map(
      "scala" -> (HeaderPattern.cStyleBlockComment, headerMsg),
      "java" -> (HeaderPattern.cStyleBlockComment, headerMsg)
    )
  )
  .enablePlugins(AutomateHeaderPlugin)
