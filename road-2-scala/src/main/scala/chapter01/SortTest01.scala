package chapter01

import org.junit.jupiter.api.Test

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, blocking}

class SortTest01 {

  import java.util.concurrent.Executors
  import scala.concurrent.ExecutionContext

  given ExecutionContext =
    ExecutionContext.fromExecutor(Executors.newVirtualThreadPerTaskExecutor())

  private val tools = NumberTools(10, 1000000)
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
    val items: Array[Int] = tools.getItems
    val start: Long = System.currentTimeMillis()
    process(items, 0, items.length - 1)
    val end: Long = System.currentTimeMillis()
    println(s"耗时${end - start}毫秒")
  }

  private def process(arr: Array[Int], min: Int, max: Int): Unit = {
    if (min == max) then ()      // 什么都不做，直接返回 Unit
    else
      val mid: Int = min + ((max - min) >> 1)
      val f1 = Future{process(arr, min, mid)}
      val f2 = Future{process(arr, mid + 1, max)}
      blocking{Await.result(Future.sequence(Seq(f1, f2)), Duration.Inf)}
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
    val inc: (Int, Int) => Unit = (k: Int, max: Int) => {
      var s: Int = k
      while (s <= max) do
        temp(i) = arr(s)
        i += 1
        s += 1
    }
    inc(p1, mid)
    inc(p2, max)
    // 把 temp 拷贝回 arr
    for k <- temp.indices do arr(min + k) = temp(k)
  }

  @Test
  def test11(): Unit = {
    val items: Array[Int] = tools.getItems
    tools.pritln()
    this.process01(items, 0, items.length - 1)
    println(items.mkString(","))
  }

  import ox.*
  private def process01(items: Array[Int], min: Int, max: Int): Unit = {
    if (min == max) then ()
    else {
      val mid: Int = min + ((max - min) >> 1)
      par (
        process01(items, min, mid),
        process01(items, mid + 1, max)
      )
      merge(items, min, mid, max)
    }
  }
  private def merge01(items: Array[Int], min: Int, mid: Int, max: Int): Unit = {
    val temp = new Array[Int](max - min + 1)
    var i  = 0
    var p1 = min
    var p2 = mid + 1
    while(i <= mid && p2 <= max) {
      temp(i) = if items(p1) > items(p2) then
        val v1 = items(p2)
        p1 += 1
        v1
      else
        val v2 = items(p1)
        p2 += 1
        v2
      i += 1
    }
    while(p1 <= mid) {
      temp(i) = items(p1)
      i += 1
      p1 += 1
    }
    while(p2 <= max) {
      temp(i) = items(p2)
      i += 1
      p2 += 1
    }
    for i <- temp.indices do items(min + i) = temp(i)
  }

}
