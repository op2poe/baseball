package retrosheet.pbp

import scala.collection.mutable.HashMap

import scala.util.matching.Regex

class Situation private (private val bases: Bases,
						 private var outs: Int,
						 private var runsScored: Int) {

  def processEvent(batter: String, event: String) {
    // 1. Parse the basic play, and collect all advancements.
    // 2. Parse the advancement section, and collect all advancements.
    // 3. An advancement from 2 has priority over a conflicting 
    //    advancement from 1 (since 2 is more explicit).
    // 4. Apply all unique advancements.
    val map = HashMap[Int, Advancement]()
    val parts = event.split("\\.")
    addToMap(map, BasicPlayParser.parse(parts(0)))
    if (parts.length == 2) {
      addToMap(map, processAdvancementOfRunners(parts(1)))
    }
    for (f <- 0 until 4) {
      map.get(f) match {
        case Some(a) => applyAdvancement(a)
        case None => // Nothing to do.
      }
    }
  }

  private def processAdvancementOfRunners(adv: String): Seq[Advancement] = {
    val codes = adv.split(";")
    codes.map(AdvancementParser.parse(_))
  }

  private def addToMap(map: HashMap[Int, Advancement], advs: Seq[Advancement]) {
    for (a <- advs) {
      map(a.fromBase) = a
    }
  }
  
  private def applyAdvancement(a: Advancement) {
    a.applyTo(bases)
    if (a.isOut) outs += 1
    if (a.runScored) runsScored += 1
  }
}


object Situation {

  def startOfInning() = {
    new Situation(Bases.empty(), 0, 0)
  }

}