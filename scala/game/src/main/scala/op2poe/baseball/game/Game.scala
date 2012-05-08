package op2poe.baseball.game

import op2poe.baseball.data.Runs
import op2poe.io.LineWriter
import op2poe.baseball.data.LineScore
import op2poe.baseball.data.pitching.PitchingStats
import op2poe.baseball.data.batting.BattingStats

final class Game(val homeTeam: Opponent, val roadTeam: Opponent) {
  
  def play(output: LineWriter = LineWriter.Console): Runs = {
    for (_ <- 1 to 8) {
      topHalf()
      bottomHalf()
    }
    do {
      topHalf()
      if (roadTeam.runs >= homeTeam.runs) bottomHalf()
      else homeTeam.updateBattingInning(-1, BattingStats.empty)
    } while (roadTeam.runs == homeTeam.runs)
    val result = new Runs(homeTeam.runs, roadTeam.runs)
    printLineScores(result, output)
    result
  }

  private def topHalf() {
    Inning.play(homeTeam, roadTeam)
  }

  private def bottomHalf() {
    Inning.play(roadTeam, homeTeam)
  }

  private def playInning(pitching: Opponent, batting: Opponent) {
    val inning = new Inning(pitching, batting)
    inning.play()
  }

  private def printLineScores(r: Runs, out: LineWriter) {
    val rl = roadTeam.lineScore
    val hl = homeTeam.lineScore
    printLine(roadTeam, rl, r.against, out)
    printLine(homeTeam, hl, r.scored, out)
    out.println
  }

  private def printLine(t: Opponent, ls: LineScore, r: Int, out: LineWriter) {
    out.println("%-20s%s  -  %2d".format(t.name, ls.toPrettyString, r))
  }

}
