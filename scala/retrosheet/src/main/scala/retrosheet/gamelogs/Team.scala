package retrosheet.gamelogs

import op2poe.baseball.data.WLT
import op2poe.baseball.data.Runs
import op2poe.baseball.data.batting.BattingStats

class Team(val name: String) {

  val homeRecord = new Record
  val roadRecord = new Record
  
  def addHomeGame(r: Runs, b: BattingStats) {
    homeRecord.addResult(r, b)
  }
    
  def addRoadGame(r: Runs, b: BattingStats) {
    roadRecord.addResult(r,  b)
  }

  def record = homeRecord + roadRecord
  
  override def toString =
    "%s: winPct=%.2f, BA=%.2f".format(name, homeAdvantage, homeBattingAdvantage)
    
  def homeAdvantage = homeRecord.winPct / roadRecord.winPct
  
  def homeBattingAdvantage = homeRecord.average / roadRecord.average

  def nameAndRecord = name + " (" + record.winLossRecord.toStringWithoutTies + ")"
  
}
