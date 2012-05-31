package retrosheet.pbp

trait EventProcessor {

  def processPlay(play: String): Unit = {
    val parts = play.split(",")
    if (parts.length < 7) {
      println(play)
      return
    }
    val event = parts(6)
    processEvent(event)
  }
  
  def processEvent(event: String): Unit

}