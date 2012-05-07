package op2poe.baseball.game

import op2poe.baseball.data.Runs

final class Game(val homeTeam: Opponent, val roadTeam: Opponent) {

  def play(): Runs = {
    for (_ <- 1 to 8) {
      topHalf()
      bottomHalf()
    }
    do {
      topHalf()
      if (roadTeam.runs >= homeTeam.runs) bottomHalf()
    } while (roadTeam.runs == homeTeam.runs)
    new Runs(homeTeam.runs, roadTeam.runs)
  }

  private def topHalf() {
    playInning(homeTeam, roadTeam)
  }

  private def bottomHalf() {
    playInning(roadTeam, homeTeam)
  }

  private def playInning(pitching: Opponent, batting: Opponent) {
    val inning = new Inning(pitching.pitcher, batting.batters)
    inning.play()
    batting.addInningScore(inning.runsScored)
  }

}
