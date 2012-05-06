package op2poe.baseball.data

import op2poe.baseball.data.batting.BattingStats
import op2poe.baseball.data.pitching.PitchingStats

class TeamRecord (val wlt: WLT, 
				  val runs: Runs, 
				  val battingStats: BattingStats = BattingStats.empty, 
				  val pitchingStats: PitchingStats = PitchingStats.empty) extends Ordered[TeamRecord] {
  
  def this() = this(new WLT(), new Runs(0, 0))
  
  def addResult(result: Runs, bs: BattingStats = BattingStats.empty,
		  	    ps: PitchingStats = PitchingStats.empty) = {
    new TeamRecord(wlt.addResult(result), runs + result, 
        battingStats + bs, pitchingStats + ps)
  }
  
  def + (other: TeamRecord): TeamRecord = new TeamRecord(
      wlt + other.wlt, 
      runs + other.runs, 
      battingStats + other.battingStats, 
      pitchingStats + other.pitchingStats)
  
  def wins = wlt.wins
  
  def losses = wlt.losses
  
  def winPct = wlt.winPct
  
  def winLossDiff = wlt.winLossDiff
  
  def runsScored = runs.scored
  
  def runsAgainst = runs.against
  
  def pythagoreanWL = runs.pythagoreanWL(wlt.wins + wlt.losses)
  
  def luck = {
    wlt.wins - pythagoreanWL.wins
  }

  def compare(other: TeamRecord): Int = wlt.compare(other.wlt)
  
}
