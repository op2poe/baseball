package op2poe.baseball.data

object Streak {

  def fromResults(results: List[Runs]) = {
    if (results.isEmpty) "-"
    val first = results.head
    val size = 1 + results.tail.takeWhile(first.outcomePredicate()).size
    val kind = if (first.isWin) "W" else if (first.isLoss) "L" else "T"
    kind + size
  }
  
}