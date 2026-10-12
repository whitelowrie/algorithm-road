package chapter01

import org.junit.jupiter.api.Test

class SortTest02 {

  @Test
  def test01(): Unit = {
    val number = 123
    val result = (0 to 31)
      .map((i: Int) => number & (1 << i))
      .map((s: Int) => if s == 0 then '0' else '1')
      .reverse
      .mkString("")

    println(result)
  }

}
