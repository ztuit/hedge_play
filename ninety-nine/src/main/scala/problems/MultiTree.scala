package problems {


	object MultiTree {

		def RunProblems() {
			var mt = MultiTree('a', List(MultiTree('b')));
			println("Basic multi tree: " + mt)
			println("Node count " + mt.nodeCount)
			println("From string afg^^c^bd^e^^^ :" + fromString("afg^^c^bd^e^h^^^"))
			println("From string afg^^c^bd^e^^^ :" + "afg^^c^bd^e^^^".internalPathLength())
		}

		//P70c - from string like abc^^d^efg^h
		//Walk the string, creating a node and recursing with the remainder of the string
		//for the children
		//if the character is a "move up" then return from a level of recursion
		def fromString(string : String) : List[MultiTree[Char]] = {

			def fromStringInternal(s : String) : (List[MultiTree[Char]], String) = {

							if(s.head=='^') return (List(), s)


							var pair : (List[MultiTree[Char]], String) = (List(),s.tail);

							var token = pair._2.head
							var sublist = List[MultiTree[Char]]()
							while(token != '^') {

								pair = fromStringInternal(pair._2)
								token = (pair._2) match {
									case null => '^'
									case "" => '^'
									case _ => pair._2.head
								}
								sublist = sublist ::: pair._1

							}

							var tailer = (pair._2) match {
								case null => "^"
								case "" => "^"
								case _ => pair._2.tail
							}
							
							return (List(MultiTree(s.head,  sublist)),tailer)
					}
				
			
			fromStringInternal(string + "^")._1

		}

		implicit def s2Mtree(s : String) : MultiTree[Char] = {
			fromString(s)(0)
		}
	
	}

	//P70a
	case class MultiTree[T](value : T, children : List[MultiTree[T]] = List()) {
		override def toString = "M(" + value.toString + " {" + children.map(_.toString).mkString(",") + "})"
		//P70b
		def nodeCount : Int = {
			1 + children.foldLeft(0){(a,b)=>a+b.nodeCount}
		}

		//P71
		def internalPathLength(distance : Int = 0) : Int = {

			distance + children.foldLeft(0){(a,b)=>a+b.internalPathLength(distance+1)}
			
			
		}
	}

	

	



}