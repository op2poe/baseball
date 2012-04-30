package retrosheet.schedules

import op2poe.baseball.data.Date
import scala.collection.mutable.Map
import retrosheet.CsvParser

final class Schedule {

  private val games = Map[Date, List[ScheduledGame]]()
  
  def addGames(gs: ScheduledGame*) {
    for (g <- gs) addGame(g)
  }
  
  def addGame(g: ScheduledGame) {
    var gamesThatDay = if (games.contains(g.date)) games(g.date) else Nil
    gamesThatDay = g :: gamesThatDay
    games += g.date -> gamesThatDay
  }
  
  def gamesOnDate(d: Date): List[ScheduledGame] = if (games.contains(d)) games(d) else Nil
  
}

object Schedule {
  
  def fromFile(filePath: String, filter: (List[String]) => Boolean): Schedule = {
    val s = new Schedule
    CsvParser.parseFile(filePath, filter, record => {
      s.addGame(ScheduledGame.fromRetrosheet(record))
    })
    s
  }
  
}
