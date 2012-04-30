package op2poe.baseball.data

final class Runs(val scored: Int, val against: Int) {

  require(scored >= 0)
  require(against >= 0)
  
  def + (that: Runs) = new Runs(scored + that.scored, against + that.against)
  
  def reverse() = new Runs(against, scored)

  def isWin = scored > against
  
  def isLoss = scored < against
  
  def isTie = scored == against
  
  override def toString = scored + "-" + against
  
  override def equals(other: Any): Boolean =
    other match {
      case that: Runs =>
        scored == that.scored && against == that.against
      case _ => false
    }
  
  override def hashCode: Int =
    41 * (
        41 + scored
    ) + against
}
