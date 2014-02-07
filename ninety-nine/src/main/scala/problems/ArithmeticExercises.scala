package problems {
	
	object ArithmeticExercises {

		def RunProblems() = {

			println("Running arithmetic problems")
			var sInt = S99Int.int2S99Int(9);
			println("Is prime ?")
			println(sInt.isPrime())
			println("Find GCD 36 and 63");
			println(sInt.gcd(36,63))
			println("Are coprime 53 and 64")
			var sInt36 = S99Int.int2S99Int(35);
			println(sInt.isCoprimeTo(64))
		}

	}
}