package retrosheet

import scala.io.Source

/**
 * Simple parser of the CSV files produced by retrosheet.
 * It makes certain assumptions, such as no field contains new lines.
 */
object CsvParser {

  def parseFile(filePath: String, f: (List[String]) => Unit) {
    def all(r: List[String]) = true
    parseFile(filePath, all, f)
  }
  
  def parseFile(filePath: String, filter: (List[String]) => Boolean, f: (List[String]) => Unit) {
    val source = Source.fromFile(filePath)
    for (line <- source.getLines) {
      val record = parseLine(line.trim)
      if (filter(record)) {
    	  f(record)
      }
    }
  }
  
  def parseLine(line: String): List[String] = {
    var record = List[String]()
    var builder = new StringBuilder
    var inQuotes = false
    for (c <- line) {
      c match {
        case '"' =>
          inQuotes = !inQuotes
        case ',' =>
          if (inQuotes) {
            builder.append(c)
          } else {
            record = builder.toString :: record
            builder = new StringBuilder
          }
        case _ =>
          builder.append(c)
      }
    }
    if (builder.length > 0 || line.endsWith(",")) {
      record = builder.toString :: record
    }
    record.reverse
  }
  
}