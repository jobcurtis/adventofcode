package com.emlett.aoc.y2016

object Day04 : Year2016() {
    private val regex = Regex("""^([a-z-]+)(\d+)\[([a-z]+)]$""")
    private val rooms by lazy {
        lines
            .mapNotNull { regex.matchEntire(it) }
            .map { it.groupValues.drop(1) }
            .map { (name, sector, checksum) -> Room(name.dropLast(1), sector.toInt(), checksum) }
    }

    private val realRooms by lazy { rooms.filter(Room::isReal) }

    override fun part1() = realRooms.sumOf(Room::sector)

    override fun part2() = realRooms.first { it.decrypt().contains("object") }.sector

    private data class Room(val name: String, val sector: Int, val checksum: String) {
        fun isReal() = checksum == name.checksum()
        fun decrypt() = name.map { if (it == '-') ' ' else it.shift(sector) }.joinToString("")
    }

    private fun Char.shift(num: Int) = (((code + num - 'a'.code) % 26) + 'a'.code).toChar()

    private fun String.checksum() = this
        .filterNot { it == '-' }
        .groupingBy { it }
        .eachCount()
        .toList()
        .sortedWith(compareByDescending(Pair<Char, Int>::second).thenBy(Pair<Char, Int>::first))
        .map { it.first }
        .take(5)
        .joinToString("")
}
