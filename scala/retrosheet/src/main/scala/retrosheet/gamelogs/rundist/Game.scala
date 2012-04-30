package retrosheet.gamelogs.rundist

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import op2poe.baseball.data.Runs
import op2poe.baseball.data.LineScore
import op2poe.io.LineWriter

final class Game(homeTeam: Team, roadTeam: Team) {

  private val homeLineScore = new ArrayBuffer[Int]
  private val roadLineScore = new ArrayBuffer[Int]
  private val rnd = new Random

  def play(out: LineWriter = LineWriter.Console) = {
    val r = playInnings()
    homeTeam.addGameResult(r)
    roadTeam.addGameResult(r.reverse)
    printLineScores(r, out)
  }

  private def playInnings() = {
    for (inn <- 0 until 8) {
      topHalf()
      bottomHalf()
    }
    do {
      topHalf()
      if (roadScore >= homeScore) bottomHalf()
    } while (roadScore == homeScore)
    new Runs(homeScore, roadScore)
  }
  
  private def topHalf() {
    val r = if (rnd.nextDouble < 0.5) roadTeam.runsScoredDistRoad else homeTeam.runsAgainstDistHome
    roadLineScore += r.rollDice(rnd) 
  }

  private def bottomHalf() {
    val h = if (rnd.nextDouble < 0.5) homeTeam.runsScoredDistHome else roadTeam.runsAgainstDistRoad
    homeLineScore += h.rollDice(rnd) 
  }

  private def homeScore = homeLineScore.sum

  private def roadScore = roadLineScore.sum

  private def printLineScores(r: Runs, out: LineWriter) {
    val rl = new LineScore(roadLineScore.toArray)
    if (homeLineScore.length < roadLineScore.length) homeLineScore += -1
    val hl = new LineScore(homeLineScore.toArray)
    printLine(roadTeam, rl, r.against, out)
    printLine(homeTeam, hl, r.scored, out)
    out.println
  }
  
  private def printLine(t: Team, ls: LineScore, r: Int, out: LineWriter) {
    out.println("%-25s%s  -  %2d".format(t.nameAndRecord, ls.toPrettyString, r))
  }
}
