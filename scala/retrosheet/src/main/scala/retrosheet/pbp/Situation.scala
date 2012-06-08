package retrosheet.pbp

class Situation private (private val batter:String,
						 private val bases: Array[Option[String]],
						 private var outs: Int,
						 private var runsScored: Int) {

  
}

object Situation {
  
  def startOfInning(batter: String) = {
    new Situation(batter, Array.fill(3)(None), 0, 0)
  }
  
}