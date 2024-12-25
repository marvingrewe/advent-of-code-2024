import kotlin.time.measureTimedValue

fun main() {
    val day = "Day11"

    fun Map<Long, Long>.updateRocks(): Map<Long, Long> {
        val updatedRocks = HashMap<Long, Long>()
        this.forEach { rock ->
            when {
                rock.key == 0L -> updatedRocks.merge(1L, rock.value, Long::plus)
                rock.key.toString().length % 2 == 0 -> rock.key.toString().chunked(rock.key.toString().length / 2)
                    .forEach { updatedRocks.merge(it.toLong(), rock.value, Long::plus) }

                else -> updatedRocks.merge(rock.key * 2024, rock.value, Long::plus)
            }
        }
        return updatedRocks
    }

    fun calcRocks(input: List<String>, targetBlinks: Int): Long {
        val rocks = input.first().split(" ").map(String::toLong).groupingBy { it }.eachCount()
        var longRocks = rocks.entries.associate { it.key to it.value.toLong() }
        for (blinks in 1..targetBlinks) {
            longRocks = longRocks.updateRocks()
        }

        return longRocks.map { it.value }.sum()
    }

    fun part1(input: List<String>): Long {
        return calcRocks(input, 25)
    }

    fun part2(input: List<String>): Long {
        return calcRocks(input, 75)
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 55312" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: " }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
