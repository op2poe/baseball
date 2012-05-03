package op2poe.baseball.data.batting

final class ArrayBattingStats extends BattingStats {

  private val values = new Array[Int](12)
  
  
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
    val sum = new ArrayBattingStats()
    sum.values(0) = atBats + other.atBats
    sum.values(1) = singles + other.singles
    sum.values(2) = doubles + other.doubles
    sum.values(3) = triples + other.triples
    sum.values(4) = homeruns + other.homeruns
    sum.values(5) = strikeouts + other.strikeouts
    sum.values(6) = walks + other.walks
    sum.values(7) = sacrificeHits + other.sacrificeHits
    sum.values(8) = sacrificeFlies + other.sacrificeFlies
    sum.values(9) = hitByPitch + other.hitByPitch
    sum.values(10) = runs + other.runs
    sum.values(11) = runsBattedIn + other.runsBattedIn
    sum
  }
  
}
