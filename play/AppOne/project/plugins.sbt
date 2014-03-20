// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

//resolvers += "Reaktor" at "http://mtkopone.github.io/scct/maven-repo/reaktor"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

//addSbtPlugin("com.sqality.scct" % "sbt-scct" % "0.3.1-SNAPSHOT")
//addSbtPlugin("com.sqality.scct" % "sbt-scct" % "0.3")

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.1")

