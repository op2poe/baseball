package op2poe.baseball.data.output

import java.text.DecimalFormat

abstract class FormattedStat(private val name: String, 
							 val width: Int, 
							 private val align: HorizontalAlign = HorizontalAlign.Right) {

  final def formatName = formatString(name)

  def formatValue(value: AnyRef)

  protected final def formatString(s: String) =
    "%" + align.code + width + "s".format(name)
  
}

object FormattedStat {
  
  class StringLike(name: String, 
      			   width: Int, 
      			   align: HorizontalAlign = HorizontalAlign.Right) 
      			   	extends FormattedStat(name, width, align) {
    
    def formatValue(value: AnyRef) =
      "%" + align.code + width + "s".format(value.toString)
  }

  class IntLike(name: String,
		  		width: Int,
		  		align: HorizontalAlign = HorizontalAlign.Right)
		  			extends FormattedStat(name, width, align) {
    
    def formatValue(value: AnyRef) =
      "%" + align.code + width + "d".format(value)
  }
  
  class AverageLike(name: String, width: Int) extends FormattedStat(name, width) {
    
    private val pct = new DecimalFormat(".000")
    
    def formatValue(value: AnyRef) = formatString(pct.format(value))
  }

  class EraLike(name: String, width: Int) extends FormattedStat(name, width) {

    private val pct = new DecimalFormat("0.00")
    
    def formatValue(value: AnyRef) = formatString(pct.format(value))
  }
  
}

