package lahman.cards

import scala.collection.mutable
import op2poe.baseball.data.game.PitchingCard
import op2poe.baseball.data.game.BattingCard
import op2poe.baseball.game.Lineup
import op2poe.baseball.game.Opponent
import op2poe.baseball.data.ResultLog
import op2poe.baseball.data.TeamRecord
import op2poe.baseball.data.Runs
import lahman.TeamFile
import retrosheet.schedules.Schedule
import retrosheet.CsvParser
import retrosheet.schedules.ScheduledGame
import op2poe.baseball.data.Date
import op2poe.io.LineWriter
import op2poe.baseball.game.Game

object CardBasedSeason extends App {
    
  val NationalLeagueTeams = 
    List(("BRO", "Brooklyn"), ("PHI", "Philadelphia"), ("BSN", "Boston"), 
		 ("NY1", "New York"), ("CHN", "Chicago"), ("SLN", "St. Louis"),
		 ("PIT", "Pittsburgh"), ("CIN", "Cincinnati"))
  
  val AmericanLeagueTeams01 = 
    List(("CLE", "Cleveland"), ("CHA", "Chicago"), ("MLA", "Milwaukee"), 
		 ("DET", "Detroit"), ("BOS", "Boston"), ("BLA", "Baltimore"),
		 ("WS1", "Washington"), ("PHA", "Philadelphia"))
  
  val AmericanLeagueTeams = 
    List(("CLE", "Cleveland"), ("CHA", "Chicago"), ("SLA", "St. Louis"), 
		 ("DET", "Detroit"), ("BOS", "Boston"), ("NYA", "New York"),
		 ("WS1", "Washington"), ("PHA", "Philadelphia"))

  val year = 1901
  
  val league = "NL"

  val teams = loadTeams(NationalLeagueTeams)

  var currentDay: Date = null
  
  var lastDay: Date = null
  
  val schedule = loadSchedule()

  playUntil(Date(1901, 4, 20), LineWriter.Console)
  
  
  
  
  
  
  def loadTeams(ids: List[(String, String)]): Map[String, Team] = {
    val cards = new CardFactories(year, league)
    val teams = mutable.Map[String, Team]()
    for ((id, name) <- ids) {
      val team = new Team(name, cards.teamPitchingCard(id), cards.teamBattingCard(id))
      teams(id) = team
    }
    teams.toMap
  }

  def loadSchedule() = {
    val schedule = new Schedule()
    val path = "C:\\z\\coding\\bb\\retrosheet\\schedules\\" + year + "SKED.TXT"
    CsvParser.parseFile(path, record => record(4) == league, record => {
      val g = ScheduledGame.fromRetrosheet(record)
      schedule.addGame(g)
      if (currentDay == null) currentDay = g.date
      lastDay = g.date
    })
    schedule
  }

  def playAllGames(out: LineWriter = LineWriter.Console) {
    while (hasDaysLeft) playDay(out)
  }
  
  def playUntil(day: Date, out:LineWriter = LineWriter.Console) {
    while (currentDay < day && hasDaysLeft) playDay(out)
  }
  
  def playDay(out: LineWriter = LineWriter.Console) {
    if (!hasDaysLeft) return
    val sgs = schedule.gamesOnDate(currentDay)
    if (sgs.isEmpty) {
      currentDay = currentDay.nextDay
      return
    }
    out.println("Games on " + currentDay.toISO8601 + ":")
    out.println("====================")
    for (sg <- sgs) {
      val g = new Game(teams(sg.homeTeamId).asOpponent, teams(sg.roadTeamId).asOpponent)
      g.play(out)
    }
    //standings.print(out)
    currentDay = currentDay.nextDay
  }
    
  def hasDaysLeft = lastDay >= currentDay

  
  
  
  class Team(val name: String, val pitching: PitchingCard, val batting: BattingCard) {

    var record = new TeamRecord
    val resultLog = new ResultLog

    def asOpponent() = {
      val lineup = new Lineup(pitching, List.fill(9)(batting))
      new Opponent(name, lineup)
    }

    def addGameResult(runs: Runs) {
      record = record.addResult(runs)
      resultLog.add(runs)
    }

    def last10 = resultLog.last10

    def oneRun = resultLog.oneRun

    def streak: String = resultLog.streak

    def nameAndRecord = name + " (" + record.wlt.toStringWithoutTies + ")"
  }
  
}
