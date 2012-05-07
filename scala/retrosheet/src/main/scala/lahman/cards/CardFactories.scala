package lahman.cards

import lahman.TeamFile
import op2poe.baseball.data.batting.BattingStats
import lahman.batting.BattingStatsFactory
import op2poe.baseball.data.pitching.PitchingStats
import lahman.pitching.PitchingStatsFactory
import op2poe.baseball.data.game.BattingCard
import scala.collection.mutable.ListBuffer
import op2poe.baseball.data.game.PitchingCard
import op2poe.baseball.data.game.Single
import op2poe.baseball.data.game.Double
import op2poe.baseball.data.game.Triple
import op2poe.baseball.data.game.Homerun
import op2poe.baseball.data.game.Strikeout
import op2poe.baseball.data.game.Walk
import op2poe.baseball.data.game.Out

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
    return BattingCard((Single, sides(0)), (Double, sides(1)), (Triple, sides(2)),
        (Homerun, sides(3)), (Strikeout, sides(4)), (Walk, sides(5)), (Out, sides(6)))
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

  def teamPitchingCard(teamId: String): PitchingCard = {
    val teamStats = PitchingStatsFactory.fromTeamLine(teamLines(teamId))
    def baseLineF = (s: PitchingStats) => s.hits + s.walks + s.outs
    val valueFs = List[(PitchingStats) => Int](
        pitchingSingles,
        pitchingDoubles,
        pitchingTriples,
        s => s.homeRuns,
        s => s.strikeouts,
        s => s.walks, 
        s => baseLineF(s) - (s.hits + s.strikeouts + s.walks)
    )
    val sides = generateNumberOfSidesInDie(leaguePitchingStats, teamStats, baseLineF, valueFs)
    return PitchingCard((Single, sides(0)), (Double, sides(1)), (Triple, sides(2)),
        (Homerun, sides(3)), (Strikeout, sides(4)), (Walk, sides(5)), (Out, sides(6)))
  }

  // TODO: These three methods can be combined to one. Currying?
  private def pitchingSingles(s: PitchingStats): Int = {
    val leagueRatio = 1.0 * leagueBattingStats.singles / leagueBattingStats.hits
    (s.hits * leagueRatio).round.toInt
  }

  private def pitchingDoubles(s: PitchingStats): Int = {
    val leagueRatio = 1.0 * leagueBattingStats.doubles / leagueBattingStats.hits
    (s.hits * leagueRatio).round.toInt
  }

  private def pitchingTriples(s: PitchingStats): Int = {
    val leagueRatio = 1.0 * leagueBattingStats.triples / leagueBattingStats.hits
    (s.hits * leagueRatio).round.toInt
  }
  
}