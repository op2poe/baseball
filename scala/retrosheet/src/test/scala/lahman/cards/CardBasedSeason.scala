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
import op2poe.baseball.io.text.FormattedStatLine
import op2poe.baseball.data.GamesBehind
import java.text.DecimalFormat
import op2poe.baseball.io.text.FormattedStat
import op2poe.baseball.io.text.HorizontalAlign
import op2poe.baseball.io.text.batting.BattingLineFormat
import op2poe.baseball.data.batting.BattingStats
import op2poe.baseball.data.pitching.PitchingStats

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

  val year = 1914
  val league = "NL"
  val teams = loadTeams(NationalLeagueTeams)
  var currentDay: Date = null
  var lastDay: Date = null
  val schedule = loadSchedule()
  
  val out = LineWriter.Console
  
  //playUntil(Date(1901, 4, 20), LineWriter.Console)
  playAllGames(out)
  printBattingStats(out)

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

  def playUntil(day: Date, out: LineWriter = LineWriter.Console) {
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
      val homeTeam = teams(sg.homeTeamId)
      val roadTeam = teams(sg.roadTeamId)
      val g = new Game(homeTeam.asOpponent, roadTeam.asOpponent)
      val result = g.play(out)
    }
    standings.print(out)
    currentDay = currentDay.nextDay
  }

  def hasDaysLeft = lastDay >= currentDay

  def standings = new Standings(teams.values)

  def printBattingStats(out: LineWriter) {
    out.println()
    out.println("Batting:")
    val fmt = new BattingLineFormat()
    out.println(fmt.header)
    out.println(fmt.separator('-'))
    List(teams.values.toArray:_*).sortWith(_.name < _.name).foreach(t =>
      out.println(fmt.format(t.battingStats)))
  }
  
  
  class Team(val name: String, val pitching: PitchingCard, val batting: BattingCard) {

    var record = new TeamRecord
    val resultLog = new ResultLog
    var battingStats = BattingStats.empty
    var pitchingStats = PitchingStats.empty

    def asOpponent() = {
      val lineup = new Lineup(pitching, List.fill(9)(batting))
      new Opponent(name, lineup) {
        def endOfGame(result: Runs, batting: BattingStats, pitching: PitchingStats) {
          record = record.addResult(result)
          resultLog.add(result)
          battingStats += batting
          pitchingStats += pitching
        }
      }
    }

    def last10 = resultLog.last10

    def oneRun = resultLog.oneRun

    def streak: String = resultLog.streak

    def nameAndRecord = name + " (" + record.wlt.toStringWithoutTies + ")"
  }

  
  final class Standings(ts: Iterable[Team]) {

    private val teams = ts.toArray.sortWith((t1, t2) => (t1.record > t2.record))

    private val gbCmp = teams.map(_.record).sortWith((r1, r2) => r1.winLossDiff > r2.winLossDiff)(0)

    private val statLine = new FormattedStatLine(
      FormattedStat.stringLike("Team Name", 20, HorizontalAlign.Left),
      FormattedStat.intLike("W", 3),
      FormattedStat.intLike("L", 5),
      FormattedStat.averageLike("PCT", 6),
      FormattedStat.stringLike("GB", 6),
      FormattedStat.intLike("RS", 7),
      FormattedStat.intLike("RA", 6),
      FormattedStat.stringLike("L10", 7),
      FormattedStat.stringLike("STRK", 6),
      FormattedStat.stringLike("PythWL", 10),
      FormattedStat.intLike("LUCK", 5),
      FormattedStat.stringLike("1-RUN", 8))

    private val pctFormat = new DecimalFormat(".000")

    def print(out: LineWriter = LineWriter.Console) {
      printHeader(out)
      teams.foreach(printTeam(_, out))
      statLine.printSeparator(out, '=')
      out.println()
    }

    private def printHeader(out: LineWriter) {
      statLine.printHeader(out)
      statLine.printSeparator(out, '-')
    }

    private def printTeam(t: Team, out: LineWriter) {
      val pct = pctFormat.format(t.record.winPct)
      val gb = new GamesBehind(gbCmp, t.record).toString
      statLine.printLine(out, List(
        t.name,
        t.record.wins,
        t.record.losses,
        t.record.winPct,
        gb,
        t.record.runsScored,
        t.record.runsAgainst,
        t.last10.toStringWithoutTies,
        t.streak,
        t.record.pythagoreanWL.toStringWithoutTies,
        t.record.luck,
        t.oneRun.toStringWithoutTies))
    }
  }
  
}
