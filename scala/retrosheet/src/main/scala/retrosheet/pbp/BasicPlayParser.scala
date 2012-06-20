package retrosheet.pbp

import scala.util.matching.Regex

/**
 * Parses the first part ("basic play") of the event description.
 * <p>
 * In contrast with the {@code AdvancementParser}, a {@code BasicPlayParser}
 * returns a {@code List} of {@code Advancement}s. This is needed to handle
 * an event like "64(1)3/GDP/G6", a double-play where the event string does not
 * contain any explicit advancements. The BasicPlayParser will return two
 * Advancements for this event: {@code Advancement(1, -2)} (representing the
 * runner on first being put out at second) and {@code Advancement(0, -1}
 * (representing the batter being put out at first).
 */
object BasicPlayParser {

  private val Single = new Regex("S(?:\\d\\d*)?")

  private val Double = new Regex("D(?:\\d\\d*)?")

  private val Triple = new Regex("T(?:\\d\\d*)?")

  private val Homerun = new Regex("H(?:R)?(?:\\d\\d*)?")
  
  private val GroundIntoDP = new Regex("\\d\\d?\\((\\d)\\)\\d")

  def parse(s: String): List[Advancement] = {
    s match {
      case Single() => List(Advancement.ofBatter(1))
      case Double() => List(Advancement.ofBatter(2))
      case Triple() => List(Advancement.ofBatter(3))
      case Homerun() => List(Advancement.ofBatter(4))
      case GroundIntoDP(baseRunner) =>
        val fromBase = baseRunner.toInt
        val toBase = -(fromBase + 1)
        List(Advancement(fromBase, toBase),
             Advancement.ofBatter(-1))
      case "DGR" => List(Advancement.ofBatter(2))
      case _ => 
        Console.err.println("Unrecognized basic play: " + s)
        Nil
    }
  }

}