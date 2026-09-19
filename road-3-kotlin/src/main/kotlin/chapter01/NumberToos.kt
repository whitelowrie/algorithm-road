package chapter01

import java.util.Collections
import kotlin.random.Random

class NumberToos(numberSize: Int, maxNumber: Int) {

    private val items = (0..numberSize).map { Random.nextInt(maxNumber + 1) - Random.nextInt(maxNumber + 1) }.toTypedArray()

    fun getItems(): Array<Int> = items.copyOf()

    fun equals(other: Array<Int>?): Boolean = other?.contentEquals(items.sortedArray()) ?: false

    override fun toString() = items.joinToString()
}