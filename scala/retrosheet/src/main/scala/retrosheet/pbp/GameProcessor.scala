package retrosheet.pbp

import scala.io.Source

abstract class GameProcessor {

  private var inning = -1
  
  private var battingTeam = -1
  
  private var stopped = false
  
  def processFile(filePath: String) {
      val source = Source.fromFile(filePath)
      for (line <- source.getLines) {
        if (stopped) return
        val parts = line.split(",");
        parts(0) match {
          case "id" => newGame()
          case "data" => endGame()
          case "play" => inspectPlayRecord(parts)
          case _ =>
        }
      }
  }
  
  private def inspectPlayRecord(play: Array[String]) {
    val i = play(1).toInt
    val t = play(2).toInt
    if (i != inning || t != battingTeam) {
      inning = i;
      battingTeam = t;
      newInning(i, t)
    }
    processPlay(play(3), play(6))
  }

  def newGame(): Unit;
  
  def newInning(inning: Int, battingTeam: Int): Unit;
  
  def processPlay(playerId: String, event: String): Unit;
  
  def endGame(): Unit;
  
  final def stop() {
    stopped = true
  }
  
}
