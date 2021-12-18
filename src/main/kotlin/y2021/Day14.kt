package com.emlett.aoc.y2021

object Day14 : Year2021() {
    private val instructions = lines.drop(2).map { it.split(" -> ") }.associate { (a, b) -> a to a[0] + b + a[1] }

    private val template = Pair(
        lines.first().groupingBy { it }.eachCount().mapValues { (_, v) -> v.toLong() },
        lines.first().windowed(2).groupBy { it }.mapValues { (_, v) -> v.size.toLong() },
    )

    private val sequence = generateSequence(template) { it.apply(instructions) }.map { lazy { it.first.values.run { max() - min() } } }

    override fun part1() = sequence.elementAt(10).value
    override fun part2() = sequence.elementAt(40).value

    private fun Pair<Map<Char, Long>, Map<String, Long>>.apply(instructions: Map<String, String>) = let { (chars, pairs) ->
        fun <T> List<Pair<T, Long>>.amalgamate(other: Map<T, Long>) = plus(other.toList())
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }

        val newChars = pairs.map { (k, v) -> instructions.getValue(k)[1] to v }.amalgamate(chars)
        val newPairs = pairs
            .flatMap { (k, v) -> instructions.getValue(k).windowed(2).map { it to v }.plus(k to -v) }
            .amalgamate(pairs)

        Pair(newChars, newPairs)
    }
}
