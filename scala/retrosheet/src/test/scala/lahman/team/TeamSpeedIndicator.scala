package lahman.team

import scala.collection.mutable
import lahman.cards.CardFactories
import op2poe.baseball.data.game.BattingCard
import lahman.TeamFile
import lahman.batting.BattingStatsFactory
import op2poe.baseball.data.batting.BattingStats
import scala.collection.mutable.ListBuffer

object TeamSpeedIndicator extends App {

  val AmericanLeagueTeams = List("CLE", "CHA", "SLA", "DET", "BOS", "NYA", "WS1", "PHA")
  val NationalLeagueTeams = List("BRO", "PHI", "BSN", "NY1", "CHN", "SLN", "PIT", "CIN")
  
  val year = 1943
  val league = "NL"
  
  val teamLines = TeamFile.read()(year, league)

  val values = new ListBuffer[Entry]()
  var leagueStats = BattingStats.empty
  for (id <- NationalLeagueTeams) {
    val teamStats = BattingStatsFactory.fromTeamLine(teamLines(id))
    leagueStats += teamStats
    values += new Entry(id, calculate(teamStats))
  }
  values.sorted.foreach(println)
  println("----------")
  println(new Entry("TOT: ", calculate(leagueStats)))
  
  def calculate(s: BattingStats) = (1.0 * s.runs) / (s.totalBases + s.walks)
  
  
  class Entry(val id: String, val value: Double) extends Ordered[Entry] {
    def compare(other: Entry): Int = other.value.compare(value) // reverse order
    override def toString = id + ": " + "%.3f".format(value)
  }
  
}
