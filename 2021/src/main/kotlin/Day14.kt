package com.emlett.aoc

private typealias CharCounter = Map<Char, Long>
private typealias PairCounter = Map<String, Long>

fun main() {
    val input = readAsLines("Day14.txt")
    val instructions = input.drop(2).map { it.split(" -> ") }.associate { (a, b) -> a to a[0] + b + a[1] }
    val template = Pair(
        input.first().groupingBy { it }.eachCount().mapValues { (_, v) -> v.toLong() },
        input.first().windowed(2).groupBy { it }.mapValues { (_, v) -> v.size.toLong() },
    )

    val sequence = generateSequence(template) { it.apply(instructions) }
        .map { lazy { it.first.values.run { max() - min() } } }

    sequence
        .elementAt(10)
        .also { println("Part 1: ${it.value}") }

    sequence
        .elementAt(40)
        .also { println("Part 2: ${it.value}") }
}

private fun Pair<CharCounter, PairCounter>.apply(instructions: Map<String, String>) = let { (chars, pairs) ->
    fun <T> List<Pair<T, Long>>.amalgamate(other: Map<T, Long>) = plus(other.toList())
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() }

    val newChars = pairs
        .map { (k, v) -> instructions.getValue(k)[1] to v }
        .amalgamate(chars)
    val newPairs = pairs
        .flatMap { (k, v) -> instructions.getValue(k).windowed(2).map { it to v }.plus(k to -v) }
        .amalgamate(pairs)

    Pair(newChars, newPairs)
}
