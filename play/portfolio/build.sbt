name := "portfolio"

version := "1.0-SNAPSHOT"

resolvers += "gideondk-repo" at "https://raw.github.com/gideondk/gideondk-mvn-repo/master"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.scalapenos" %% "riak-scala-client" % "0.9.0",
  "com.basho.riak" % "riak-client" % "1.4.4",
  "com.typesafe" %% "play-plugins-mailer" % "2.1-RC2"
)     

play.Project.playScalaSettings
