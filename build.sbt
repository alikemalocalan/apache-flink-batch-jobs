name := "ty-flink-jobs"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val flinkVersion = "1.7.1"

  Seq(
    "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,
    "org.apache.flink" %% "flink-clients" % flinkVersion,
    "org.scalaz" %% "scalaz-core" % "7.2.27",
    "commons-logging" % "commons-logging" % "1.2",
    "com.typesafe" % "config" % "1.3.3",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.apache.flink" %% "flink-test-utils" % flinkVersion % Test,
    "org.apache.flink" %% "flink-tests" % flinkVersion % Test,
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )
}