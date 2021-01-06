import utils.getResourceAsLines

private const val FILENAME = "/day14.txt"

private class Bitmask(private val mask: CharArray) {
    companion object {
        fun of(s: String): Bitmask {
            require(s.length == 36)
            return Bitmask(s.toCharArray())
        }
    }
    
    fun apply(value: Long): Long {
        return value.toString(2)
            .padStart(36, '0')
            .mapIndexed { i, c ->
                when (mask[i]) {
                    '0' -> '0'
                    '1' -> '1'
                    else -> c
                }
            }.joinToString("")
            .toLong(2)
    }
    
    fun applyFloating(value: Long): List<Long> {
        



        TODO()
    }

}

fun part1(input: List<String>): Long {
    var mask = Bitmask.of(input[0].substringAfter("mask = "))
    val memoryMap: MutableMap<Long, Long> = mutableMapOf()

    input.forEach { instr ->
        when(instr[1]) {
            'a' -> mask = Bitmask.of(instr.substringAfter("mask = "))
            'e' -> {
                val (first, last) = instr.split(" = ")
                val location = first.substringAfter("mem[").substringBefore(']').toInt()
                memoryMap[location.toLong()] = mask.apply(last.toLong())
            }
        }
    }
    return memoryMap.values.sum()
}

fun main() {
    val input = getResourceAsLines(FILENAME)

    val part1 = part1(input)
    val part2 = null

    println("Part 1: $part1")
    println("Part 2: $part2")
}