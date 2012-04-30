package op2poe.baseball.data

import scala.util.Sorting

object WLTJUnitTest extends App {

  testAdd
  testCompare
  testAddResult
  
  def testAdd() {
    val wlt_1 = new WLT()
    val wlt_2 = new WLT(2, 1, 0)
    var sum = wlt_1 + wlt_2
    assert(sum.wins == 2)
    assert(sum.losses == 1)
    assert(sum.ties == 0)
    sum += new WLT(0, 3, 1)
    assert(sum.wins == 2)
    assert(sum.losses == 4)
    assert(sum.ties == 1)
  }
  
  def testCompare() {
    val wlt_0 = new WLT(0, 0, 0)
    val wlt_1 = new WLT(2, 0, 0)
    val wlt_2 = new WLT(2, 1, 1)
    assert(wlt_0 == new WLT())
    assert(wlt_0 < wlt_1)
    assert(wlt_0 < wlt_2)
    assert(wlt_1 > wlt_2)
    assert(wlt_2 < wlt_1)
  }
  
  def testAddResult() {
    val wlt_0 = new WLT
    val wlt_1 = wlt_0.addResult(new Runs(1, 0))
    assert(wlt_1 == new WLT(1, 0, 0))
    val wlt_2 = wlt_1.addResult(new Runs(0, 1))
    assert(wlt_2 == new WLT(1, 1, 0))
    val wlt_3 = wlt_2.addResult(new Runs(0, 0))
    assert(wlt_3 == new WLT(1, 1, 1))
  }
  
}