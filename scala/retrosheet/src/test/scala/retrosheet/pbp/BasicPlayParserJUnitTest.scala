package retrosheet.pbp

object BasicPlayParserJUnitTest extends App {

  testStraightForwardBaseHits()
  testGroundRuleDouble()
  testGroundIntoDoublePlay()
  testLinedIntoDoublePlay()
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
  
  def testGroundRuleDouble() {
    oneAdvancement("DGR", Advancement.ofBatter(2))
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
}
