import kotlin.time.measureTimedValue

fun main() {
    val day = "Day02"

    fun List<Int>.isSafeReport(): Boolean =
        this.zipWithNext().map { it.first - it.second }.run { this.all { it in 1..3 } || this.all { it in -3..-1 } }

    fun part1(input: List<String>): Int = input.toIntLists().count { it.isSafeReport() }

    fun List<Int>.isSafeReportDampened(): Boolean =
        indices.map { this.filterIndexed { index, _ -> index != it } }.any { it.isSafeReport() }

    fun part2(input: List<String>): Int = input.toIntLists().count { it.isSafeReportDampened() }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 2" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 4" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
