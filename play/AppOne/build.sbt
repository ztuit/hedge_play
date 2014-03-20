name := "AppOne"

version := "1.0-SNAPSHOT"

resolvers ++= Seq("Sonatype Releases"   at "http://oss.sonatype.org/content/repositories/releases",
					"gideondk-repo" at "https://raw.github.com/gideondk/gideondk-mvn-repo/master",
                  "Spray Repository"    at "http://repo.spray.io/")
                  //"Reaktor" at "http://mtkopone.github.io/scct/maven-repo")

libraryDependencies ++= {
val sprayVersion = "1.2.0"
	Seq(
  		jdbc,
  		anorm,
  		cache,
   //  	"io.spray"                %   "spray-client"           % sprayVersion,
   // 	"io.spray"                %%  "spray-json"             % "1.2.5",
    	"com.scalapenos" %% "riak-scala-client" % "0.9.0"
	)
}    





play.Project.playScalaSettings


