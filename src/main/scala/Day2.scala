package net.mosur

object Day2 extends App {

  val execute: (List[String], Int, Part) => Unit = { (lines, expected, part) =>
    val games = lines.map(
      _.split(":").last
        .split(";")
        .map(_.split(",")
          .map(_.trim)
          .map { pick =>
            val count = pick.split(" ")
            count.last -> count.head.toInt
          }
        )
    )
    val availableCubes = Map(
      "red" -> 12,
      "green" -> 13,
      "blue" -> 14
    )

    val result =
      part match
        case Part.One => games
          .zipWithIndex
          .filterNot { (g) =>
            g._1.exists { game =>
              game.exists { round =>
                round._2 > availableCubes(round._1)
              }
            }
          }
          .map(_._2 + 1)
          .sum
        case Part.Two => games.map { game =>
          var minCubes = Map(
            "red" -> 0,
            "green" -> 0,
            "blue" -> 0
          )
          game.foreach { round =>
            round.foreach { colorAndCount =>
              if (colorAndCount._2 > minCubes(colorAndCount._1))
                minCubes = minCubes.updated(colorAndCount._1, colorAndCount._2)
            }
          }
          minCubes.values.product
        }.sum


    println(s"$part Result is $result. Expected result is: $expected ")
  }

  println("Part 1:")
  execute(readLines("day2-small.txt"), 8, Part.One)
  execute(readLines("day2.txt"), 2286, Part.One)

  println("Part 2:")
  execute(readLines("day2-small.txt"), 2286, Part.Two)
  execute(readLines("day2.txt"), 0, Part.Two)
}