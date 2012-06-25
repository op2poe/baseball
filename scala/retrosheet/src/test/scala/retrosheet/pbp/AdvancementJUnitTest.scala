package retrosheet.pbp

object AdvancementJUnitTest extends App {

  val ruth = Some("Ruth")
  val mantle = Some("Mantle")

  testRunnersOn()
  testMovingRunnerFromFirstToSecond()
  testMovingRunnerFromSecondToThird()
  testMovingRunnerFromThirdToHome()
  testMovingRunnerFromFirstToThird()
  testNoAdvance()
  testMovingBatterToFirstBase()
  testBatterHitsHomerun()
  testRunnerOnFirstOutAtSecond()
  testRunnerOnSecondOutAtHome()
  testBatterOutAtSecond()
  ensureRunnertoAdvanceIsOnBase()
  ensureWeDontPutTwoRunnerOnSameBase()
  ensureOutsAreDetected()
  println("OK")

  def testRunnersOn() {
    val b = Bases.empty()
    assert(b.runnersOn == 0)
    b.first = ruth
    assert(b.runnersOn == 1)
    b.second = mantle
    assert(b.runnersOn == 2)
    b.third = Some("Mays")
    assert(b.runnersOn == 3)
  }
  
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

  def testMovingBatterToFirstBase() {
    val b = Bases.empty()
    val a = Advancement.ofBatter(1)
    a.applyTo(b)
    assert(b.first == Some("[batter]"))
  }
  
  def testBatterHitsHomerun() {
    val b = Bases.empty()
    val a = Advancement.ofBatter(4)
    a.applyTo(b)
    assert(a.runScored)
    assert(b.isEmpty)
  }
  
  def testRunnerOnFirstOutAtSecond() {
    val b = Bases.empty()
    b.first = ruth
    val a = Advancement(1, -2)
    a.applyTo(b)
    assert(b.isEmpty)
  }
  
  def testRunnerOnSecondOutAtHome() {
    val b = Bases.empty()
    b.second = ruth
    val a = Advancement(2, -4)
    a.applyTo(b)
    assert(b.isEmpty)
  }
 
  def testBatterOutAtSecond() {
    val b = Bases.empty
    b.second = ruth
    val a = Advancement.ofBatter(-2)
    a.applyTo(b)
    assert(b.runnersOn == 1)
    assert(b.second == ruth)
  }
  
  def ensureOutsAreDetected() {
    assert(Advancement.ofBatter(0).isOut)
    assert(!Advancement.ofBatter(1).isOut)
    assert(Advancement.ofBatter(-1).isOut)
    assert(Advancement(1, -2).isOut)
    assert(!Advancement(1, 2).isOut)
    assert(!Advancement(1, 1).isOut)
  }
  
}
