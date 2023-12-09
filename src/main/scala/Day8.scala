package net.mosur

import scala.annotation.tailrec

object Day8 extends App {
  case class Node(name: String, left: String, right: String)

  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>

    val path = lines.head
    val nodes = lines.drop(2).map {
      case s"$node = ($left, $right)" => node -> Node(node, left, right)
    }.toMap

    val (startNodes, finishNodes) = part match {
      case Part.One => (nodes.filter((key, _) => key.endsWith("AAA")).values.toList, nodes.filter((key, _) => key.endsWith("ZZZ")).values.toList)
      case Part.Two => (nodes.filter((key, _) => key.endsWith("A")).values.toList, nodes.filter((key, _) => key.endsWith("Z")).values.toList)
    }

    @tailrec
    def countStepUntil(currentNode: Node, finishNodes: List[Node], cyclicPath: Iterator[Char], count: Int = 0): Int = {
      if (finishNodes.contains(currentNode)) count
      else
        val nextNode = if (cyclicPath.next() == 'L')
          nodes(currentNode.left)
        else
          nodes(currentNode.right)
        countStepUntil(nextNode, finishNodes, cyclicPath, count + 1)
    }

    val result = startNodes.map { startNode =>
      val cyclicPath = LazyList.iterate(path)(_ => path).flatten.iterator
      countStepUntil(startNode, finishNodes, cyclicPath, 0).toLong
    }.lcm
    println(s"$part Result is $result. Expected result is: $expected ")
  }
  time {
    println("Part 1:")
    execute(readLines("day8-small.txt"), 6, Part.One)
    execute(readLines("day8.txt"), 16531, Part.One)

    println("Part 2:")
    execute(readLines("day8-small-2.txt"), 6, Part.Two)
    execute(readLines("day8.txt"), 24035773251517L, Part.Two)
  }

  implicit class LCMListOps(numbers: List[Long]) {
    def gcd(a: Long, b: Long): Long = if (b == 0) a.abs else gcd(b, a % b)

    // Function to calculate the least common multiple (LCM) of two numbers
    def lcm(a: Long, b: Long): Long = if (a == 0 || b == 0) 0 else (a.abs * b.abs) / gcd(a, b)

    def lcm: Long = {
      numbers.foldLeft(1L) { (acc, num) =>
        lcm(acc, num)
      }
    }
  }

}