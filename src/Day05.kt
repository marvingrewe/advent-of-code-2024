import kotlin.time.measureTimedValue

fun main() {
    val day = "Day05"

    fun List<String>.updateIsSorted(comparator: Comparator<String>): Boolean {
        return this.zipWithNext().all { comparator.compare(it.first, it.second) < 0}
    }

    fun part1(input: List<String>): Int {
        val orderingRules = input.takeWhile { it.isNotBlank() }.map { it.split("|").toPair() }.toHashSet()
        val updates = input.takeLastWhile { it.isNotBlank() }.map { it.split(",") }

        val pageComparator = Comparator<String> { left, right ->
            when (orderingRules.contains(left to right)) {
                true -> -1
                false -> 1
            }
        }

        return updates.filter { it.updateIsSorted(pageComparator) }.sumOf { it[it.size / 2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val orderingRules = input.takeWhile { it.isNotBlank() }.map { it.split("|").toPair() }.toHashSet()
        val updates = input.takeLastWhile { it.isNotBlank() }.map { it.split(",") }

        val pageComparator = Comparator<String> { left, right ->
            when (orderingRules.contains(left to right)) {
                true -> -1
                false -> 1
            }
        }

        return updates.filter { !it.updateIsSorted(pageComparator) }.map { it.sortedWith(pageComparator) }.sumOf { it[it.size / 2].toInt() }
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 143" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 123" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
