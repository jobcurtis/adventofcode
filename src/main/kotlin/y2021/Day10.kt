package com.emlett.aoc.y2021

object Day10 : Year2021() {
    private val pairs = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    private val values = mapOf(')' to 1L, ']' to 2L, '}' to 3L, '>' to 4L)

    override fun part1() = lines.mapNotNull { line ->
        val stack = ArrayDeque<Char>()
        line.firstOrNull { char ->
            when (char) {
                in pairs.values -> stack.addLast(char).let { false }
                in pairs.keys -> {
                    val lastChar = stack.removeLast()
                    lastChar != pairs.getValue(char)
                }
                else -> false
            }
        }
    }.sumOf {
        when (it) {
            ')' -> 3L
            ']' -> 57L
            '}' -> 1197L
            '>' -> 25137L
            else -> throw IllegalStateException()
        }
    }

    override fun part2() = lines.mapNotNull { line ->
        val stack = ArrayDeque<Char>()
        line.forEach { char ->
            when (char) {
                in pairs.values -> stack.addLast(char).let { false }
                in pairs.keys -> {
                    val lastChar = stack.removeLast()
                    if (lastChar != pairs.getValue(char)) return@mapNotNull null
                }
            }
        }
        stack.reversed().map { pairs.entries.single { (_, v) -> v == it }.key }
    }.map { it.map(values::getValue).reduce { acc, i -> (acc * 5) + i } }.median()

}
