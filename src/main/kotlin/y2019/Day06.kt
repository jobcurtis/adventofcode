package com.emlett.aoc.y2019

object Day06 : Year2019() {

    private val orbits = lines.map { it.split(')') }.associate { (left, right) -> right to left }

    private tailrec fun getOrbits(obj: String, end: String = "COM", total: List<String> = emptyList()): List<String> {
        val around = orbits[obj]!!
        if (around == end) return (total + around)
        return getOrbits(around, end, total + around)
    }

    override fun part1() = orbits.keys.flatMap(::getOrbits).size

    private val intersection = getOrbits("SAN").first { it in getOrbits("YOU") }

    override fun part2() = getOrbits("SAN", end = intersection).size + getOrbits("YOU", end = intersection).size - 2
}