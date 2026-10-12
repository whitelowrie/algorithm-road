package chapter01

import org.junit.jupiter.api.Test

class SortTest02 {

    @Test
    fun test01(){
        val number = 123;
        val result = (0 .. 31)
            .map { number and (1 shl it) }
            .map { if(it == 0) '0' else '1' }
            .reversed()
            .joinToString("")
        println(result)
    }

}