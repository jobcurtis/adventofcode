package com.emlett.aoc.y2022

object Day07 : Year2022() {
  private val filesystem by lazy {
    val stack = ArrayDeque<Directory>().apply { addFirst(Directory()) }

    for (line in lines) {
      val parts = line.split(' ')
      when {
        parts[0] == "dir" || parts[1] == "ls" -> continue
        parts[0] != "$" -> stack.last().contents += parts[0].toLong()
        parts[1] == "cd" && parts[2] == "/" -> stack.subList(1, stack.size).clear()
        parts[1] == "cd" && parts[2] == ".." -> stack.removeLast()
        parts[1] == "cd" -> Directory().also(stack.last().children::add).also(stack::addLast)
      }
    }

    return@lazy stack.first()
  }

  private val directories by lazy { filesystem.toList() }

  override fun part1() = directories.filter { it.size <= 100000 }.sumOf { it.size }
  override fun part2() = directories.filter { it.size >= 30000000 - (70000000 - filesystem.size) }.minOf { it.size }

  private class Directory {
    val children: MutableList<Directory> = mutableListOf()
    var contents: Long = 0
    val size: Long by lazy { children.sumOf { it.size } + contents }
  }

  private fun Directory.toList(): List<Directory> {
    return listOf(this) + children.flatMap { it.toList() }
  }
}
