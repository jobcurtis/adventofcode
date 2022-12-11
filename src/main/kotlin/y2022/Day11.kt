package com.emlett.aoc.y2022

object Day11 : Year2022() {
    private val regex = Regex("""^new = (old|\d+) ([+*]) (old|\d+)$""")
    private val monkeys = text.split("\n\n").map(String::lines).map(::parse)

    override fun part1() = play(20) { it.floorDiv(3) }
    override fun part2() = play(10_000)

    private fun play(rounds: Int, extra: (Long) -> Long = { it }): Long {
        val inventories = monkeys.map { ArrayDeque(it.items) }
        val inspections = MutableList(monkeys.size) { 0L }
        val product = monkeys.map(Monkey::divisor).reduce(Long::times)

        repeat(rounds) {
            monkeys.forEachIndexed { index, monkey ->
                val inventory = inventories[index]
                while (inventory.isNotEmpty()) {
                    val item = inventory.removeFirst()
                    val inspected = extra(monkey.operation(item) % product)
                    val target = if (inspected % monkey.divisor == 0L) monkey.outcome.first else monkey.outcome.second
                    inventories[target].addLast(inspected)
                    inspections[index]++
                }
            }
        }

        return inspections.sortedDescending().let { it[0] * it[1] }
    }

    private data class Monkey(
        val items: List<Long>,
        val operation: (Long) -> Long,
        val divisor: Long,
        val outcome: Pair<Int, Int>
    )

    private fun parse(block: List<String>) = Monkey(
        items = block[1].removePrefix("  Starting items: ").split(", ").map(String::toLong),
        operation = regex.matchEntire(block[2].removePrefix("  Operation: "))!!.destructured.let { (p1, p2, p3) ->
            val op: (Long, Long) -> Long = if (p2 == "*") Long::times else Long::plus
            val l = p1.toLongOrNull()
            val r = p3.toLongOrNull()
            return@let { x: Long -> op.invoke(l ?: x, r ?: x) }
        },
        divisor = block[3].removePrefix("  Test: divisible by ").toLong(),
        outcome = Pair(
            block[4].removePrefix("    If true: throw to monkey ").toInt(),
            block[5].removePrefix("    If false: throw to monkey ").toInt(),
        ),
    )
}
