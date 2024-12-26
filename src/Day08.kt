import kotlin.time.measureTimedValue

fun main() {
    val day = "Day08"

    fun part1(input: List<String>): Int {
        val antennas = mutableSetOf<Antenna>()
        val antinodes = mutableSetOf<Point>()

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, char ->
                if (char != '.') antennas.add(
                    Antenna(char.toString(), Point(rowIndex, colIndex))
                )
            }
        }

        antennas.groupBy { it.frequency }.forEach { (_, antennas) ->
            antennas.elementPairs().forEach { pair ->
                val vector = pair.first.location - pair.second.location
                antinodes += listOf(
                    pair.first.location + vector, pair.second.location - vector
                ).filter { input.isInBounds(it.first, it.second) }
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennas = mutableSetOf<Antenna>()
        val antinodes = mutableSetOf<Point>()

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, char ->
                if (char != '.') antennas.add(
                    Antenna(char.toString(), Point(rowIndex, colIndex))
                )
            }
        }

        antennas.groupBy { it.frequency }.forEach { (_, antennas) ->
            antennas.elementPairs().forEach { pair ->
                val vector = pair.first.location - pair.second.location
                antinodes += integers.map { pair.first.location + vector * it }
                    .takeWhile { input.isInBounds(it.first, it.second) }
                antinodes += integers.map { pair.second.location - vector * it }
                    .takeWhile { input.isInBounds(it.first, it.second) }
            }
        }

        return antinodes.size
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 14" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 34" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}

data class Antenna(val frequency: String, val location: Point)
