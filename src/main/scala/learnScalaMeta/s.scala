package scalaworld

import org.scalameta.logger // useful for debugging

object Playground extends App {
  import scala.meta._

    val tokens = "val x = 2".tokenize.get
    logger.elem(tokens.syntax)
    logger.elem(tokens.structure)
  
  
    val tree = "val x = 2".parse[Stat].get
    logger.elem(tree.syntax)
    logger.elem(tree.structure)

}
