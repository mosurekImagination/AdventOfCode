package net.mosur

import scala.util.matching.Regex
import scala.util.Try

object Day1 extends App {

  extension (str: String) {
    def extractInt: String = {
      extractIntMatch(str).getOrElse(str.substring(0, str.length - 1).extractInt)
    }
  }

  def extractIntMatch(str: String): Option[String] = {
    str match
      case "one" | "1" => Some("1")
      case "two" | "2" => Some("2")
      case "three" | "3" => Some("3")
      case "four" | "4" => Some("4")
      case "five" | "5" => Some("5")
      case "six" | "6" => Some("6")
      case "seven" | "7" => Some("7")
      case "eight" | "8" => Some("8")
      case "nine" | "9" => Some("9")
      case _ => None
  }

  val execute: (List[String], Int, Part) => Unit = { (lines, expected, part) =>
    val regex = part match {
      case Part.One => "\\d".r
      case Part.Two => "(?=(\\d|one|two|three|four|five|six|seven|eight|nine))".r
    }
    val result = lines.map { it =>
        val matches = regex.findAllIn(it).matchData.map(_.start).toList
        val first = matches.head
        val last = matches.last
        val text = it + "XXXXX"
        text.substring(first, first + 5).extractInt + text.substring(last, last + 5).extractInt
      }
      .map(_.toInt)
      .sum

    println(s"$part Result is $result. Expected result is: $expected ")
  }

  //  //part1
  //  execute(readLines("day1-small.txt"), 142, Part.One)
  //  execute(readLines("day1.txt"), 54331, Part.One)
  //  //
  //  //  //part2
    execute(readLines("day1-small-2.txt"), 281, Part.Two)
    execute(readLines("day1.txt"), 54533, Part.Two)
  //
  //  //54533 is wrong - too high
  //
  //  //tests
  //  execute(List("five"), 55, Part.Two)
  //  execute(List("afivea"), 55, Part.Two)
  //  execute(List("a6fivea"), 65, Part.Two)
  //  execute(List("a61fivea"), 65, Part.Two)
  //  execute(List("sixonefive"), 65, Part.Two)
  private val eightwo = "eightwo"
  execute(List(eightwo), 82, Part.Two)

  val regex = "(?=(\\d|one|two|three|four|five|six|seven|eight|nine))".r
  regex.findAllIn(eightwo).matchData.map(_.start).map { start =>
      (eightwo + "zzzzz").substring(start, start + 5).extractInt
    }
    .foreach(println(_))
}
