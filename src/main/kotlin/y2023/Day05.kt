package com.emlett.aoc.y2023

private typealias Mapping = List<Pair<LongRange, Long>>

object Day05 : Year2023() {
    private val seeds by lazy { lines.first().split(' ').mapNotNull(String::toLongOrNull) }
    private val seedRanges by lazy { seeds.chunked(2).map { (start, length) -> start..<(start + length) } }
    private val mappings: Collection<Mapping> by lazy { text.split("\n\n").drop(1).map(::parseMapping) }

    private fun parseMapping(chunk: String): Mapping = chunk
        .lines()
        .drop(1)
        .map { it.split(' ').map(String::toLong) }
        .map { (dst, src, len) -> (src..<src + len) to (dst - src) }

    override fun part1() = mappings
        .map(::toNumberTransformer)
        .fold(seeds) { inputs, transformer -> transformer(inputs) }
        .min()

    override fun part2() = mappings
        .map(::toRangeTransformer)
        .fold(seedRanges) { inputs, transformer -> transformer(inputs) }
        .minOf(LongRange::first)

    private fun toNumberTransformer(mapping: Mapping) = { inputs: Collection<Long> ->
        inputs.map { n -> mapping.find { (range, _) -> n in range }?.let { (_, diff) -> n + diff } ?: n }
    }

    private fun toRangeTransformer(mapping: Mapping) = { inputs: Collection<LongRange> ->
        mapping.fold(emptySet<LongRange>() to inputs) { (intersection, others), (range, diff) ->
            others
                .map { input -> getInRange(input, range) }
                .reduceOrNull { (lAcc, rAcc), (l, r) -> lAcc + l to rAcc + r }
                .let { it ?: (emptySet<LongRange>() to emptySet()) }
                .let { (newIntersects, others) -> newIntersects.map { it.offset(diff) }.toSet() to others }
                .let { (newIntersects, others) -> newIntersects + intersection to others }
        }.let { it.first + it.second }.toList()
    }

    /**
     * @return a pair of sets of ranges, where [Pair.first] is the parts of [input] within [range], and [Pair.second] is
     * the remaining parts of [input]
     */
    private fun getInRange(input: LongRange, range: LongRange): Pair<Set<LongRange>, Set<LongRange>> {
        return when {
            input.first in range && input.last in range -> setOf(input) to emptySet()
            input.last < range.first || input.first > range.last -> emptySet<LongRange>() to setOf(input)
            input.first < range.first && input.last in range -> Pair(
                setOf(range.first..input.last),
                setOf(input.first..<range.first),
            )

            input.first in range && input.last > range.last -> Pair(
                setOf(input.first..range.last),
                setOf(range.last + 1..input.last),
            )

            input.first < range.first && input.last > range.last -> Pair(
                setOf(range),
                setOf((input.first..<range.first), (range.last + 1..input.last)),
            )

            else -> throw IllegalArgumentException("$input, $range")
        }
    }

    private fun LongRange.offset(a: Long): LongRange = (first + a)..(last + a)
}
