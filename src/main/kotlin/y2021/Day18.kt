package com.emlett.aoc.y2021

import kotlinx.serialization.json.*
import kotlin.math.*

object Day18 : Year2021() {
    private val numbers = lines.map { line -> SnailFishNumber.parse(line) }

    override fun part1() = numbers.reduce(SnailFishNumber::plus).magnitude
    override fun part2() = numbers.flatMap { numbers.map(it::plus) }.map(SnailFishNumber::magnitude).max()

    sealed interface SnailFishNumber {
        val magnitude: Int

        fun plusLeft(other: SnailFishNumber): SnailFishNumber
        fun plusRight(other: SnailFishNumber): SnailFishNumber

        operator fun plus(other: SnailFishNumber) = Pair(l = this, r = other).reduce()

        fun reduce() = generateSequence(this) { number -> number.explode() ?: number.split() }.last()

        fun explode(): SnailFishNumber? {
            data class ExplodeResult(val new: SnailFishNumber, val nl: Literal? = null, val nr: Literal? = null)

            fun explodeRecursive(curr: SnailFishNumber, depth: Int): ExplodeResult? = when (curr) {
                is Literal -> null
                is Pair -> {
                    if (depth >= 4) ExplodeResult(Literal(0), curr.l as Literal, curr.r as Literal)
                    else explodeRecursive(curr.l, depth + 1)?.let { nextL ->
                        ExplodeResult(
                            new = Pair(nextL.new, nextL.nr?.let { curr.r.plusLeft(it) } ?: curr.r),
                            nl = nextL.nl,
                        )
                    } ?: explodeRecursive(curr.r, depth + 1)?.let { nextR ->
                        ExplodeResult(
                            new = Pair(nextR.nl?.let { curr.l.plusRight(it) } ?: curr.l, nextR.new),
                            nr = nextR.nr,
                        )
                    }
                }
            }

            return explodeRecursive(this, 0)?.new
        }

        fun split(): SnailFishNumber? = when (this) {
            is Pair -> l.split()?.let { Pair(it, r) } ?: r.split()?.let { Pair(l, it) }
            is Literal -> magnitude.takeIf { it >= 10 }?.let {
                Pair(Literal(floor(it / 2.0).toInt()), Literal(ceil(it / 2.0).toInt()))
            }
        }

        private data class Literal(override val magnitude: Int) : SnailFishNumber {
            override fun plusLeft(other: SnailFishNumber) = plusRight(other)
            override fun plusRight(other: SnailFishNumber) = Literal(magnitude + other.magnitude)
            override fun toString() = magnitude.toString()
        }

        private data class Pair(val l: SnailFishNumber, val r: SnailFishNumber) : SnailFishNumber {
            override val magnitude: Int get() = (l.magnitude * 3) + (r.magnitude * 2)
            override fun plusLeft(other: SnailFishNumber) = copy(l = l.plusLeft(other))
            override fun plusRight(other: SnailFishNumber) = copy(r = r.plusRight(other))
            override fun toString() = "[$l,$r]"
        }

        companion object {
            fun parse(input: String) = parse(Json.parseToJsonElement(input))
            private fun parse(input: JsonElement): SnailFishNumber = when (input) {
                is JsonArray -> Pair(parse(input[0]), parse(input[1]))
                is JsonPrimitive -> Literal(input.int)
                is JsonObject -> throw IllegalArgumentException("Unexpected JSON object")
            }
        }
    }
}
