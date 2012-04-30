package op2poe.baseball.data

trait BattingStats {

  val atBats: Int
  
  def hits: Int
  
  def totalBases: Int
  
  def average = (1.0 * hits) / atBats
  
  def slugging = (1.0 * totalBases) / atBats
  
}