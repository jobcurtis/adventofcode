package com.emlett.aoc.y2025

object Day06 : Year2025() {
  val whitespace = Regex("""\s+""")
  val input = lines.map { it.split(whitespace).filterNot { it.isEmpty() } }

  override fun part1(): Any {
    var sum = 0L
    for (i in input.first().indices) {
      val op: (Long, Long) -> Long = if (input.last()[i] == "*") Long::times else Long::plus
      val res = input.dropLast(1).map { it[i].toLong() }.reduce(op)
      sum = sum.plus(res)
    }
    return sum
  }

  override fun part2(): Any {
    var sum = 0L
    var numbers = mutableListOf<Long>()
    for (i in lines.first().indices.reversed()) {
      val chars = lines.mapNotNull { it.getOrNull(i) }.filterNot { it == ' ' }
      if (chars.isEmpty()) {
        numbers = mutableListOf()
        continue
      }

      numbers += chars.filter { it.isDigit() }.joinToString("").toLong()

      when (chars.last()) {
        '+' -> sum += numbers.sum()
        '*' -> sum += numbers.reduce(Long::times)
      }
    }
    return sum
  }
}
