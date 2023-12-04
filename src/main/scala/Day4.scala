package net.mosur

import scala.math.pow

object Day4 extends App {

  val execute: (List[String], Int, Part) => Unit = { (lines, expected, part) =>
    val cards = lines.map(
      _.split(":").last
        .split("\\|")
        .map(_.trim)
        .map(
          _.split(" ")
            .filter(_.nonEmpty)
            .map(_.toInt)
        )

    )
    val result = if (part == Part.One)
      cards.foldLeft(0.0) { (sum, card) =>
        val intersection = card.head.intersect(card.last)
        if (!intersection.isEmpty)
          sum + pow(2, intersection.length - 1)
        else sum
      }
    else
      val cardsWithIndexes = cards.zipWithIndex
      var cardsToProcess = cards.zipWithIndex
      var count = 0.0
      while (cardsToProcess.nonEmpty){
        val currentCard = cardsToProcess.head
        val currentCardIndex = currentCard._2
        val currentDeck = currentCard._1
        count = count + 1
        val intersection = currentDeck.head.intersect(currentDeck.last).length
        cardsToProcess = cardsToProcess ::: cardsWithIndexes.slice(currentCardIndex + 1, currentCardIndex + 1 + intersection)
        cardsToProcess = cardsToProcess.tail
      }
      count
    println(s"$part Result is $result. Expected result is: $expected ")
  }

  println("Part 1:")
  execute(readLines("day4-small.txt"), 13, Part.One)
  execute(readLines("day4.txt"), 20117, Part.One)

  println("Part 2:")
  execute(readLines("day4-small.txt"), 30, Part.Two)
  execute(readLines("day4.txt"), 30, Part.Two)
}