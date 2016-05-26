
import com.typesafe.sbt.SbtNativePackager.{packageArchetype, _}
import com.typesafe.sbt.packager.Keys._
name := "netty"

version := "1.0"

scalaVersion := "2.10.5"

mainClass in Compile := Some("com.madhouse.netty.NettyServer")

crossScalaVersions := Seq("2.10.5")

packageArchetype.java_server

packageSummary in Linux := "netty start"

packageDescription := "netty start"

bashScriptConfigLocation := Some("/services/data/netty/conf/jvmopts")

mappings in Universal <++= sourceDirectory map { src =>
  val confDir = src / "main" / "resources"
  confDir.*** pair rebase(confDir, "conf") filterNot(fn => fn._2.contains("app-local.conf"))
}
libraryDependencies += "io.netty" % "netty-all" % "5.0.0.Alpha2"
// http://mvnrepository.com/artifact/ch.qos.logback/logback-classic
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"
// http://mvnrepository.com/artifact/ch.qos.logback/logback-core
libraryDependencies += "log4j" % "log4j" % "1.2.17"
// http://mvnrepository.com/artifact/org.jboss.marshalling/jboss-marshalling
libraryDependencies += "org.jboss.marshalling" % "jboss-marshalling" % "1.4.10.Final"
// http://mvnrepository.com/artifact/com.typesafe.akka/akka-actor_2.11
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.3.15"


javacOptions ++= Seq("-source", "1.8", "-target", "1.8","-encoding", "UTF-8" )
