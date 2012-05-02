package op2poe.baseball.data.output

import op2poe.io.LineWriter

final class FormattedStatLine(private val stats: FormattedStat*) {

  def printHeader(out: LineWriter = LineWriter.Console) {
    val sb = new StringBuilder
    stats.foreach(s => sb.append(s.formatName))
    out.println(sb.toString)
  }

  def printLine(out: LineWriter, values: List[Any]) {
    val line = formatLine(values)
    out.println(line)
  }
  
  def formatLine(values: List[Any]) = {
    val sb = new StringBuilder
    for ((f, v) <- stats.zip(values)) {
      sb.append(f.formatValue(v))
    }
    sb.toString
  }

  def separator(out: LineWriter, char: Char) {
    val totalWidth = (0 /: stats)((w, s) => w + s.width)
    out.println(char.toString * totalWidth)
  }
  
}
