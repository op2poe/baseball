package retrosheet.gamelogs

import scala.collection.mutable.Map
import scala.io.Source
import java.io.BufferedWriter
import java.io.FileWriter
import op2poe.baseball.data.LineScore
import retrosheet.CsvParser

object InningDistributions extends App {

  for (year <- 1901 to 1901) {
      //println(year)
	  val counts = new InningRunDistribution
	  val file = "C:\\z\\coding\\baseball-2\\retrosheet\\gamelogs\\gl" + year + ".txt"
	  CsvParser.parseFile(file, record => {
	    processRecord(record, counts)
	  })
	  val outPath = "C:\\z\\coding\\baseball-2\\retrosheet\\derived\\runs_in_inning_dist\\" + year + ".txt"
	  writeDistrubitionsToFile(counts, outPath)
	  //reportHighScoringInnings(year, counts)
  }

  def processRecord(record: List[String], counts: InningRunDistribution) {
    process(record(19), counts)
    process(record(20), counts)
  }
  
  def process(innings: String, counts: InningRunDistribution) {
    if (innings.isEmpty) return
    val lineScore = LineScore.fromString(innings)
    counts.update(lineScore)
  }

  def writeDistrubitionsToFile(counts: InningRunDistribution, outPath: String) = {
    val out = new BufferedWriter(new FileWriter(outPath))
    for((r, c, f) <- counts.frequencies) {
      val line = "%2d: %6d  %.7f".format(r, c, f)
      out.write(line)
      out.newLine
    }
	out.close  
  }
  
  def reportHighScoringInnings(year: Int, counts: InningRunDistribution) {
    for ((r, c) <- counts.collectHighScoringInnings(14)) {
      println(r + " runs: " + c + " time(s) in " + year)
    }
  }
  
}