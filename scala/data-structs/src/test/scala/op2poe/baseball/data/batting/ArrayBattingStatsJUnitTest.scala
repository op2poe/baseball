package op2poe.baseball.data.batting

object ArrayBattingStatsJUnitTest extends App {

  testBabeRuths1921Line
  
  def testBabeRuths1921Line() {
    val stats = ArrayBattingStats(
        ab = 540,
        s = 204 - (44 + 16 + 59),
        doubles = 44,
        triples = 16,
        hr = 59,
        bb = 145,
        so = 61,
        hbp = 4,
        sh = 4)
    val delta = 0.0005
    val expectedAverage = 0.378
    assert((expectedAverage - stats.battingAverage).abs < delta)
    val expectedSlugging = 0.846
    assert((expectedSlugging - stats.sluggingAverage).abs < delta)
    val expectedOnBase = 0.512
    assert((expectedOnBase -  stats.onBaseAverage).abs < delta)
    val expectedOPS = 1.359
    assert((expectedOPS - stats.ops).abs < delta)
    val expectedPA = 693
    assert(expectedPA == stats.plateAppearances)
  }
  
}