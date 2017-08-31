name := "LoveScala"

version := "1.0"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

seq(webSettings: _*)

//resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= {
  val liftVersion = "2.6-RC1"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile",
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" %
      "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" %
      "container,compile" artifacts Artifact("javax.servlet", "jar", "jar"),
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
    "com.typesafe.akka" %% "akka-actor" % "2.4.17",//,
    "com.h2database" % "h2" % "1.4.187"
//    "com.github.alexarchambault" %% "argonaut-shapeless_6.2" % "1.2.0-M4"
  )
}



 