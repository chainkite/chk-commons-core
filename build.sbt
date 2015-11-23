import Dependencies._
import ShellPromptPlugin._


lazy val VERSION = "0.1.0-SHAPSHOT"

lazy val jvmSource = "1.8"
lazy val jvmTarget = "1.8"


lazy val commonDeps = Seq(
  slf4j,
  scalaLogging,
  typesafeConfig,
  scalatest
)

lazy val buildSettings = Seq(
  organization := "com.chn",
  version in ThisBuild := VERSION,
  scalaVersion := "2.11.7",
  shellPrompt := buildShellPrompt(version.value),
  unmanagedJars in Compile <<= baseDirectory.map(base => (base / "lib" ** "*.jar").classpath),
  scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:higherKinds", "-language:postfixOps",
    "-language:implicitConversions", "-language:reflectiveCalls", "-language:existentials", s"-target:jvm-$jvmTarget",
    "-encoding", "utf8"),
  javacOptions in Compile ++= Seq("-target", jvmTarget, "-source", jvmSource, "-Xlint:deprecation")
)

lazy val `chk-commons` = (project in file(".")).
    settings(buildSettings: _*).
    aggregate(`chk-commons-core`)

lazy val `chk-commons-core` = (project in file("core")).
  settings(buildSettings: _*).
  settings(
    libraryDependencies ++= commonDeps ++ Seq(
      scaldiCore % Provided, mavenArtifact % Provided
    )
  )