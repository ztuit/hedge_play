package problems {
	
	object ArithmeticExercises {

		def RunProblems() : Boolean = {


			println("Running arithmetic problems")
			var sInt = S99Int.int2S99Int(9);
			println("Is prime ?")
			println(sInt.isPrime())
			println("Find GCD 36 and 63");
			println(sInt.gcd(36,63))
			println("Are coprime 53 and 64")
			var sInt36 = S99Int.int2S99Int(35);
			println(sInt.isCoprimeTo(64))
			println("Totient to 10:");
			var sInt10 = S99Int.int2S99Int(10);
			println(sInt10.totients)
			println("Is 5 coprime to 10")
			println(sInt10.isCoprimeTo(5))
			println("Find prime factors")
			var sInt315 = S99Int.int2S99Int(315);
			println(sInt315.findPrimeFactors())
			println("Frequency of primes: ")
			println(sInt315.countFrequencyOfPrimes())
			println("Phi improved for 10:")
			println(sInt10.phi())
			println("Phi Methods compared using 10090:")
			var sInt10090 = S99Int.int2S99Int(10090)
			var now = System.nanoTime
			println(sInt10090.totient)
			var micros = (System.nanoTime - now) / 1000
			println("Method 1 took: " + micros)
			now = System.nanoTime
			println(sInt10090.phi)
			micros = (System.nanoTime - now) / 1000
			println("Methood 2 took: " + micros)
			println("range of primes 7 to 31")
			println(sInt10090.rangeOfPrimes(7,31))
			println("Goldbach Conjecture for 28:")
			println(S99Int.int2S99Int(28).goldbach)
			println("Goldbach range for 27 28:")
			println(S99Int.int2S99Int(28).printGoldbachList(26 to 28))
			println("Goldbach limited to 50:")
			println(S99Int.int2S99Int(0).printGoldbachListLimited(1 to 2000, 50))
			return true;

		}

	}
}