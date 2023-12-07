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

  def getCharValue(value: Int): Char = ('@' + value).toChar

  val charValues = (1 to 9).map(i => i.toString.head -> getCharValue(i)).toMap ++
    Map(
      'T' -> getCharValue(10),
      'J' -> getCharValue(11),
      'Q' -> getCharValue(12),
      'K' -> getCharValue(13),
      'A' -> getCharValue(14)
    )

  val extractType: String => Type = sortedCards => types.find(_.regex.findFirstIn(sortedCards).isDefined).get


  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>
    val sorted = lines.map { line =>
      val s"$cards $bid" = line
      Hand(cards, cards.sorted, bid.toInt, extractType(cards.sorted), cards.map(charValues(_)))
    }.sortWith((a, b) =>
      if (a.handType.value == b.handType.value)
        a.cardOrderValue < b.cardOrderValue
      else a.handType.value < b.handType.value
    )

    val result = sorted
      .zipWithIndex
      .map((card, index) =>
        card.bid.toLong * (index + 1)
      ).sum
    println(s"$part Result is $result. Expected result is: $expected ")
  }

  //247070026 too low
  //253930474 too low
  time {
    println("Part 1:")
    execute(readLines("day7-small.txt"), 6440, Part.One)
    execute(readLines("day7.txt"), 253954294, Part.One)
    println("Part 2:")
    execute(readLines("day7-small.txt"), 5905, Part.Two)
    execute(readLines("day7.txt"), 20048741, Part.Two)
  }
}