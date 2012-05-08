package op2poe.baseball.game

import scala.collection.mutable.ListBuffer
import op2poe.baseball.data.LineScore
import op2poe.baseball.data.pitching.PitchingStats
import op2poe.baseball.data.batting.BattingStats

final class Opponent(val name: String, val lineup: Lineup) {
  
  private var innings = LineScore.empty
  private var pitchingStats = PitchingStats.empty
  private var battingStats = BattingStats.empty

  def updateBattingInning(score: Int, stats: BattingStats) {
    innings += score
    battingStats += stats
  }
  
  def updatePitchingInning(stats: PitchingStats) {
    pitchingStats += stats
  }
  
  def lineScore = innings

  def runs = innings.sum
  
  def hits = battingStats.hits
  
  def pitcher = lineup.pitcher
  
  def batters = lineup.batters
  
}
