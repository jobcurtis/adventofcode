package com.emlett.aoc.y2018

object Day07 : Year2018() {
    val instr by lazy { lines.map { line -> line[5] to line[36] } }
    val steps by lazy { instr.flatMap { it.toList() }.distinct().sorted() }

    val requirements by lazy { instr.groupBy { it.second }.mapValues { (_, reqs) -> reqs.map { it.first }.toSet() } }

    override fun part1() = work().joinToString("")
    override fun part2() = TODO()

    tailrec fun work(completed: Set<Char> = emptySet()): Set<Char> {
        if (completed.size == steps.size) return completed

        val next = steps
            .filterNot(completed::contains)
            .first { step -> requirements.getOrDefault(step, emptySet()).all(completed::contains) }

        return work(completed + next)
    }

    override val text = """
        Step C must be finished before step A can begin.
        Step C must be finished before step F can begin.
        Step A must be finished before step B can begin.
        Step A must be finished before step D can begin.
        Step B must be finished before step E can begin.
        Step D must be finished before step E can begin.
        Step F must be finished before step E can begin.
    """.trimIndent()
}
