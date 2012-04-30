package op2poe.baseball.data

final class BasicBattingStats(val atBats: Int, val hits: Int) extends BattingStats {

  require(atBats >= 0)
  require(hits >= 0)

  def totalBases = hits
  
  def + (that: BasicBattingStats) = 
    new BasicBattingStats(atBats + that.atBats, hits + that.hits)
  
}

object BasicBattingStats {
  
  def none = new BasicBattingStats(0, 0)
  
}