package retrosheet.gamelogs

import op2poe.baseball.data.WLT
import op2poe.baseball.data.BasicBattingStats
import op2poe.baseball.data.Runs

class Record(private var wlt: WLT, private var batting: BasicBattingStats) {

  def this() = this(new WLT, BasicBattingStats.none)
  
  def addResult(r: Runs, b: BasicBattingStats) = {
    wlt = wlt.addResult(r)
    batting += b
  }

  def + (that: Record) =
    new Record(wlt + that.wlt, batting + that.batting)
  
  def winLossRecord = wlt
  
  def winPct = wlt.winPct
  
  def average = batting.average
  
}