package com.emlett.aoc.y2023

import com.emlett.aoc.utils.merge
import kotlin.math.max

private typealias BlockCollection = Map<String, Int>

object Day02 : Year2023() {
    private val bag: BlockCollection = mapOf("red" to 12, "green" to 13, "blue" to 14)
    private val games: List<Game> = lines.map(::parse)

    override fun part1() = with(bag) { games.filter { it.isPossible() }.sumOf { it.id } }

    override fun part2() = games
        .map { game -> game.rounds.reduce { highest, round -> highest.merge(round, ::max) } }
        .map(BlockCollection::values)
        .sumOf { it.reduce(Int::times) }

    private data class Game(val id: Int, val rounds: List<BlockCollection>)

    context(BlockCollection)
    private fun Game.isPossible() = rounds.all { it.all { (colour, count) -> getOrDefault(colour, 0) >= count } }

    private fun parse(str: String): Game = str.split(':').let { (first, second) ->
        val blockPattern = Regex("""[\d\w]+""")
        Game(id = first.digits.toInt(), rounds = second.split(';').toList().map { round ->
            blockPattern.findAll(round).map(MatchResult::value).chunked(2).associate { it[1] to it[0].toInt() }
        })
    }
}
