package op2poe.baseball.game

import scala.collection.mutable.ListBuffer
import op2poe.baseball.data.LineScore
import op2poe.baseball.data.pitching.PitchingStats
import op2poe.baseball.data.batting.BattingStats
import op2poe.baseball.data.Runs

abstract class Opponent(val name: String, val lineup: Lineup) {
  
  def endOfGame(result: Runs, batting: BattingStats, pitching: PitchingStats)
  
  private var innings = LineScore.empty
  private var pitchingStats = PitchingStats(g = 1)
  private var battingStats = BattingStats(g = 1)

  final def updateBattingInning(score: Int, stats: BattingStats) {
    innings += score
    battingStats += stats
  }
  
  final def updatePitchingInning(stats: PitchingStats) {
    pitchingStats += stats
  }
  
  final def endOfGame(result: Runs) {
    endOfGame(result, battingStats, pitchingStats)
  }
  
  final def lineScore = innings

  final def runs = innings.sum
  
  final def hits = battingStats.hits
  
  final def pitcher = lineup.pitcher
  
  final def batters = lineup.batters
  
}
