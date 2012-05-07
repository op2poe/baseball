package lahman.cards

import op2poe.baseball.data.game.PlayerCard

object CardFactoriesTestDriver extends App {

  //test1927YankeesBattingCard()
  test1927YankeesPitchingCard()
  
  def test1927YankeesBattingCard() {
    val f = new CardFactories(1927, "AL")
    val bc = f.teamBattingCard("NYA")
    testCard(bc)
  }

  def testCard(card: PlayerCard) {
    var outs = 0
    while (outs < 27) {
      val oc = card.outcome()
      println(oc)
      if (oc.isOut) outs += 1
    }
  }
  
  def test1927YankeesPitchingCard() {
    val f = new CardFactories(1927, "AL")
    val bc = f.teamPitchingCard("NYA")
    testCard(bc)
  }
  
  
  
}