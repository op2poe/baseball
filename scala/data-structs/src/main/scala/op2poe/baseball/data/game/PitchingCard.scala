package op2poe.baseball.data.game

import op2poe.baseball.data.game.Die.Side

final class PitchingCard(private val die: Die[Outcome]) {

  def outcome(): Outcome = die.roll()
  
}

object PitchingCard {
  
  def apply(battersFaced: Int,
			singles: Int,
			doubles: Int,
			triples: Int,
			homeruns: Int,
			strikeouts: Int,
			walks: Int) {
    val outs = battersFaced - (singles + doubles + triples + homeruns + strikeouts + walks)
    new PitchingCard(Die(Side(new Single, singles), 
    				     Side(new Double, doubles),
    				     Side(new Triple, triples),
    				     Side(new Homerun, homeruns),
    				     Side(new Strikeout,  strikeouts),
    				     Side(new Walk, walks)))
  }
  
}