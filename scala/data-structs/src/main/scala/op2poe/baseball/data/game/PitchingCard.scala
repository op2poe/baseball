package op2poe.baseball.data.game

import op2poe.baseball.data.game.Die.Side

final class PitchingCard(private val die: Die[Outcome]) extends PlayerCard {

  def outcome(): Outcome = die.roll()
  
}

object PitchingCard {
  
  def apply(battersFaced: Int,
			singles: Int,
			doubles: Int,
			triples: Int,
			homeruns: Int,
			strikeouts: Int,
			walks: Int) = {
    val outs = battersFaced - (singles + doubles + triples + homeruns + strikeouts + walks)
    new PitchingCard(Die(Side(Single, singles), 
    				     Side(Double, doubles),
    				     Side(Triple, triples),
    				     Side(Homerun, homeruns),
    				     Side(Strikeout,  strikeouts),
    				     Side(Walk, walks),
    				     Side(Out, outs)))
  }
  
}