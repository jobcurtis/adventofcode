package com.emlett.aoc.utils.input

private val intRegex = Regex("""-?\d+""")

fun String.ints() = intRegex.findAll(this).map(MatchResult::value).map(String::toInt).toList()

fun <T> Regex.extract(str: String, block: (List<String>) -> T) = matchEntire(str)!!.groupValues.drop(1).run(block)
