organization := "vision.id"
name := "graphgephi"
version := "0.1.1"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  "NetBeans" at "http://bits.netbeans.org/nexus/content/groups/netbeans/",
  "gephi-thirdparty" at "https://raw.github.com/gephi/gephi/mvn-thirdparty-repo/"
)

libraryDependencies ++= Seq(
  "org.scala-graph" %% "graph-core" % "1.12.5",
  "com.lihaoyi" %% "os-lib" % "0.2.6",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.gephi" % "gephi-toolkit" % "0.9.2" classifier "all"
)

Compile / unmanagedSourceDirectories += baseDirectory.value / "jvm" / "src"
Test / unmanagedSourceDirectories += baseDirectory.value / "jvm" / "test" / "src"