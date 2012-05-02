package op2poe.baseball.data.output

import java.text.DecimalFormat

abstract class FormattedStat(private val name: String, val width: Int, private val align: HorizontalAlign) {

  final def formatName = formatString(name)

  def formatValue(value: AnyRef)

  protected final def formatString(s: String) =
    "%" + align.code + width + "s".format(name)

}

object FormattedStat {

  def stringLike(name: String, width: Int, align: HorizontalAlign = HorizontalAlign.Right) =
    new FormattedStat(name, width, align) {
      def formatValue(value: AnyRef) =
        "%" + align.code + width + "s".format(value.toString)
    }

  def intLike(name: String, width: Int, align: HorizontalAlign = HorizontalAlign.Right) =
    new FormattedStat(name, width, align) {
      def formatValue(value: AnyRef) =
        "%" + align.code + width + "d".format(value)
    }

  def averageLike(name: String, width: Int) = {
    val pct = new DecimalFormat(".000")
    new FormattedStat(name, width, HorizontalAlign.Right) {
      def formatValue(value: AnyRef) = formatString(pct.format(value))
    }
  }

  def eraLike(name: String, width: Int) = {
    val pct = new DecimalFormat("0.00")
    new FormattedStat(name, width, HorizontalAlign.Right) {
      def formatValue(value: AnyRef) = formatString(pct.format(value))
    }
  }

}
