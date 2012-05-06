package op2poe.baseball.data.game

import op2poe.baseball.data.pitching.PitchingStats

/**
 * The possible outcomes of a pitcher-batter matchup.
 */
sealed abstract class Outcome {
  
  def isOut: Boolean
  
  def isHit: Boolean
  
}

case object Single extends Outcome {
  def isOut = false
  def isHit = true
}


case object Double extends Outcome {
  def isOut = false
  def isHit = true
}

case object Triple extends Outcome {
  def isOut = false
  def isHit = true
}

case object Homerun extends Outcome {
  def isOut = false
  def isHit = true
}

case object Strikeout extends Outcome {
  def isOut = true
  def isHit = false
}

case object Walk extends Outcome {
  def isOut = false
  def isHit = false
}

case object Out extends Outcome {
  def isOut = true
  def isHit = false
}
