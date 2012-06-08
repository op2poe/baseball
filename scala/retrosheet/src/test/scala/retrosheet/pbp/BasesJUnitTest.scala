package retrosheet.pbp

object BasesJUnitTest extends App {

  testEquals()
  testApply()
  testRemove()
  ensureTwoRunnersCannotBeOnSameBase()
  println("OK")
  
  def testEquals() {
    assert(Bases.empty() == Bases.empty())
    assert(Bases.empty() == Bases(None, None, None))
    assert(Bases(Some("a"), Some("b"), Some("c")) == 
      Bases(Some("a"), Some("b"), Some("c")))
  }
  
  def testApply() {
    assert(None == Bases.empty().first)
    assert(Some("a") == Bases(None, Some("a"), None).second)
    assert(Some("b") == Bases(None, None, Some("b")).third)
  }
  
  def testRemove() {
    val b = Bases(None, Some("a"), None)
    assert(Some("a") == b.second)
    b.remove(2)
    assert(None == b.second)
  }
  
  def ensureTwoRunnersCannotBeOnSameBase() {
    try {
	    val b = Bases(Some("a"), None, None)
	    b(1) = Some("b")
	    assert(false)      
    } catch {
      case ex: IllegalBaseOperation => /* expected */
      case _ => assert(false)
    }
  }
  
}