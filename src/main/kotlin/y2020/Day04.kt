package com.emlett.aoc.y2020

object Day04 : Year2020() {
  override fun part1() = input.map { it.toPassport() }.count { it.isComplete() }
  override fun part2() = input.map { it.toPassport() }.filter { it.isComplete() }.count { it.isValid() }

  val input = text.split(Regex("\n\n")).map { it.replace(Regex("\n"), " ") }

  private fun String.isInt(
    length: Int, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE
  ) = this.length == length && this.all(Char::isDigit) && this.toInt() >= min && this.toInt() <= max

  private fun String.validHeight() = when (this.takeLast(2)) {
    "cm" -> this.dropLast(2).isInt(3, 150, 193)
    "in" -> this.dropLast(2).isInt(2, 59, 76)
    else -> false
  }

  // @formatter:off
    private val passportValidation: Map<String, (String) -> Boolean> = mapOf("byr" to { it.isInt(4, 1920, 2002) },
        "iyr" to { it.isInt(4, 2010, 2020) },
        "eyr" to { it.isInt(4, 2020, 2030) },
        "hgt" to { it.validHeight() },
        "hcl" to { it.matches(Regex("^#[0-9a-fA-F]{6}$")) },
        "ecl" to { it in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") },
        "pid" to { it.all(Char::isDigit) && it.length == 9 })
    // @formatter:on

  private fun Map<String, String>.isComplete() = keys.containsAll(passportValidation.keys)

  private fun Map<String, String>.isValid() = all { (k, v) -> passportValidation[k]?.invoke(v) ?: true }

  private fun String.toPassport() =
    split(" ").filter(String::isNotEmpty).associate { val s = it.split(":"); s[0] to s[1] }
}
