package chapter01

import org.junit.jupiter.api.Test

class SortTest01 {

  val tools = NumberTools(10, 20)
  /**
   * 选择排序
   */
  @Test
  def test01(): Unit = {
    val items = Array(5, 3, 1, 4, 2)
    for i <- items.indices do
      var min = i
      for j <- (i + 1) until items.length do
        if items(j) < items(min) then
          min = j
      swap(items, i, min)
    println(items.mkString("Array(", ", ", ")"))
  }

  def swap(items: Array[Int], a: Int, b: Int): Unit = {
    val temp = items(a)
    items(a) = items(b)
    items(b) = temp
  }

  /**
   * 冒泡排序
   */
  @Test
  def test02(): Unit = {
    val items = Array(5, 3, 1, 4, 2)
    for i <- items.indices do
      for j <- 0 until items.length - i - 1 do
        if items(j) > items(j + 1) then
          swap(items, j, j + 1)

    println(items.mkString("Array(", ", ", ")"))
  }

  @Test
  def test03(): Unit = {
    val arr = Array(17, 18, 19, 5, 18, 19, 17, 5, 19, 5)
    val eor = arr.foldLeft(0)(_ ^ _)
    val rightOne = eor & (~eor + 1)
    val eor1 = arr
      .filter(s => (s & rightOne) == 0)
      .foldLeft(eor)(_ ^ _)

    println(s"结果为${eor1}, ${eor ^ eor1}")
  }

  /**
   * 插入排序
   */

  @Test
  def test04(): Unit = {

    val items = tools.getItems

    println(items.mkString("Array(", ", ", ")"))

    for i <- 1 until items.length do {
      for j <- (i - 1) to (0, -1) if items(j) > items(j + 1) do {
          swap(items, j, j + 1)
      }
    }
    println(items.mkString("Array(", ", ", ")"))

    println(tools.equals(items))

  }

  /**
   * 归并排序
   */
  @Test
  def test05(): Unit = {
    val items = tools.getItems
    tools.pritln()
    process(items, 0, items.length - 1)
    println(items.mkString("Array(", ", ", ")"))
  }

  private def process(arr: Array[Int], min: Int, max: Int): Unit = {
    if (min == max) then ()      // 什么都不做，直接返回 Unit
    else
      val mid = min + ((max - min) >> 1)
      process(arr, min, mid)
      process(arr, mid + 1, max)
      merge(arr, min, mid, max)
  }

  private def merge(arr: Array[Int], min: Int, mid: Int, max: Int): Unit = {
    var i = 0
    var p1 = min
    var p2 = mid + 1
    val temp = new Array[Int](max - min + 1)
    while (p1 <= mid && p2 <= max) {
      if arr(p1) <= arr(p2) then
        temp(i) = arr(p1)
        p1 += 1
      else
        temp(i) = arr(p2)
        p2 += 1
      i += 1
    }
    val inc = (k: Int, max: Int) => {
      var s = k
      while (s <= max) {
        temp(i) = arr(s)
        i += 1
        s += 1
      }
    }
    inc(p1, mid)
    inc(p2, max)
    // 把 temp 拷贝回 arr
    for k <- temp.indices do arr(min + k) = temp(k)
  }

}
