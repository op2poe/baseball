package op2poe.baseball.data.batting

import scala.collection.mutable.HashMap;

final class MapBattingStats extends BattingStats {

  val map = new HashMap[String, Int] {

    override def default(key: String) = 0

  }

  def atBats(): Int = { map("AB") }

  def hits(): Int = { map("H") }

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

  def stolenBases(): Int = { map("SB") }
  
  def caughtStealing(): Int = { map("CS") }
  
  def add(ab: Int = 0, h: Int = 0, doubles: Int = 0, triples: Int = 0, hr: Int = 0,
		  so: Int = 0, bb: Int = 0, sh: Int = 0, sf: Int = 0, hbp: Int = 0, 
		  r: Int = 0, rbi: Int = 0,
		  sb: Int = 0, cs: Int = 0): BattingStats = {
    val sum = new MapBattingStats
    sum.map("AB") = atBats + ab
    sum.map("H") = hits + h
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
    sum.map("SB") = stolenBases + sb
    sum.map("CS") = caughtStealing + cs
    sum.sanityCheck()
    sum
  }
  
  private def sanityCheck() {
    require(map.values.forall(_ >= 0))
    checkInvariants()
  }
}

object MapBattingStats {
  
  def empty = new MapBattingStats

  // TODO: Identical code in the ArrayBattingStats companion object.
  // Can we eliminate this duplication?
  def apply(ab: Int = 0, h: Int = 0, doubles: Int = 0, triples: Int = 0, hr: Int = 0,
		  so: Int = 0, bb: Int = 0, sh: Int = 0, sf: Int = 0, hbp: Int = 0, 
		  r: Int = 0, rbi: Int = 0,
		  sb: Int = 0, cs: Int = 0): BattingStats = {
    val stats = empty
    stats.add(ab, h, doubles, triples, hr, so, bb, sh, sf, hbp, r, rbi, sb, cs)
  }
  
}
