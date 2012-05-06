package lahman

import scala.collection.mutable
import scala.io.Source

final class TeamFile(private val data: Map[TeamFile.League, mutable.Map[String, String]]) {

  def teamLine(year: Int, league: String, team: String): String =
    data(TeamFile.League(year, league))(team)
  
  def leagueLines(year: Int, league: String) =
    data(TeamFile.League(year, league))
    
}

object TeamFile {
  
  case class League(val year: Int, val league: String)
  
  def read(path: String) = {
    val map = mutable.Map[League, mutable.Map[String, String]]()
    val source = Source.fromFile(path)
    for (line <- source.getLines if !line.isEmpty; if !line.startsWith("yearID")) {
      val parts = line.split(",")
      val league = League(parts(0).toInt, parts(1))
      val team = parts(2)
      var teams = if (map.contains(league)) map(league) else mutable.Map[String, String]()
      teams(parts(2)) = line
      map(league) = teams
    }
    new TeamFile(map.toMap)
  }
  
}