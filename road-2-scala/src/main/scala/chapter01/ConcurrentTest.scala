package chapter01

import ox.*
import org.junit.jupiter.api.Test

import scala.concurrent.duration.DurationInt

class ConcurrentTest {

  /**
   * 演示 1：结构化并发 —— 一个 fork 失败会取消整个作用域
   * 这是 Ox 的核心特性：boom! 会中断另一个 sleep，导致 Hello! 永远不打印。
   * 若不希望测试因异常而失败，可用 try/catch 包一层。
   */
  @Test
  def test01(): Unit = {
    try
      supervised {
        forkUser:
          sleep(1.second)
          println("Hello!")

        forkUser:
          sleep(500.millis)
          throw new RuntimeException("boom!")
      }
    catch case e: RuntimeException =>
      println(s"caught: ${e.getMessage}")   // 预期能捕获到 boom!
  }

  /**
   * 演示 2：par —— 最像 Kotlin coroutineScope 的写法
   * 两个任务并行跑，都完成才返回；总耗时约 1 秒而不是 2 秒。
   */
  @Test
  def test02(): Unit = {
    val start = System.currentTimeMillis()
    val (a, b) = par(
      { sleep(1.second); "任务A" },
      { sleep(1.second); "任务B" }
    )
    println(s"$a & $b, 耗时 ${System.currentTimeMillis() - start} ms")
  }

  /**
   * 演示 3：fork + join —— 手动拿协程结果
   * 类似 Kotlin async/await。
   */
  @Test
  def test03(): Unit = {
    supervised {
      val f1 = fork:
        sleep(300.millis)
        1 + 2
      val f2 = fork:
        sleep(500.millis)
        10 * 10
      println(s"result = ${f1.join() + f2.join()}")   // 103
    }
  }

  /**
   * 演示 4：timeout —— 超时自动取消协程
   */
  @Test
  def test04(): Unit = {
    try
      timeout(300.millis) {
        sleep(1.second)
        println("不会执行到这里")
      }
    catch case _: java.util.concurrent.TimeoutException =>
      println("超时被取消，符合预期")
  }

}
