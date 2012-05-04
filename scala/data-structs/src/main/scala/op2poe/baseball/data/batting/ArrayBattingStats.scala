package op2poe.baseball.data.batting

final class ArrayBattingStats private (values: Array[Int]) extends BattingStats {

  require(values.length == 15)
  require(values.forall(_ >= 0))
  checkInvariants()

  def games(): Int = { values(0) }
  
  def atBats(): Int = { values(1) }

  def hits(): Int = { values(2) }

  def doubles(): Int = { values(3) }

  def triples(): Int = { values(4) }

  def homeruns(): Int = { values(5) }

  def strikeouts(): Int = { values(6) }

  def walks(): Int = { values(7) }

  def sacrificeHits(): Int = { values(8) }

  def sacrificeFlies(): Int = { values(9) }

  def hitByPitch(): Int = { values(10) }

  def runs(): Int = { values(11) }

  def runsBattedIn(): Int = { values(12) }

  def stolenBases(): Int = { values(13) }
  
  def caughtStealing(): Int = { values(14) }
  
  def add(g: Int = 0, ab: Int = 0, 
		  h: Int = 0, doubles: Int = 0, triples: Int = 0, hr: Int = 0,
		  so: Int = 0, bb: Int = 0, sh: Int = 0, sf: Int = 0, hbp: Int = 0, 
		  r: Int = 0, rbi: Int = 0, 
		  sb: Int = 0, cs: Int = 0): BattingStats = {
    val sum = Array[Int](15)
    values(0) = games + g
    values(1) = atBats + ab
    values(2) = hits + h
    values(3) = this.doubles + doubles
    values(4) = this.triples + triples
    values(5) = homeruns + hr
    values(6) = strikeouts + so
    values(7) = walks + bb
    values(8) = sacrificeHits + sh
    values(9) = sacrificeFlies + sf
    values(10) = hitByPitch + hbp
    values(11) = runs + r
    values(12) = runsBattedIn + rbi
    values(13) = stolenBases + sb
    values(14) = caughtStealing + cs
    new ArrayBattingStats(values)
  }

}

object ArrayBattingStats {

  def empty = new ArrayBattingStats(new Array[Int](15))

  // TODO: Identical code in the MapBattingStats companion object.
  // Can we eliminate this duplication?
  def apply(g: Int = 0, ab: Int = 0, 
      		h: Int = 0, doubles: Int = 0, triples: Int = 0, hr: Int = 0,
		    so: Int = 0, bb: Int = 0, sh: Int = 0, sf: Int = 0, hbp: Int = 0, 
		  r: Int = 0, rbi: Int = 0,
		  sb: Int = 0, cs: Int = 0): BattingStats = {
    val stats = empty
    stats.add(g, ab, h, doubles, triples, hr, so, bb, sh, sf, hbp, r, rbi, sb, cs)
  }
  
}
