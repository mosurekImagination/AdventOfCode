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
    val result = part match
      case Part.One => cards.foldLeft(0.0) { (sum, card) =>
        val intersection = card.head.intersect(card.last)
        if (!intersection.isEmpty)
          sum + pow(2, intersection.length - 1)
        else sum
      }
      case Part.Two =>
        val cardsWithIndexes = cards.zipWithIndex
        cardsWithIndexes.foldRight(Map[Int, Int]()) { (currentCard, cardsPointMap) =>
          val currentCardIndex = currentCard._2
          val currentDeck = currentCard._1
          val intersection = currentDeck.head.intersect(currentDeck.last).length
          val wonCards = cardsWithIndexes.slice(currentCardIndex + 1, currentCardIndex + 1 + intersection)
          val resolvedPointsForGivenCard = wonCards.map { cardInfo => cardsPointMap.getOrElse(cardInfo._2, 0) }.sum + 1
          cardsPointMap + (currentCardIndex -> resolvedPointsForGivenCard)
        }.values.sum
    println(s"$part Result is $result. Expected result is: $expected ")
  }

  time {
    println("Part 1:")
    execute(readLines("day4-small.txt"), 13, Part.One)
    execute(readLines("day4.txt"), 20117, Part.One)

    println("Part 2:")
    execute(readLines("day4-small.txt"), 30, Part.Two)
    execute(readLines("day4.txt"), 13768818, Part.Two)
  }
}