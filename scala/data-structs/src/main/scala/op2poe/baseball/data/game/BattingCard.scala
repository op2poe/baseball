package op2poe.baseball.data.game

import op2poe.util.Die
import op2poe.util.Die.Side

final class BattingCard(private val die: Die[Outcome], val weak: Boolean) extends PlayerCard {

  def outcome(): Outcome = die.roll()
  
}

object BattingCard {
  
  def apply(values: (Outcome, Int)*): BattingCard = {
    val sides = values.map(v => Die.Side[Outcome](v._1, v._2))
    val die = Die.fromSeq(sides)
    val weak = false // TODO: Implement me.
    new BattingCard(die, weak)
  }
  
}
