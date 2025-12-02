package com.emlett.aoc.y2021

object Day03 : Year2021() {

  private fun mostCommonBits(input: List<String>, default: Int = 1): String {
    val out = IntArray(input.first().length)
    input.forEach { line ->
      line.forEachIndexed { index, c ->
        out[index] = (out.getOrNull(index) ?: 0) + c.toString().toInt()
      }
    }

    return out.map {
      when {
        it * 2 > input.size -> 1
        it * 2 < input.size -> 0
        else -> default
      }
    }.joinToString(separator = "")
  }

  override fun part1(): Any {
    val bGamma = mostCommonBits(lines)
    val bEpsilon = bGamma.map {
      when (it) {
        '0' -> '1'
        '1' -> '0'
        else -> throw IllegalArgumentException()
      }
    }.joinToString(separator = "")

    return bGamma.toInt(2) * bEpsilon.toInt(2)
  }

  override fun part2(): Any {
    val ogr = (0..lines[0].length).fold(lines) { acc, i ->
      if (acc.size == 1) {
        return@fold acc
      } else {
        val mcb = mostCommonBits(acc, 1)
        acc.filter { it[i] == mcb[i] }
      }
    }.first()

    val csr = (0..lines[0].length).fold(lines) { acc, i ->
      if (acc.size == 1) {
        return@fold acc
      } else {
        val lcb = mostCommonBits(acc, 1).map(Char::digitToInt).map { (it + 1) % 2 }.joinToString("")
        acc.filter { it[i] == lcb[i] }
      }
    }.first()

    return ogr.toInt(2) * csr.toInt(2)
  }

}
