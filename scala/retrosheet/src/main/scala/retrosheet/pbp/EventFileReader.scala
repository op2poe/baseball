package retrosheet.pbp

import scala.io.Source

object EventFileReader extends App {

  def readAllEvents(filePath: String, processor: EventProcessor) {
    val source = Source.fromFile(filePath)
    for (line <- source.getLines if line.startsWith("play")) {
      processor.processPlay(line)
    }
  }

}