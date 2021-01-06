import utils.getResourceAsLines

private const val FILENAME = "/day13.txt"

fun main() {
    val (input1, input2) = getResourceAsLines(FILENAME)
    
    val depart = input1.toInt()
    val ids = input2.split(',')
    val earliest = ids.mapNotNull(String::toIntOrNull).minByOrNull { it - (depart % it) }!!
    
    val part1 = earliest * (earliest - (depart % earliest))
    val part2 = Day13(getResourceAsLines(FILENAME)).solvePart2()

    println("Part 1: $part1")
    println("Part 2: $part2")
}


class Day13(input: List<String>) {

    private val startTime: Int = input.first().toInt()
    private val busses: List<Int> = input
        .last()
        .split(",")
        .mapNotNull { s -> if (s == "x") null else s.toInt() }

    private val indexedBusses: List<IndexedBus> = input
        .last()
        .split(",")
        .mapIndexedNotNull { index, s -> if (s == "x") null else IndexedBus(index, s.toLong()) }

    fun solvePart1(): Int =
        generateSequence(startTime) { it + 1 }
            .mapNotNull { time ->
                busses
                    .firstOrNull { bus -> time % bus == 0 }
                    ?.let { bus -> Pair(time, bus) }
            }
            .first()
            .run { (first - startTime) * second }

    fun solvePart2(): Long {
        var stepSize = indexedBusses.first().bus
        var time = 0L
        indexedBusses.drop(1).forEach { (offset, bus) ->
            while ((time + offset) % bus != 0L) {
                time += stepSize
            }
            stepSize *= bus // New Ratio!
        }
        return time
    }

    data class IndexedBus(val index: Int, val bus: Long)
}