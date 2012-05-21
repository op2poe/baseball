package retrosheet.pbp

import scala.io.Source

object EventFileReader extends App {

  def readAllEvents(filePath: String, processor: EventProcessor) {
    readAllEvents(List(filePath), processor)
  }
  
  def readAllEvents(filePaths: List[String], processor: EventProcessor) {
    for (filePath <- filePaths) {
      val source = Source.fromFile(filePath)
      for (line <- source.getLines if line.startsWith("play")) {
        processor.processPlay(line)
      }
    }
  }

}