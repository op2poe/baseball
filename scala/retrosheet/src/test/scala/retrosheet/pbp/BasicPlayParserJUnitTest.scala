package retrosheet.pbp

object BasicPlayParserJUnitTest extends App {

  testStraightForwardBaseHits()
  println("OK")
  
  def testStraightForwardBaseHits() {
    assert(BasicPlayParser.parse("S") == Advancement.ofBatter(1))
    assert(BasicPlayParser.parse("S7") == Advancement.ofBatter(1))
    assert(BasicPlayParser.parse("D8") == Advancement.ofBatter(2))
    assert(BasicPlayParser.parse("D") == Advancement.ofBatter(2))
    assert(BasicPlayParser.parse("T9") == Advancement.ofBatter(3))
    assert(BasicPlayParser.parse("T") == Advancement.ofBatter(3))
    assert(BasicPlayParser.parse("H") == Advancement.ofBatter(4))
    assert(BasicPlayParser.parse("HR") == Advancement.ofBatter(4))
    assert(BasicPlayParser.parse("H8") == Advancement.ofBatter(4))
    assert(BasicPlayParser.parse("HR9") == Advancement.ofBatter(4))
  }
  
}