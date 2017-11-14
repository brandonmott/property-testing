import brandonmott.testing._

val seed = Seed(36854775807L) //> Long.MaxValue

val res = for {
  bool <- Seed.boolean
  int <- Seed.int
  long <- Seed.long
  pos <- Seed.nonNegativeInt    
  double <- Seed.double
} yield (bool, int, long, pos, double)

res.run(seed).value 