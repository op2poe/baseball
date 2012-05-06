package op2poe.baseball.data

final class ResultLog {

  var results = List[Runs]()
  
  def add(r: Runs) {
    results ::= r
  }

  def sum = (new Runs(0, 0) /: results)(_ + _)
  
  def last10 = toWLT(results.take(10))

  private def toWLT(values: List[Runs]) = (new WLT() /: values)(_.addResult(_))
  
  def oneRun = toWLT(results.filter(_.isOneRunGame))
  
  def streak: String = {
    Streak.fromResults(results)
  }

}