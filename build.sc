import mill._
import scalalib._
import publish._
import coursier.maven.MavenRepository

trait Packageable {

  val organization: String = "vision.id"

  val name: String = "graphgephi"

}

trait Versioned extends ScalaModule with PublishModule with Packageable {

  val githubName: String = "scala-graph-to-gephi"

  def scalaVersion: T[String] = "2.12.8"

  def publishVersion: T[String] = "0.1.2"

  override def artifactName: T[String] = name

  def pomSettings: T[PomSettings] = PomSettings(
    description = "Basic conversion tool to visualize Graph4Scala graphs with the Gephi toolkit",
    organization = organization,
    url = "https://github.com/mcallisto/" + githubName,
    licenses = Seq(License.`GPL-3.0`),
    versionControl = VersionControl.github("mcallisto", githubName),
    developers = Seq(
      Developer("mcallisto", "Mario Càllisto", "https://github.com/mcallisto")
    )
  )

}

trait Testable extends ScalaModule {

  override def ivyDeps: T[Agg[Dep]] = super.ivyDeps() ++ Agg(
    ivy"org.scalatest::scalatest:3.0.8"
  )

}

object jvm extends Versioned { outer ⇒

  override def repositories: Seq[coursier.Repository] = super.repositories ++ Seq(
    MavenRepository("https://raw.github.com/gephi/gephi/mvn-thirdparty-repo/")
  )

  override def ivyDeps: T[Agg[Dep]] = super.ivyDeps() ++ Agg(
    ivy"org.scala-graph::graph-core:1.12.5",
    ivy"com.lihaoyi::os-lib:0.2.8",
    ivy"org.gephi:gephi-toolkit:0.9.2",
    ivy"org.netbeans.modules:org-netbeans-core:RELEASE90",
    ivy"org.netbeans.modules:org-netbeans-core-startup-base:RELEASE90",
    ivy"org.netbeans.modules:org-netbeans-modules-masterfs:RELEASE90",
    ivy"org.netbeans.api:org-openide-util-lookup:RELEASE90"
  )

  object test extends outer.Tests with Testable with Packageable {

    override def unmanagedClasspath: T[Agg[PathRef]] = T {
      super.unmanagedClasspath() ++ outer.unmanagedClasspath()
    }

    def testFrameworks: T[Seq[String]] = Seq("org.scalatest.tools.Framework")

    def one(args: String*) = T.command {
      super.runMain("org.scalatest.run", args.map(List(organization, name, _).mkString(".")): _*)
    }
  }

}
