package op2poe.io

import java.io.Closeable
import java.io.BufferedWriter
import java.io.FileWriter

// TODO: Support different encodings

final class FileLineWriter(filePath: String) extends LineWriter {

  private val w = new BufferedWriter(new FileWriter(filePath))

  override def println(line: String): Unit = {
    w.write(line)
    println()
  }

  override def println() {
    w.newLine()
  }
  
  def close { w.close }
  
}
