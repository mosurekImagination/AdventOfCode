package net.mosur

import scala.collection.parallel.CollectionConverters._


case class Entry(destinationStart: Long, sourceStart: Long, range: Long)

object Day5 extends App {

  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>
    val input = lines.mkString("\n")
    val regex = """(\d+\s)+\n""".r
    val iterator = regex.findAllIn(input).map(_.trim).toList
    val mapList = iterator.tail.map(
      _.split("\n")
        .map(_.split(" ").map(_.toLong))
        .map { array => Entry(array.head, array.tail.head, array.last) }
        .toList
    )
    val result = part match
      case Part.One => iterator.head.split(" ").map(_.toLong).toList
      case Part.Two => iterator.head.split(" ").map(_.toLong).sliding(2, 2).toList
        .zipWithIndex
        .par
        .flatMap { (a, index) =>
          println(s"Processing ${index}")
          val result = (a.head until a.head + a.last)
            .par
            .map { seed =>
              //println(s"2:Processing ${index} ")
              mapList.foldLeft(seed) { (currentNumber, map) =>
                map.find { entry =>
                  entry.sourceStart <= currentNumber && entry.sourceStart + entry.range > currentNumber
                }.map { entry =>
                  currentNumber - entry.sourceStart + entry.destinationStart
                } getOrElse currentNumber
              }
            }
          println(s"Finished processing ${index}")
          result
        }.min
    println(s"$part Result is $result. Expected result is: $expected ")
  }

  time {
    println("Part 1:")
    execute(readLines("day5-small.txt"), 35, Part.One)
    execute(readLines("day5.txt"), 227653707, Part.One)
    println("Part 2:")
    execute(readLines("day5-small.txt"), 46, Part.Two)
    execute(readLines("day5.txt"), 13768818, Part.Two)
  }
}