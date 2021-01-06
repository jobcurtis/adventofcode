import utils.getResourceAsLines

private const val FILENAME = "/day11.txt"

private typealias Layout = Array<CharArray>

val travel: List<(Pair<Int, Int>) -> Pair<Int, Int>> = listOf(
    { it.first + 0 to it.second + 1 }, //north
    { it.first + 1 to it.second + 1 }, //ne
    { it.first + 1 to it.second + 0 }, //east
    { it.first + 1 to it.second - 1 }, //se
    { it.first + 0 to it.second - 1 }, //south
    { it.first - 1 to it.second - 1 }, //sw
    { it.first - 1 to it.second + 0 }, //west
    { it.first - 1 to it.second + 1 }, //nw
)

private fun Layout.mutate(block: (Layout) -> Layout): Layout {
    var curr = this
    var next = block.invoke(this)
    while (!(curr contentDeepEquals next)) {
        curr = next
        next = block.invoke(curr)
    }
    return next
}

private fun Layout.next(): Layout {
    val next = this.map(CharArray::copyOf).toTypedArray()
    this.forEachIndexed { i, it ->
        it.forEachIndexed { j, c ->
            when (c) {
                '#' -> next[i][j] =
                    if (this.occupiedAdjacent(i, j) >= 4) 'L' else '#'
                'L' -> next[i][j] =
                    if (this.occupiedAdjacent(i, j) == 0) '#' else 'L'
            }
        }
    }
    return next
}

private fun Layout.next2(): Layout {
    val next = this.map(CharArray::copyOf).toTypedArray()
    this.forEachIndexed { i, chars ->
        chars.forEachIndexed { j, c ->
            when (c) {
                '#' -> next[i][j] = if (travel.map { this.occupiedVisual(i to j, it) }.sum() >= 5) 'L' else '#'
                'L' -> next[i][j] = if (travel.map { this.occupiedVisual(i to j, it) }.sum() == 0) '#' else 'L'
            }
        }
    }
    return next
}

private fun Layout.occupiedVisual(c: Pair<Int, Int>, direction: (Pair<Int, Int>) -> Pair<Int, Int>): Int {
    val new = direction(c)
    return try {
        when(this[new.first][new.second]) {
            '.' -> this.occupiedVisual(new, direction)
            'L' -> 0
            '#' -> 1
            else -> TODO() // shouldn't happen
        }
    } catch (e: IndexOutOfBoundsException) {
        0
    }

}

private fun Layout.occupiedAdjacent(x: Int, y: Int): Int {
    var count = 0
    for (i in (x - 1)..(x + 1)) {
        for (j in (y - 1)..(y + 1)) {
            try {
                if (!(i == x && j == y) && this[i][j] == '#') count++
            } catch (e: IndexOutOfBoundsException) {
            }
        }
    }
    return count
}

fun main() {
    val input =
        getResourceAsLines(FILENAME).map(String::toCharArray).toTypedArray()

    val part1 = input.mutate(Layout::next).flatMap(CharArray::toList).count { it == '#' }
    val part2 = input.mutate(Layout::next2).flatMap(CharArray::toList).count { it == '#' }

    println("Part 1: $part1")
    println("Part 2: $part2")
}