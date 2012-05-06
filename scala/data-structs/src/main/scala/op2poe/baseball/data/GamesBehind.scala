package op2poe.baseball.data

final class GamesBehind(wlt1: WLT, wlt2: WLT) {

  def this(r1: TeamRecord, r2: TeamRecord) = this(r1.wlt, r2.wlt)
  
  private val halfs = wlt1.winLossDiff - wlt2.winLossDiff
  
  override def toString =
    if (halfs == 0) "-" 
    else if (halfs % 2 == 0) (halfs/2).toString 
    else (halfs/2) + ".5"
  
}