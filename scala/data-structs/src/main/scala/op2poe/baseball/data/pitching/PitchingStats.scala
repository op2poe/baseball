package op2poe.baseball.data.pitching

trait PitchingStats {

  def wins: Int
  
  def losses: Int
  
  def games: Int
  
  def gamesStarted: Int
  
  def completeGames: Int
  
  def shutouts: Int
  
  def saves: Int
  
  def battersFaced: Int
  
  def outs: Int
  
  def runs: Int

  def earnedRuns: Int
  
  def hits: Int
  
  def homeRuns: Int
  
  def walks: Int
  
  def strikeouts: Int
  
  def wildPitches: Int
  
  def hitByPitch: Int
  
  def sacrificeHits: Int
  
  def sacrificeFlies: Int
  
  def era: Double = per9Innings(earnedRuns)

  def per9Innings(v: Int) = 9 * (v / innings)
  
  private def innings: Double = outs / 3.0
  
  def strikeoutsPer9 = per9Innings(strikeouts)
  
  def walksPer9 = per9Innings(walks)
  
  def whip = (hits + walks) / innings
  
  def add(w: Int = 0, lo: Int = 0, 
          g: Int = 0, gs: Int = 0, cg: Int = 0,
          sho: Int = 0, sv: Int = 0, bf: Int = 0, outs: Int = 0,
          r: Int = 0, er: Int = 0, h: Int = 0, hr: Int = 0,
          bb: Int = 0, so: Int = 0, wp: Int = 0, hbp: Int = 0,
          sh: Int = 0, sf: Int = 0): PitchingStats
          
   def + (other: PitchingStats): PitchingStats =
     add(w = other.wins,
         lo = other.losses,
         g = other.games,
         gs = other.gamesStarted,
         cg = other.completeGames,
         sho = other.shutouts,
         sv = other.saves,
         bf = other.battersFaced,
         outs = other.outs,
         r = other.runs,
         er = other.earnedRuns,
         h = other.hits,
         hr = other.homeRuns,
         bb = other.walks,
         so = other.strikeouts,
         wp = other.wildPitches,
         hbp = other.hitByPitch,
         sh = other.sacrificeHits,
         sf = other.sacrificeFlies)
  
}
