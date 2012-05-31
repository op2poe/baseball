package retrosheet.pbp

import scala.util.matching.Regex

object PickedOffCounter extends App {

  class Processor extends EventProcessor {

    val po = new Regex("PO(?:CS)?(\\d).*")
    
    val counts = new Array[Int](3)
    
    def processEvent(event: String) {
      event match {
        case po(base) => counts(base.toInt - 1) += 1
        case _ => // Not a pickoff
      }
    }
    
    def printResult() {
      println("Picked of first: " + counts(0))
      println("Picked of second: " + counts(1))
      println("Picked of third: " + counts(2))
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
