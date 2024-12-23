import kotlin.time.measureTimedValue

fun main() {
    val day = "Day06"

    fun part1(input: List<String>): Int {
        val map = input.map { it.chunked(1).toMutableList() }
        val lab = Lab(map)

        lab.move()

        // map.forEach { it.joinToString("").println() }

        return map.sumOf { row -> row.count { it == "X" } }
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.chunked(1).toMutableList() }
        val lab = Lab(map)

        lab.move()
        val loops = lab.findLoops()

        // map.forEach { it.joinToString("").println() }

        return loops
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 41" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 6" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}

class Lab(private val map: List<MutableList<String>>) {
    var outOfBounds = false
    private var guardDirection = Direction.UP
    private var guardRow = map.indexOfFirst { it.contains("^") }
    private var initialGuardRow = guardRow
    private var guardCol = map[guardRow].indexOf("^")
    private var initialGuardCol = guardCol

    fun move() {
        while (!outOfBounds) {
            map[guardRow][guardCol] = "X"
            val (nextRow, nextCol) = nextTile(guardDirection, guardRow, guardCol)
            if (isOutOfBounds(nextRow, nextCol)) {
                outOfBounds = true
                return
            }
            if (map[nextRow][nextCol] == "#") {
                guardDirection = guardDirection.turn()
            } else {
                guardRow = nextRow
                guardCol = nextCol
            }
        }
    }

    private fun isOutOfBounds(row: Int, col: Int) = row !in map.indices || col !in map[row].indices

    private fun nextTile(direction: Direction, row: Int, col: Int) = when (direction) {
        Direction.UP -> row - 1 to col
        Direction.RIGHT -> row to col + 1
        Direction.DOWN -> row + 1 to col
        Direction.LEFT -> row to col - 1
    }

    fun findLoops(): Int {
        var loops = 0
        map.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, _ ->
                if (map[rowIndex][colIndex] == "X") {
                    map[rowIndex][colIndex] = "#"
                    if (isLoop(Direction.UP, initialGuardRow, initialGuardCol)) loops++
                    map[rowIndex][colIndex] = "X"
                }
            }
        }
        return loops
    }

    private fun isLoop(initialDirection: Direction, initialRow: Int, initialCol: Int): Boolean {
        var direction = initialDirection
        var row = initialRow
        var col = initialCol
        val visitedSpaces = mutableSetOf<Triple<Direction, Int, Int>>()

        while (!visitedSpaces.contains(Triple(direction, row, col))) {
            visitedSpaces += Triple(direction, row, col)
            val (nextRow, nextCol) = nextTile(direction, row, col)
            if (isOutOfBounds(nextRow, nextCol)) {
                return false
            }
            if (map[nextRow][nextCol] == "#") {
                direction = direction.turn()
            } else {
                row = nextRow
                col = nextCol
            }
        }
        return true
    }
}

enum class Direction {
    UP {
        override fun turn() = RIGHT
    },
    RIGHT {
        override fun turn() = DOWN
    },
    DOWN {
        override fun turn() = LEFT
    },
    LEFT {
        override fun turn() = UP
    };

    abstract fun turn(): Direction
}
