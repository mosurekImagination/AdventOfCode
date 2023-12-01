package net.mosur

import scala.io.Source

sealed trait Part

object Part {
  case object One extends Part

  case object Two extends Part
}

def readLines(path: String): List[String] = {
  Source.fromResource(path).getLines().toList
}