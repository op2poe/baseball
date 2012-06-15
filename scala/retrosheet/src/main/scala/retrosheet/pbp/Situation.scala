package retrosheet.pbp

import scala.util.matching.Regex

class Situation private (private val bases: Bases,
						 private var outs: Int,
						 private var runsScored: Int) {

  private var runnerAdvanceRegEx = new Regex("(\\d)-(\\d).*")
  
  private var runnerScoredRegEx = new Regex("(\\d)-H.*")
  
  private var runnerOutRegEx = new Regex("(\\d)X(\\d|H)")
  
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
      
    }
    return batter
  }
  
}


object Situation {
  
  def startOfInning() = {
    new Situation(Bases.empty(), 0, 0)
  }
  
}