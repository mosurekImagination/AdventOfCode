package net.mosur

object Day8 extends App {

  case class Node(name: String, left: String, right: String)

  val execute: (List[String], Long, Part) => Unit = { (lines, expected, part) =>

    val path = lines.head

    val nodes = lines.drop(2).map { line =>
      val s"$node = ($left, $right)" = line
      node -> Node(node, left, right)
    }.toMap

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
    val result = counter
    println(s"$part Result is $result. Expected result is: $expected ")
  }
  time {
    println("Part 1:")
    execute(readLines("day8-small.txt"), 6, Part.One)
    execute(readLines("day8.txt"), 253954294, Part.One)

    println("Part 2:")
    execute(readLines("day8-small-2.txt"), 6, Part.Two)
    execute(readLines("day8.txt"), 254837398, Part.Two)
  }
}