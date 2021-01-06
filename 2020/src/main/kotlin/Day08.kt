import Instruction.*
import utils.getResourceAsLines

private const val FILENAME = "/day08.txt"

private enum class Instruction {
    NOP, ACC, JMP;
}

private fun String.toInstruction(): Pair<Instruction, Int> {
    val (first, last) = this.split(' ')
    return Instruction.valueOf(first.toUpperCase()) to last.toInt()
}

private fun List<Pair<Instruction, Int>>.solve(): Int {
    var acc = 0
    var pointer = 0
    val history = mutableSetOf<Int>()
    while (true) {
        if (pointer in history || pointer == this.size) return acc else history.add(pointer)
        val (op, n) = this[pointer]
        when (op) {
            NOP -> pointer++
            ACC -> { acc += n; pointer++ }
            JMP -> { pointer += n; }
        }
    }
}

private fun List<Pair<Instruction, Int>>.isInfinite(): Boolean {
    var acc = 0
    var pointer = 0
    val history = mutableSetOf<Int>()
    while (true) {
        if (pointer in history) return true else if (pointer == this.size) return false else history.add(pointer)
        val (op, n) = this[pointer]
        when (op) {
            NOP -> pointer++
            ACC -> { acc += n; pointer++ }
            JMP -> { pointer += n; }
        }
    }
}

private fun List<Pair<Instruction, Int>>.variants(): List<List<Pair<Instruction, Int>>> {
    val l = mutableListOf(this)
    this.forEachIndexed { i, p ->
        when (p.first) {
            NOP -> l.add(this.toMutableList().also { it[i] = JMP to p.second })
            JMP -> l.add(this.toMutableList().also { it[i] = NOP to p.second })
            ACC -> return@forEachIndexed
        }
    }
    return l
}

fun main() {
    val input = getResourceAsLines(FILENAME).map(String::toInstruction)

    val part1: Int = input.solve()
    val part2 = input.variants().first { !it.isInfinite() }.solve()

    println("Part 1: $part1")
    println("Part 2: $part2")
    
}