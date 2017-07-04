
import com.typesafe.sbt.SbtNativePackager.{packageArchetype, _}
import com.typesafe.sbt.packager.Keys._
name := "mklog"

version := "1.0"

scalaVersion := "2.10.6"

mainClass in Compile := Some("com.madhouse.netty.NettyServer")

crossScalaVersions := Seq("2.10.6")

packageArchetype.java_server

packageSummary in Linux := "netty start"

packageDescription := "netty start"

bashScriptConfigLocation := Some("/services/data/netty/conf/jvmopts")

mappings in Universal <++= sourceDirectory map { src =>
  val confDir = src / "main" / "resources"
  confDir.*** pair rebase(confDir, "conf") filterNot(fn => fn._2.contains("app-local.conf"))
}
libraryDependencies ++= Seq(
"com.google.guava" % "guava" % "18.0",
 "io.netty" % "netty-all" % "5.0.0.Alpha2",
"ch.qos.logback" % "logback-classic" % "1.1.7",
"log4j" % "log4j" % "1.2.17",
"org.jboss.marshalling" % "jboss-marshalling" % "1.4.10.Final",
"com.typesafe.akka" % "akka-actor_2.10" % "2.3.15",
"org.apache.kafka" % "kafka-clients" % "0.10.2.1"
exclude("log4j", "log4j")
exclude("org.slf4j","slf4j-log4j12"),
"org.apache.kafka" % "kafka-clients" % "0.10.2.1",
"com.alibaba" % "fastjson" % "1.1.15",
"org.apache.commons" % "commons-lang3" % "3.4"
)



javacOptions ++= Seq("-source", "1.8", "-target", "1.8","-encoding", "UTF-8" )
