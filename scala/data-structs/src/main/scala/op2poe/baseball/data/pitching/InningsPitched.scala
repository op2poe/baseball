package op2poe.baseball.data.pitching

final class InningsPitched(private val outs: Int) extends Ordered[InningsPitched] {

  require(outs >= 0)
  
  override def toString = whole + "." + parts
  
  private def whole: Int = outs / 3
  
  private def parts: Int = outs % 3
    
  def + (other: InningsPitched) =
    new InningsPitched(outs + other.outs)

  def toDouble = outs / 3.0
  
  def per9(stat: Int) = 9 * (stat / toDouble)
  
  def compare(other: InningsPitched) = outs.compare(other.outs)
  
  override def equals(other: Any): Boolean =
    other match {
    case other: InningsPitched => outs == other.outs
    case _ => false
  }
  
  override def hashCode = outs
  
}
