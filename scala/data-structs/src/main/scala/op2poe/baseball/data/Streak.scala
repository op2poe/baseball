package op2poe.baseball.data

object Streak {

  def fromResults(results: List[Runs]) =
    results match {
      case Nil =>  "-"
      case r :: rs =>
	    val size = 1 + rs.takeWhile(r.outcomePredicate()).size
	    val kind = if (r.isWin) "W" else if (r.isLoss) "L" else "T"
	    kind + size
  }
  
}