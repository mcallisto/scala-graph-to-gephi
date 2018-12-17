package vision.id.graphgephi

import scala.util.{Failure, Success}

import org.scalatest.FlatSpec

import scalax.collection.Graph
import scalax.collection.GraphEdge.UnDiEdge
import scalax.collection.GraphPredef._
import os.RelPath

/**
  * @author Mario Càllisto
  */
class containerTest extends FlatSpec with Drawable {

  "An undirected graph" can "be converted to a Gephi container" in {
    val g = Graph(1 ~ 2, 2 ~ 3, 3 ~ 1)
    val c = toContainer(g)
    assert(c match {
      case Success(container) =>
        val loader = container.getLoader
        loader.nodeExists("0") &&
        loader.nodeExists("1") &&
        loader.nodeExists("2") &&
        !loader.nodeExists("3") &&
        loader.edgeExists("0", "1") &&
        loader.edgeExists("0", "2") &&
        loader.edgeExists("1", "2")
      case Failure(_) => false
    })
  }

  val path: String            = "out/jvm/test/myTest/"
  val name: String            = "directed"
  val g: Graph[Int, UnDiEdge] = Graph(1 ~> 2, 2 ~> 3, 3 ~> 4, 4 ~> 5, 5 ~> 1, 1 ~ 4, 6 ~> 4)
  os.makeDir.all(os.Path(RelPath(path), os.pwd))

  "A directed graph" can "be drawn as a PNG image" in {
    assert(makeImage(g, path, name + ".png") match {
      case Success(png) => png.isFile
      case Failure(_)   => false
    })
  }

  it can "be drawn as an SVG image" in {
    assert(makeImage(g, path, name + ".svg") match {
      case Success(svg) => svg.isFile
      case Failure(_)   => false
    })
  }

}
