package net.mosur

object Day2 extends App {

  val execute: (List[String], Int, Part) => Unit = { (lines, expected, part) =>
    val games = lines.map(
      _.split(":").last
        .split(";")
        .map(_.split(",")
          .map(_.trim)
          .map { pick =>
            val countAndColour = pick.split(" ")
            countAndColour.last -> countAndColour.head.toInt
          }
        )
    )
    val availableCubes = Map(
      "red" -> 12,
      "green" -> 13,
      "blue" -> 14
    )
    val minCubes = Map(
      "red" -> 0,
      "green" -> 0,
      "blue" -> 0
    )

    val result =
      part match
        case Part.One => games
          .zipWithIndex
          .filterNot { (g, _) =>
            g.exists { game =>
              game.exists { (color, count) =>
                count > availableCubes(color)
              }
            }
          }
          .map(_._2 + 1)
          .sum
        case Part.Two => games.map { game =>
          game.foldLeft(minCubes) { (minCubes, round) =>
            round.foldLeft(minCubes) { (minCubes, colorAndCount) =>
              val (color, count) = colorAndCount
              if (count > minCubes(color))
                minCubes.updated(color, count)
              else minCubes
            }
          }.values.product
        }.sum


    println(s"$part Result is $result. Expected result is: $expected ")
  }

  println("Part 1:")
  execute(readLines("day2-small.txt"), 8, Part.One)
  execute(readLines("day2.txt"), 2286, Part.One)

  println("Part 2:")
  execute(readLines("day2-small.txt"), 2286, Part.Two)
  execute(readLines("day2.txt"), 72596, Part.Two)
}