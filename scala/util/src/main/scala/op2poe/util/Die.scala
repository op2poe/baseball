package op2poe.util
import scala.collection.immutable.TreeMap
import scala.collection.immutable.SortedMap
import scala.collection.mutable;
import scala.util.Random

final class Die[A](val sides: SortedMap[Int, A]) {
  
  private val numberOfSides = sides.lastKey
  
  private val rnd = new Random
  
  def roll(): A = {
    val d = 1 + rnd.nextInt(numberOfSides)
    for ((weight, value) <- sides) {
      if (d <= weight) return value
    }
    return sides.last._2
  }
}

object Die {
  
  case class Side[A](val value: A, val weight: Int)
  
  def fromSeq[A](sides: Seq[Side[A]]) = {
    var sum = 0
    val sidesMap = mutable.Map[Int, A]()
    for (side <- sides) {
      sum += side.weight
      sidesMap(sum) = side.value
    }
    new Die(TreeMap(sidesMap.toArray:_*))
  }
  
  def apply[A](sides: Side[A]*): Die[A] = {
    fromSeq(sides)
  }
  
}
