enablePlugins(GatlingPlugin)

scalaVersion := "2.11.8"

name := "gatling-sql"

version := "0.1.0"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.4"
libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.2.4"

libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1212"
