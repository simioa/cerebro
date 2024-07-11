name := "cerebro"

maintainer := "Leonardo Menezes <leonardo.menezes@xing.com>"

packageSummary := "Elasticsearch web admin tool"

packageDescription := """cerebro is an open source(MIT License) elasticsearch web admin tool built
  using Scala, Play Framework, AngularJS and Bootstrap."""

version := "0.9.4"

scalaVersion := "2.13.14"

rpmVendor := "lmenezes"

rpmLicense := Some("MIT")

rpmUrl := Some("http://github.com/lmenezes/cerebro")

libraryDependencies ++= Seq(
  "org.playframework" %% "play"                    % "3.0.4",
  "org.playframework" %% "play-json"               % "3.0.4",
  "org.playframework" %% "play-slick"              % "6.1.1",
  "org.playframework" %% "play-slick-evolutions"   % "6.1.1",
  "org.xerial"        %  "sqlite-jdbc"             % "3.46.0.0"
)

libraryDependencies += filters
libraryDependencies += ws
libraryDependencies += guice
libraryDependencies += specs2 % Test

lazy val root = (project in file(".")).
  enablePlugins(PlayScala, BuildInfoPlugin, LauncherJarPlugin, JDebPackaging, RpmPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "models"
  )

Compile / doc / sources := Seq.empty

enablePlugins(JavaServerAppPackaging)
enablePlugins(SystemdPlugin)

pipelineStages := Seq(digest, gzip)

serverLoading := Some(ServerLoader.Systemd)

Debian / systemdSuccessExitStatus += "143"
Rpm / systemdSuccessExitStatus += "143"

linuxPackageMappings += packageTemplateMapping(s"/var/lib/${packageName.value}")() withUser((Linux / daemonUser).value) withGroup((Linux / daemonGroup).value)
