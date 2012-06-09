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
  require(fromBase < 4);
  require(toBase <= 4)
  require(toBase >= fromBase)
  
  /**
   * Applies this {@code Advancement} object to the current base situation, 
   * by modifying the inputted {@code Bases} object.
   */
  def applyTo(bases: Bases) {
    if (fromBase == toBase) return
    val runner = bases(fromBase)
    runner match {
      case None => throw new IllegalBaseOperation("No runner on base " + fromBase)
      case Some(player) => 
        if (toBase < 4) {
        	bases(toBase) = runner
        }
        bases remove fromBase
    }
  }
  
  /**
   * Tests if this {@code Advancement} represents a runner reaching home.
   */
  def runScored = (toBase == 4)
  
}