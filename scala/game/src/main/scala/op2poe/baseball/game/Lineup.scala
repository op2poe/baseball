package op2poe.baseball.game

import op2poe.baseball.data.game.PitchingCard
import op2poe.baseball.data.game.BattingCard

final class Lineup(val pitcher: PitchingCard, val battingOrder: List[BattingCard]) {

  require(battingOrder.length == 9)
  
  private var spot = 0
  
  def batters: Iterator[BattingCard] = new Batters
  
  private class Batters extends Iterator[BattingCard] {
    
    def hasNext = true
    
    def next() = {
      val b = battingOrder(spot)
      spot = (spot + 1) % 9
      b
    }
  }
  
}
