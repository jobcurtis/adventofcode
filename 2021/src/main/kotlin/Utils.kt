package com.emlett.aoc

import java.io.File
import java.io.FileNotFoundException

private val classloader = {}::class.java.classLoader!!

private val String.fullPath: String
    get() = classloader.getResource(this)?.path ?: throw FileNotFoundException()

fun readAsLines(filename: String) = File(filename.fullPath).readLines()
fun readAsInts(filename: String) = readAsLines(filename).map(String::toInt)
fun readAsString(filename: String) = File(filename.fullPath).readText()
