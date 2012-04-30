package retrosheet.gamelogs

object InningRunDistributionJUnitTest extends App {

  val r0 = new InningRunDistribution(List.range(0, 20).toArray)
  val s0 = r0.toString
  val r1 = InningRunDistribution.fromString(s0)
  assert(r0 == r1)
  println(r1.distribution.mkString(","))
  
}