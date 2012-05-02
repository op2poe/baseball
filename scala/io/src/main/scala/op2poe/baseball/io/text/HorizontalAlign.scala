package op2poe.baseball.io.text

sealed trait HorizontalAlign { def code: String }

object HorizontalAlign {
  case object Left extends HorizontalAlign { val code = "-" }
  case object Right extends HorizontalAlign { val code = "" }
}
