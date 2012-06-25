package retrosheet.pbp

import scala.util.matching.Regex

class Situation private (val bases: Bases,
						 var outs: Int,
						 var runsScored: Int) {

  def processEvent(batter: String, event: String): Unit = {
    // 1. Parse the basic play, and collect all advancements.
    // 2. Parse the advancement section, and collect all advancements.
    // 3. An advancement from 2 has priority over a conflicting 
    //    advancement from 1 (since 2 is more explicit).
    // 4. Apply all unique advancements.
    val parts = event split "\\."
    val basic = BasicPlayParser parse parts(0)
    val runners = if (parts.length == 2) parseAdvancementOfRunners(parts(1)) else Nil
    val map = toMap(basic) ++ toMap(runners)
    for (f <- 3 to 0 by -1) {
      map.get(f).foreach(applyAdvancement(_))
    }
  }

  private def parseAdvancementOfRunners(adv: String): Seq[Advancement] = {
    adv.split(";").map(AdvancementParser.parse(_))
  }
  
  private def toMap(advs: Seq[Advancement]) = 
    advs map {a => (a.fromBase, a)} toMap
  
  private def applyAdvancement(a: Advancement) {
    a applyTo bases
    if (a.isOut) outs += 1
    if (a.runScored) runsScored += 1
  }
}


object Situation {

  def startOfInning(): Situation = {
    new Situation(Bases.empty(), 0, 0)
  }

}