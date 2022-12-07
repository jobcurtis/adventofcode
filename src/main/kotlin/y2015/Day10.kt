package com.emlett.aoc.y2015

object Day10 : Year2015() {
    override fun part1() = generateSequence(text) { lookAndSay(it) }.drop(40).first().length
    override fun part2() = generateSequence(text) { lookAndSay(it) }.drop(50).first().length

    private fun lookAndSay(sequence: String) = buildString {
        var current = sequence.first()
        var count = 0
        for (c in sequence) {
            if (c == current) {
                count++
            } else {
                append(count)
                append(current)
                current = c
                count = 1
            }
        }
        append(count)
        append(current)
    }
}
