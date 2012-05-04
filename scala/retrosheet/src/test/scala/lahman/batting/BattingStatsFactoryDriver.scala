package lahman.batting

import op2poe.baseball.io.text.batting.BattingLineFormat
import op2poe.io.LineWriter
import op2poe.baseball.data.batting.ArrayBattingStats
import op2poe.baseball.data.batting.BattingStats

object BattingStatsFactoryDriver extends App {

  val root = "C:\\z\\coding\\bb\\lahman\\data\\"
  
  val teamLines = TeamFileReader.readFile(root + "Teams.csv")
  
  val playerLines = PlayerFileReader.readFile(root + "Batting.csv")
  
  print1927YankeesBattingLine()
  print1921BabeRuth()
  
  def print1927YankeesBattingLine() {
    val line = teamLines((1927, "NYA"))
    val stats = BattingStatsFactory.fromTeamLine(line)
    printStats(stats)
  }
  
  def printStats(stats: BattingStats) {
    val format = new BattingLineFormat
    val out = LineWriter.Console
    out.println(format.header)
    out.println(format.format(stats))
  }
  
  def print1921BabeRuth() {
    val lines = playerLines.get(("ruthba01", 1921))
    var stats: BattingStats = ArrayBattingStats.empty
    for (line <- lines) {
      stats += BattingStatsFactory.fromPlayerLine(line(0))
    }
    printStats(stats)
  }
  
}