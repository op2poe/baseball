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

  private implicit def toList(a: Advancement) = List(a)
  
  private val FieldingOut = new Regex("\\d\\d*")
  
  private val Single = new Regex("S(?:\\d\\d*)?")

  private val Double = new Regex("D(?:\\d\\d*)?")

  private val Triple = new Regex("T(?:\\d\\d*)?")

  private val Homerun = new Regex("H(?:R)?(?:\\d\\d*)?")
  
  private val Strikeout = new Regex("K(?:\\+.*)")
  
  private val Walk = new Regex("(?:(?:I)|(?:IW)|(?:W)).*")
  
  private val FieldersChoice = new Regex("FC\\d.*")
  
  private val Error = new Regex("E\\d.*")
  
  private val GroundedIntoDP = new Regex("\\d+\\((\\d)\\)\\d+")
  
  private val LinedIntoDP = new Regex("\\d\\(B\\)\\d+\\((\\d)\\)")
  
  private val ErrorOnFoulFly = new Regex("FLE\\d")
  
  private val GroundedIntoTP = new Regex("\\d+\\((\\d)\\)\\d+\\((\\d)\\)\\d+")
  
  private val LinedIntoTP = new Regex("\\d\\(B\\)\\d+\\((\\d)\\)\\d+\\((\\d)\\)")

  private val Interference = new Regex("C/E\\d")
  
  def parse(s: String): List[Advancement] = {
    // XXX: Split into multiple match expression to workaround bug
    // in Scala (supposedly being fixed in 2.10)
    s match {
      case FieldingOut() => Advancement.ofBatter(-1)
      case Single() => batterToFirst()
      case Double() => Advancement.ofBatter(2)
      case Triple() => Advancement.ofBatter(3)
      case Homerun() => Advancement.ofBatter(4)
      case _ => tryMore(s)
    }
  }
  
  private def tryMore(s: String): List[Advancement] = {
    s match {
      // According to play-by-play description file, advancement of
      // the batter to first on a wild pitch strike three is always 
      // given explicitly in the advancement section.
      // ==> Here we can always assume the batter is out. 
      case Strikeout() => Advancement.ofBatter(0)
      case Walk() => batterToFirst()
      case FieldersChoice() => batterToFirst()
      case Error() => batterToFirst()
      case "NP" => Nil
      case GroundedIntoDP(baseRunner) =>
        List(outAtNextBase(baseRunner), Advancement.ofBatter(-1))
      case LinedIntoDP(baseRunner) =>
        List(outAtNextBase(baseRunner), Advancement.ofBatter(0))
      case _ => tryMore2(s)
    }
  }
  
  private def tryMore2(s: String): List[Advancement] = {
    s match {
      case "DGR" => List(Advancement.ofBatter(2))
      case "HP" => batterToFirst()
      case ErrorOnFoulFly() => Nil
      case GroundedIntoTP(firstOut, secondOut) =>
        List(outAtNextBase(firstOut), outAtNextBase(secondOut), Advancement.ofBatter(-1))
      case LinedIntoTP(firstOut, secondOut) =>
        List(outAtNextBase(firstOut), outAtNextBase(secondOut), Advancement.ofBatter(0))
      case Interference() => batterToFirst()
      case _ => 
        Console.err.println("Unrecognized basic play: " + s)
        Nil
    }
  }

  private def batterToFirst() = List(Advancement.ofBatter(1))
  
  private def outAtNextBase(base: String) = {
        val fromBase = base.toInt
        val toBase = -(fromBase + 1)
        Advancement(fromBase, toBase)
  }

}