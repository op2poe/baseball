package op2poe.baseball.data

final class ResultLog {

  var results = List[Runs]()
  
  def add(r: Runs) {
    results ::= r
  }

  def sum = results.foldLeft(new Runs(0, 0))(_ + _)
  
  def last10 = results.take(10).foldLeft(new WLT())((wlt, r) => wlt.addResult(r))

  def streak: String = {
    // Refactor me!
    if (results.isEmpty) "-"
    var kind = ""
    var value = 0
    for (r <- results) {
      if (r.isWin) {
        if (kind == "") kind = "W"
        if (kind == "W") value += 1
        else return kind + value
      }
      if (r.isLoss) {
        if (kind == "") kind = "L"
        if (kind == "L") value += 1
        else return kind + value
      }
    }
    if (value == 0) return "-" else return kind + value
  }

}