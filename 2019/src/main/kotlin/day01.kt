import java.io.File
import kotlin.math.max

object Day01 {
    
    private const val FILENAME = "day01.txt"
    
    private fun fuelNeeded(mass: Int) = max((mass / 3) - 2, 0)
    
    private tailrec fun totalFuelNeeded(mass: Int, totalFuel: Int = 0, newFuel: Int? = null): Int =
        when (val nextFuel = fuelNeeded(newFuel ?: mass)) {
            0 -> totalFuel
            else -> totalFuelNeeded(mass, totalFuel + nextFuel, nextFuel)
        }
    
    val part1 = File(ClassLoader.getSystemResource(FILENAME).file)
        .readLines()
        .sumBy { fuelNeeded(it.toInt()) }

    val part2 = File(ClassLoader.getSystemResource(FILENAME).file)
        .readLines()
        .sumBy { totalFuelNeeded(it.toInt()) }

}

fun main() {
    println("Part 1: ${Day01.part1}")
    println("Part 2: ${Day01.part2}")
}