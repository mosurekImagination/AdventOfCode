package net.mosur

object Day8 extends App {
  case class Node(name: String, left: String, right: String)

  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>

    val path = lines.head
    val nodes = lines.drop(2).map { line =>
      val s"$node = ($left, $right)" = line
      node -> Node(node, left, right)
    }.toMap

    val startNodes = nodes.filter((key, _) => key.endsWith("A")).values.toList
    val finishNodes = nodes.filter((key, _) => key.endsWith("Z")).values.toList

    val result = part match {
      case Part.One =>
        var counter = 0
        val cyclicPath = LazyList.iterate(path)(_ => path).flatten.iterator
        val startNode = nodes("AAA")
        val finishNode = nodes("ZZZ")
        var currentNode = startNode
        while (currentNode != finishNode) {
          counter = counter + 1
          if (cyclicPath.next() == 'L')
            currentNode = nodes(currentNode.left)
          else
            currentNode = nodes(currentNode.right)
        }
        counter
      case Part.Two => startNodes.flatMap { startNode =>
        val cyclicIndexes = LazyList.continually(path.indices).flatten.iterator
        val cyclicPath = LazyList.iterate(path)(_ => path).flatten.iterator
        var currentNode = startNode
        var endStepList = List[(Node, Int)]()
        var endCounters = List[Long]()
        var counter = 0
        var currentIndex = cyclicIndexes.next()
        while ((currentNode != startNode || counter == 0) && !endStepList.contains((currentNode, currentIndex))) {
          if (finishNodes.contains(currentNode)) {
            endCounters = endCounters :+ counter
            endStepList = endStepList :+ (currentNode, currentIndex)
          }
          if (cyclicPath.next() == 'L')
            currentNode = nodes(currentNode.left)
          else
            currentNode = nodes(currentNode.right)
          counter = counter + 1
          currentIndex = cyclicIndexes.next()
        }
        endCounters
      }.lcm
    }
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