organization := "vision.id"
name := "graphgephi"
version := "0.1.2"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  "gephi-thirdparty" at "https://raw.github.com/gephi/gephi/mvn-thirdparty-repo/"
)

libraryDependencies ++= Seq(
  "org.scala-graph" %% "graph-core" % "1.12.5",
  "com.lihaoyi" %% "os-lib" % "0.2.8",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test",
  "org.gephi" % "gephi-toolkit" % "0.9.2" classifier "all"
)

dependencyOverrides ++= Seq(
  "org.netbeans.modules" % "org-netbeans-core"              % "RELEASE90",
  "org.netbeans.modules" % "org-netbeans-core-startup-base" % "RELEASE90",
  "org.netbeans.modules" % "org-netbeans-modules-masterfs"  % "RELEASE90",
  "org.netbeans.api"     % "org-openide-util-lookup"        % "RELEASE90"
)

Compile / unmanagedSourceDirectories += baseDirectory.value / "jvm" / "src"
Test / unmanagedSourceDirectories += baseDirectory.value / "jvm" / "test" / "src"