package retrosheet.gamelogs.rundist

import op2poe.baseball.data.Date

object Driver extends App {
  
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
  
  val year = 1925
  val Teams = Map("NL" -> NationalLeagueTeams, "AL" ->AmericanLeagueTeams)

  val season = createSeason()
  season.playAllGames()
  //season.playUntil(Date(year, 5, 15))
  
  def createSeason() = {
    val lg = "AL"
    val s = new Season(year, lg)
    val teams = Teams(lg).map(t => new Team(t._1, t._2))
    s.addTeams(teams: _*)
    s.loadSchedule("C:\\z\\coding\\bb\\retrosheet\\schedules\\" + year + "SKED.TXT")
    s.populateRunDists("C:\\z\\coding\\bb\\retrosheet\\gamelogs\\gl" + year + ".txt")
    s
  }
  
}
