package op2poe.baseball.data

object StreakJUnitTest extends App {

  testFromResults()
  
  def testFromResults() {
    var results = List[Runs]()
    assert("-" == Streak.fromResults(results))
    results ::= new Runs(1, 0)
    assert("W1" == Streak.fromResults(results))
    results ::= new Runs(1, 0)
    assert("W2" == Streak.fromResults(results))
    results ::= new Runs(0, 0)
    assert("T1" == Streak.fromResults(results))
    results ::= new Runs(0, 1)
    assert("L1" == Streak.fromResults(results))
    results ::= new Runs(0, 1)
    assert("L2" == Streak.fromResults(results))
  }
  
}