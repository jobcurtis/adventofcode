import utils.getResourceAsInts

private const val FILENAME = "/day01.txt"

private fun Collection<Int>.findTwoParts(total: Int) =
    this.firstOrNull { this.contains(total - it) }
        ?.let { it to total - it }

private fun Collection<Int>.findThreeParts(total: Int): Triple<Int, Int, Int>? {
    for (i in this) {
        val twoParts = this.findTwoParts(total - i) ?: continue
        return Triple(i, twoParts.first, twoParts.second)
    }
    return null
}

fun main() {
    val input = getResourceAsInts(FILENAME)
    val part1 = input.findTwoParts(2020)!!
    val part2 = input.findThreeParts(2020)!!

    println("Part 1: ${part1.first}x${part1.second} = ${part1.first * part1.second}")
    println("Part 1: ${part2.first}x${part2.second}x${part2.third} = ${part1.first * part1.second * part2.third}")
}