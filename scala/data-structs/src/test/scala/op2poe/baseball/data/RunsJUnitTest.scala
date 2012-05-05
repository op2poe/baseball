package op2poe.baseball.data

object RunsJUnitTest extends App {

  testPythagoreanWL()
  
  def testPythagoreanWL() {
    val r = new Runs(830, 631) // 1941 New York Yankees
    val pythWL = r.pythagoreanWL(154)
    println(pythWL)
    assert(pythWL.wins == 96)
    assert(pythWL.losses == 58)
  }
  
}