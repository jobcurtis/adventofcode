package com.emlett.aoc.y2020

object Day13 : Year2020() {
    private val input1 = lines.component1()
    private val input2 = lines.component2()

    private val depart = input1.toInt()
    private val ids = input2.split(',')
    private val earliest = ids.mapNotNull(String::toIntOrNull).minByOrNull { it - (depart % it) }!!

    override fun part1() = earliest * (earliest - (depart % earliest))
    override fun part2() = Day13(lines).solvePart2()

    private class Day13(input: List<String>) {

        private val startTime: Int = input.first().toInt()
        private val busses: List<Int> = input.last().split(",").mapNotNull { s -> if (s == "x") null else s.toInt() }

        private val indexedBusses: List<IndexedBus> = input
            .last()
            .split(",")
            .mapIndexedNotNull { index, s -> if (s == "x") null else IndexedBus(index, s.toLong()) }

        fun solvePart1(): Int = generateSequence(startTime) { it + 1 }.mapNotNull { time ->
            busses.firstOrNull { bus -> time % bus == 0 }?.let { bus -> Pair(time, bus) }
        }.first().run { (first - startTime) * second }

        fun solvePart2(): Long {
            var stepSize = indexedBusses.first().bus
            var time = 0L
            indexedBusses.drop(1).forEach { (offset, bus) ->
                while ((time + offset) % bus != 0L) {
                    time += stepSize
                }
                stepSize *= bus // New Ratio!
            }
            return time
        }

        data class IndexedBus(val index: Int, val bus: Long)
    }
}
