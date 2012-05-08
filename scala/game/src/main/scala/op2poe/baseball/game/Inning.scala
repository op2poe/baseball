package op2poe.baseball.game

import op2poe.baseball.data.game.PitchingCard
import op2poe.baseball.data.game.BattingCard
import op2poe.baseball.data.game.PlayerCard
import op2poe.baseball.data.game.Outcome
import op2poe.baseball.data.game.Strikeout
import op2poe.baseball.data.game.Out
import op2poe.util.Die
import op2poe.util.Die.Side
import op2poe.baseball.data.pitching.PitchingStats
import op2poe.baseball.data.batting.BattingStats
import op2poe.baseball.data.game.Single
import op2poe.baseball.data.game.Double
import op2poe.baseball.data.game.Triple
import op2poe.baseball.data.game.Homerun
import op2poe.baseball.data.game.Walk

class Inning(private val pitching: Opponent, private val batting: Opponent) {

  private val cardPicker = Die(Side("P", 1), Side("B", 1))
  
  private var numberOfOuts = 0
  private var runnersOn: RunnersOn = NoBaseRunners
  private var pitchingStats = PitchingStats.empty
  private var battingStats = BattingStats.empty

  def runsScored = battingStats.runs
  def numberOfHits = battingStats.hits

  def play() {
    while (numberOfOuts < 3) {
      val batter = batting.batters.next
      val card: PlayerCard = if (cardPicker.roll() == "P") pitching.pitcher else batter
      val outcome = card.outcome()
      var runsScoredOnPlay = 0
      val x = runnersOn.advance(outcome, numberOfOuts)
      runnersOn = x._1
      updateStats(outcome, x._2)
      if (outcome.isOut) numberOfOuts += 1
    }
    pitching.updatePitchingInning(pitchingStats)
    batting.updateBattingInning(runsScored, battingStats)
  }

  private def updateStats(outcome: Outcome, runs: Int) {
    outcome match {
      case Single => 
        pitchingStats = pitchingStats.add(bf = 1, h = 1, r = runs)
        battingStats = battingStats.add(ab = 1, h = 1, r = runs)
      case Double =>
        pitchingStats = pitchingStats.add(bf = 1, h = 1, r = runs)
        battingStats = battingStats.add(ab = 1, h = 1, doubles = 1, r = runs)
      case Triple =>
        pitchingStats = pitchingStats.add(bf = 1, h = 1, r = runs)
        battingStats = battingStats.add(ab = 1, h = 1, triples = 1, r = runs)
      case Homerun =>
        pitchingStats = pitchingStats.add(bf = 1, h = 1, hr = 1, r = runs)
        battingStats = battingStats.add(ab = 1, h = 1, hr = 1, r = runs)
      case Strikeout =>
        pitchingStats = pitchingStats.add(bf = 1, so = 1, outs = 1, r = runs)
        battingStats = battingStats.add(ab = 1, so = 1, r = runs)
      case Walk =>
        pitchingStats = pitchingStats.add(bf = 1, bb = 1, r = runs)
        battingStats = battingStats.add(bb = 1, r = runs)
      case Out =>
        pitchingStats = pitchingStats.add(bf = 1, outs = 1, r = runs)
        battingStats = battingStats.add(ab = 1, r = runs)
    }
  }
  
}

object Inning {
  
  def play(pitching: Opponent, batting: Opponent) {
    val inning = new Inning(pitching, batting)
    inning.play()
  }
  
}