package op2poe.baseball.game

import op2poe.baseball.data.game.PitchingCard
import op2poe.baseball.data.game.BattingCard
import op2poe.baseball.data.game.PlayerCard
import op2poe.baseball.data.game.Outcome
import op2poe.baseball.data.game.Strikeout
import op2poe.baseball.data.game.Out
import op2poe.util.Die
import op2poe.util.Die.Side

class Inning(var pitcher: PitchingCard, val battingOrder: Iterator[BattingCard]) {

  private val cardPicker = Die(Side("P", 1), Side("B", 1))
  
  private var numberOfOuts = 0
  
  private var numberOfRuns = 0
  
  def runsScored = numberOfRuns
  
  private var numberOfHits = 0
  
  private var runnersOn: RunnersOn = NoBaseRunners
  
  def play() {
    while (numberOfOuts < 3) {
      val batter = battingOrder.next
      val card: PlayerCard = if (cardPicker.roll() == "P") pitcher else batter
      val outcome = card.outcome()
      println(outcome)
      var runsScoredOnPlay = 0
      val x = runnersOn.advance(outcome, numberOfOuts)
      runnersOn = x._1
      numberOfRuns += x._2
      if (outcome.isHit) numberOfHits += 1
      if (outcome.isOut) numberOfOuts += 1
      println("end of inning")
    }
  }
  
}
