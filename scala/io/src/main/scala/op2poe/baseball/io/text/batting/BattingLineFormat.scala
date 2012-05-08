package op2poe.baseball.io.text.batting

import op2poe.baseball.io.text.FormattedStatLine
import op2poe.baseball.io.text.FormattedStatLine
import op2poe.baseball.io.text.FormattedStat
import op2poe.baseball.data.batting.BattingStats
import op2poe.baseball.io.text.HorizontalAlign

class BattingLineFormat(private var nameWidth: Int = 20) {

    val line = new FormattedStatLine(
        FormattedStat.stringLike("", nameWidth, HorizontalAlign.Left),
        FormattedStat.intLike("G", 5),
        FormattedStat.intLike("AB", 6),
        FormattedStat.intLike("R", 6),
        FormattedStat.intLike("H", 6),
        FormattedStat.intLike("2B", 6),
        FormattedStat.intLike("3B", 5),
        FormattedStat.intLike("HR", 5),
        FormattedStat.intLike("RBI", 6),
        FormattedStat.intLike("BB", 7),
        FormattedStat.intLike("SO", 6),
        FormattedStat.intLike("SB", 6),
        FormattedStat.intLike("CS", 5),
        FormattedStat.averageLike("BA", 7),
        FormattedStat.averageLike("OBP", 6),
        FormattedStat.averageLike("SLG", 6),
        FormattedStat.averageLike("OPS", 6)
    )

    def header: String = line.header
    
    def separator(c: Char): String = line.separator(c)
    
    def format(name: String, stats: BattingStats): String = line.formatLine(List[Any](
        name,
        stats.games,
        stats.atBats, 
        stats.runs,
        stats.hits,
        stats.doubles,
        stats.triples,
        stats.homeruns,
        stats.runsBattedIn,
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
