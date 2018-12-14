package vision.id.graphgephi

import org.scalatest.FlatSpec
import os.RelPath
import scalax.collection.Graph
import scalax.collection.GraphPredef._

import scala.util.{Failure, Success}

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

  "A directed graph" can "be drawn as a PNG image" in {
    val path = "out/jvm/test/myTest/"
    val name = "directed.png"
    val g = Graph(1 ~> 2, 2 ~> 3, 3 ~> 4, 4 ~> 5, 5 ~> 1, 1 ~> 4, 6 ~> 4)
    val f = makeImage(g, path, name)
    assert(f match {
      case Success(f) => f.isFile
      case Failure(_) => false
    })

  }

}
