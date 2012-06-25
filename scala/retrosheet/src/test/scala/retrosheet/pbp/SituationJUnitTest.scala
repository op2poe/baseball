package retrosheet.pbp

object SituationJUnitTest extends App {

  bottomOfFirstBostonAtBrooklyn1921April17()
  println("OK")

  var situation: Situation = null;
  
  def bottomOfFirstBostonAtBrooklyn1921April17() {
    situation = Situation.startOfInning()
    
    situation.processEvent("power101", "W")
    /* TODO: This should work: assert(situation.bases.first == Some("power101"))*/
    checkState(true, false, false, 0, 0)

    situation.processEvent("barbw101", "SB2")
    checkState(false, true, false, 0, 0)

    situation.processEvent("barbw101", "K")
    checkState(false, true, false, 1, 0)

    situation.processEvent("soutb101", "E6.2-3")
    checkState(true, false, true, 1, 0)

    situation.processEvent("nichf101", "23/SH.3-H(UR);1-2")
    checkState(false, true, false, 2, 1)

    situation.processEvent("boect101", "5/FL")
    checkState(false, true, false, 3, 1)
  }

  private def checkState(runnerOn1st: Boolean,
    runnerOn2nd: Boolean,
    runnerOn3rd: Boolean,
    outs: Int,
    runs: Int) {
    assert(situation.bases.isOccupied(1) == runnerOn1st)
    assert(situation.bases.isOccupied(2) == runnerOn2nd)
    assert(situation.bases.isOccupied(3) == runnerOn3rd)
    assert(situation.outs == outs)
    assert(situation.runsScored == runs)
  }

}