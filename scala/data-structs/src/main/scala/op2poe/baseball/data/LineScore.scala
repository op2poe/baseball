package op2poe.baseball.data

import scala.collection.Iterable
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayBuffer

final class LineScore(private val line: Array[Int]) extends Iterable[Int] {

  require(line.length > 0)
  
  def iterator: Iterator[Int] = 
    if (line.last == -1) line.dropRight(1).iterator else line.iterator
  
  def toPrettyString: String = {
      val slices = for (i <- List.range(0, line.length, 3)) yield line.slice(i, i + 3)
      slices.map(s => s.map(toString).mkString(" ")).mkString("  ")
  }
    
  override def toString = line.map(toString).mkString
    
  private def toString(r: Int) = {
    if (r < 0) "x"
    else if (r < 10) r.toString
    else "(" + r + ")"
  }

  override def equals(other: Any): Boolean = 
    other match {
      case other: LineScore => line equals other.line
      case _ => false
    }
  
  override def hashCode: Int = line.hashCode
  
}

object LineScore {
  
  def fromString(line: String): LineScore = {
    val b = ArrayBuffer.empty[Int]
    var doubleDigit = ""
    var inDoubleDigit = false
    for (c <- line) {
      if (c == '(') {
      	inDoubleDigit = true
      } else if (c == ')') {
        b += doubleDigit.toInt
        doubleDigit = ""
        inDoubleDigit = false
      } else if (c != 'x') {
        if (inDoubleDigit) doubleDigit += c
        else {
          b += String.valueOf(c).toInt
        }
      }
    }
    new LineScore(b.toArray)
  }
  
}