package net.mosur

import scala.util.matching.Regex

object Day7 extends App {

  case class Hand(cards: String, sortedCards: String, bid: Int, handType: Type, cardOrderValue: String)

  sealed trait Type(val value: Int, val regex: Regex)

  object FiveOfAKind extends Type(6, """(.)\1{4}""".r)

  object FourOfAKind extends Type(5, """(.)\1{3}""".r)

  object FullHouse extends Type(4, """(.)\1{2}(.)\2{1}|(.)\3{1}(.)\4{2}""".r)

  object ThreeOfAKind extends Type(3, """(.)\1{2}""".r)

  object TwoPair extends Type(2, """(.)\1{1}.?(.)\2{1}""".r)

  object Pair extends Type(1, """(.)\1{1}""".r)

  object HighCard extends Type(0, ".*".r)

  val types = List(FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, Pair, HighCard)

  def cardValueAsChar(value: Int): Char = ('@' + value).toChar

  val cardToCharValue = (1 to 9).map(i => i.toString.head -> cardValueAsChar(i)).toMap ++
    Map(
      'T' -> cardValueAsChar(10),
      'J' -> cardValueAsChar(11),
      'Q' -> cardValueAsChar(12),
      'K' -> cardValueAsChar(13),
      'A' -> cardValueAsChar(14)
    )

  val cardToCharValue2 = cardToCharValue.updated('J', cardValueAsChar(0))

  val extractType: String => Type = cards => types.find(_.regex.findFirstIn(cards.sorted).isDefined).get

  val extractMaximumJokerType: String => Type = card => cardToCharValue.keys.map(
    key => card.replaceAll("J", key.toString)
  ).map(extractType(_)).maxBy(_.value)

  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>
    val sorted = lines.map { line =>
      val s"$cards $bid" = line
      part match
        case Part.One => Hand(cards, cards.sorted, bid.toInt, extractType(cards), cards.map(cardToCharValue(_)))
        case Part.Two => Hand(cards, cards.sorted, bid.toInt, extractMaximumJokerType(cards), cards.map(cardToCharValue2(_)))
    }.sortWith((a, b) =>
      if (a.handType.value == b.handType.value)
        a.cardOrderValue < b.cardOrderValue
      else a.handType.value < b.handType.value
    )

    val result = sorted
      .zipWithIndex
      .map((card, index) => card.bid.toLong * (index + 1))
      .sum
    println(s"$part Result is $result. Expected result is: $expected ")
  }
  time {
    println("Part 1:")
    execute(readLines("day7-small.txt"), 6440, Part.One)
    execute(readLines("day7.txt"), 253954294, Part.One)

    println("Part 2:")
    execute(readLines("day7-small.txt"), 5905, Part.Two)
    execute(readLines("day7.txt"), 254837398, Part.Two)
  }
}