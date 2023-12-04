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

def time[T](block: => T): T = {
          val before = System.currentTimeMillis
          val result = block
          val after = System.currentTimeMillis
           println("Elapsed time: " + (after - before) + "ms")
           result
}