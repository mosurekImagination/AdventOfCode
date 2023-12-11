package net.mosur

import scala.annotation.tailrec

object Day10 extends App {

  implicit class ExtendedListList(matrix: TileMatrix) {
    def get(x: Int, y: Int): Option[Tile] =
      matrix.lift(x).flatMap(_.lift(y))
  }
  case class Tile(val y: Int, val x: Int, tileType: Type)
  type TileMatrix = List[List[Tile]]
  //type Matrix = List[List[]]
  enum Type(val tile: Char):
    case NorthSouth extends Type('|')
    case EastWest extends Type('-')
    case NorthEast extends Type('L')
    case NorthWest extends Type('J')
    case SouthWest extends Type('7')
    case SouthEast extends Type('F')
    case Ground extends Type('.')
    case Start extends Type('S')

  def findType(matrix: TileMatrix, start: Tile) =
    val topTile = matrix.get(start.y-1, start.x)
    val bottomTile = matrix.get(start.y+1, start.x)
    val leftTile = matrix.get(start.y, start.x-1)
    val rightTile = matrix.get(start.y, start.x+1)
    5


  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>
    val result = 1
    val matrix = lines
      .zipWithIndex
      .map(
        (line, yIndex) => line
          .map(
            char => Type.values.find(_.tile == char).get)
          .zipWithIndex
          .map( (tileType, xIndex) => Tile(yIndex, xIndex, tileType))
          .toList
      )
    val start = matrix.map(_.find(_.tileType == Type.Start)).filter(_.nonEmpty)
    //val startType = findType(matrix, start)
    println(s"$part Result is $result. Expected result is: $expected ")

  }
  time {
    println("Part 1:")
    execute(readLines("day10-small.txt"), 8, Part.One)
    //execute(readLines("day10.txt"), 0, Part.One)
    //
    //    println("Part 2:")
    //    execute(readLines("day10-small.txt"), 2, Part.Two)
    //    execute(readLines("day10.txt"), 975, Part.Two)

  }

}