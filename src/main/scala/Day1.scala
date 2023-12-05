package net.mosur

object Day1 extends App {

  private def parseNumber(str: String): Option[String] = {
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
      val firstMatchIndex = matches.head
      val lastMatchIndex = matches.last
      val digits = """\d|one|two|three|four|five|six|seven|eight|nine""".r
      parseNumber(digits.findPrefixOf(it.substring(firstMatchIndex)).get).get + parseNumber(digits.findPrefixOf(it.substring(lastMatchIndex)).get).get
    }
      .map(_.toInt)
      .sum

    println(s"$part Result is $result. Expected result is: $expected ")
  }

  println("Part 1:")
  execute(readLines("day1-small.txt"), 142, Part.One)
  execute(readLines("day1.txt"), 54331, Part.One)

  println("Part 2:")
  execute(readLines("day1-small-2.txt"), 281, Part.Two)
  execute(readLines("day1.txt"), 54518, Part.Two)


  println("Tests section:")
  execute(List("five"), 55, Part.Two)
  execute(List("afivea"), 55, Part.Two)
  execute(List("a6fivea"), 65, Part.Two)
  execute(List("a61fivea"), 65, Part.Two)
  execute(List("sixonefive"), 65, Part.Two)
  execute(List("eightwo"), 82, Part.Two)
}
