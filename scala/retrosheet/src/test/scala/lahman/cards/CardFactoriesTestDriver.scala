package lahman.cards

object CardFactoriesTestDriver extends App {

  test1927YankeesBattingCard()
  
  def test1927YankeesBattingCard() {
    val f = new CardFactories(1927, "AL")
    val bc = f.teamBattingCard("NYA")
    var outs = 0
    while (outs < 27) {
      val oc = bc.outcome()
      println(oc)
      if (oc.isOut) outs += 1
    }
  }
  
}