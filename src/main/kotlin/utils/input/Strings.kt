package com.emlett.aoc.utils.input

private val intRegex = Regex("""-?\d+""")

fun String.ints() = intRegex.findAll(this).map(MatchResult::value).map(String::toInt).toList()
