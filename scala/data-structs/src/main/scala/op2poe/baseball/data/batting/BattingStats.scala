package op2poe.baseball.data.batting

trait BattingStats {

  def atBats: Int
  
  def hits: Int
  
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
  
  def stolenBases: Int
  
  def caughtStealing: Int
  
  final def singles = hits - (doubles + triples + homeruns)
  
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

  def add(ab: Int = 0, h: Int = 0, doubles:Int = 0, triples: Int = 0, hr: Int = 0,
      so: Int = 0, bb: Int = 0, sh: Int = 0, sf:Int = 0, hbp: Int = 0, r: Int = 0,
      rbi: Int = 0, sb: Int = 0, cs: Int = 0): BattingStats

  final def +(other: BattingStats): BattingStats = {
    add(ab = other.atBats, 
        h = other.hits, 
        doubles = other.doubles, 
        triples = other.triples, 
        hr = other.homeruns,
    	so = other.strikeouts, 
    	bb = other.walks, 
    	sh = other.sacrificeHits, 
    	sf = other.sacrificeFlies,
    	hbp = other.hitByPitch, 
    	r = other.runs, 
    	rbi = other.runsBattedIn,
    	sb = other.stolenBases,
    	cs = other.caughtStealing)
  }
  
  protected final def checkInvariants() {
    require(hits >= (doubles + triples + homeruns))
    require(atBats >= (hits + strikeouts))
  }
  
}
