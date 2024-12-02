import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    val day = "Day01"

    fun part1(input: List<String>): Int = input.toIntLists().toPairOfLists().map { it.sorted() }.run {
        (first zip second).sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int =
        input.toIntLists().toPairOfLists().run { this.first.sumOf { l -> l * this.second.count { r -> l == r } } }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 11" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 31" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
