package problems {

	object LogicAndCodes {

		def RunProblems() {

			var s99bTrue : S99Boolean = true;
			var s99bFalse : S99Boolean = false;
			println("Test AND true false = " + s99bTrue.and(false));
			println("Test AND true true = " + s99bTrue.and(true));
			println("Test NAND true true = " + s99bTrue.nand(true));
			println("Test NAND false true = " + s99bFalse.nand(true));
			println("Test OR true false = " + s99bTrue.or(false))
			println("Test OR false false = " + s99bFalse.or(false))
			println("Test NOR true false = " + s99bTrue.nor(false))
			println("Test NOR false false = " + s99bFalse.nor(false))
			println("Test NOT false  = " + s99bFalse.not())
			println("Test equal false true = " + s99bFalse.equ(true))
			truthTable({(a,b) => S99Boolean.b2s99b(a) xor b});
			println(grayCode(3));
			var huffmanList = List(("a", 45), ("b", 13), ("c", 12), ("d", 16), ("e", 9), ("f", 5));
			println("Huffman coding with : " + huffmanList)
			println(huffmanCoding(huffmanList));
		}

		def truthTable(f : (Boolean, Boolean) => Boolean)  = {
				println("A     B     result")
    		for {a <- List(true, false);
         		b <- List(true, false)} {
     				printf("%-5s %-5s %-5s\n", a, b, f(a, b))
    		}
		}

		def grayCode(bitlength : Int) : List[String] = {
			val values = List[String]("a","b", "c");
			permutations(bitlength, values)
		}

		def permutations(bitlength : Int, values : List[String]) : List[String] = {
			//To build permutation of length bitlength
			//values will hold the first result bitlength = 1
			//to form a one of bit length 2 each element of values will be combined with each element of values to form a new list
			//the can be done with nested iteration but ideally would be good to do using
			//recursion
			if(bitlength==1) return values;
			//bitlengh == 2

			combine(values, permutations(bitlength-1, values ))//this could be an aribrary length biut initially will be 2
		}


		def combine(values : List[String], pnMinus1 : List[String]) : List[String] = {

				if(values.length==0) return List();

				(pnMinus1 map {(x) => values.head+x}) ::: combine(values.tail, pnMinus1)
		}

		def huffmanCoding(values : List[(String,Int)]) : List[(String,String)] = {
				//order the list based on probabilites
				
				//remove the two litems of least probability
				var mapped = values map ( (x) => (x._1, x._2, (("",0,Nil),("",0,Nil))));
				huffmanEncode(recursiveBuildHuffmanTree (mapped), "");
		}

		def recursiveBuildHuffmanTree(values : List[(String, Int, (Any,Any))]) :  (String, Int, (Any,Any)) = {
			if(values.length==1) return values(0);
			var sortedValues = values sortWith(_._2>_._2)
			var split = values.splitAt(2);
			var toCombine = split._1;
			var newNode = ("+", toCombine(0)._2+toCombine(1)._2, (toCombine(0), toCombine(1)));
			recursiveBuildHuffmanTree(  newNode :: split._2 )
		}

		def huffmanEncode(root : (String, Int, (Any,Any) ), encoding : String  ) : List[(String,String)] = {

			var left = 
			(root._3._1) match {
				case  left : (String, Int, (Any, Any)) => left;
			}
			var right = (root._3._2) match {
				case  right : (String, Int, (Any, Any)) => right;
			}
			(root) match  {

					case ("+", _, _) => huffmanEncode(left, encoding + "1") ::: huffmanEncode(right, encoding + "0")
					case _ => List((root._1, encoding)); 
			}
		}



	}
		object S99Boolean {
			implicit def b2s99b(b:Boolean) : S99Boolean = new S99Boolean(b)
		}

		class S99Boolean(val left : Boolean) {

			import S99Boolean._

		def and( right : Boolean) : Boolean = {

			(left, right)  match  {
				case (true, true) => true
				case _ => false
			}
		}

		def nand(right : Boolean) : Boolean = {
			!and(right)
		}

		def or(right : Boolean) : Boolean = {
			(left, right) match  {
				case (true, _) => true
				case (_,true) => true
				case _ => false
			}
		}

  		def not() : Boolean = left match {
    		case true  => false
    		case false => true
  		}

  		def equ(b: Boolean): Boolean = and(b) or (not and b.not)

		def nor(right : Boolean) : Boolean = {
			!or(right)
		}

		def xor(right : Boolean) : Boolean = {
			left != right
		}

		
	}


}
