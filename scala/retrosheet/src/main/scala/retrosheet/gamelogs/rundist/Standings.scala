package retrosheet.gamelogs.rundist

import scala.util.Sorting
import op2poe.io.LineWriter
import java.text.DecimalFormat
import op2poe.baseball.data.GamesBehind
import op2poe.baseball.io.text.FormattedStat
import op2poe.baseball.io.text.HorizontalAlign
import op2poe.baseball.io.text.FormattedStatLine

final class Standings(ts: Iterable[Team]) {

  private val teams = ts.toArray.sortWith((t1, t2) => (t1.record > t2.record))

  private val gbCmp = teams.map(_.record).sortWith((r1, r2) => r1.winLossDiff > r2.winLossDiff)(0)

  private val statLine = new FormattedStatLine(
    FormattedStat.stringLike("Team Name", 20, HorizontalAlign.Left),
    FormattedStat.intLike("W", 3),
    FormattedStat.intLike("L", 5),
    FormattedStat.averageLike("PCT", 6),
    FormattedStat.stringLike("GB", 6),
    FormattedStat.intLike("RS", 7),
    FormattedStat.intLike("RA", 6),
    FormattedStat.stringLike("L10", 7),
    FormattedStat.stringLike("STRK", 6),
    FormattedStat.stringLike("PythWL", 10),
    FormattedStat.intLike("LUCK", 5),
    FormattedStat.stringLike("1-RUN", 8))

  private val pctFormat = new DecimalFormat(".000")

  def print(out: LineWriter = LineWriter.Console) {
    printHeader(out)
    teams.foreach(printTeam(_, out))
    statLine.printSeparator(out, '=')
    out.println()
  }

  private def printHeader(out: LineWriter) {
    statLine.printHeader(out)
    statLine.printSeparator(out, '-')
  }

  private def printTeam(t: Team, out: LineWriter) {
    val pct = pctFormat.format(t.record.winPct)
    val gb = new GamesBehind(gbCmp, t.record).toString
    statLine.printLine(out, List(
        t.name,
        t.record.wins,
        t.record.losses,
        t.record.winPct,
        gb,
        t.record.runsScored,
        t.record.runsAgainst,
        t.last10.toStringWithoutTies, 
        t.streak,
        t.record.pythagoreanWL.toStringWithoutTies,
        t.record.luck,
        t.oneRun.toStringWithoutTies))
  }

}