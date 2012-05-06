package op2poe.util

import op2poe.util.Die.Side
import java.util.Arrays

object DieTest extends App {

  testTwoSidedDie()
  testSixSidedDie()
  testWeightedSixSidedDie()
  
  def testTwoSidedDie() {
    val die: Die[String] = Die(Side("Even", 1), Side("Odd", 1))
    println(die.roll())
    println(die.roll())
    println(die.roll())
    println(die.roll())
  }
  
  def testSixSidedDie() {
    val die = Die[Int]((for (v <- 1 to 6) yield Side(v, 1)):_*)
    val sums = new Array[Int](6)
    for (_ <- 1 to 1000) sums(die.roll() - 1) += 1
    println(Arrays.toString(sums))
  }
  
  def testWeightedSixSidedDie() {
    val die = Die((for (v <- 1 to 6) yield Side(v, v)):_*)
    val sums = new Array[Int](6)
    for (_ <- 1 to 1000) sums(die.roll() - 1) += 1
    println(Arrays.toString(sums))
  }
  
}
