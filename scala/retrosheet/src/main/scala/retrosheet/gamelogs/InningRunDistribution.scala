package retrosheet.gamelogs

import op2poe.baseball.data.LineScore
import java.io.BufferedWriter
import java.io.FileWriter
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

final class InningRunDistribution(private val counts: Array[Int]) {

  def this() = this(new Array[Int](20))
  
  def update(ls: LineScore) {
    ls.foreach(counts(_) += 1)
  }
  
  def collectHighScoringInnings(limit: Int) = {
    val slice = counts.slice(limit, counts.length)
    for {(c, r) <- slice.view.zipWithIndex if c > 0} yield (r + limit, c)
  }

  def frequencies = {
    var total = counts.sum
    for {(c, r) <- counts.view.zipWithIndex} yield (r, c, (1.0 * c) / total)
  }
  
  def distribution = {
    var sum = 0.0
    frequencies.map(f => {
      sum += f._3
      sum
    })
  }
  
  def rollDice(): Int = rollDice(new Random)

  def rollDice(rnd: Random): Int = {
    val v = rnd.nextDouble
    var r = 0
    for (d <- distribution) {
      if (v < d) return r
      r += 1
    }
    20
  }
  
  override def toString = counts.mkString(",")
  
  override def equals(other: Any): Boolean =
    other match {
    case that: InningRunDistribution =>
      counts.sameElements(that.counts)
    case _ => false
  }

  // FIXME: Not consistent with equals
  override def hashCode: Int = counts.hashCode
  
}


object InningRunDistribution {
  
  def fromString(s: String) = new InningRunDistribution(s.split(",").map(_.toInt))
  
}
