package op2poe.io

import java.io.BufferedWriter
import java.io.FileWriter

trait LineWriter {

  def println(line: String): Unit
  
  def println(): Unit
  
}


object LineWriter {
  
  val Console = new LineWriter {
    
    override def println(line: String) {
      scala.Console.println(line)
    }
    
    override def println() {
      scala.Console.println
    }
  }
  
  val None = new LineWriter {
    
    override def println(line: String) {/**/}
    
    override def println() {/**/}
  }
  
}
