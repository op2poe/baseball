package retrosheet.pbp

object GameProcessorTest extends App {

  val fileNames = List("1921BRO.EVN", "1921BSN.EVN",  "1921CHN.EVN", "1921CIN.EVN", "1921NY1.EVN",
      "1921PHI.EVN", "1921PIT.EVN", "1921SLN.EVN")
  val root = "C:\\z\\coding\\bb\\retrosheet\\play-by-play\\1921\\"
  val filePaths = fileNames.map(root + _)
  val processor = new Processor()
  processor.processFile(filePaths(0))

  
  class Processor extends GameProcessor {
    
    def newGame() {
      println("New game")
    }
    
    def endGame() {
      println("Game over")
      stop()
    }
    
    def newInning(inning: Int, battingTeam: Int) {
      println("Inning: %d, batting team: %d".format(inning, battingTeam))
    }
    
    def processEvent(event: String) {
      println("Event: " + event)
    }
  }
  
}
