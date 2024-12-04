import kotlin.time.measureTimedValue

fun main() {
    val day = "Day04"

    fun xmasHorizontal() = Regex("(?=XMAS)")
    fun xmasDiagonalRight(lineLength: Int) = Regex("(?=X.{${lineLength - 1}}M.{${lineLength - 1}}A.{${lineLength - 1}}S)")
    fun xmasVertical(lineLength: Int) = Regex("(?=X.{$lineLength}M.{$lineLength}A.{$lineLength}S)")
    fun xmasDiagonalLeft(lineLength: Int) = Regex("(?=X.{${lineLength + 1}}M.{${lineLength + 1}}A.{${lineLength + 1}}S)")

    fun part1(input: List<String>): Int = input.joinToString("|").let {
        listOf(
            xmasHorizontal(),
            xmasDiagonalRight(input.size),
            xmasVertical(input.size),
            xmasDiagonalLeft(input.size)
        ).sumOf { regex -> regex.findAll(it).count() + regex.findAll(it.reversed()).count() }
    }

    fun x_masDiagonalLeft(lineLength: Int) = Regex("(?=S.M.{${lineLength - 1}}A.{${lineLength - 1}}S.M)")
    fun x_masDiagonalRight(lineLength: Int) = Regex("(?=M.M.{${lineLength - 1}}A.{${lineLength - 1}}S.S)")

    fun part2(input: List<String>): Int = input.joinToString("|").let {
        listOf(
            x_masDiagonalLeft(input.size),
            x_masDiagonalRight(input.size)
        ).sumOf { regex -> regex.findAll(it).count() + regex.findAll(it.reversed()).count() }
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 18" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 9" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
