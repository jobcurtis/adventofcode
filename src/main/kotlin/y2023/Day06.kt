package com.emlett.aoc.y2023

object Day06 : Year2023() {
    override fun part1() = lines
        .map { line -> Regex("""\d+""").findAll(line).map { it.value.toLong() }.toList() }
        .let { (times, distances) -> times.zip(distances) }
        .map { (time, distance) -> Result(time, distance) }
        .map(Result::winners)
        .reduce(Int::times)

    override fun part2() = lines
        .map { it.digits.toLong() }
        .let { (time, distance) -> Result(time, distance) }
        .winners

    private data class Result(val time: Long, val distance: Long) {
        val winners: Int get() = (0..time).count { t -> t * (time - t) > distance }
    }
}
