package lahman.batting

import scala.collection.mutable;
import scala.io.Source

object PlayerFileReader {

  def readFile(path: String): Map[(String, Int), List[String]] = {
    val map = mutable.Map[(String, Int), List[String]]()
    val source = Source.fromFile(path)
    for (line <- source.getLines if !line.isEmpty; if !line.startsWith("playerID")) {
      val parts = line.split(",")
      val playerId = parts(0)
      val year = parts(1).toInt
      val key = (playerId, year)
      var lines:List[String] = if (map.contains(key)) map(key) else Nil
      lines = line :: lines
      map(key) = lines
    }
    map.toMap
  }

}