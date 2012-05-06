package op2poe.baseball.data.game

import op2poe.util.Die
import op2poe.util.Die.Side

final class PitchingCard(private val die: Die[Outcome]) extends PlayerCard {

  def outcome(): Outcome = die.roll()
  
}

object PitchingCard {
  
  // TODO: Very similar code in the BattingCard factory method
  def apply(values: (Outcome, Int)*): PitchingCard = {
    val sides = values.map(v => Die.Side[Outcome](v._1, v._2))
    val die = Die.fromSeq(sides)
    new PitchingCard(die)
  }
  
}