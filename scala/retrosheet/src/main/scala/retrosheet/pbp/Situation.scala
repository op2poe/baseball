package retrosheet.pbp

import scala.util.matching.Regex

class Situation private (private val bases: Bases,
						 private var outs: Int,
						 private var runsScored: Int) {
  
  def processEvent(batter: String, event: String) {
    val parts = event.split("\\.")
    var batterHasBeenProcessed = parts.length match {
      case 1 => // TODO
      case 2 => processAdvancementOfRunners(parts(2))
    }
  }
  
  private def processAdvancementOfRunners(adv: String): Boolean = {
    var batter = false
    val codes = adv.split(";")
    for (code <- codes) {
      val a = AdvancementParser.parse(code)
      a.applyTo(bases)
      if (a.runScored) runsScored += 1
      if (a.isOut) outs += 1
      batter = batter || a.fromBase == 0
    }
    return batter
  }
  
}


object Situation {
  
  def startOfInning() = {
    new Situation(Bases.empty(), 0, 0)
  }
  
}