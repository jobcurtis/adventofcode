package com.emlett.aoc.y2023

private typealias Boxes = Map<Int, MutableMap<String, Int>>

object Day15 : Year2023() {
    private val steps by lazy { text.trim().split(',') }
    private val boxes: Boxes by lazy { (0..<256).associateWith { mutableMapOf() } }

    override fun part1() = steps.sumOf(::hash)
    override fun part2() = steps
        .map(::parse)
        .fold(boxes) { boxes, step -> step.apply(boxes) }
        .flatMap { (i, box) -> box.values.mapIndexed { slot, focal -> (i + 1) * (slot + 1) * focal } }
        .sum()

    private fun hash(str: String) = str.fold(0) { acc, c -> ((acc + c.code) * 17).mod(256) }

    private sealed interface Step {
        val label: String
        val hash: Int get() = hash(label)

        fun apply(boxes: Boxes): Boxes = when (this) {
            is Insert -> boxes.apply { getValue(hash)[label] = focal }
            is Remove -> boxes.apply { getValue(hash).remove(label) }
        }
    }

    private data class Insert(override val label: String, val focal: Int) : Step
    private data class Remove(override val label: String) : Step

    private fun parse(str: String): Step = when (str.endsWith('-')) {
        true -> Remove(str.dropLast(1))
        false -> str.split('=').let { (label, focal) -> Insert(label, focal.toInt()) }
    }
}
