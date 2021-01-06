import utils.getResourceAsInts
import utils.getResourceAsLines

private const val FILENAME = "/day09.txt"

private fun List<Int>.containsPairWithSum(sum: Int): Boolean {
    for (i in 0 until size) {
        for (j in 0 until size) {
            when {
                i == j -> continue
                this[i] + this[j] == sum -> {
                    return true
                }
            }
        }
    }
    return false
}

private fun List<Int>.findSetWithSum(sum: Int): List<Int>? {
    for (i in 0 until size) {
        val set = mutableListOf<Int>()
        for (j in i until size) {
            set.add(this[j])
            when {
                set.sum() == sum -> return set
                set.sum() > sum -> break
            }
        }
    }
    return null
}

private fun List<Int>.validateXmas(preambleSize: Int): Int? {
    for (i in preambleSize..size) {
        if (subList(i - preambleSize, i).containsPairWithSum(this[i]))
            continue
        else
            return this[i]
    }
    return null
}

fun main() {
    val input = getResourceAsInts(FILENAME)
    
    val part1 = input.validateXmas(25)
    val part2 = input.findSetWithSum(part1!!)?.let {
        it.minOrNull()!! + it.maxOrNull()!!
    }

    println("Part 1: $part1")
    println("Part 2: $part2")


}