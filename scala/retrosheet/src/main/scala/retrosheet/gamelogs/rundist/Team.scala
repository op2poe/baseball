package retrosheet.gamelogs.rundist

import retrosheet.gamelogs.InningRunDistribution
import op2poe.baseball.data.LineScore
import op2poe.baseball.data.WLT
import op2poe.baseball.data.Runs
import op2poe.baseball.data.ResultLog
import op2poe.baseball.data.TeamRecord

final class Team(val id: String, val name: String) {

  var record = new TeamRecord
  var resultLog = new ResultLog

  val runsScoredDistHome = new InningRunDistribution
  val runsScoredDistRoad = new InningRunDistribution
  val runsAgainstDistHome = new InningRunDistribution
  val runsAgainstDistRoad = new InningRunDistribution

  def updateHomeDists(scored: LineScore, against: LineScore) {
    runsScoredDistHome.update(scored)
    runsAgainstDistHome.update(against)
  }

  def updateRoadDists(scored: LineScore, against: LineScore) {
    runsScoredDistRoad.update(scored)
    runsAgainstDistRoad.update(against)
  }

  def addGameResult(runs: Runs) {
    record = record.addResult(runs)
    resultLog.add(runs)
  }

  def last10 = resultLog.last10

  def oneRun = resultLog.oneRun
  
  def streak:String = resultLog.streak
  
  def nameAndRecord = name + " (" + record.wlt.toStringWithoutTies + ")"

}
