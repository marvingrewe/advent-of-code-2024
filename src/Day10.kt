import kotlin.time.measureTimedValue

fun main() {
    val day = "Day10"

    fun part1(input: List<String>): Int {
        val mountainMap = input.map { it.map(Char::digitToInt) }
        val mountain = Mountain(mountainMap)

        return mountain.mountainScore()
    }

    fun part2(input: List<String>): Int {
        val mountainMap = input.map { it.map(Char::digitToInt) }
        val mountain = Mountain(mountainMap)

        return mountain.mountainRating()
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 36" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 81" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}

class Mountain(private val map: List<List<Int>>) {

    fun mountainScore(): Int {
        var mountainScore = 0

        map.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, _ ->
                if (map[rowIndex][colIndex] == 0) mountainScore += trailheadScore(rowIndex, colIndex)
            }
        }

        return mountainScore
    }

    fun mountainRating(): Int {
        var mountainRating = 0

        map.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, _ ->
                if (map[rowIndex][colIndex] == 0) mountainRating += trailheadRating(rowIndex, colIndex)
            }
        }

        return mountainRating
    }

    private fun trailheadScore(row: Int, col: Int): Int {
        verifyInBounds(row, col)
        val reachableMountainTops = hikeUpwardsScore(setOf(row to col))

        return reachableMountainTops.size
    }

    private fun trailheadRating(row: Int, col: Int): Int {
        verifyInBounds(row, col)
        val distinctHikingTrails = hikeUpwardsRating(listOf(row to col))

        return distinctHikingTrails.size
    }

    private tailrec fun hikeUpwardsScore(currentPositions: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        currentPositions.forEach { verifyInBounds(it.first, it.second) }
        if (currentPositions.map { map[it.first][it.second] }
                .toSet().size > 1) throw IllegalArgumentException("given positions have varying heights")

        if (currentPositions.first().let { map[it.first][it.second] == 9 }) return currentPositions

        val nextTiles = mutableSetOf<Pair<Int, Int>>()
        currentPositions.forEach { nextTiles.addAll(hikableTiles(it.first, it.second)) }

        return hikeUpwardsScore(nextTiles)
    }

    private tailrec fun hikeUpwardsRating(currentPositions: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        currentPositions.forEach { verifyInBounds(it.first, it.second) }
        if (currentPositions.map { map[it.first][it.second] }
                .toSet().size > 1) throw IllegalArgumentException("given positions have varying heights")

        if (currentPositions.first().let { map[it.first][it.second] == 9 }) return currentPositions

        val nextTiles = mutableListOf<Pair<Int, Int>>()
        currentPositions.forEach { nextTiles.addAll(hikableTiles(it.first, it.second)) }

        return hikeUpwardsRating(nextTiles)
    }

    private fun hikableTiles(row: Int, col: Int): Set<Pair<Int, Int>> {
        verifyInBounds(row, col)
        val targetHeight = map[row][col] + 1

        val neighbouringTiles = setOfNotNull(
            if (isInBoundsAndHeight(row - 1, col, targetHeight)) row - 1 to col else null,
            if (isInBoundsAndHeight(row, col + 1, targetHeight)) row to col + 1 else null,
            if (isInBoundsAndHeight(row + 1, col, targetHeight)) row + 1 to col else null,
            if (isInBoundsAndHeight(row, col - 1, targetHeight)) row to col - 1 else null
        )

        return neighbouringTiles
    }

    private fun isInBoundsAndHeight(row: Int, col: Int, height: Int) = isInBounds(row, col) && map[row][col] == height

    private fun isInBounds(row: Int, col: Int): Boolean = row in map.indices && col in map[row].indices

    private fun verifyInBounds(row: Int, col: Int) {
        if (!isInBounds(row, col)) throw IndexOutOfBoundsException()
    }
}
