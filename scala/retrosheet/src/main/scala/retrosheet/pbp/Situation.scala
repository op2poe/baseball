package retrosheet.pbp

import scala.util.matching.Regex

class Situation private (private val bases: Bases,
						 private var outs: Int,
						 private var runsScored: Int) {

  def processEvent(batter: String, event: String) {
    var batterHasBeenProcessed = false
    val parts = event.split("\\.")
    if (parts.length == 2) {
      batterHasBeenProcessed = processAdvancementOfRunners(parts(1))
    }
    if (!batterHasBeenProcessed) {
      processAdvancementOfBatter(parts(0))
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

  private def processAdvancementOfBatter(adv: String) {
    // TODO: Implement me.
  }

}

object Situation {

  def startOfInning() = {
    new Situation(Bases.empty(), 0, 0)
  }

}