package problems {
  class S99Int(val start: Int) {
    import S99Int._
 
    def isPrime(v : Int ) : Boolean =  { var vInt = new S99Int(v); vInt.isPrime();}
    def isPrime() : Boolean = { if(start % 2 == 0) return false; recurseIsPrime(3); }

    def recurseIsPrime(next : Int) : Boolean = {
    	if(next == start) return true;
    	if(start % next == 0 ) return false;
    	recurseIsPrime(next+2)
    }

    def gcd(small : Int, big : Int) : Int = {
    	if(small==0) {
            return big;
        }
        else
    	   return gcd(big % small, small);
    }

    def findGcd(other : Int) : Int = {
        gcd(start, other)
    }

    def isCoprimeTo(number2 : Int) : Boolean = {
    	if(start < number2) 
            return (1==gcd(start, number2));
        else {
    	   return (1==gcd(number2,start))
        }
    }

    def totients() : List[Int] = {
    	return (1 to start) filter { (x) => start.isCoprimeTo(x)}  toList
    }

    def totient() : Int = {
        totients.length;
    }



    def findSmallestPrimeFactor() : Int = {

    	if(isPrime()) return start;
    	
    	primes takeWhile { _ <= start } foreach {(x : Int)=>if((start%x)==0) return x.toInt}

    	return 1;
    }

    def findPrimeFactors() : List[Int] = {

		    	

    	var smallest = findSmallestPrimeFactor();

    	if(smallest==1 || smallest==start) return List(start)

    	smallest :: S99Int.int2S99Int(start/smallest).findPrimeFactors()
    }

    def countFrequencyOfPrimes() : List[(Int, Int)] = {
        (findPrimeFactors() groupBy { (x) => x} ).toList flatMap {(x) => List((x._2.length, x._1))}

    }

    def phi() : Int = {
            (countFrequencyOfPrimes()).foldLeft(1) { (a : Int, b : (Int,Int)) => (a*((b._2-1)*Math.pow(b._2,b._1-1))).toInt}
    }

    def rangeOfPrimes(start : Int, end : Int) : List[Int] = {
        val startIndex = primes indexOf start
        val endIndex = primes indexOf end
        primes slice(startIndex, endIndex+1) toList
    }

    def goldbach() : (Int, Int) = {
        if(start%2!=0) return (0,0)
        buildPairs(primes takeWhile {_<start} toList) foreach { (x)=>if(x._1+x._2 == start) return (x._1,x._2)}
        return (0,0)
    }

    def buildPairs(input : List[Int]) : List[(Int,Int)] = {

            (input) match {
                case h::Nil => List()
                case Nil => List()
                case _ => (input.tail map {(x :Int) => (input.head, x)} ) ::: buildPairs(input.tail)
            }
    }

    def goldbachList( r: List[Int] ) : List[(Int,Int)] = {
                if(r.length==1) return List(r.head.goldbach);
                r.head.goldbach :: goldbachList(r.tail)
    }

    def printGoldbachList(r : Range ) {
        goldbachList((r filter {(x)=>x%2==0}) toList) foreach {(x) => println(x._1+x._2 + "=" + x._1 + "+" + x._2);}
    }

    def printGoldbachListLimited(r : Range, limit : Int ) {
        goldbachList((r filter {(x)=>x%2==0}) toList) filter {(x) => x._1>50 && x._2 > 50 } foreach {(x) => println(x._1+x._2 + "=" + x._1 + "+" + x._2);}
    }

  }



  object S99Int {
    implicit def int2S99Int(i: Int): S99Int = new S99Int(i)
    val primes = ( Stream.from(3,2) filter { (x) => x.isPrime(); } );
  }

 

}