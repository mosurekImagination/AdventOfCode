package net.mosur

object Day6 extends App {

  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>
    val numberRegex = "\\d+".r
    val (times, minDistance) = part match
      case Part.One => (numberRegex.findAllIn(lines.head).map(_.toLong).toList, numberRegex.findAllIn(lines.last).map(_.toLong).toList)
      case Part.Two => (numberRegex.findAllIn(lines.head).mkString("").toLong :: Nil, numberRegex.findAllIn(lines.last).mkString("").toLong :: Nil)
    val result = times
      .zip(minDistance)
      .map { (time, minDistance) =>
        (1L until time).map { accelerationTime =>
          (time - accelerationTime) * accelerationTime
        }.count(_ > minDistance)
      }.product
    println(s"$part Result is $result. Expected result is: $expected ")
  }

  time {
    println("Part 1:")
    execute(readLines("day6-small.txt"), 288, Part.One)
    execute(readLines("day6.txt"), 633080, Part.One)
    println("Part 2:")
    execute(readLines("day6-small.txt"), 71503, Part.Two)
    execute(readLines("day6.txt"), 20048741, Part.Two)
  }
}