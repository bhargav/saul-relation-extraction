// to automatically add header descriptions to files
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "1.6.0")

// scala style formatter
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// to generate dependency tree based upon the library dependencies
addSbtPlugin("com.gilt" % "sbt-dependency-graph-sugar" % "0.7.5-1")

// to check dependency updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")
