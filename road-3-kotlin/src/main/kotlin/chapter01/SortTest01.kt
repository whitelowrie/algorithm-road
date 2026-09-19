package chapter01

import org.junit.jupiter.api.Test

class SortTest01 {

    val tools = NumberToos(100000000, 100)
    /**
     * 选择排序
     */
    @Test
    fun test01(){
        val items = arrayOf(5, 3, 1, 4, 2)
        for (i in items.indices) {
            var minIndex = i
            for (j in i + 1 until items.size) {
                if(items[j] < items[minIndex]) {
                    minIndex = j
                }
            }
            swap(items, i, minIndex)
        }
        println(items.joinToString())
    }

    fun swap(items: Array<Int>, i: Int, j: Int) {
        val temp = items[i]
        items[i] = items[j]
        items[j] = temp
    }

    /**
     * 冒泡排序
     */
    @Test
    fun test02(){
        val items = arrayOf(5, 3, 1, 4, 2)
        for (i in items.indices) {
            for (i in 0 until items.size - i - 1) {
                if(items[i] > items[i + 1]) {
                    swap(items, i, i + 1)
                }
            }
        }
        println(items.joinToString())
    }

    @Test
    fun test03(){
        val items = arrayOf(17, 18, 19, 5, 18, 19, 17, 5, 19, 5)

        val eor = items.fold(0){ acc, item -> acc xor item }
        val rightOne = eor and (eor.inv() + 1)
        val eor1 = items.filter { it and rightOne == 0 }.fold(eor){ acc, item -> acc xor item }
        println(eor.toString() + " => " + eor1 + ":" + (eor xor eor1))
    }

    /**
     * 插入排序
     */
    @Test
    fun test04(){
        val items = arrayOf(5, 3, 1, 4, 2)
        for (i in 1 until items.size) {
            for (j in i - 1 downTo 0) {
                if (items[j] > items[j + 1]) {
                    swap(items, j, j + 1)
                } else {
                    break
                }
            }
        }
        println(items.joinToString())
    }

    @Test
    fun test05(){
        val items = this.tools.getItems()
        println(items.joinToString())
        process(items, 0, items.size - 1)
        println(items.joinToString())
    }

    fun process(items: Array<Int>, min: Int, max: Int) {
        if(min == max) {
            return
        }
        val mid = min + ((max - min) shr 1)
        process(items, min, mid)
        process(items, mid + 1, max)
        merge(items, min, mid, max)
    }
    fun merge(items: Array<Int>, min: Int, mid: Int, max: Int) {
        val helper = Array(max - min + 1){ 0 }
        var i0 = 0
        var p1 = min
        var p2 = mid + 1
        while(p1 <= mid && p2 <= max) {
            helper[i0++] = if(items[p1] > items[p2]) items[p2++] else items[p1++]
        }
        while(p1 <= mid) {
            helper[i0++] = items[p1++]
        }
        while(p2 <= max) {
            helper[i0++] = items[p2++]
        }
        System.arraycopy(helper, 0, items, min, helper.size)
    }
}