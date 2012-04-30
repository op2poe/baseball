package op2poe.baseball.data

final class Date(val year: Int, val month: Int, val day: Int) extends Ordered[Date] {

  validate()
  
  private def validate() {
    require(year >= 1800 && year < 2200)
    require(month >= 1 && month <= 12)
    require(day <= daysInMonth)
  }

  def nextDay: Date = {
    var y = year
    var m = month
    var d = day + 1
    if (d > daysInMonth) {
      d = 1
      m += 1
      if (m > 12) {
        m = 1
        y += 1
      }
    }
    new Date(y, m, d)
  }

  private def daysInMonth =
    month match {
      case 1 | 3 | 5 | 7 | 8 | 10 | 12 => 31
      case 4 | 6 | 9 | 11 => 30
      case 2 => if (isLeapYear) 29 else 28
      case _ => throw new Exception("Invalid month")
    }

  private def isLeapYear = ((year % 4) == 0) && ((year % 100) != 0 || ((year % 400) == 0))

  def compare(that: Date): Int = {
    var r = year.compare(that.year)
    if (r == 0) r = month.compare(that.month)
    if (r == 0) r = day.compare(that.day)
    r
  }
  
  override def equals(other: Any): Boolean =
    other match {
      case other: Date =>
        year == other.year && month == other.month && day == other.day
      case _ => false
    }

  override def hashCode: Int =
    41 * (
      41 * (
        41 + year) + month) + day

  def toISO8601 = concat("-")

  def toYYYYMMDD = concat("")

  private def concat(sep: String) =
    List(year, toTwoDigitString(month), toTwoDigitString(day)).mkString(sep)

  private def toTwoDigitString(i: Int) = "" + (if (i < 10) "0" + i else i)

  def dayOfWeek = {
    // Using the Gaussian algorithm:
    // http://en.wikipedia.org/wiki/Determination_of_the_day_of_the_week#Gaussian_algorithm
    val Y = if (month <= 2) year - 1 else year
    val d = day
    val m = ((month + 9) % 12) + 1
    val y = year % 100
    val c = year / 100
    (d + (2.6 * m - 0.2) + y + (y / 4) + (c / 4) - 2 * c) % 7
  }
  
}

object Date {

  def apply(y: Int, m: Int, d: Int) = new Date(y, m, d)

  def yyyymmdd(s: String) = {
    val y = s.slice(0, 4).toInt
    val m = s.slice(4, 6).toInt
    val d = s.slice(6, 8).toInt
    new Date(y, m, d)
  }

  def iso8601(s: String) = {
    val parts = s.split("-")
    val y = parts(0).toInt
    val m = parts(1).toInt
    val d = parts(2).toInt
    new Date(y, m, d)
  }

}
