package lahman

import scala.collection.mutable;
import scala.io.Source

object TeamFileReader {

  def readFile(path: String): mutable.Map[(Int, String), String] = {
    val map = mutable.Map[(Int, String), String]()
    val source = Source.fromFile(path)
    for (line <- source.getLines if !line.isEmpty; if !line.startsWith("yearID")) {
      val parts = line.split(",")
      val year = parts(0).toInt
      val teamId = parts(2)
      map((year, teamId)) = line
    }
    map
  }
  
}
