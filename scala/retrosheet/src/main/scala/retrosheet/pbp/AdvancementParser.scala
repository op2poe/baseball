package retrosheet.pbp

import scala.util.matching.Regex

object AdvancementParser {

  private var runnerAdvance = new Regex("(\\d)-(\\d|H).*")
  
  private var runnerOut = new Regex("(\\d)X(\\d|H).*")

  private var batterAdvance = new Regex("B-(\\d|H).*")
  
  private var batterOut = new Regex("BX(\\d|H).*")
  
  def parse(s: String): Advancement = {
    s match {
      case runnerAdvance(from, to) => Advancement(toInt(from), toInt(to))
      case runnerOut(from, to) => Advancement(toInt(from), -toInt(to))
      case batterAdvance(to) => Advancement.ofBatter(toInt(to))
      case batterOut(to) => Advancement(0, 0)
    }
  }
  
  private def toInt(s: String) = if (s == "H") 4 else s.toInt
  
}