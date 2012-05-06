package op2poe.baseball.data.pitching

class ArrayPitchingStats private (values: Array[Int]) extends PitchingStats {

  require(values.length == 19)
  require(values.forall(_ >= 0))

  def games(): Int = values(0)
  
  def wins(): Int = values(1)

  def losses(): Int = values(2)

  def gamesStarted(): Int = values(3)

  def completeGames(): Int = values(4)

  def shutouts(): Int = values(5)

  def saves(): Int = values(6)

  def battersFaced(): Int = values(7)

  def outs(): Int = values(8)

  def runs(): Int = values(9)

  def earnedRuns(): Int = values(10)

  def hits(): Int = values(11)

  def homeRuns(): Int = values(12)

  def walks(): Int = values(13)

  def strikeouts(): Int = values(14)

  def wildPitches(): Int = values(15)

  def hitByPitch(): Int = values(16)

  def sacrificeHits(): Int = values(17)

  def sacrificeFlies(): Int = values(18)

  def add(w: Int = 0, lo: Int = 0, g: Int = 0, gs: Int = 0, cg: Int = 0, 
          sho: Int = 0, sv: Int = 0, bf: Int = 0, outs: Int = 0, 
          r: Int = 0, er: Int = 0, h: Int = 0, hr: Int = 0, 
          bb: Int = 0, so: Int = 0, wp: Int = 0, hbp: Int = 0, 
          sh: Int = 0, sf: Int = 0): ArrayPitchingStats = {
    val sum = new Array[Int](19)
    sum(0) = games + g
    sum(1) = wins + w
    sum(2) = losses + lo
    sum(3) = gamesStarted + gs
    sum(4) = completeGames + cg
    sum(5) = shutouts + sho
    sum(6) = saves + sv
    sum(7) = battersFaced + bf
    sum(8) = this.outs + outs
    sum(9) = runs + r
    sum(10) = earnedRuns + er
    sum(11) = hits + h
    sum(12) = homeRuns + hr
    sum(13) = walks + bb
    sum(14) = strikeouts + so
    sum(15) = wildPitches + wp
    sum(16) = hitByPitch + hbp
    sum(17) = sacrificeHits + sh
    sum(18) = sacrificeFlies + sf
    new ArrayPitchingStats(sum)
  }

}

object ArrayPitchingStats {
  
  def empty = new ArrayPitchingStats(new Array[Int](19))

  def apply(w: Int = 0, lo: Int = 0, g: Int = 0, gs: Int = 0, cg: Int = 0, 
          sho: Int = 0, sv: Int = 0, bf: Int = 0, outs: Int = 0, 
          r: Int = 0, er: Int = 0, h: Int = 0, hr: Int = 0, 
          bb: Int = 0, so: Int = 0, wp: Int = 0, hbp: Int = 0, 
          sh: Int = 0, sf: Int = 0): ArrayPitchingStats = {
    val stats = empty
    stats.add(w, lo, g, gs, cg, sho, sv, bf, outs, r, er, 
        h, hr, bb, so, wp, hbp, sh, sf)
  }
  
}