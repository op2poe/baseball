package op2poe.baseball.data

final class Runs(val scored: Int, val against: Int) {

  require(scored >= 0)
  require(against >= 0)

  def +(that: Runs) = new Runs(scored + that.scored, against + that.against)

  def reverse() = new Runs(against, scored)

  def isWin = scored > against

  def isLoss = scored < against

  def isTie = scored == against

  /**
   * Returns a predicate that evaluates to true when passed a Runs instance 
   * with the same outcome (win, loss, or tie) as this instance.
   */
  def outcomePredicate(): (Runs => Boolean) =
    (r: Runs) => r.scored.compare(r.against) == this.scored.compare(this.against)

  /**
   * Returns "W" if this instance represents a win, "L" if it represents a loss,
   * and "T" if it represents a tie.
   */
  def outcomeCode =
    if (isWin) "W"
    else if (isLoss) "L"
    else "T"

  override def toString = scored + "-" + against

  override def equals(other: Any): Boolean =
    other match {
      case that: Runs =>
        scored == that.scored && against == that.against
      case _ => false
    }

  override def hashCode: Int =
    41 * (
      41 + scored) + against
}
