package lahman.batting

import op2poe.baseball.data.batting.BattingStats
import op2poe.baseball.data.batting.ArrayBattingStats

object BattingStatsFactory {

  def fromTeamLine(line: String):BattingStats = {
    val parts = line.split(",")
    ArrayBattingStats(
        r = toInt(parts(14)),
        ab = toInt(parts(15)),
        h = toInt(parts(16)),
        doubles = toInt(parts(17)),
        triples = toInt(parts(18)),
        hr = toInt(parts(19)),
        bb = toInt(parts(20)),
        so = toInt(parts(21)),
        hbp = toInt(parts(24)),
        sf = toInt(parts(25)),
        sh = 0, // Not included in the file
        sb = toInt(parts(22)),
        cs = toInt(parts(23))
    )
  }
  
  private def toInt(s: String) = if (s.isEmpty) 0 else s.toInt
  
}