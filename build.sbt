lazy val root = project.in(file(".")).enablePlugins(PlayJava)

name := """images3-play"""

version := "0.3.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % "3.0",
  "joda-time" % "joda-time" % "2.4"
)


libraryDependencies ++= Seq(
  "com.images3" % "images3-server" % "0.3.0",
  "com.images3" % "images3-spi-mongodb" % "0.3.0",
  "com.images3" % "images3-spi-imgcontent" % "0.3.0",
  "com.images3" % "images3-spi-imgprocessor" % "0.3.0"
)

resolvers += Resolver.mavenLocal