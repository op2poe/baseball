package retrosheet.pbp

object SituationJUnitTest extends App {

  topOfFirstBostonAtBrooklyn1921April17()
  bottomOfSeventhBostonAtBrooklyn1921April22()
  bottomOfFourthBostonAtBrooklyn1921April24()
  println("OK")

  var situation: Situation = null;
  
  def topOfFirstBostonAtBrooklyn1921April17() {
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

  
  def bottomOfSeventhBostonAtBrooklyn1921April22() {
    situation = Situation.startOfInning()
    
    situation.processEvent("olsoi101", "T8")
    checkState(false, false, true, 0, 0)
    
    situation.processEvent("johnj107", "W")
    checkState(true, false, true, 0, 0)
    
    situation.processEvent("grift102", "D/89.3-H;1-3")
    checkState(false, true, true, 0, 1)
    
    situation.processEvent("wheaz101", "IW")
    checkState(true, true, true, 0, 1)

    situation.processEvent("konee101", "HP.3-H;2-3;1-2")
    checkState(true, true, true, 0, 2)
    
    situation.processEvent("myerh102", "6/P")
    checkState(true, true, true, 1, 2)
    
    situation.processEvent("neisb101", "K")
    checkState(true, true, true, 2, 2)
    
    situation.processEvent("power101", "2/FL")
    checkState(true, true, true, 3, 2)
  }
  
  
  def bottomOfFourthBostonAtBrooklyn1921April24() {
    situation = Situation.startOfInning()
    
    situation.processEvent("millo103" ,"D/78")
    checkState(false, true, false, 0, 0)
    
    situation.processEvent("mamaa101", "S1.2-H;B-2(E1/TH)")
    checkState(false, true, false, 0, 1)
    
    situation.processEvent("olsoi101", "FC1.2-2;B-1")
    checkState(true, true, false, 0, 1)
    
    situation.processEvent("johnj107", "S5.2-H;1-3;B-2(E5/TH)")
    checkState(false, true, true, 0, 2)
    
    situation.processEvent("grift102", "4/P")
    checkState(false, true, true, 1, 2)
    
    situation.processEvent("wheaz101", "NP")
    checkState(false, true, true, 1, 2)
    
    situation.processEvent("wheaz101", "FC3.3XH(352);2-3;B-2")
    checkState(false, true, true, 2, 2)
    
    situation.processEvent("konee101", "W")
    checkState(true, true, true, 2, 2)
    
    situation.processEvent("myerh102", "W.3-H;2-3;1-2")
    checkState(true, true, true, 2, 3)
    
    situation.processEvent("neisb101", "S9.3-H;2-H;1-3")
    checkState(true, false, true, 2, 5)
    
    situation.processEvent("millo103", "OA.1-2(E2);3-H(UR)#")
    checkState(false, true, false, 2, 6)
    
    situation.processEvent("millo103", "6/P")
    checkState(false, true, false, 3, 6)
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