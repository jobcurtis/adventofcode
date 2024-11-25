package com.emlett.aoc

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import kotlin.io.path.*

fun getInput(puzzle: Puzzle): String {
    val path = with(puzzle) { Path("src/main/kotlin/y$year/Day$day.txt") }

    return when {
        path.exists() -> path.readText()
        else -> with(puzzle) { downloadInput(year.toInt(), day.toInt()) }
            .also { path.parent.createDirectories() }
            .also { input -> path.writeText(input) }
    }
}

fun getPuzzle(year: String, day: String): Puzzle {
    val yr = year.filter(Char::isDigit)
    val dy = day.filter(Char::isDigit).padStart(2, '0')
    val qualifiedName = "${Puzzle::class.java.packageName}.y$yr.Day$dy"
    val kClass = Class.forName(qualifiedName).kotlin
    return (requireNotNull(kClass.objectInstance) as Puzzle)
}

fun main(args: Array<String>) = args.let { (year, day) ->
    getPuzzle(year, day).print()
}

fun downloadInput(year: Int, day: Int): String {
    val uri = URI.create("https://adventofcode.com/$year/day/$day/input")
    val session = System.getProperty("aoc.session")

    require(session != null && session.isNotBlank()) {
        "Missing system property 'aoc.session'"
    }

    println("Fetching $uri")

    val client = HttpClient.newBuilder().build()
    val request = HttpRequest
        .newBuilder(uri)
        .header("Cookie", "session=$session")
        .header("User-Agent", "https://github.com/jobcurtis/adventofcode")
        .build()

    val response = client
        .send(request, BodyHandlers.ofString())
        .apply { check(statusCode() == 200) { "${statusCode()} ${body()}" } }

    return response.body()
}
