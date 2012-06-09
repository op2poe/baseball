package retrosheet.pbp

/**
 * The current base runners.
 * <p>
 * This class is mutable.
 */
class Bases private (private val bases: Array[Option[String]]) {

  def apply(base: Int) = bases(base - 1)

  def first = bases(0)

  def first_=(runner: Option[String]): Unit = bases(0) = runner
  
  def second = bases(1)

  def second_=(runner: Option[String]): Unit = bases(1) = runner

  def third = bases(2)

  def third_=(runner: Option[String]): Unit = bases(2) = runner

  def isEmpty(): Boolean = bases.forall(_.isEmpty)
  
  def update(base: Int, runner: Option[String]) {
    sanityCheck(base)
    runner match {
      case None => bases(base - 1) = None
      case Some(player) =>
        bases(base - 1) match {
          case None => bases(base - 1) = runner
          case Some(otherPlayer) =>
            if (player != otherPlayer) {
              throw new IllegalBaseOperation("Two men on base " + base + "!")
            }
        }
    }
  }

  private def sanityCheck(base: Int) {
    require(base >= 1 && base < 4)
  }

  def isOccupied(base: Int) = {
    sanityCheck(base)
    bases(base - 1).isDefined
  }

  def remove(base: Int) {
    update(base, None)
  }

  override def equals(other: Any) = {
    other match {
      case other: Bases => bases sameElements other.bases
      case _ => false
    }
  }

  override def hashCode = bases.hashCode
}

class IllegalBaseOperation(s: String) extends RuntimeException(s)

object Bases {

  def empty() = new Bases(Array.fill(3)(None))

  def apply(first: Option[String], second: Option[String], third: Option[String]) =
    new Bases(Array(first, second, third))

}