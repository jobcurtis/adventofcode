package utils

import java.io.File
import java.net.URL

val anon: Class<*> = object {}.javaClass

private fun getResource(name: String): URL = anon.getResource(name)

fun getResourceAsLines(name: String) = File(getResource(name).file).readLines()
fun getResourceAsInts(name: String) = getResourceAsLines(name).mapNotNull(String::toIntOrNull)

