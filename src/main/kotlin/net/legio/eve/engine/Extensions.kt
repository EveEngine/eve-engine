package net.legio.eve.engine

import java.io.InputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path

/** Returns true if the character is one of the following: !@#$%^&*() */
fun Char.isSpecial(): Boolean {
    return """!@#$%^&*()""".contains(this)
}

fun Path.inputStream(): InputStream {
    return Files.newInputStream(this)
}

fun String.containsWhitespace(): Boolean {
    return this.toCharArray().any { c -> c.isWhitespace() }
}

fun String.isEmail(): Boolean {
    return this.matches("(?:[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#${'$'}%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex())
}

inline fun <T> MutableList<T>.findOrDefaultAdd(predicate: (T) -> Boolean, default: () -> T): T {
    for (element in this) if (predicate.invoke(element)) return element
    return default.invoke().apply { add(this) }
}

fun InputStream.contentAsString(charset: Charset = Charsets.UTF_8): String? {
    return this.readBytes().toString(charset)
}