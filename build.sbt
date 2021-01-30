lazy val akkaHttpVersion = "10.2.1"
lazy val akkaVersion = "2.6.9"
val circeVersion = "0.13.0"

lazy val global = project
  .in(file("."))
  .aggregate(
    core,
    actors
  )

lazy val core = (project in file("core"))
  .settings(
    inThisBuild(
      List(
        organization := "com.talentmind.procapi.counter",
        scalaVersion := "2.13.3"
      )),
    name := "Counter example",
    mainClass in Compile := Some("com.talentmind.procapi.counter.CounterApp"),
    dockerBaseImage := "openjdk:jre-alpine",
    dockerExposedPorts := Seq(9090),
    packageName := "counter",
    version := "1.0",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      //"com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "de.heikoseeberger" %% "akka-http-circe" % "1.35.3",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,

      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "org.scalatest" %% "scalatest" % "3.1.4" % Test
    )
  )
  .dependsOn(actors)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .enablePlugins(AshScriptPlugin)

lazy val actors = (project in file("actors"))
  .settings(
    inThisBuild(
      List(
        organization := "com.talentmind.procapi.counter",
        scalaVersion := "2.13.3"
      )),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      //"com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "de.heikoseeberger" %% "akka-http-circe" % "1.35.3",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,

      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "org.scalatest" %% "scalatest" % "3.1.4" % Test
    )
  )
