package retrosheet.pbp

/**
 * Represents a runner (possibly) advancing from one base.
 * <p>
 * The batter is represented by {@code fromBase = 0}. A negative value
 * of {@code toBase} represents the runner being out at that base.
 * <p>
 * Due tothe retrosheet play-by-play format, it is sometimes the case that
 * {@code fromBase} equals {@code toBase} (i.e. no advancement was
 * actually made).
 * <p>
 * {@code fromBase} and {@code toBase} are 1-based, since the play-by-play
 * files are.
 */
case class Advancement(val fromBase: Int, val toBase: Int) {

  require(fromBase >= 0)
  require(fromBase < 4);
  require(toBase.abs >= 0)
  require(toBase.abs <= 4)
  require(toBase.abs >= fromBase)

  /**
   * Applies this {@code Advancement} object to the current base situation,
   * by modifying the inputted {@code Bases} object.
   */
  def applyTo(bases: Bases) {
    if (fromBase == toBase) return
    if (toBase > 0) {
      // advancement
      if (fromBase == 0) {
        advanceBatter(bases)
      } else {
        advanceRunner(bases)
      }
    } else {
      // out on base
      if (fromBase == 0) {
        // Batter is out, e.g. trying to stretch a single into a double.
        // Nothing to do.
      } else {
        bases(fromBase) = None
      }
    }
  }

  private def advanceBatter(bases: Bases) {
    if (toBase > 0 & toBase < 4) {
      bases(toBase) = Some("[batter]")
    }
  }

  private def advanceRunner(bases: Bases) {
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

object Advancement {
  
  def ofBatter(toBase: Int) = Advancement(0, toBase)
  
}