import utils.getResourceAsString

private const val FILENAME = "/day06.txt"

fun main() {

    val input = getResourceAsString(FILENAME)
        .split(Regex("\n\n"))

    val part1 = input.map { it.filter(Char::isLetter).toSet().size }.sum()
    val part2 = input.map { str ->
        val lines = str.split(Regex("\n"))
        return@map str.filter(Char::isLetter).toSet()
            .count { char ->
                lines.all { it.contains(char) }
            }
    }.sum()

    println("Part 1: $part1")
    println("Part 2: $part2")


}
