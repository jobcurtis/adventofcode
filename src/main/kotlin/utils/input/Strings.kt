package com.emlett.aoc.utils.input

import com.emlett.aoc.utils.geometry.Point2D

fun String.integers() = split(' ').filter(String::isNotEmpty).map(String::toInt)

fun <T> Regex.extract(str: String, block: (List<String>) -> T) = matchEntire(str)!!.groupValues.drop(1).run(block)
fun <T> Regex.extractInts(str: String, block: (List<Int>) -> T) = extract(str) { s -> s.map(String::toInt).run(block) }

fun String.parseGrid(invertY: Boolean = false) =
  lines().flatMapIndexed { y, row -> row.mapIndexed { x, c -> Point2D(x, if (invertY) -y else y) to c } }.toMap()

fun String.clean() = lines().filter(String::isNotBlank).joinToString("\n", prefix = "\n", transform = String::trimEnd)

fun ints(string: String): List<Int> = buildList {
  var start = -1

  for ((i, ch) in string.withIndex()) when {
    ch.isDigit() || (ch == '-' && start == -1) -> if (start == -1) start = i
    start != -1 -> add(string.substring(start, i).toInt()).also { start = -1 }
  }

  if (start != -1) add(string.substring(start).toInt())
}
