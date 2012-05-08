package lahman.batting

import op2poe.baseball.io.text.batting.BattingLineFormat
import op2poe.baseball.data.batting.ArrayBattingStats
import op2poe.baseball.data.batting.BattingStats
import lahman.TeamFile

object BattingStatsFactoryDriver extends App {

  val root = "C:\\z\\coding\\bb\\lahman\\data\\"
  
  val teamFile = TeamFile.read(root + "Teams.csv")
  
  val playerLines: Map[(String, Int), List[String]] = PlayerFileReader.readFile(root + "Batting.csv")
  
  print1901NationalLeagueBattingLine()
  print1927YankeesBattingLine()
  print1921BabeRuth()

  def print1901NationalLeagueBattingLine() {
    val lines = teamFile(1901, "NL").values
    val stats = (BattingStats.empty /: lines)(_ + BattingStatsFactory.fromTeamLine(_))
    println("1901 National League:")
    printStats("National League", stats)
  }
  
  def printStats(name: String, stats: BattingStats) {
    val format = new BattingLineFormat
    println(format.header)
    println(format.format(name, stats))
    println
  }
  
  def print1927YankeesBattingLine() {
    val line = teamFile(1927, "AL", "NYA")
    val stats = BattingStatsFactory.fromTeamLine(line)
    println("1927 Yankees:")
    printStats("New York", stats)
  }
  
  def print1921BabeRuth() {
    val lines = playerLines(("ruthba01", 1921))
    val stats = (BattingStats.empty /: lines) (_ + BattingStatsFactory.fromPlayerLine(_))
    println("1921 Babe Ruth:")
    printStats("Babe Ruth", stats)
  }
  
}