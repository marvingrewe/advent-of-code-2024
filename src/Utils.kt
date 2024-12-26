import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun <T> T.println() = this.also { println(this) }

fun <T> List<T>.toPair() = this[0] to this[1]

fun <T, R> Pair<T, T>.map(f: (T) -> R) = f(first) to f(second)

fun <T> List<List<T>>.toPairOfLists() = map { it.toPair() }.unzip()

fun List<String>.toIntLists(regex: Regex = Regex("\\s+")) = map { it.split(regex).map(String::toInt) }
fun Iterable<String>.toIntLists(regex: Regex = Regex("\\s+")) = map { it.split(regex).map(String::toInt) }

fun List<String>.isInBounds(row: Int, col: Int): Boolean = row in this.indices && col in this[row].indices

inline fun <T> Array<T>.indexOfPrevious(startIndex: Int, predicate: (T) -> Boolean): Int {
    for (index in startIndex downTo 0) {
        if (predicate(this[index]))
            return index
    }
    return -1
}

fun <T> List<T>.elementPairs(): Sequence<Pair<T, T>> = sequence {
    for (i in 0..<lastIndex) {
        for (j in i + 1..lastIndex) {
            yield(this@elementPairs[i] to this@elementPairs[j])
        }
    }
}

val integers = generateSequence(0) {it + 1}

typealias Point = Pair<Int, Int>

operator fun Point.plus(other: Point) = this.first + other.first to this.second + other.second

operator fun Point.minus(other: Point) = this.first - other.first to this.second - other.second

operator fun Point.times(scalar: Int) = this.first * scalar to this.second * scalar
