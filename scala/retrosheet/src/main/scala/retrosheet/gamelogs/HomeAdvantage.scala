package retrosheet.gamelogs

import retrosheet.CsvParser

object HomeAdvantage extends App {

  val file = "C:\\z\\coding\\baseball-2\\retrosheet\\gamelogs\\gl1984.txt"
  val gameLog = new GameLog
  
  CsvParser.parseFile(file, gameLog.processRecord)
  
  gameLog.printReport()

}