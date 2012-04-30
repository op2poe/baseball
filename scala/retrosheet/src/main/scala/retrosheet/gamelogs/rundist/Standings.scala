package retrosheet.gamelogs.rundist

import scala.util.Sorting
import op2poe.io.LineWriter
import java.text.DecimalFormat
import op2poe.baseball.data.GamesBehind

final class Standings(ts: Iterable[Team]) {

  private val teams = ts.toArray.sortWith((t1, t2) => (t1.record > t2.record))

  private val gbCmp = teams.map(_.record).sortWith((r1, r2) => r1.winLossDiff > r2.winLossDiff)(0)

  private val pctFormat = new DecimalFormat(".000")

  def print(out: LineWriter = LineWriter.Console) {
    printHeader(out)
    teams.foreach(printTeam(_, out))
    out.println("=" * 66)
    out.println()
  }

  private def printHeader(out: LineWriter) {
    out.println("%-20s%3s%5s%6s%6s%7s%6s%7s%6s".format("Team Name", "W", "L", "PCT", "GB", "RS", "RA", "L10", "STRK"))
    out.println("-" * 66)
  }

  private def printTeam(t: Team, out: LineWriter) {
    val pct = pctFormat.format(t.record.winPct)
    val gb = new GamesBehind(gbCmp, t.record).toString
    out.println("%-20s%3d%5d%6s%6s%7d%6d%7s%6s".format(
      t.name,
      t.record.wins, t.record.losses,
      pct, gb,
      t.runs.scored, t.runs.against,
      t.last10.toStringWithoutTies, t.streak))
  }

}