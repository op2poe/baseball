package retrosheet.pbp

import scala.util.matching.Regex

/**
 * Counts all plays that ended up with a fielding out, or a would-be fielding out
 * that was voided due to an error, and calculates the distribution of the fielding
 * positions that made the initial play.
 */
object FieldingOutsDistribution extends App {

  class Processor extends EventProcessor {

    val distrib = new FieldingPositionDistribution()
    val error = new Regex("^E(\\d).*")
    val fc = new Regex("^FC(\\d).*");
    val out = new Regex("^(\\d).*")

    def processEvent(event: String) {
      val p = checkEvent(event)
      p match {
        case Some(i) => distrib += i
        case None => // not an out
      }
    }
    
    private def checkEvent(event: String): Option[Int] = {
      event match {
        case out(pos) => Some(pos.toInt)
        case fc(pos) => Some(pos.toInt)
        case error(pos) => Some(pos.toInt)
        case _ => None
      }
    }
  }

  val fileNames = List("1921BRO.EVN", "1921BSN.EVN",  "1921CHN.EVN", "1921CIN.EVN", "1921NY1.EVN",
      "1921PHI.EVN", "1921PIT.EVN", "1921SLN.EVN")
  val root = "C:\\z\\coding\\bb\\retrosheet\\play-by-play\\1921\\"
  val filePaths = fileNames.map(root + _)
  val processor = new Processor()
  EventFileReader.readAllEvents(filePaths, processor)
  processor.distrib.printResult()
  
}
