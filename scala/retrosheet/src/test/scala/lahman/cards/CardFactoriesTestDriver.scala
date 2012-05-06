package lahman.cards

object CardFactoriesTestDriver extends App {

  test1927YankeesBattingCard()
  
  def test1927YankeesBattingCard() {
    val f = new CardFactories(1927, "AL")
    val bc = f.teamBattingCard("NYA")
    for (_ <- 1 to 10) println(bc.outcome())
  }
  
}