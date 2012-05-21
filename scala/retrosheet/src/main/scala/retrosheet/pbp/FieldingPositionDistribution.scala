package retrosheet.pbp

class FieldingPositionDistribution {

  private val counts = new Array[Int](9)

  def +=(p: Int) { counts(p - 1) += 1 }

  def printResult() {
    val total = 1.0 * counts.sum
    println("total: " + total.toInt)
    def ratioAsString(c: Int) = "%.2f".format(c / total)
    counts.indices.foreach(i => println(toPos(i + 1) + ": " + ratioAsString(counts(i))))
    val of = counts(6) + counts(7) + counts(8)
    println("Outfield: " + ratioAsString(of))
  }

  private def toPos(i: Int) =
    i match {
      case 1 => "P"
      case 2 => "C"
      case 3 => "1B"
      case 4 => "2B"
      case 5 => "3B"
      case 6 => "SS"
      case 7 => "LF"
      case 8 => "CF"
      case 9 => "RF"
      case _ => throw new RuntimeException("Unexpected position: " + i)
    }

}