package op2poe.baseball.game

import op2poe.baseball.data.game._

sealed abstract class RunnersOn {

  def advance(outcome: Outcome, outs: Int): (RunnersOn, Int)
  
  def leftOnBase: Int
  
}

case object NoBaseRunners extends RunnersOn {

  val leftOnBase = 0
  
  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (RunnerOnFirst, 0)
      case Double => (RunnerOnSecond, 0)
      case Triple => (RunnerOnThird, 0)
      case Homerun => (NoBaseRunners, 1)
      case Strikeout => (this, 0)
      case Walk => (RunnerOnFirst, 0)
      case Out => (this, 0)
    }  
}

case object RunnerOnFirst extends RunnersOn {

  val leftOnBase = 1
  
  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (RunnersOnFirstAndSecond, 0)
      case Double => (RunnersOnSecondAndThird, 0)
      case Triple => (RunnerOnThird, 1)
      case Homerun => (NoBaseRunners, 2)
      case Strikeout => (this, 0)
      case Walk => (RunnersOnFirstAndSecond, 0)
      case Out => (RunnerOnSecond, 0)
    }
}

case object RunnerOnSecond extends RunnersOn {

  val leftOnBase = 1
  
  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (RunnersOnFirstAndThird, 0)
      case Double => (RunnerOnSecond, 1)
      case Triple => (RunnerOnThird, 1)
      case Homerun => (NoBaseRunners, 2)
      case Strikeout => (this, 0)
      case Walk => (RunnersOnFirstAndSecond, 0)
      case Out => (RunnerOnThird, 0)
    }
}

case object RunnerOnThird extends RunnersOn {

  val leftOnBase = 1
  
  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (RunnerOnFirst, 1)
      case Double => (RunnerOnSecond, 1)
      case Triple => (RunnerOnThird, 1)
      case Homerun => (NoBaseRunners, 2)
      case Strikeout => (this, 0)
      case Walk => (RunnersOnFirstAndThird, 0)
      case Out => if (outs < 2) (NoBaseRunners, 1) else (this, 0)
    }
}

case object RunnersOnFirstAndSecond extends RunnersOn {

  val leftOnBase = 2
  
  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (BasesLoaded, 0)
      case Double => (RunnersOnSecondAndThird, 1)
      case Triple => (RunnerOnThird, 2)
      case Homerun => (NoBaseRunners, 3)
      case Strikeout => (this, 0)
      case Walk => (BasesLoaded, 0)
      case Out => (RunnersOnSecondAndThird, 0)
    }
}

case object RunnersOnFirstAndThird extends RunnersOn {
  
  val leftOnBase = 2

  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (RunnersOnFirstAndSecond, 1)
      case Double => (RunnersOnSecondAndThird, 1)
      case Triple => (RunnerOnThird, 2)
      case Homerun => (NoBaseRunners, 3)
      case Strikeout => (this, 0)
      case Walk => (BasesLoaded, 0)
      case Out => if (outs < 2) (RunnerOnSecond, 1) else (this, 0)
    }
}

case object RunnersOnSecondAndThird extends RunnersOn {
  
  val leftOnBase = 2
  
  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (RunnersOnFirstAndThird, 1)
      case Double => (RunnerOnSecond, 2)
      case Triple => (RunnerOnThird, 2)
      case Homerun => (NoBaseRunners, 3)
      case Strikeout => (this, 0)
      case Walk => (BasesLoaded, 0)
      case Out => if (outs < 2) (RunnerOnThird, 1) else (this, 0)
    }
}

case object BasesLoaded extends RunnersOn {
  
  val leftOnBase = 3
  
  def advance(outcome: Outcome, outs: Int) =
    outcome match {
      case Single => (BasesLoaded, 1)
      case Double => (RunnersOnSecondAndThird, 2)
      case Triple => (RunnerOnThird, 3)
      case Homerun => (NoBaseRunners, 4)
      case Strikeout => (this, 0)
      case Walk => (BasesLoaded, 1)
      case Out => if (outs < 2) (RunnersOnSecondAndThird, 1) else (this, 0)
    }
}
