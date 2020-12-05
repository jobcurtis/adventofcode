import utils.getResourceAsLines

private const val FILENAME = "/day05.txt"

private data class BoardingPass(val value: CharSequence) {

    init {
        require(value.length == 10)
        require(value.take(7).all { it == 'F' || it == 'B' })
        require(value.takeLast(3).all { it == 'L' || it == 'R' })
    }
    
    private fun getRow(): Int {
        var (min, max) = 0 to 127
        value.take(7).forEach {
            when (it) {
                'F' -> max -= ((max - min) + 1) / 2
                'B' -> min += ((max - min) + 1) / 2
                else -> throw IllegalStateException()
            }
        }
        assert(min == max)
        return max
    }
    
    private fun getCol(): Int { //TODO deduplicate
        var (min, max) = 0 to 7
        value.takeLast(3).forEach {
            when (it) {
                'L' -> max -= ((max - min) + 1) / 2
                'R' -> min += ((max - min) + 1) / 2
                else -> throw IllegalStateException()
            }
        }
        assert(min == max)
        return max
    }

    val id = (getRow() * 8) + getCol()

}

fun List<Int>.findMissing(): Int {
    require(this.isNotEmpty())
    val min = this.minOrNull()!!
    val max = this.maxOrNull()!!
    val sumTotal = (min..max).toList().sum()
    val sum = this.sum()
    assert(!this.contains(sumTotal - sum))
    return sumTotal - sum
}

fun main() {
    
    val input = getResourceAsLines(FILENAME)

    val part1 = input.map(::BoardingPass).map(BoardingPass::id).maxOrNull()
    val part2 = input.map(::BoardingPass).map(BoardingPass::id).findMissing()
    
    println("Part 1: $part1")
    println("Part 2: $part2")

}
