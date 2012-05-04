package retrosheet.gamelogs

import scala.collection.mutable.Map
import op2poe.baseball.data.Runs
import op2poe.baseball.data.batting.BattingStats

class GameLog {

  val teams = Map[String, Team]()
  
  def processRecord(record: List[String]) {
    val roadTeam = getTeam(record(3))
    val homeTeam = getTeam(record(6))
    val roadScore = record(9).toInt
    val homeScore = record(10).toInt
    val runs = new Runs(roadScore, homeScore)
    val roadBatting = readBattingStats(record, 21)
    val homeBatting = readBattingStats(record, 49)
    roadTeam.addRoadGame(runs, roadBatting)
    homeTeam.addHomeGame(runs.reverse, homeBatting)
  }
  
  def readBattingStats(record: List[String], index: Int) = {
    val atBats = record(index)
    if (atBats.isEmpty) BattingStats.empty
    else BattingStats(ab = atBats.toInt, h = record(index + 1).toInt)
  }
    
    
  private def getTeam(name: String): Team =
    if (teams.contains(name)) teams(name)
    else {
      val team = new Team(name)
      teams += (name -> team)
      team
    }

  def printReport() { 
    teams.values.foreach(println)
  }
  
}