package op2poe.baseball.game

import scala.collection.mutable.ListBuffer
import op2poe.baseball.data.LineScore

final class Opponent(val name: String, val lineup: Lineup) {
  
  private var innings = LineScore.empty
  
  def addInningScore(score: Int) {
    innings += score
  }
  
  def lineScore = innings

  def runs = innings.sum
  
  def pitcher = lineup.pitcher
  
  def batters = lineup.batters
  
}
