package com.emlett.aoc.y2024

import com.emlett.aoc.utils.input.extract

object Day24 : Year2024() {
  val regex = Regex("""^([\w\d]{3}) (AND|XOR|OR) ([\w\d]{3}) -> ([\w\d]{3})$""")

  val gates by lazy { text.substringAfter("\n\n").lines().map(::parse).associateBy(Gate::reg) }
  val input by lazy { text.substringBefore("\n\n").lines().map(::parseIn).associateBy(Gate::reg) }

  override fun part1() = (input + gates).eval()
  override fun part2() = TODO("not got a programmatic solution")

  fun Map<String, Gate>.eval() = keys
    .filter { it.startsWith('z') }
    .sortedDescending()
    .joinToString("") { if (getValue(it).output()) "1" else "0" }
    .toLong(2)

  sealed interface Gate {
    val reg: String

    data class In(override val reg: String, val value: Boolean) : Gate
    data class Or(override val reg: String, val i1: String, val i2: String) : Gate
    data class And(override val reg: String, val i1: String, val i2: String) : Gate
    data class Xor(override val reg: String, val i1: String, val i2: String) : Gate
  }

  context(ctx: Map<String, Gate>)
  fun Gate.output(): Boolean = when (this) {
    is Gate.In -> value
    is Gate.Or -> ctx.getValue(i1).output() || ctx.getValue(i2).output()
    is Gate.And -> ctx.getValue(i1).output() && ctx.getValue(i2).output()
    is Gate.Xor -> ctx.getValue(i1).output() != ctx.getValue(i2).output()
  }

  context(ctx: Map<String, Gate>)
  fun Gate.debug(level: Int): String = if (level == 0) reg else when (this) {
    is Gate.In -> this.reg
    is Gate.Or -> "(${ctx.getValue(this.i1).debug(level - 1)} or ${ctx.getValue(this.i2).debug(level - 1)})"
    is Gate.And -> "(${ctx.getValue(this.i1).debug(level - 1)} and ${ctx.getValue(this.i2).debug(level - 1)})"
    is Gate.Xor -> "(${ctx.getValue(this.i1).debug(level - 1)} xor ${ctx.getValue(this.i2).debug(level - 1)})"
  }

  fun parse(str: String) = regex.extract(str) { (i1, gate, i2, id) ->
    when (gate) {
      "OR" -> Gate.Or(id, i1, i2)
      "AND" -> Gate.And(id, i1, i2)
      "XOR" -> Gate.Xor(id, i1, i2)
      else -> throw IllegalArgumentException(gate)
    }
  }

  fun parseIn(str: String) = str.split(": ").let { (id, value) -> Gate.In(id, value == "1") }
}
