package retrosheet.pbp

/**
 * Represents a runner (possibly) advancing from one base. Due to
 * the retrosheet play-by-play format, it is sometimes the case that
 * {@code fromBase} equals {@code toBase} (i.e. no advancement was 
 * actually made).
 * <p>
 * {@code fromBase} and {@code toBase} are 1-based, since the play-by-play 
 * files are.
 */
case class Advancement(val fromBase: Int, val toBase: Int) {

  require(fromBase > 0)
  require(toBase >= fromBase)
  require(toBase <= 4)
  
  /**
   * Applies this {@code Advancement} object to the current base situation, 
   * by modifying the inputted {@code Bases} object.
   */
  def applyTo(bases: Bases) {
    if (fromBase == toBase) return
    val runner = bases(fromBase)
    runner match {
      case None => throw new Exception("No runner on base " + fromBase)
      case Some(player) => 
        if (toBase < 4) {
        	bases(toBase) = runner
        }
        bases remove toBase
    }
  }
  
  /**
   * Tests if this {@code Advancement} represents a runner reaching home.
   */
  def runScored = (toBase == 4)
  
}