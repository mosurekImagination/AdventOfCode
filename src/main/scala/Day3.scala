package net.mosur

object Day3 extends App {

  val execute: (List[String], Int, Part) => Unit = { (lines, expected, part) =>
    val specialCharactersRegex = "[\\d.]*[^\\d.][\\d.]*".r
    val asteriskRegex = "\\*".r
    var asterisksMap = Map[(Int, Int), List[Int]]()
    val extractedNumbers = lines.zipWithIndex.flatMap { lineWithIndex =>
      val line = lineWithIndex._1
      val index = lineWithIndex._2
      """(\d+)""".r.findAllIn(line).matchData.map { data =>
        Map[String, Int](
          "start" -> data.start,
          "end" -> data.end,
          "value" -> data.matched.toInt,
          "index" -> index
        )
      }.toList
    }
    val result = part match

      case Part.One => extractedNumbers.filter { data =>
        val index = data("index")
        val startVerticalIndex = if (index - 1 >= 0) index - 1 else 0
        val endVerticalIndex = if (index + 1 < lines.size) index + 1 else lines.size - 1
        val startHorizontalIndex = if (data("start") - 1 >= 0) data("start") - 1 else data("start")
        val endHorizontalIndex = if (data("end") + 1 < lines.size) data("end") + 1 else data("end")
        lines.slice(startVerticalIndex, endVerticalIndex + 1)
          .map(_.slice(startHorizontalIndex, endHorizontalIndex))
          .exists { subLine =>
            specialCharactersRegex.matches(subLine)
          }
      }.map {
        _ ("value")
      }.sum

      case Part.Two =>
        extractedNumbers.foreach { data =>
          val index = data("index")
          val startVerticalIndex = if (index - 1 >= 0) index - 1 else 0
          val endVerticalIndex = if (index + 1 < lines.size) index + 1 else lines.size - 1
          val startHorizontalIndex = if (data("start") - 1 >= 0) data("start") - 1 else data("start")
          val endHorizontalIndex = if (data("end") + 1 < lines.size) data("end") + 1 else data("end")
          lines.slice(startVerticalIndex, endVerticalIndex + 1)
            .map(_.slice(startHorizontalIndex, endHorizontalIndex))
            .zipWithIndex
            .foreach { subLineWithIndex =>
              val subLine = subLineWithIndex._1
              val subLineIndex = subLineWithIndex._2
              asteriskRegex
                .findAllIn(subLine)
                .matchData
                .map(found => ((subLineIndex + startVerticalIndex, startHorizontalIndex + found.start), data("value")))
                .foreach { result =>
                  asterisksMap = asterisksMap.updated(result._1, result._2 :: asterisksMap.getOrElse(result._1, List()))
                }
              asteriskRegex.matches(subLine)
            }
        }
        asterisksMap.filter(_._2.size == 2).map(_._2.product).sum
    println(s"$part Result is $result. Expected result is: $expected ")
  }

  println("Part 1:")
  execute(readLines("day3-small.txt"), 4361, Part.One)
  execute(readLines("day3.txt"), 525119, Part.One)
  println("Part 2:")
  execute(readLines("day3-small.txt"), 467835, Part.Two)
  execute(readLines("day3.txt"), 76504829, Part.Two)
}