package com.emlett.aoc.y2016

import com.emlett.aoc.utils.input.extract

object Day10 : Year2016() {
    private val valRegex = Regex("""^value (\d+) goes to (\w+ \d+)$""")
    private val botRegex = Regex("""^(\w+ \d+) gives low to (\w+ \d+) and high to (\w+ \d+)$""")

    private val nodes: MutableMap<String, Node> by lazy {
        lines
            .filter { it.startsWith("bot") }
            .map { botRegex.extract(it) { (key, low, high) -> Bot(key, low, high) } }
            .associateBy { it.key }
            .toMutableMap()
    }

    override fun part1(): String {
        lines
            .filter { line -> line.startsWith("value") }
            .map { valRegex.extract(it) { (value, target) -> value.toInt() to target } }
            .forEach { (value, target) -> nodes[target]?.also { node -> node(value) } }

        return setOf(61, 17).let { nodes.values.first { node -> node.contents.containsAll(it) }.key }
    }

    override fun part2() = setOf("output 0", "output 1", "output 2")
        .mapNotNull { nodes[it] }
        .map { it.contents.first() }
        .reduce { a, b -> a * b }

    sealed interface Node : (Int) -> Unit {
        val key: String
        val contents: Set<Int>
    }

    data class Output(override val key: String, override val contents: MutableSet<Int> = mutableSetOf()) : Node {
        override fun invoke(value: Int) {
            contents.add(value)
        }
    }

    data class Bot(override val key: String, val l: String, val h: String) : Node {
        override val contents: MutableSet<Int> = mutableSetOf()

        override fun invoke(value: Int) {
            contents.add(value)

            if (contents.size == 2) {
                nodes.getOrPut(l) { Output(l) }.also { node -> node(contents.min()) }
                nodes.getOrPut(h) { Output(h) }.also { node -> node(contents.max()) }
            }
        }
    }
}
