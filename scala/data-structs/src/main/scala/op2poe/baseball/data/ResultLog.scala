package op2poe.baseball.data

final class ResultLog {

  var results = List[Runs]()
  
  def add(r: Runs) {
    results ::= r
  }

  def sum = (new Runs(0, 0) /: results)(_ + _)
  
  def last10 = (new WLT() /: results.take(10))(_.addResult(_))

  def streak: String = {
    Streak.fromResults(results)
  }

}