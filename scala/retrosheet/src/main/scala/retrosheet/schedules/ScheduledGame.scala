package retrosheet.schedules

import op2poe.baseball.data.Date

final class ScheduledGame(val date: Date, val homeTeamId: String, val roadTeamId: String)

object ScheduledGame {
  
  def fromRetrosheet(r: List[String]) = {
      val d = Date.yyyymmdd(r(0))
      val visitor = r(3)
      val home = r(6)
      new ScheduledGame(d, home, visitor)
  }
  
}