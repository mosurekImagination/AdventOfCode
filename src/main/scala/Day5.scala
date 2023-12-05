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

    val ranges: List[List[Long]] = part match
      case Part.One =>
        // singe seed as range seed + 1
        iterator.head.split(" ").map(_.toLong).toList.map { seed => List(seed, 1) }
      case Part.Two => iterator.head.split(" ").map(_.toLong).sliding(2, 2).map(_.toList).toList

    val result = ranges
      .zipWithIndex
      .par //parallel processing
      .foldRight(Long.MaxValue) { (rangeDefAndIndex, globalMin) =>
        val (rangeDef, index) = rangeDefAndIndex
        val rangeResult = (rangeDef.head until rangeDef.head + rangeDef.last)
          .foldRight(Long.MaxValue) { (seed, rangeMin) =>
            val oneSeedResult = mapList.foldLeft(seed) { (currentNumber, map) =>
              map.find { entry =>
                entry.sourceStart <= currentNumber && entry.sourceStart + entry.range > currentNumber
              }.map { entry =>
                currentNumber - entry.sourceStart + entry.destinationStart
              } getOrElse currentNumber
            }
            if (rangeMin > oneSeedResult) oneSeedResult else rangeMin
          }
        if (globalMin > rangeResult) rangeResult else globalMin
      }
    println(s"$part Result is $result. Expected result is: $expected ")
  }

  time {
    println("Part 1:")
    execute(readLines("day5-small.txt"), 35, Part.One)
    execute(readLines("day5.txt"), 227653707, Part.One)
    println("Part 2:")
    execute(readLines("day5-small.txt"), 46, Part.Two)
    execute(readLines("day5.txt"), 78775051, Part.Two)
  }
}