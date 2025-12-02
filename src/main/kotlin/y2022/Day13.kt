package com.emlett.aoc.y2022

import kotlinx.serialization.json.*

object Day13 : Year2022() {
  private val input = text
    .split("\n\n")
    .map { it.lines().map(Json::parseToJsonElement).map(::parse).let { (a, b) -> a to b } }

  private val dividers = listOf("[[2]]", "[[6]]").map(Json::parseToJsonElement).map(::parse)

  override fun part1() = input.map { (l, r) -> l < r }.withIndex().sumOf { (i, b) -> if (b) i + 1 else 0 }
  override fun part2() = input
    .flatMap { it.toList() }
    .let { list -> (list.count { it < dividers[0] } + 1) * (list.count { it < dividers[1] } + 2) }

  private sealed interface UnionListInt : Comparable<UnionListInt>

  private data class UnionList(val value: List<UnionListInt>) : UnionListInt {
    override fun compareTo(other: UnionListInt): Int = when (other) {
      is UnionInt -> this.compareTo(UnionList(listOf(other)))
      is UnionList -> this.value
        .zip(other.value)
        .map { (l, r) -> l.compareTo(r) }
        .firstOrNull { it != 0 } ?: this.value.size.compareTo(other.value.size)
    }
  }

  private data class UnionInt(val value: Int) : UnionListInt {
    override fun compareTo(other: UnionListInt): Int = when (other) {
      is UnionInt -> this.value.compareTo(other.value)
      is UnionList -> UnionList(listOf(this)).compareTo(other)
    }
  }

  private fun parse(element: JsonElement): UnionListInt = when (element) {
    is JsonArray -> UnionList(element.map { parse(it) })
    is JsonPrimitive -> UnionInt(element.int)
    else -> error("Can't handle element $element")
  }
}
