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

  def add(ab: Int = 0, s: Int = 0, doubles: Int = 0, triples: Int = 0, hr: Int = 0,
		  so: Int = 0, bb: Int = 0, sh: Int = 0, sf: Int = 0, hbp: Int = 0, r: Int = 0,
		  rbi: Int = 0): BattingStats = {
    val sum = new MapBattingStats
    sum.map("AB") = atBats + ab
    sum.map("S") = singles + s
    sum.map("2B") = this.doubles + doubles
    sum.map("3B") = this.triples + triples
    sum.map("HR") = homeruns + hr
    sum.map("SO") = strikeouts + so
    sum.map("BB") = walks + bb
    sum.map("SH") = sacrificeHits + sh
    sum.map("SF") = sacrificeFlies + sf
    sum.map("HBP") = hitByPitch + hbp
    sum.map("R") = runs + r
    sum.map("RBI") = runsBattedIn + rbi
    sum
  }
}

object MapBattingStats {
  
  def empty = new MapBattingStats
  
}
