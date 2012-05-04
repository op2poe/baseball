package lahman.batting

import op2poe.baseball.io.text.batting.BattingLineFormat
import op2poe.io.LineWriter

object BattingStatsFactoryDriver extends App {

  val lines = TeamFileReader.readFile("C:\\z\\coding\\bb\\lahman\\data\\Teams.csv")
  
  print1927YankeesBattingLine()
  
  def print1927YankeesBattingLine() {
    val line = lines((1927, "NYA"))
    val stats = BattingStatsFactory.fromTeamLine(line)
    val format = new BattingLineFormat
    val out = LineWriter.Console
    out.println(format.header)
    out.println(format.format(stats))
  }
  
}