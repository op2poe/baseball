package op2poe.baseball.data.batting

import scala.collection.mutable.HashMap;

final class MapBattingStats extends BattingStats {

  val map = new HashMap[String, Int] {
    
    override def default(key: String) = 0
    
  }
  
  def atBats(): Int = { map("AB") }

  def singles(): Int = { map("S") }

  def doubles(): Int = { map("2B") }

  def triples(): Int = { map("3B") }

  def homeruns(): Int = { map("HR") }

  def strikeouts(): Int = { map("SO") }

  def walks(): Int = { map("BB") }

  def sacrificeHits(): Int = { map("SH") }

  def sacrificeFlies(): Int = { map("SF") }

  def hitByPitch(): Int = { map("HBP") }

  def runs(): Int = { map("R") }

  def runsBattedIn(): Int = { map("RBI") }

  def +(other: BattingStats): BattingStats = { 
    val sum = new MapBattingStats
    sum.map("AB") = atBats + other.atBats
    sum.map("S") = singles + other.singles
    sum.map("2B") = doubles + other.doubles
    sum.map("3B") = triples + other.triples
    sum.map("HR") = homeruns + other.homeruns
    sum.map("SO") = strikeouts + other.strikeouts
    sum.map("BB") = walks + other.walks
    sum.map("SH") = sacrificeHits + other.sacrificeHits
    sum.map("SF") = sacrificeFlies + other.sacrificeFlies
    sum.map("HBP") = hitByPitch + other.hitByPitch
    sum.map("R") = runs + other.runs
    sum.map("RBI") = runsBattedIn + other.runsBattedIn
    sum
  }

}