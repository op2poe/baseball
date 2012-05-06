package op2poe.baseball.data.game

import op2poe.baseball.data.game.Die.Side

final class BattingCard(private val die: Die[Outcome], val weak: Boolean) extends PlayerCard {

  def outcome(): Outcome = die.roll()
  
}

object BattingCard {
  
  def apply(plateAppearances: Int,
			singles: Int,
			doubles: Int,
			triples: Int,
			homeruns: Int,
			strikeouts: Int,
			walks: Int) {
    val outs = plateAppearances - (singles + doubles + triples + homeruns + strikeouts + walks)
    val die = Die[Outcome](Side(Single, singles), 
    				       Side(Double, doubles),
    				       Side(Triple, triples),
    				       Side(Homerun, homeruns),
    				       Side(Strikeout,  strikeouts),
    				       Side(Walk, walks))
    val weak = false // TODO: Implement me.
    new BattingCard(die, weak)
  }
  
}
