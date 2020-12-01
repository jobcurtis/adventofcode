import intcode.Intcode
import java.io.File

object Day02 {

    private const val FILENAME = "day02.txt"

    val part1 = Intcode(
        File(ClassLoader.getSystemResource(FILENAME).file)
            .readText()
            .split(",")
            .map { it.toInt() }
            .toIntArray()
            .also {
                it[1] = 12
                it[2] = 2
            }
    ).output
    
    val part2: Int
        get() {
            val input = File(ClassLoader.getSystemResource(FILENAME).file)
                .readText()
                .split(",")
                .map { it.toInt() }
                .toIntArray()



            for (i in 0 until 100) {
                for (j in 0 until 100) {
                    val newInput = input.copyOf().also {
                        it[1] = i
                        it[2] = j
                    }

                    if (Intcode(newInput).output == 19690720) {
                        return 100 * i + j
                    }
                }
            }
            throw IllegalStateException()
        }

}

fun main() {
    println("Part 1: ${Day02.part1}")
    println("Part 2: ${Day02.part2}")
}