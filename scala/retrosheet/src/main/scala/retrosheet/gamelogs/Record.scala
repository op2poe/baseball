package retrosheet.gamelogs

import op2poe.baseball.data.WLT
import op2poe.baseball.data.Runs
import op2poe.baseball.data.batting.BattingStats

class Record(private var wlt: WLT, private var batting: BattingStats) {

  def this() = this(new WLT, BattingStats.empty)
  
  def addResult(r: Runs, b: BattingStats) = {
    wlt = wlt.addResult(r)
    batting += b
  }

  def + (that: Record) =
    new Record(wlt + that.wlt, batting + that.batting)
  
  def winLossRecord = wlt
  
  def winPct = wlt.winPct
  
  def average = batting.battingAverage
  
}