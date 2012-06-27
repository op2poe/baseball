package retrosheet.pbp

object BostonAtBrooklyn19210417 extends App {

  val p = new Processor()
  p.processFile("C:\\z\\coding\\bb\\retrosheet\\play-by-play\\1921\\1921BOS.evA")
  
  class Processor extends GameProcessor {

    val score = Array(0, 0)

    var situation: Situation = null

    var battingTeam: Int = -1

    def newGame(): Unit = { /* nothing to do */ }

    def newInning(inning: Int, battingTeam: Int): Unit = {
      endCurrentInning()
      situation = Situation.startOfInning
      this.battingTeam = battingTeam
    }

    private def endCurrentInning() {
      if (situation != null) {
        score(this.battingTeam) += situation.runsScored
      }
    }

    def processPlay(playerId: String, event: String): Unit = {
      situation.processEvent(playerId, event)
    }

    def endGame(): Unit = {
      endCurrentInning()
      println("Score: " + score(0) + " - " + score(1))
      stop()
    }
    
  }

}