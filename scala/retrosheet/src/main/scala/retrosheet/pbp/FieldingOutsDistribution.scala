package retrosheet.pbp

import scala.util.matching.Regex

/**
 * Counts all plays that ended up with a fielding out, or a would-be fielding out
 * that was voided due to an error, and calculates the distribution of the fielding
 * positions that made the initial play.
 */
object FieldingOutsDistribution extends App {

  class Processor extends EventProcessor {

    val counts = new Array[Int](9)
    val error = new Regex("^E(\\d).*")
    val fc = new Regex("^FC(\\d).*");
    val out = new Regex("^(\\d).*")

    def processEvent(event: String) {
      val p = checkEvent(event)
      p match {
        case Some(i) => counts(i - 1) += 1
        case None => // not an out
      }
    }
    
    private def checkEvent(line: String): Option[Int] = {
      line match {
        case out(pos) => toInt(pos)
        case fc(pos) => toInt(pos)
        case error(pos) => toInt(pos)
        case _ => None
      }
    }
    
    private def toInt(pos: String) = Some(pos.toInt)
    
    def printResult() {
      val total = 1.0 * counts.sum
      println("total: " + total.toInt)
      def ratioAsString(c: Int) = "%.2f".format(c / total)
      counts.indices.foreach(i => println(toPos(i + 1) + ": " + ratioAsString(counts(i))))
      val of = counts(6) + counts(7) + counts(8)
      println("Outfield: " + ratioAsString(of))
    }
    
    private def toPos(i: Int) = {
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
  }

  val filePath = "C:\\z\\coding\\bb\\retrosheet\\play-by-play\\1921\\1921NYA.EVA"
  val processor = new Processor()
  EventFileReader.readAllEvents(filePath, processor)
  processor.printResult()
  
}
