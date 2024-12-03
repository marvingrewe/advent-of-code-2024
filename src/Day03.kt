import kotlin.time.measureTimedValue

fun main() {
    val day = "Day03"

    fun part1(input: List<String>): Int =
        input.joinToString().let { Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)").findAll(it) }.map(MatchResult::destructured)
            .sumOf { (l, r) -> l.toInt() * r.toInt() }

    fun part2(input: List<String>): Int =
        input.joinToString().split(Regex("do\\(\\)")).map { it.split(Regex("don't\\(\\)")).first() }
            .flatMap { Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)").findAll(it) }.map(MatchResult::destructured)
            .sumOf { (l, r) -> l.toInt() * r.toInt() }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 161" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 48" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
