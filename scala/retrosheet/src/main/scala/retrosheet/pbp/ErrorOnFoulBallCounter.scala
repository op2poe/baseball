package retrosheet.pbp

/**
 * Counts the number of errors on foul balls, and the fielding position
 * distribution of those errors.
 */
object ErrorOnFoulBallCounter extends App {

  class Processor extends EventProcessor {

    val fle = "FLE(\\d).*".r

    val distrib = new FieldingPositionDistribution()

    def processEvent(event: String) {
      event match {
        case fle(pos) => distrib += pos.toInt
        case _ => // not an error on foul fly.
      }
    }

  }

  val fileNames = List("1921BRO.EVN", "1921BSN.EVN", "1921CHN.EVN", "1921CIN.EVN", "1921NY1.EVN",
    "1921PHI.EVN", "1921PIT.EVN", "1921SLN.EVN")
  val root = "C:\\z\\coding\\bb\\retrosheet\\play-by-play\\1921\\"
  val filePaths = fileNames.map(root + _)
  val processor = new Processor()
  EventFileReader.readAllEvents(filePaths, processor)
  processor.distrib.printResult()

}
