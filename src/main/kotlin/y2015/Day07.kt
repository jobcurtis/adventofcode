package com.emlett.aoc.y2015

object Day07 : Year2015() {
  private val literalRegex = Regex("""^(\d+)$""")
  private val variableRegex = Regex("""^([A-Za-z]+)$""")
  private val unaryRegex = Regex("""^(NOT) ([A-Za-z]+|\d+)$""")
  private val binaryRegex = Regex("""^([A-Za-z]+|\d+) (OR|AND|LSHIFT|RSHIFT) ([A-Za-z]+|\d+)$""")

  private val environment: MutableMap<String, Expr>
    get() = lines
      .map { it.split(" -> ") }
      .associate { (left, right) -> right to left }
      .mapValues { (_, expr) -> parse(expr) }
      .toMutableMap()

  override fun part1() = environment.interpret(Expr.Variable("a"))
  override fun part2() = environment.also { it["b"] = Expr.Literal(part1()) }.interpret(Expr.Variable("a"))

  private fun parse(instr: String): Expr = when {
    instr.matches(literalRegex) -> Expr.Literal(instr.toUShort())
    instr.matches(variableRegex) -> Expr.Variable(instr)
    instr.matches(unaryRegex) -> Expr.Not(parse(instr.removePrefix("NOT ")))
    instr.matches(binaryRegex) -> instr.split(' ').let { (l, op, r) -> parseBinary(op)(parse(l), parse(r)) }
    else -> throw IllegalArgumentException(instr)
  }

  private fun parseBinary(instr: String): (Expr, Expr) -> Expr = when (instr) {
    "OR" -> { left, right -> Expr.Or(left, right) }
    "AND" -> { left, right -> Expr.And(left, right) }
    "LSHIFT" -> { left, right -> Expr.LShift(left, right) }
    "RSHIFT" -> { left, right -> Expr.RShift(left, right) }
    else -> throw IllegalArgumentException(instr)
  }

  private fun MutableMap<String, Expr>.interpret(expr: Expr): UShort = when (expr) {
    is Expr.Literal -> expr.value
    is Expr.Or -> interpret(expr.left) or interpret(expr.right)
    is Expr.And -> interpret(expr.left) and interpret(expr.right)
    is Expr.LShift -> (interpret(expr.left).toInt() shl interpret(expr.right).toInt()).toUShort()
    is Expr.Not -> interpret(expr.right).inv()
    is Expr.RShift -> (interpret(expr.left).toInt() shr interpret(expr.right).toInt()).toUShort()
    is Expr.Variable -> interpret(this[expr.name]!!).also { this[expr.name] = Expr.Literal(it) }
  }

  private sealed interface Expr {
    data class Or(val left: Expr, val right: Expr) : Expr
    data class Not(val right: Expr) : Expr
    data class And(val left: Expr, val right: Expr) : Expr
    data class LShift(val left: Expr, val right: Expr) : Expr
    data class RShift(val left: Expr, val right: Expr) : Expr
    data class Literal(val value: UShort) : Expr
    data class Variable(val name: String) : Expr
  }
}
