package com.emlett.aoc.utils.input

fun String.integers() = split(' ').filter(String::isNotEmpty).map(String::toInt)

fun <T> Regex.extract(str: String, block: (List<String>) -> T) = matchEntire(str)!!.groupValues.drop(1).run(block)
