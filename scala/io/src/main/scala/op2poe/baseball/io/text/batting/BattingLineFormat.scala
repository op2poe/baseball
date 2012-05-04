package op2poe.baseball.io.text.batting

import op2poe.baseball.io.text.FormattedStatLine
import op2poe.baseball.io.text.FormattedStatLine
import op2poe.baseball.io.text.FormattedStat
import op2poe.baseball.data.batting.BattingStats

class BattingLineFormat {

    val line = new FormattedStatLine(
        FormattedStat.intLike("AB", 5),
        FormattedStat.intLike("R", 6),
        FormattedStat.intLike("H", 6),
        FormattedStat.intLike("2B", 6),
        FormattedStat.intLike("3B", 5),
        FormattedStat.intLike("HR", 5),
        FormattedStat.intLike("BB", 7),
        FormattedStat.intLike("SO", 6),
        FormattedStat.averageLike("BA", 8),
        FormattedStat.intLike("SB", 7),
        FormattedStat.averageLike("CS", 5),
        FormattedStat.averageLike("OBP", 6),
        FormattedStat.averageLike("SLG", 6),
        FormattedStat.averageLike("OPS", 6)
    )

    def header: String = line.header
    
    def separator(c: Char): String = line.separator(c)
    
    def format(stats: BattingStats): String = line.formatLine(List[Any](
        stats.atBats, 
        stats.runs,
        stats.hits,
        stats.doubles,
        stats.triples,
        stats.homeruns,
        stats.walks,
        stats.strikeouts,
        stats.stolenBases,
        stats.caughtStealing,
        stats.battingAverage,
        stats.onBaseAverage,
        stats.sluggingAverage,
        stats.ops
    ))
  
}
