import ReleaseTransformations._

lazy val replicaSettings = Seq(
  organization := "com.mthaler",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.11.8", "2.12.8", "2.13.0"),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % "test"
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature"
  ),
  licenses += ("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("http://github.com/mthaler/replica")),

  // release stuff
  // credentials += Credentials(Path.userHome / ".ivy2" / ".credentials"),
  releaseCrossBuild := true,
  releasePublishArtifactsAction := PgpKeys.publishSigned.value,
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := Function.const(false),
  publishTo := sonatypePublishTo.value,
  pomExtra :=
    <scm>
      <url>git@github.com:mthaler/replica.git</url>
      <connection>scm:git:git@github.com:mthaler/replica.git</connection>
    </scm>
    <developers>
      <developer>
        <id>m_t</id>
        <name>Michael Thaler</name>
        <url>http://github.com/mthaler/</url>
      </developer>
    </developers>
  ,
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    ReleaseStep(action = Command.process("package", _)),
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    ReleaseStep(action = Command.process("publishSigned", _)),
    setNextVersion,
    commitNextVersion,
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _)),
    pushChanges))

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false)

lazy val root = project.in(file("."))
  .aggregate(core)
  .settings(name := "root")
  .settings(replicaSettings: _*)
  .settings(noPublish: _*)

lazy val core = project.in(file("."))
  .settings(name := "replica")
  .settings(replicaSettings: _*)
