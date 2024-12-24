import kotlin.time.measureTimedValue

fun main() {
    val day = "Day07"

    fun operators() = listOf<(Long, Long) -> Long>(Long::plus, Long::times)

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val (target, values) = line.split(": ")
                .let { (target, values) -> target.toLong() to values.split(" ").map(String::toLong) }

            if (values.drop(1).fold(listOf(values.first())) { results, right ->
                    results.flatMap {
                        operators().map { operator ->
                            operator(it, right)
                        }
                    }
                }.contains(target)) {
                target
            } else 0
        }
    }

    fun part2operators() = operators() + { l, r -> (l.toString() + r.toString()).toLong() }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val (target, values) = line.split(": ")
                .let { (target, values) -> target.toLong() to values.split(" ").map(String::toLong) }

            if (values.drop(1).fold(listOf(values.first())) { results, right ->
                    results.flatMap {
                        part2operators().map { operator ->
                            operator(it, right)
                        }
                    }
                }.contains(target)) {
                target
            } else 0
        }
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 3749" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 11387" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
