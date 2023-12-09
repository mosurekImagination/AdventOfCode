package net.mosur

import scala.annotation.tailrec

object Day9 extends App {
  case class Node(name: String, left: String, right: String)

  def extractSublines(currentLine: List[Int]): List[List[Int]] =
    @tailrec
    def extractSublines(currentLine: List[Int], extractedLines: List[List[Int]]): List[List[Int]] =
      if (currentLine.forall(_ == 0))
        extractedLines
      else
        val differencesSubline = currentLine.sliding(2, 1).map(pair => pair.last - pair.head).toList
        extractSublines(differencesSubline, differencesSubline :: extractedLines)

    extractSublines(currentLine, List(currentLine))

  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>
    val result = lines.map(
      _.split(" ").map(_.toInt).toList
    ).map(extractSublines(_).foldRight(0)((subLine, extrapolatedSum) =>
      part match
        case Part.One => subLine.last + extrapolatedSum
        case Part.Two => subLine.head - extrapolatedSum
    )
    ).sum
    println(s"$part Result is $result. Expected result is: $expected ")

  }
  time {
    println("Part 1:")
    execute(readLines("day9-small.txt"), 114, Part.One)
    execute(readLines("day9.txt"), 1641934234, Part.One)

    println("Part 2:")
    execute(readLines("day9-small.txt"), 2, Part.Two)
    execute(readLines("day9.txt"), 975, Part.Two)

  }

}