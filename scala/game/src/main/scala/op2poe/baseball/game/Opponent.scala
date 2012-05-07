package op2poe.baseball.game

import scala.collection.mutable.ListBuffer
import op2poe.baseball.data.LineScore

final class Opponent(val name: String, val lineup: Lineup) {
  
  private val innings = new ListBuffer[Int]
  
  def addInningScore(score: Int) {
    innings += score
  }
  
  def lineScore = new LineScore(innings.toArray)

  def runs = innings.sum
  
  def pitcher = lineup.pitcher
  
  def batters = lineup.batters
  
}
