package lahman.cards

import lahman.TeamFile
import op2poe.baseball.data.batting.BattingStats
import lahman.batting.BattingStatsFactory
import op2poe.baseball.data.pitching.PitchingStats
import lahman.pitching.PitchingStatsFactory
import op2poe.baseball.data.game.BattingCard
import scala.collection.mutable.ListBuffer
import op2poe.baseball.data.game.PitchingCard

class CardFactories(private val year: Int, private val league: String) {

  val teamLines = TeamFile.read()(year, league)
  
  val leagueBattingStats = (BattingStats.empty /: teamLines.values)(_ + BattingStatsFactory.fromTeamLine(_))
  
  val leaguePitchingStats = (PitchingStats.empty /: teamLines.values)(_ + PitchingStatsFactory.fromTeamLine(_))

  def teamBattingCard(teamId: String): BattingCard = {
    val teamStats = BattingStatsFactory.fromTeamLine(teamLines(teamId))
    def baseLineF = (s: BattingStats) => s.plateAppearances
    val valueFs = List[(BattingStats) => Int](
        s => s.singles,
        s => s.doubles,
        s => s.triples,
        s => s.homeruns,
        s => s.strikeouts,
        s => s.walks, 
        s => s.plateAppearances - (s.singles + s.doubles + s.triples + s.homeruns + s.strikeouts + s.walks)
    )
    val sides = generateNumberOfSidesInDie(leagueBattingStats, teamStats, baseLineF, valueFs)
    return BattingCard(plateAppearances = 1000, singles = sides(0), doubles = sides(1), 
        triples = sides(2), homeruns = sides(3), 
        strikeouts = sides(4), walks = sides(5))
  }

  private def generateNumberOfSidesInDie[A](leagueStats: A, teamStats: A,
		  						  			baseLineF: (A) => Int,
		  						  			valueFs: List[(A) => Int]): List[Int] = {
    // TODO: leagueStats, teamStates, and baseLineF are all just passed on to the
    // teamValue function. I think this can be done in a nicer way.
    var rates = valueFs.map(vf => teamValue(leagueStats, teamStats, baseLineF, vf))
    val sum = rates.sum
    rates.map(r => (1000 * (r / sum)).round.toInt)
  }
  
  private def teamValue[A](league: A, team: A, baseLineF: (A) => Int, valueF: (A) => Int) = {
      val lg = 1.0 * valueF(league) / baseLineF(league)
      val tm = 1.0 * valueF(team) / baseLineF(team)
      2 * tm - lg
  }
  
  
  
}