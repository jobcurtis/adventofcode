package com.emlett.aoc.y2022

object Day05 : Year2022() {
    private val regex = Regex("""^move (\d+) from (\d+) to (\d+)$""")
    private val instructions = lines.mapNotNull(regex::matchEntire).map { it.groupValues.drop(1).map(String::toInt) }

    private val stackedChars = rawtext
        .substringBefore("\n\n")
        .lines()
        .dropLast(1)
        .map { row -> row.chunked(4).map { it.firstOrNull(Char::isLetter) } }

    private fun getStacks(): Array<ArrayDeque<Char>> =
        stackedChars.fold(Array(stackedChars.first().size, ::ArrayDeque)) { acc, chars ->
            chars.forEachIndexed { index, c -> if (c != null) acc[index].addFirst(c) }
            acc
        }

    override fun part1() = instructions.fold(getStacks()) { stacks, (amount, from, dest) ->
        (0 until amount).map { stacks[from - 1].removeLast() }.forEach { stacks[dest - 1].addLast(it) }
        return@fold stacks
    }.map(ArrayDeque<Char>::last).joinToString("")

    override fun part2() = instructions.fold(getStacks()) { stacks, (amount, from, dest) ->
        (0 until amount).map { stacks[from - 1].removeLast() }.asReversed().forEach { stacks[dest - 1].addLast(it) }
        return@fold stacks
    }.map(ArrayDeque<Char>::last).joinToString("")
}
