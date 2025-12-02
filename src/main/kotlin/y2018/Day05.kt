package com.emlett.aoc.y2018

object Day05 : Year2018() {
  override fun part1() = text.fold("") { acc, char -> acc.react(char) }.length

  override fun part2() = ('a'..'z').minOf {
    text.fold("") { acc, char -> if (char.equals(it, ignoreCase = true)) acc else acc.react(char) }.length
  }

  fun String.react(char: Char) = if (reactsTo(char)) dropLast(1) else this + char

  fun String.reactsTo(char: Char) = when (val last = lastOrNull()) {
    null -> false
    char -> false
    else -> last.equals(char, ignoreCase = true)
  }
}
