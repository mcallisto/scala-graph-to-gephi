import mill._
import scalalib._
import publish._
import coursier.maven.MavenRepository
import mill.util.Loose

trait Packageable {

  val organization: String = "vision.id"

  val artName: String = "graphgephi"

}

trait Versioned extends ScalaModule with PublishModule with Packageable {

  val gitName: String = "scala-graph-to-gephi"

  def scalaVersion: T[String] = "2.12.8"

  def publishVersion: T[String] = "0.1.1"

  override def artifactName: T[String] = artName

  def pomSettings: T[PomSettings] = PomSettings(
    description = "Basic conversion tool to visualize Graph4Scala graphs with the Gephi toolkit",
    organization = organization,
    url = "https://github.com/mcallisto/" + gitName,
    licenses = Seq(License.`GPL-3.0`),
    versionControl = VersionControl.github("mcallisto", gitName),
    developers = Seq(
      Developer("mcallisto", "Mario Càllisto", "https://github.com/mcallisto")
    )
  )

}

trait Testable extends ScalaModule {

  override def ivyDeps: T[Agg[Dep]] = super.ivyDeps() ++ Agg(
    ivy"org.scalatest::scalatest:3.0.5",
    ivy"org.scalacheck::scalacheck:1.14.0"
  )

}

object jvm extends Versioned { outer ⇒

  override def repositories: Seq[coursier.Repository] = super.repositories ++ Seq(
    MavenRepository("http://bits.netbeans.org/nexus/content/groups/netbeans/"),
    MavenRepository("https://raw.github.com/gephi/gephi/mvn-thirdparty-repo/")
  )

  override def ivyDeps: T[Agg[Dep]] = super.ivyDeps() ++ Agg(
    ivy"org.scala-graph::graph-core:1.12.5",
    ivy"com.lihaoyi::os-lib:0.2.6",
    ivy"org.gephi:gephi-toolkit:0.9.2"
  )

  def coverageMeasurementsDir = T.persistent(T.ctx().dest)
  override def scalacOptions = T(super.scalacOptions() ++ Seq(s"-P:scoverage:dataDir:${coverageMeasurementsDir().toIO.getAbsolutePath}"))
  override def compileIvyDeps = Agg(ivy"org.scoverage::scalac-scoverage-runtime:1.3.1")
//  override def scalacOptions: T[Seq[String]] = Seq("-P:scoverage:dataDir:out/jvm/scoverage/")
  override def scalacPluginIvyDeps: T[Loose.Agg[Dep]] = Agg(ivy"org.scoverage::scalac-scoverage-plugin:1.3.1")

//  def coverage(args: String*) = T.command {
//    super.compile
//  }

  object test extends outer.Tests with Testable with Packageable {

//    override def compileIvyDeps = Agg(ivy"org.scoverage::scalac-scoverage-runtime:1.3.0")
//    override def scalacOptions: T[Seq[String]] = Seq("-P:scoverage:dataDir:out/jvm/scoverage/")
//    override def scalacPluginIvyDeps: T[Loose.Agg[Dep]] = Agg(ivy"org.scoverage::scalac-scoverage-plugin:1.3.0")

    override def compileIvyDeps: T[Loose.Agg[Dep]] = Agg.empty[Dep]
    override def scalacOptions: T[Seq[String]] = Seq("")
    override def scalacPluginIvyDeps: T[Loose.Agg[Dep]] = Agg.empty[Dep]

    def testFrameworks: T[Seq[String]] = Seq("org.scalatest.tools.Framework")

    def one(args: String*) = T.command {
      super.runMain("org.scalatest.run", args.map(List(organization, artName, _).mkString(".")): _*)
    }
  }

}
