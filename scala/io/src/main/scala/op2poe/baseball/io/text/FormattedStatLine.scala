package op2poe.baseball.io.text

import op2poe.io.LineWriter

final class FormattedStatLine(private val stats: FormattedStat*) {

  def header = {
    val sb = new StringBuilder
    stats.foreach(s => sb.append(s.formatName))
    sb.toString
  }
  
  def printHeader(out: LineWriter = LineWriter.Console) {
    out.println(header)
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

  def separator(c: Char) = {
    val totalWidth = (0 /: stats)((w, s) => w + s.width)
    c.toString * totalWidth
  }
  
  def printSeparator(out: LineWriter, c: Char) {
    out.println(separator(c))
  }
  
}
