package retrosheet.pbp

object AdvancementJUnitTest extends App {

  val ruth = Some("Ruth")
  val mantle = Some("Mantle")

  testMovingRunnerFromFirstToSecond()
  testMovingRunnerFromSecondToThird()
  testMovingRunnerFromThirdToHome()
  testMovingRunnerFromFirstToThird()
  testNoAdvance()
  ensureRunnertoAdvanceIsOnBase()
  ensureWeDontPutTwoRunnerOnSameBase()
  println("OK")

  def testMovingRunnerFromFirstToSecond() {
    val b = Bases.empty()
    b.first = ruth
    val a = Advancement(1, 2)
    a.applyTo(b)
    assert(b.first.isEmpty)
    assert(b.second == ruth)
  }

  def testMovingRunnerFromSecondToThird() {
    val b = Bases.empty()
    b.second = ruth
    val a = Advancement(2, 3)
    a.applyTo(b)
    assert(b.second.isEmpty)
    assert(b.third == ruth)
  }

  def testMovingRunnerFromThirdToHome() {
    val b = Bases.empty()
    b.third = ruth
    val a = Advancement(3, 4)
    assert(a.runScored)
    a.applyTo(b)
    assert(b.isEmpty)
  }
  
  def testMovingRunnerFromFirstToThird() {
    val b = Bases.empty()
    b.first = ruth
    val a = Advancement(1, 3)
    a.applyTo(b)
    assert(b.first.isEmpty)
    assert(b.third == ruth)
  }

  def testNoAdvance() {
    val b = Bases.empty()
    b.first = ruth
    val a = Advancement(1, 1)
    a.applyTo(b)
    assert(b.first == ruth)
    assert(b.second.isEmpty)
    assert(b.third.isEmpty)
  }

  def ensureRunnertoAdvanceIsOnBase() {
    try {
      val b = Bases.empty()
      b.second = ruth
      val a = Advancement(1, 3)
      a.applyTo(b)
      assert(false)
    } catch {
      case ex: IllegalBaseOperation => assert(true) // expected
      case _ => assert(false)
    }
  }
  
  def ensureWeDontPutTwoRunnerOnSameBase() {
    try {
      val b = Bases.empty()
      b.first = mantle
      b.second = ruth
      val a = Advancement(1, 2)
      a.applyTo(b)
      assert(false)
    } catch {
      case ex: IllegalBaseOperation => assert(true) // expected
      case _ => assert(false)
    }
  }

}