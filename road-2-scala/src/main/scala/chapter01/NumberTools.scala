package chapter01

class NumberTools(maxSize: Int, maxValue: Int) {

  val items: Array[Int] = Array.fill(maxSize)(generate(maxValue))


  def generate(maxValue: Int): Int = ((maxValue+1)*Math.random() - maxValue*Math.random()).toInt


  def getItems: Array[Int] = Array.copyOf(items, items.length)

  def equals(other: Array[Int]): Boolean =
    items.sorted sameElements other
    
  def pritln(): Unit = {
    println(items.mkString("Array(", ", ", ")"))
  }

}
