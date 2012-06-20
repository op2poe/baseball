package retrosheet.pbp

object BasicPlayParserJUnitTest extends App {

  testStraightForwardBaseHits()
  testStraightForwardFieldingOuts()
  testWalks()
  testErrors()
  testGroundRuleDouble()
  testHitByPitch()
  testGroundIntoDoublePlay()
  testLinedIntoDoublePlay()
  testGroundedIntoTriplePlay()
  testLinedIntoTriplePlay()
  testInterference()
  println("OK")
  
  def testStraightForwardBaseHits() {
    oneAdvancement("S", Advancement.ofBatter(1))
    oneAdvancement("S4", Advancement.ofBatter(1))
    oneAdvancement("D", Advancement.ofBatter(2))
    oneAdvancement("D7", Advancement.ofBatter(2))
    oneAdvancement("T", Advancement.ofBatter(3))
    oneAdvancement("T8", Advancement.ofBatter(3))
    oneAdvancement("H", Advancement.ofBatter(4))
    oneAdvancement("HR", Advancement.ofBatter(4))
    oneAdvancement("H9", Advancement.ofBatter(4))
    oneAdvancement("HR7", Advancement.ofBatter(4))
  }
  
  private def oneAdvancement(s: String, expected: Advancement) {
    assert(BasicPlayParser.parse(s) == List(expected))
  }
  
  def testStraightForwardFieldingOuts() {
    oneAdvancement("5", Advancement.ofBatter(-1))
    oneAdvancement("43", Advancement.ofBatter(-1))
    oneAdvancement("543", Advancement.ofBatter(-1))
  }
  
  def testErrors() {
    oneAdvancement("E1", Advancement.ofBatter(1))
    oneAdvancement("E7", Advancement.ofBatter(1))
  }
  
  def testWalks() {
    oneAdvancement("I", Advancement.ofBatter(1))
    oneAdvancement("IW", Advancement.ofBatter(1))
    oneAdvancement("W", Advancement.ofBatter(1))
    oneAdvancement("W+WP", Advancement.ofBatter(1))
  }
  
  def testGroundRuleDouble() {
    oneAdvancement("DGR", Advancement.ofBatter(2))
  }
  
  def testHitByPitch() {
    oneAdvancement("HP", Advancement.ofBatter(1))
  }
  
  def testGroundIntoDoublePlay() {
    twoAdvancements("64(1)3", Advancement(1, -2), Advancement.ofBatter(-1))
    twoAdvancements("4(1)3", Advancement(1, -2), Advancement.ofBatter(-1))
  }
  
  private def twoAdvancements(s: String, expectedFirst: Advancement, 
      expectedSecond: Advancement) {
    assert(BasicPlayParser.parse(s) == List(expectedFirst, expectedSecond))
  }
  
  def testLinedIntoDoublePlay() {
    twoAdvancements("8(B)84(2)", Advancement(2, -3), Advancement.ofBatter(0))
    twoAdvancements("3(B)3(1)", Advancement(1, -2), Advancement.ofBatter(0))
  }
  
  def testGroundedIntoTriplePlay() {
    threeAdvancements("5(2)54(1)3", 
        Advancement(2, -3), Advancement(1, -2), Advancement.ofBatter(-1))
  }
  
  def testLinedIntoTriplePlay() {
    threeAdvancements("1(B)16(2)63(1)",
        Advancement(2, -3), Advancement(1, -2), Advancement.ofBatter(0))
  }
  
  private def threeAdvancements(s: String, exp1: Advancement, exp2: Advancement, 
      exp3: Advancement) {
    assert(BasicPlayParser.parse(s) == List(exp1, exp2, exp3))
  }
  
  def testInterference() {
    oneAdvancement("C/E1", Advancement.ofBatter(1))
    oneAdvancement("C/E2", Advancement.ofBatter(1))
    oneAdvancement("C/E3", Advancement.ofBatter(1))
  }
  
}
