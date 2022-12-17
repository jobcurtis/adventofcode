package com.emlett.aoc.utils

fun <T> Sequence<T>.repeat(): Sequence<T> = sequence { while (true) yieldAll(this@repeat) }