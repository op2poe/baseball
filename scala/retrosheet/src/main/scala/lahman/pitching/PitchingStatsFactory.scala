package lahman.pitching

import op2poe.baseball.data.pitching.PitchingStats

object PitchingStatsFactory {

  def fromTeamLine(line: String): PitchingStats = {
    val parts = line.split(",")
    PitchingStats(
        g = toInt(parts(6)),
        w = toInt(parts(8)),
        lo = toInt(parts(9)),
        gs = toInt(parts(6)),
        cg = toInt(parts(29)),
        sho = toInt(parts(30)),
        sv = toInt(parts(31)),
        outs = toInt(parts(32)),
        r = toInt(parts(26)),
        er = toInt(parts(27)),
        h = toInt(parts(33)),
        hr = toInt(parts(34)),
        bb = toInt(parts(35)),
        so = toInt(parts(36))
    )
  }
  
    
  private def toInt(s: String) = if (s.isEmpty) 0 else s.toInt

}