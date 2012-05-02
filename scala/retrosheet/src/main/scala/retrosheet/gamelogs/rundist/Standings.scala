package retrosheet.gamelogs.rundist

import scala.util.Sorting
import op2poe.io.LineWriter
import java.text.DecimalFormat
import op2poe.baseball.data.GamesBehind
import op2poe.baseball.data.output.FormattedStat
import op2poe.baseball.data.output.HorizontalAlign
import op2poe.baseball.data.output.FormattedStatLine

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
    FormattedStat.stringLike("STRK", 6))

  private val pctFormat = new DecimalFormat(".000")

  def print(out: LineWriter = LineWriter.Console) {
    printHeader(out)
    teams.foreach(printTeam(_, out))
    statLine.separator(out, '=')
    out.println()
  }

  private def printHeader(out: LineWriter) {
    statLine.printHeader(out)
    statLine.separator(out, '-')
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
        t.runs.scored,
        t.runs.against,
        t.last10.toStringWithoutTies, 
        t.streak))
  }

}