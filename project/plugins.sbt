addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.8.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")

addSbtPlugin("com.cavorite" % "sbt-avro" % "0.3.2")

addSbtPlugin("com.typesafe.sbt" %% "sbt-twirl" % "1.1.1")

resolvers ++= Seq(
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/",
  "sbt-plugin-releases" at "https://dl.bintray.com/sbt/sbt-plugin-releases/",
  "madhouse" at "http://172.16.26.237:8081/nexus/content/repositories/releases/"
)