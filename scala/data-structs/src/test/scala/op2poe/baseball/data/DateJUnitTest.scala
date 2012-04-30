package op2poe.baseball.data

object DateJUnitTest extends App {

  testNextDay
  testNextDayFromEndOfMonth
  testLeapYears
  testFromStringConversion
  testToStringConversions
  testOrder
  
  // TODO: Test for valid dates.
  
  def testNextDay {
    val today = Date(2012, 04, 27)
    val tomorrow = today.nextDay
    assert(Date(2012, 04, 28) == tomorrow)
    val valborg = Date(2012, 04, 30)
    assert(Date(2012, 5, 1) == valborg.nextDay)
    val dayBeforeLeapDay = Date(2012, 02, 28)
    val leapDay = dayBeforeLeapDay.nextDay
    assert(Date(2012, 2, 29) == leapDay)
    val dayAfterLeapDay = leapDay.nextDay
    assert(Date(2012, 3, 1) == dayAfterLeapDay)
    val feb28 = Date(2011, 2, 28)
    assert(Date(2011, 3, 1) == feb28.nextDay)
    val newYearsEve = Date(2012, 12, 31)
    val newYearsDay = newYearsEve.nextDay
    assert(Date(2013, 1, 1) == newYearsDay)
  }
  
  def testNextDayFromEndOfMonth {
    assert(Date(2013, 2, 1) == Date(2013, 1, 31).nextDay)
    assert(Date(2013, 3, 1) == Date(2013, 2, 28).nextDay)
    assert(Date(2013, 4, 1) == Date(2013, 3, 31).nextDay)
    assert(Date(2013, 5, 1) == Date(2013, 4, 30).nextDay)
    assert(Date(2013, 6, 1) == Date(2013, 5, 31).nextDay)
    assert(Date(2013, 7, 1) == Date(2013, 6, 30).nextDay)
    assert(Date(2013, 8, 1) == Date(2013, 7, 31).nextDay)
    assert(Date(2013, 9, 1) == Date(2013, 8, 31).nextDay)
    assert(Date(2013, 10, 1) == Date(2013, 9, 30).nextDay)
    assert(Date(2013, 11, 1) == Date(2013, 10, 31).nextDay)
    assert(Date(2013, 12, 1) == Date(2013, 11, 30).nextDay)
    assert(Date(2014, 1, 1) == Date(2013, 12, 31).nextDay)
  }

  def testLeapYears {
    assert(Date(1896, 2, 29) == Date(1896, 2, 28).nextDay)
    assert(Date(1900, 3, 1) == Date(1900, 2, 28).nextDay)
    assert(Date(2000, 2, 29) == Date(2000, 2, 28).nextDay)
  }
  
  def testFromStringConversion {
    assert(Date(2012, 4, 27) == Date.yyyymmdd("20120427"))
    assert(Date(2012, 1, 1) == Date.yyyymmdd("20120101"))
    assert(Date(2012, 4, 27) == Date.iso8601("2012-04-27"))
    assert(Date(2012, 1, 1) == Date.iso8601("2012-01-01"))
  }
  
  def testToStringConversions {
    assert("20120427" == Date(2012, 4, 27).toYYYYMMDD)
    assert("2012-04-02" == Date(2012, 4, 2).toISO8601)
  }

  def testDayOfWeek {
    assert(5 == Date(2012, 4, 27).dayOfWeek) // This code was written on a Friday
    assert(5 == Date(1973, 2, 2).dayOfWeek)  // I was born on a Friday
    assert(5 == Date(2007, 11, 2).dayOfWeek) // Ludvig was born on a Friday
    assert(4 == Date(1915, 5, 6).dayOfWeek)  // Babe Ruth's first home run came on a Thursday
    assert(6 == Date(1876, 4, 22).dayOfWeek) // First NL game was on a Saturday (Boston beat Philadelphia, 6-5)
  }

  def testOrder {
    val today = Date(2012, 4, 27)
    val tomorrow = Date(2012, 4, 28)
    assert(today < tomorrow)
    assert(tomorrow > today)
    val lastYear = Date(2011, 4, 27)
    assert(today > lastYear)
    val nextYear = Date(2013, 4, 27)
    assert(today < nextYear)
  }
  
}

