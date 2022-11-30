package com.emlett.aoc.y2020

object Day07 : Year2020() {
    private val bags = text
        .removeSuffix(".")
        .split(".")
        .filterNot { it.contains("no other") }
        .map { it.replace("bag[s]?".toRegex(), "").replace("contain", ",").split(",") }
        .map { it.map(String::trim) }
        .associate { it[0] to Bag(it[0], toInnerBags(it.subList(1, it.size))) }

    override fun part1() = bags.filter { it.value.containsColour("shiny gold", bags) }.count()
    override fun part2() = bags.getValue("shiny gold").numberOfInnerBags(bags)

    private data class Bag(val colour: String, val innerBags: Map<String, Int>) {
        fun containsColour(colour: String, bags: Map<String, Bag>): Boolean =
            if (this.innerBags.keys.contains(colour)) true
            else this.innerBags.keys.any { bags[it]?.containsColour(colour, bags) == true }

        fun numberOfInnerBags(bags: Map<String, Bag>): Int =
            this.innerBags.entries.sumOf { (bags[it.key]?.numberOfInnerBags(bags) ?: 0) * it.value + it.value }
    }

    private fun toInnerBags(input: List<String>) =
        input.associate { it.substring(2) to Integer.parseInt(it.substring(0, 1)) }
}
