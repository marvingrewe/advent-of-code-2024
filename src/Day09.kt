import kotlin.time.measureTimedValue

fun main() {
    val day = "Day09"

    fun part1(input: List<String>): Long {
        val diskMap = input.first().map(Char::digitToInt).withIndex()
        var pos = 0
        val disk = Array<Int?>(diskMap.sumOf { it.value }) { _ -> null }

        diskMap.forEach { block ->
            when (block.index % 2) {
                0 -> for (offset in 0..<block.value) {
                    disk[pos] = block.index / 2
                    pos++
                }

                1 -> for (offset in 0..<block.value) {
                    disk[pos] = null
                    pos++
                }
            }
        }

        for (location in disk.indices) {
            if (disk[location] == null) {
                val indexOfLast = disk.indexOfLast { it != null }
                if (indexOfLast < location) break
                disk[location] = disk[indexOfLast].also { disk[indexOfLast] = null }
            }
        }

        return disk.withIndex().sumOf { (it.index * (it.value ?: 0)).toLong() }
    }

    fun findFirstSpaceLargerThan(map: Array<Int?>, targetLength: Int): IntRange {
        var previousElement: Int? = 0
        var currentRunLength = Int.MAX_VALUE
        for (index in map.indices) {
            if (map[index] != previousElement) {
                if (previousElement == null && currentRunLength >= targetLength) {
                    return (index - currentRunLength)..<index
                }
                previousElement = map[index]
                currentRunLength = 0
            }
            currentRunLength++
        }

        return IntRange.EMPTY
    }

    fun part2(input: List<String>): Long {
        val diskMap = input.first().map(Char::digitToInt).withIndex()
        var pos = 0
        val disk = Array<Int?>(diskMap.sumOf { it.value }) { _ -> null }

        diskMap.forEach { block ->
            when (block.index % 2) {
                0 -> for (offset in 0..<block.value) {
                    disk[pos] = block.index / 2
                    pos++
                }

                1 -> for (offset in 0..<block.value) {
                    disk[pos] = null
                    pos++
                }
            }
        }

        var previousElement: Int? = -1
        for (location in disk.indices.reversed()) {
            if (disk[location] != null && disk[location] != previousElement) {
                val nextBlock = disk.indexOfPrevious(location) { it != disk[location] }
                if (nextBlock < 0) break
                val lengthOfSpace = location - nextBlock

                val firstBlock = findFirstSpaceLargerThan(disk, lengthOfSpace)

                if (firstBlock != IntRange.EMPTY && firstBlock.first < location) {
                    firstBlock.last - firstBlock.first
                    val element = disk[location]
                    for (tile in (location - lengthOfSpace + 1)..location) {
                        disk[tile] = null
                    }
                    for (tile in firstBlock.first..<firstBlock.first + lengthOfSpace) {
                        disk[tile] = element
                    }
                }

            }
            previousElement = disk[location]
        }

        return disk.withIndex().sumOf { (it.index * (it.value ?: 0)).toLong() }
    }

    val testInput = readInput(day + "_test")
    val input = readInput(day)

    measureTimedValue { part1(testInput) }.run { "Test 1 solved in $duration with result: $value, expected: 1928" }.println()
    measureTimedValue { part2(testInput) }.run { "Test 2 solved in $duration with result: $value, expected: 2858" }.println()
    measureTimedValue { part1(input) }.run { "Part 1 solved in $duration with result: $value" }.println()
    measureTimedValue { part2(input) }.run { "Part 2 solved in $duration with result: $value" }.println()
}
