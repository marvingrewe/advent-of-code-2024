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

fun List<String>.toPairOfLists(regex: Regex) = map { line -> line.split(regex).toPair() }.unzip()
