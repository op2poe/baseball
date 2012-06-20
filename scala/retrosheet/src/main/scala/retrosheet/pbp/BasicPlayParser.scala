package retrosheet.pbp

import scala.util.matching.Regex

/**
 * Parses the first part ("basic play") of the event description.
 */
object BasicPlayParser {

  private val Single = new Regex("S(?:\\d.*)?")
  
  private val Double = new Regex("D(?:\\d.*)?")
  
  private val Triple = new Regex("T(?:\\d.*)?")
  
  private val Homerun = new Regex("H(?:R)?(?:\\d.*)?")
  
  def parse(s: String): Advancement = {
    s match {
      case Single() => Advancement.ofBatter(1)
      case Double() => Advancement.ofBatter(2)
      case Triple() => Advancement.ofBatter(3)
      case Homerun() => Advancement.ofBatter(4)
      case _ => throw new Exception("Not handled: " + s)
    }
  }
  
}