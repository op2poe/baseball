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
  
  private val FieldingOut = new Regex("\\d\\d*(?:/.*)?")
  
  private val Single = new Regex("S(?:\\d\\d*)?")

  private val Double = new Regex("D(?:\\d\\d*)?")

  private val Triple = new Regex("T(?:\\d\\d*)?")

  private val Homerun = new Regex("H(?:R)?(?:\\d\\d*)?")
  
  private val Strikeout = new Regex("K(?:\\+.*)?")
  
  private val Walk = new Regex("(?:(?:I)|(?:IW)|(?:W)).*")
  
  private val FieldersChoice = new Regex("FC\\d.*")
  
  private val Error = new Regex("E\\d.*")
  
  private val ForceOut = new Regex("\\d+\\((\\d)\\)/FO.*")
  
  private val GroundedIntoDP = new Regex("\\d+\\((\\d)\\)\\d*")
  
  private val LinedIntoDP = new Regex("\\d\\(B\\)\\d+\\((\\d)\\)")
  
  private val StolenBase = new Regex("((?:SB(?:\\d|H);?)+)")
  
  private val CaughtStealing = new Regex("CS(\\d|H)(?:\\(\\d\\d+\\))?.*")
  
  private val PickedOff = new Regex("PO(\\d)(?:\\(\\d\\d\\))?")
  
  private val PickedOffCaughtStealing = new Regex("POCS(\\d)(?:\\(\\d\\d+\\))?")
  
  private val ErrorOnPickOffAttempt = new Regex("PO\\d\\(E\\d\\)")
  
  private val ErrorOnFoulFly = new Regex("FLE\\d")
  
  private val GroundedIntoTP = new Regex("\\d+\\((\\d)\\)\\d+\\((\\d)\\)\\d+")
  
  private val LinedIntoTP = new Regex("\\d\\(B\\)\\d+\\((\\d)\\)\\d+\\((\\d)\\)")
  
  def parse(s: String): List[Advancement] = {
    // We need the "/FO" part to recognize a force out. For all other plays,
    // however, we are not interested in the modifier part ("/.*"). Handle
    // those two cases separately.
    s match {
      case ForceOut(baseRunner) =>
        List(outAtNextBase(baseRunner), Advancement.ofBatter(1))
      case _ => parseNonForceOut(s)
    }
  }
  
  private def parseNonForceOut(s: String): List[Advancement] = {
    val toParse = s.split("/")(0)
    // Split up in to several match expressions (tryMore, tryMore1, etc),
    // to work around Scala bug.
    toParse match {
      case FieldingOut() => Advancement.ofBatter(-1)
      case Single() => batterToFirst()
      case Double() => Advancement.ofBatter(2)
      case Triple() => Advancement.ofBatter(3)
      case Homerun() => Advancement.ofBatter(4)
      case _ => tryMore(toParse)
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
  
  private def parseStolenBaseEvent(sb: String) = {
    val parts = sb.split(";")
    parts.map(sbToAdvancement(_)).toList
  }
  
  private def sbToAdvancement(sb: String) = {
    sb match {
      case "SB2" => Advancement(1, 2)
      case "SB3" => Advancement(2, 3)
      case "SBH" => Advancement(3, 4)
    }
  }

  private def csToAdvancement(base: String) = {
    base match {
      case "2" => Advancement(1, -2)
      case "3" => Advancement(2, -3)
      case "H" => Advancement(3, -4)
    }
  }
  
  private def poToAdvancement(base: String) = {
    val b = base.toInt
    Advancement(b, -b)
  }
  
  private def tryMore2(s: String): List[Advancement] = {
    s match {
      case StolenBase(sb) => parseStolenBaseEvent(sb)
      case CaughtStealing(base) => csToAdvancement(base)
      case PickedOff(base) => poToAdvancement(base)
      case PickedOffCaughtStealing(base) => poToAdvancement(base) // Same as PO as far as we are concerned
      case ErrorOnPickOffAttempt() => Nil
      case "OA" => Nil
      case "DGR" => List(Advancement.ofBatter(2))
      case "HP" => batterToFirst()
      case _ => tryMore3(s)
    }
  }

  private def tryMore3(s: String): List[Advancement] = {
    s match {
      case ErrorOnFoulFly() => Nil
      case GroundedIntoTP(firstOut, secondOut) =>
        List(outAtNextBase(firstOut), outAtNextBase(secondOut), Advancement.ofBatter(-1))
      case LinedIntoTP(firstOut, secondOut) =>
        List(outAtNextBase(firstOut), outAtNextBase(secondOut), Advancement.ofBatter(0))
      case "C" => batterToFirst()
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