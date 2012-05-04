package op2poe.baseball.data.batting

final class ArrayBattingStats private (values: Array[Int]) extends BattingStats {

  require(values.length == 14)
  require(values.forall(_ >= 0))
  checkInvariants()

  def atBats(): Int = { values(0) }

  def hits(): Int = { values(1) }

  def doubles(): Int = { values(2) }

  def triples(): Int = { values(3) }

  def homeruns(): Int = { values(4) }

  def strikeouts(): Int = { values(5) }

  def walks(): Int = { values(6) }

  def sacrificeHits(): Int = { values(7) }

  def sacrificeFlies(): Int = { values(8) }

  def hitByPitch(): Int = { values(9) }

  def runs(): Int = { values(10) }

  def runsBattedIn(): Int = { values(11) }

  def stolenBases(): Int = { values(12) }
  
  def caughtStealing(): Int = { values(13) }
  
  def add(ab: Int = 0, h: Int = 0, doubles: Int = 0, triples: Int = 0, hr: Int = 0,
		  so: Int = 0, bb: Int = 0, sh: Int = 0, sf: Int = 0, hbp: Int = 0, 
		  r: Int = 0, rbi: Int = 0, 
		  sb: Int = 0, cs: Int = 0): BattingStats = {
    val sum = Array[Int](14)
    values(0) = atBats + ab
    values(1) = hits + h
    values(2) = this.doubles + doubles
    values(3) = this.triples + triples
    values(4) = homeruns + hr
    values(5) = strikeouts + so
    values(6) = walks + bb
    values(7) = sacrificeHits + sh
    values(8) = sacrificeFlies + sf
    values(9) = hitByPitch + hbp
    values(10) = runs + r
    values(11) = runsBattedIn + rbi
    values(12) = stolenBases + sb
    values(13) = caughtStealing + cs
    new ArrayBattingStats(values)
  }

}

object ArrayBattingStats {

  def empty = new ArrayBattingStats(new Array[Int](14))

  // TODO: Identical code in the MapBattingStats companion object.
  // Can we eliminate this duplication?
  def apply(ab: Int = 0, h: Int = 0, doubles: Int = 0, triples: Int = 0, hr: Int = 0,
		  so: Int = 0, bb: Int = 0, sh: Int = 0, sf: Int = 0, hbp: Int = 0, r: Int = 0,
		  rbi: Int = 0): BattingStats = {
    val stats = empty
    stats.add(ab, h, doubles, triples, hr, so, bb, sh, sf, hbp, r, rbi)
  }
  
}
