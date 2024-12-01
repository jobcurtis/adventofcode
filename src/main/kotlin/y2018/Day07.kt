package com.emlett.aoc.y2018

object Day07 : Year2018() {
    val instr by lazy { lines.map { line -> line[5] to line[36] } }
    val steps by lazy { instr.flatMap { it.toList() }.distinct().sorted() }

    val requirements by lazy { instr.groupBy { it.second }.mapValues { (_, reqs) -> reqs.map { it.first }.toSet() } }

    override fun part1() = work().joinToString("")
    override fun part2() = part2(workers = 5, cost = { it.code - 4 })

    tailrec fun work(completed: Set<Char> = emptySet()): Set<Char> {
        if (completed.size == steps.size) return completed

        val next = steps
            .filterNot(completed::contains)
            .first { step -> requirements.getOrDefault(step, emptySet()).all(completed::contains) }

        return work(completed + next)
    }

    fun part2(workers: Int, cost: (Char) -> Int): Int {
        data class Work(val step: Char, var time: Int)

        var time = 0
        val remaining = steps.map { Work(it, cost(it)) }.toMutableList()
        val completed = mutableListOf<Char>()
        val active = mutableListOf<Work>()

        while (completed.size != steps.size) {

            remaining
                .filter { it.time > 0 }
                .filter { (step) -> requirements.getOrDefault(step, emptySet()).all(completed::contains) }
                .take(workers - active.size)
                .forEach {
                    remaining.remove(it)
                    active.add(it)
                }

            active.forEach { it.time-- }

            active.filter { it.time == 0 }.forEach {
                completed.add(it.step)
                active.remove(it)
            }

            time += 1
        }

        return time
    }
}
