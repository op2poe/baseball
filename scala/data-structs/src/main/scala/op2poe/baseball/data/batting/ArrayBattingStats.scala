package op2poe.baseball.data.batting

final class ArrayBattingStats private(values: Array[Int]) extends BattingStats {

  require(values.length == 12)
  
  def atBats(): Int = { values(0) }

  def singles(): Int = { values(1) }

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

  def + (other: BattingStats): BattingStats = {
    val sum = Array[Int](12)
    values(0) = atBats + other.atBats
    values(1) = singles + other.singles
    values(2) = doubles + other.doubles
    values(3) = triples + other.triples
    values(4) = homeruns + other.homeruns
    values(5) = strikeouts + other.strikeouts
    values(6) = walks + other.walks
    values(7) = sacrificeHits + other.sacrificeHits
    values(8) = sacrificeFlies + other.sacrificeFlies
    values(9) = hitByPitch + other.hitByPitch
    values(10) = runs + other.runs
    values(11) = runsBattedIn + other.runsBattedIn
    new ArrayBattingStats(values)
  }
  
}

object ArrayBattingStats {
  
  def empty = new ArrayBattingStats(new Array[Int](12))
  
}