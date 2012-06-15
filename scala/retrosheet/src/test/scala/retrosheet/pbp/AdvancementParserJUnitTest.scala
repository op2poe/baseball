package retrosheet.pbp

object AdvancementParserJUnitTest extends App {

  assert(AdvancementParser.parse("1-2") == Advancement(1, 2))
  assert(AdvancementParser.parse("1-H") == Advancement(1, 4))
  assert(AdvancementParser.parse("1X2") == Advancement(1, -2))
  assert(AdvancementParser.parse("2-3") == Advancement(2, 3))
  assert(AdvancementParser.parse("2XH") == Advancement(2, -4))
  assert(AdvancementParser.parse("B-2") == Advancement(0, 2))
  assert(AdvancementParser.parse("B-H") == Advancement(0, 4))
  assert(AdvancementParser.parse("BX2") == Advancement(0, 0))
  println("OK")
  
  
}