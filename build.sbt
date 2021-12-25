name := """divu"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice
libraryDependencies += javaJdbc
libraryDependencies += jdbc

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))


libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"
libraryDependencies += "io.jsonwebtoken" % "jjwt" % "0.9.0"

//Firebase
libraryDependencies += "com.google.firebase" % "firebase-admin" % "6.1.0"

//Recharge Epay
libraryDependencies += "javax.xml.rpc" % "javax.xml.rpc-api" % "1.1.1"
libraryDependencies += "org.apache.axis" % "axis" % "1.4"
libraryDependencies += "wsdl4j" % "wsdl4j" % "1.6.2"
libraryDependencies += "commons-discovery" % "commons-discovery" % "0.5"

//NodeJs
libraryDependencies += "io.socket" % "socket.io-client" % "1.0.0"

//Email
libraryDependencies += "javax.mail" % "mail" % "1.4.7"