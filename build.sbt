name := "LoveScala"

version := "1.0"

scalaVersion := "2.11.8"

List(webSettings: _*)
resolvers += Resolver.sonatypeRepo("releases")
resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= {
  val liftVersion = "3.1.0"
  Seq(
//    scala core
    "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.5",
    
//    liftweb library
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile",
    
//    jetty
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910" % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,compile" artifacts Artifact("javax.servlet", "jar", "jar"),

//    akka
    "com.typesafe.akka" %% "akka-http" % "10.0.10",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.9" % Test,
    "com.typesafe.akka" %% "akka-stream" % "2.5.4", // or whatever the latest version is
    "com.typesafe.akka" %% "akka-actor" % "2.5.4", // or whatever the latest version is
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.4",

//    log
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
    
//    tests
    "com.h2database" % "h2" % "1.4.187",
    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
}



 