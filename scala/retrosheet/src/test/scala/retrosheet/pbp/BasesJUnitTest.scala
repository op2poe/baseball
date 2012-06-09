package retrosheet.pbp

object BasesJUnitTest extends App {

  testIsEmpty()
  testEquals()
  testApply()
  testUpdate()
  testRemove()
  ensureTwoRunnersCannotBeOnSameBase()
  println("OK")
  
  def testIsEmpty() {
    assert(Bases.empty().isEmpty)
  }
  
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
    b.third = Some("a")
    assert(Some("a") == b.third)
  }
  
  def testUpdate() {
    val b = Bases.empty()
    b(1) = Some("a")
    assert(Some("a") == b(1))
    b(2) = Some("b")
    assert(Some("b") == b(2))
    b(3) = Some("c")
    assert(Some("c") == b(3))
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