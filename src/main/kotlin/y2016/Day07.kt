package com.emlett.aoc.y2016

import com.emlett.aoc.y2021.atLeast

object Day07 : Year2016() {
    private val brackets = Regex("""[\[\]]""")
    private val addresses by lazy {
        lines
            .map { it.split(brackets) }
            .map { it.withIndex() }
            .map { it.partition { (i, _) -> i % 2 == 0 } }
            .map { (chunks, hypernets) -> IpAddress(chunks.map { it.value }, hypernets.map { it.value }) }
    }

    override fun part1() = addresses.count(IpAddress::isTLS)
    override fun part2() = addresses.count(IpAddress::isSSL)

    private data class IpAddress(val chunks: List<String>, val hypernets: List<String>) {
        fun isTLS() = hypernets.none { it.containsABBA() } && chunks.atLeast(1) { it.containsABBA() }
        fun isSSL(): Boolean {
            val maybeBABs = hypernets.flatMap { it.windowed(3) }.filter { it.isABA() }
            return chunks
                .flatMap { chunk -> chunk.windowed(3).filter { it.isABA() } }
                .any { aba -> maybeBABs.any { it.isBAB(aba) } }
        }
    }

    private fun String.containsABBA() = windowed(4).any { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] }
    private fun String.isABA() = get(0) == get(2) && get(0) != get(1)
    private fun String.isBAB(aba: String) = get(0) == aba[1] && get(1) == aba[0]
}
