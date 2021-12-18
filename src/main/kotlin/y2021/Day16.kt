package com.emlett.aoc.y2021

object Day16 : Year2021() {
    private val binary = text.trim()
        .map { it.digitToInt(16).toString(2).padStart(4, '0') }
        .joinToString("")

    override fun part1() = Packet.parse(binary).flatten().sumOf { it.version }
    override fun part2() = Packet.parse(binary).value

    private sealed class Packet {
        abstract val version: Int
        abstract val type: Int
        abstract val length: Int
        abstract val input: String
        abstract val value: Long

        abstract val children: List<Packet>

        fun flatten(): List<Packet> = children.flatMap { it.flatten() } + listOf(this)

        companion object {
            fun parse(input: String): Packet {
                require(input.all(('0'..'1')::contains))

                return when (input.drop(3).take(3)) {
                    "100" -> Literal.parse(input)
                    else -> Operator.parse(input)
                }
            }
        }
    }

    private data class Literal(
        override val version: Int,
        override val type: Int,
        override val length: Int,
        override val input: String,
        override val value: Long,
    ) : Packet() {

        override val children get() = emptyList<Packet>()

        companion object {
            fun parse(input: String): Literal {
                val version = input.take(3)
                val type = input.drop(3).take(3)
                val value = input.drop(6).chunked(5).takeUntil { it.first() != '1' }.joinToString("")
                val length = (version.length + type.length + value.length)

                return Literal(
                    version = version.toInt(2),
                    type = type.toInt(2),
                    value = value.chunked(5).joinToString("") { it.drop(1) }.toLong(2),
                    length = length,
                    input = input.take(length),
                )
            }
        }
    }

    private data class Operator(
        override val version: Int,
        override val type: Int,
        override val length: Int,
        override val input: String,
        override val children: List<Packet>,
    ) : Packet() {

        override val value: Long
            get() = children.map { it.value }.let {
                when (type) {
                    0 -> it.sum()
                    1 -> it.reduce(Long::times)
                    2 -> it.min()
                    3 -> it.max()
                    5 -> it.let { (a, b) -> if (a > b) 1 else 0 }
                    6 -> it.let { (a, b) -> if (a < b) 1 else 0 }
                    7 -> it.let { (a, b) -> if (a == b) 1 else 0 }
                    else -> throw IllegalArgumentException()
                }
            }

        companion object {
            fun parse(input: String) = when (input[6]) {
                '0' -> parse0(input)
                '1' -> parse1(input)
                else -> throw IllegalArgumentException()
            }

            fun parse0(input: String): Operator {
                val version = input.take(3)
                val type = input.drop(3).take(3)
                val childrenLength = input.drop(7).take(15).toInt(2)
                val length = 1 + 3 + 3 + 15 + childrenLength
                var childrenInput = input.drop(1 + 3 + 3 + 15).take(childrenLength)
                val children = mutableListOf<Packet>()

                while (childrenInput.isNotEmpty()) {
                    val child = Packet.parse(childrenInput)
                    children += child
                    childrenInput = childrenInput.drop(child.length)
                }

                return Operator(version = version.toInt(2),
                    type = type.toInt(2),
                    length = length,
                    input = input.take(length),
                    children = children)
            }

            fun parse1(input: String): Operator {
                val version = input.take(3)
                val type = input.drop(3).take(3)
                val childrenCount = input.drop(7).take(11).toInt(2)
                val children = (0 until childrenCount).fold(emptyList<Packet>()) { acc, _ ->
                    acc + Packet.parse(input.drop(7 + 11 + acc.sumOf { it.length }))
                }
                val length = 7 + 11 + children.sumOf { it.length }

                return Operator(version = version.toInt(2),
                    type = type.toInt(2),
                    length = length,
                    input = input.take(length),
                    children = children)
            }
        }
    }
}
