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
    
    private def checkEvent(line: String): Option[Int] = {
      line match {
        case out(pos) => toInt(pos)
        case fc(pos) => toInt(pos)
        case error(pos) => toInt(pos)
        case _ => None
      }
    }
    
    private def toInt(pos: String) = Some(pos.toInt)
    
    def printResult() {
      distrib.printResult()
    }
  }

  val filePath = "C:\\z\\coding\\bb\\retrosheet\\play-by-play\\1921\\1921NYA.EVA"
  val processor = new Processor()
  EventFileReader.readAllEvents(filePath, processor)
  processor.printResult()
  
}
