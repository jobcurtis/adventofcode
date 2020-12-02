import utils.getResourceAsLines

data class Password(
    val bound: Pair<Int, Int>,
    val char: Char,
    val value: String,
)

private const val FILENAME = "/day02.txt"

private fun String.toPassword(): Password {
    val (range, req, password) = this.split(" ")
    val (min, max) = range.split("-").map(String::toInt)
    return Password(min to max, req[0], password)
}

private fun Password.isValid(): Boolean {
    return value.filter { it == char }.count() in bound.first..bound.second
}

private fun Password.isActuallyValid(): Boolean {
    return (value[bound.first - 1] == char) xor (value[bound.second - 1] == char)
}

fun main() {
    val input = getResourceAsLines(FILENAME)

    println("Part 1: ${input.count { it.toPassword().isValid() }}")
    println("Part 2: ${input.count { it.toPassword().isActuallyValid() }}")
}