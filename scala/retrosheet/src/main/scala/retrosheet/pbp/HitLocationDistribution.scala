package retrosheet.pbp

import scala.util.matching.Regex

/**
 * Processes all plays that resulted in a base-hit other than a homerun, and
 * counts the number of hits of each type that was fielded by each fielding position.
 */
object HitLocationDistribution extends App {

  class Processor extends EventProcessor {

    val distribs = Map("S" -> new FieldingPositionDistribution(),
      "D" -> new FieldingPositionDistribution(),
      "T" -> new FieldingPositionDistribution())
    val hit = new Regex("^(S|D|T)(\\d).*")
    
    def processEvent(event: String) {
      event match {
        case hit(kind, pos) => distribs(kind) += pos.toInt
        case _ => // not a hit
      }
    }
    
    def printResult() {
      for (hit <- List("Singles", "Doubles", "Triples")) {
        val distrib = distribs(hit.substring(0, 1))
        println(hit)
        distrib.printResult()
        println()
      }
    }
  }

  val fileNames = List("1921BRO.EVN", "1921BSN.EVN",  "1921CHN.EVN", "1921CIN.EVN", "1921NY1.EVN",
      "1921PHI.EVN", "1921PIT.EVN", "1921SLN.EVN")
  val root = "C:\\z\\coding\\bb\\retrosheet\\play-by-play\\1921\\"
  val filePaths = fileNames.map(root + _)
  val processor = new Processor()
  EventFileReader.readAllEvents(filePaths, processor)
  processor.printResult()

}