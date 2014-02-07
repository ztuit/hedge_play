package problems {
  class S99Int(val start: Int) {
    import S99Int._

    def isPrime() : Boolean = { if(start % 2 == 0) false; recurseIsPrime(3); }

    def recurseIsPrime(next : Int) : Boolean = {
    	if(next == start) return true;
    	if(start % next == 0 ) return false;
    	recurseIsPrime(next+2)
    }

    def gcd(small : Int, big : Int) : Int = {
    	if(small==0) return big;
    	gcd(big % small, small)
    }

    def isCoprimeTo(number2 : Int) : Boolean = {
    	if(start < number2) return (1==gcd(start, number2));
    	(1==gcd(number2,start))
    }

  }

  object S99Int {
    implicit def int2S99Int(i: Int): S99Int = new S99Int(i)
  }


}