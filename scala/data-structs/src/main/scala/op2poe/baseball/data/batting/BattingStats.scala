package op2poe.baseball.data.batting

trait BattingStats {

  def atBats: Int
  
  def singles: Int
  
  def doubles: Int
  
  def triples: Int
  
  def homeruns: Int
  
  def strikeouts: Int
  
  def walks: Int
  
  def sacrificeHits: Int
  
  def sacrificeFlies: Int
  
  def hitByPitch: Int
  
  def runs: Int
  
  def runsBattedIn: Int
  
  final def hits = singles + doubles + triples + homeruns
  
  final def totalBases = singles + 2 * doubles + 3 * triples + 4 * homeruns
  
  // We do not include Times Reached on Defensive (e.g. Catcher's) Interference. 
  final def plateAppearances = atBats + walks + sacrificeHits + sacrificeFlies + hitByPitch
  
  final def battingAverage = {
    val ab = atBats
    if (ab > 0) (1.0 * hits) / ab
    else 0.0
  }
  
  final def sluggingAverage = {
    val ab = atBats
    if (ab > 0) (1.0 * totalBases) / ab
    else 0.0
  }
  
  final def onBaseAverage = {
    val denominator = atBats + walks + hitByPitch + sacrificeFlies
    if (denominator > 0) (1.0 * (hits + walks + hitByPitch)) / denominator
    else 0.0
  }
  
  final def ops = onBaseAverage + sluggingAverage
  
}
