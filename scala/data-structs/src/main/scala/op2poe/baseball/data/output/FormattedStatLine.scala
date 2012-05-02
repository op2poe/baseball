package op2poe.baseball.data.output

import op2poe.io.LineWriter

final class FormattedStatLine(private val stats: FormattedStat*) {

  def printHeader(out: LineWriter = LineWriter.Console) {
    val sb = new StringBuilder
    stats.foreach(s => sb.append(s.formatName))
    out.println(sb.toString)
  }

  def printLine(out: LineWriter, values: AnyRef*) {
    val sb = new StringBuilder
    for ((f, v) <- stats.zip(values)) {
      sb.append(f.formatValue(v))
    }
    sb.toString
  }

  def separator(out: LineWriter, char: String) {
    val totalWidth = (0 /: stats)((w, s) => w + s.width)
    out.println(char * totalWidth)
  }
  
}
