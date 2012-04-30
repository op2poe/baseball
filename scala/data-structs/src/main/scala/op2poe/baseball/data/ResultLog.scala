package op2poe.baseball.data

final class ResultLog {

  var results = List[Runs]()
  
  def add(r: Runs) {
    results ::= r
  }

  def sum = results.foldLeft(new Runs(0, 0))(_ + _)
  
  def last10 = results.take(10).foldLeft(new WLT())((wlt, r) => wlt.addResult(r))

  def streak: String = {
    Streak.fromResults(results)
  }

}