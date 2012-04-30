package retrosheet.gamelogs.rundist

import scala.collection.mutable.Map
import retrosheet.CsvParser
import op2poe.baseball.data.LineScore
import retrosheet.schedules.Schedule
import retrosheet.schedules.ScheduledGame
import op2poe.baseball.data.Date
import op2poe.io.LineWriter

final class Season(year: Int, league: String) {

  private val teams = Map[String, Team]()

  private val schedule = new Schedule

  private var currentDay: Date = null
  
  private var lastDay: Date = null

  def addTeams(teams: Team*) {
    for (t <- teams) this.teams += (t.id -> t)
  }

  def loadSchedule(filePath: String) {
    CsvParser.parseFile(filePath, record => record(4) == league, record => {
      val g = ScheduledGame.fromRetrosheet(record)
      schedule.addGame(g)
      if (currentDay == null) currentDay = g.date
      lastDay = g.date
    })
  }

  def populateRunDists(file: String) {
    CsvParser.parseFile(file, record => record(4) == league, record => {
      try {
      val homeTeam = teams(record(6))
      val roadTeam = teams(record(3))
      val homeScore = LineScore.fromString(record(20))
      val roadScore = LineScore.fromString(record(19))
      homeTeam.updateHomeDists(homeScore, roadScore)
      roadTeam.updateRoadDists(roadScore, homeScore)
      } catch {
        case e: Exception => 
          println("Invalid gamelog record: " + record)
      }
    })
  }

  def playAllGames(out: LineWriter = LineWriter.Console) {
    while (hasDaysLeft) playDay(out)
  }
  
  def playUntil(day: Date, out:LineWriter = LineWriter.Console) {
    while (currentDay < day && hasDaysLeft) playDay(out)
  }
  
  def hasDaysLeft = lastDay >= currentDay
  
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
      val g = new Game(teams(sg.homeTeamId), teams(sg.roadTeamId))
      g.play(out)
    }
    standings.print(out)
    currentDay = currentDay.nextDay
  }

  def playGame(homeTeamId: String, roadTeamId: String, out: LineWriter = LineWriter.Console) {
    val g = new Game(teams(homeTeamId), teams(roadTeamId))
    g.play(LineWriter.None)
  }

  def standings = new Standings(teams.values)
  
}
