import utils.getResourceAsString

private const val FILENAME = "/day04.txt"

typealias Passport = Map<String, String>

fun String.isInt(
    length: Int,
    min: Int = Int.MIN_VALUE,
    max: Int = Int.MAX_VALUE
): Boolean {
    return this.length == length &&
            this.all(Char::isDigit) &&
            this.toInt() >= min &&
            this.toInt() <= max
}

fun String.validHeight(): Boolean =
    when (this.takeLast(2)) {
        "cm" -> this.dropLast(2).isInt(3, 150, 193)
        "in" -> this.dropLast(2).isInt(2, 59, 76)
        else -> false
    }

// @formatter:off
val passportValidation: Map<String, (String) -> Boolean> = mapOf(
    "byr" to { it.isInt(4, 1920, 2002) },
    "iyr" to { it.isInt(4, 2010, 2020) },
    "eyr" to { it.isInt(4, 2020, 2030) },
    "hgt" to String::validHeight,
    "hcl" to { it.matches(Regex("^#(?:[0-9a-fA-F]{6})$")) },
    "ecl" to { it in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
    "pid" to { it.all(Char::isDigit) && it.length == 9 }
)
// @formatter:on

fun Passport.isComplete(): Boolean =
    this.keys.containsAll(passportValidation.keys)

fun Passport.isValid(): Boolean =
    this.all {
        val (k, v) = it; passportValidation[k]?.invoke(v) ?: true
    }

fun String.toPassport(): Passport {
    return this.split(" ")
        .filter(String::isNotEmpty)
        .map { val s = it.split(":"); s[0] to s[1] }
        .toMap()
}

fun main() {
    val input = getResourceAsString(FILENAME)
        .split(Regex("\n\n"))
        .map { it.replace(Regex("\n"), " ") }

    val part1 = input.map(String::toPassport).count(Passport::isComplete)
    val part2 = input.map(String::toPassport)
        .filter(Passport::isComplete)
        .count(Passport::isValid)

    println("Part 1: $part1")
    println("Part 2: $part2")
}