package op2poe.baseball.data

final class WLT(val wins: Int, val losses: Int, val ties: Int) extends Ordered[WLT] {

  require(wins >= 0)
  require(losses >= 0)
  require(ties >= 0)
  
  def this() = this(0, 0, 0)
  
  def compare(that: WLT) = winPct compare that.winPct
  
  def games = wins + losses
  
  def winPct = if (games == 0) 0.0 else (1.0 * wins) / games

  def + (that: WLT) = new WLT(wins + that.wins, losses + that.losses, ties + that.ties)
  
  def addResult(r: Runs) = 
    if (r.scored > r.against) new WLT(wins + 1, losses, ties)
    else if (r.scored < r.against) new WLT(wins, losses + 1, ties)
    else new WLT(wins, losses, ties + 1)
  
  def winLossDiff = wins - losses
  
  override def toString = toStringWithoutTies + "-" + ties

  def toStringWithoutTies = wins + "-" + losses
  
  override def equals(other: Any): Boolean =
    other match {
      case that: WLT =>
        wins == that.wins && losses == that.losses && ties == that.ties
      case _ => false
    }

  override def hashCode: Int =
    41 * (
        41 * (
            41 + wins
        ) + losses
    ) + ties
  
}
