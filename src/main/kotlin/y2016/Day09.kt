package com.emlett.aoc.y2016

object Day09 : Year2016() {
    override fun part1() = decompress(text).length
    override fun part2() = parse(text).sumOf(Node::invoke)

    private tailrec fun decompress(str: String, index: Int = 0): String {
        val markerStart = str.indexOf('(', startIndex = index)

        return if (markerStart == -1) str else {
            val markerEnd = str.indexOf(')', startIndex = markerStart)
            val marker = str.substring(markerStart + 1, markerEnd)
            val (take, times) = marker.split('x').map(String::toInt)
            val nextIndex = markerEnd + 1 + take
            val add = str.substring(markerEnd + 1, nextIndex).repeat(times)

            val nextStr = str.replaceRange(markerStart, nextIndex, add)
            decompress(nextStr, markerStart + (take * times))
        }
    }

    sealed interface Node : () -> Long
    data class Literal(val value: CharSequence) : Node {
        override fun invoke() = value.length.toLong()
    }

    data class Expression(val value: Collection<Node>, val times: Int) : Node {
        override fun invoke() = value.sumOf { it.invoke() } * times
    }

    private fun parse(str: CharSequence, nodes: List<Node> = emptyList()): List<Node> {
        val start = str.indexOf('(')
        return when {
            start < 0 -> nodes + Literal(str)
            start > 0 -> parse(str.subSequence(start, str.length), nodes + Literal(str.subSequence(0, start)))
            else -> {
                val end = str.indexOf(')')
                val (take, times) = str.subSequence(1, end).split('x').map(String::toInt)
                val expr = Expression(parse(str.subSequence(end + 1, end + take + 1)), times)
                parse(str.subSequence(end + take + 1, str.length), nodes + expr)
            }
        }
    }
}
