package op2poe.baseball.data.game

import op2poe.baseball.data.pitching.PitchingStats

/**
 * The possible outcomes of a pitcher-batter matchup.
 */
sealed abstract class Outcome

case class Single extends Outcome
case class Double extends Outcome
case class Triple extends Outcome
case class Homerun extends Outcome  
case class Strikeout extends Outcome
case class Walk extends Outcome
case class Out extends Outcome
