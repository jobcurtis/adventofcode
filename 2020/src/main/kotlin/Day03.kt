import utils.getResourceAsLines

private const val FILENAME = "/day03.txt"

data class Slope(val x: Int, val y: Int)

fun List<String>.traverse(slope: Slope): Long {
    var x = 0
    var y = 0
    val xWidth = this[y].length
    var count = 0L

    while (y < this.size) {
        if (this[y][x] == '#') count++
        x = (x + slope.x) % xWidth
        y += slope.y
    }

    return count
}

fun main() {
    val input = getResourceAsLines(FILENAME)

    val part1 = input.traverse(Slope(3, 1))
    val part2 = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2),
    ).map(input::traverse).reduce(Long::times)

    println("Part 1: $part1")
    println("Part 2: $part2")
}