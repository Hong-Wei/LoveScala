name := "LoveScala"

version := "1.0"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

List(webSettings: _*)
resolvers += Resolver.sonatypeRepo("releases")

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
    "com.typesafe.akka" %% "akka-actor" % "2.4.17", //,
    "com.h2database" % "h2" % "1.4.187",
    // For Akka 2.4.x or 2.5.x
    "com.typesafe.akka" %% "akka-http" % "10.0.10",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9" % Test,
    // Only when running against Akka 2.5 explicitly depend on akka-streams in same version as akka-actor
    "com.typesafe.akka" %% "akka-stream" % "2.5.4", // or whatever the latest version is
    "com.typesafe.akka" %% "akka-actor" % "2.5.4", // or whatever the latest version is
    //    "com.github.alexarchambault" %% "argonaut-shapeless_6.2" % "1.2.0-M4"

    "com.typesafe.akka" %% "akka-slf4j" % "2.5.4",
    //https://github.com/typesafehub/scala-logging#prerequisites
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    //https://github.com/typesafehub/scala-logging#getting-scala-logging
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2"
  )
}



 