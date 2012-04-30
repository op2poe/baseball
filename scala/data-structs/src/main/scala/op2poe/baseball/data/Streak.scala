package op2poe.baseball.data

object Streak {

  def fromResults(results: List[Runs]) =
    results match {
      case Nil =>  "-"
      case r :: rs =>
	    val size = 1 + rs.takeWhile(r.outcomePredicate()).size
	    r.outcomeCode + size
  }
  
}