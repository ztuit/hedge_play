package problems



class casematch extends ptrait {
	def RunProblem() : Boolean = {

		val l = 1 to 1000 toList;
		println("\nFind the head of the list expecting value of 7: ");
		println(matchHead(l))
		println("\nFind the tail of the list expecting value of 16: ");
		println(matchTail(l))
		println("\nFind the penultimate value expecting 15: ")
		println(matchPenultimate(l))
		println("\nFind the number of elements in the list, expecting 10: ")
		println(listLength(l))
		println("\nList reverse, current head:")
		println(matchHead(l))
		println("\nCurrent Tail:")
		println(matchTail(l))
		val reversedList = reverseFoldLeft(l);
		println("\nPost reverse, head:")
		println(matchHead(reversedList))
		println("\nPost reversed, tail")
		println(matchTail(reversedList))
		println("\nIs paladromic ")
		val paladromicList = List(1,2,3,2,1)
		println(paladromicList==reverse(paladromicList))
		println("\nFlatten a list")
		val nestedList = List(List(1,2,3),4,5,List(6,7,8,9),10)
		println(flattern(nestedList))
		println("\nEliminate consecutive duplicates:")
		val lcd = List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')
		println(removeConsequtiveDupes(lcd))
		println("\nPack consecutive duplicates functional:")
		println(packConsequtiveDupesFunctional(lcd))
		println("\nRun length encode:")
		println(runLengthEncode(lcd))
		println("\nModified run length encoding.")
		println(modifiedRunLengthEncode(lcd))
		println("\nRun length decode:")
		println(runLengthDecode(runLengthEncode(lcd)))
		return true;
	}

	def matchHead(ls : List[Any])  : Any = ls match { 
		case h :: t => "value is " + h
		case Nil => Nil
	}	

	def matchTail(ls : List[Any])  : Any = ls match { 
		case h :: Nil => "value is " + h
		case _ :: tail => matchTail(tail)
	}

	def matchPenultimate(ls: List[Any]) : Any = ls match {
		
		case h :: t => if((h::t).length == 2) h else matchPenultimate(t)
		case Nil => Nil
	}

	def listLength(ls : List[Any]) : Any = {

			def innerListLength(count : Int, curList : List[Any]) : Any = curList match {
				case h :: t => innerListLength(count+1, t)
				case Nil => count
			}

			innerListLength(0,ls)
		}

	
	def reverse(ls : List[Any]) : List[Any] = ls match {
			case h :: t => reverse(t) ::: List(h)
			case Nil => Nil
		}

	def reverseFoldLeft(ls: List[Any]) : List[Any] = {
			ls.foldLeft(List[Any]()) {(x,b) => List(b) ::: x}
		}

	def flattern(ls: List[Any]) : List[Any] = ls match {
			case Nil => Nil 
			case h :: t => ( h match {
				case l : List[Any] => flattern(l) 
				case i => List(i)
				}) ::: flattern(t)
		}

	def removeConsequtiveDupes(ls : List[Any]) : List[Any] = {
		def innerRemoveDupes(innerls : List[Any], previous : Any) : List[Any] = innerls match {
				case h :: t => if(h==previous) return innerRemoveDupes(t, h); else return (h :: innerRemoveDupes(t,h))
				case Nil => Nil
		}
		return innerRemoveDupes(ls, "")
	}

	def packConsequtiveDupesFunctional(ls : List[Any]) : List[List[Any]] = {
		if(ls.isEmpty) List(List())
		else {
			val (sp, rest) = ls span { _ == ls.head}
			if(rest == Nil) 
				List(sp) 
			else
				sp :: packConsequtiveDupesFunctional(rest)
		}
	}

	def runLengthEncode(ls : List[Any]) : List[(Int,Any)] = {
		packConsequtiveDupesFunctional(ls) map { x=> (x.length,x.head) }
	}

	def modifiedRunLengthEncode(ls : List[Any]) : List[Any] = {
		if(ls.isEmpty || ls == Nil) List()
		else {
			val (sp, rest) = ls span { _ == ls.head}
			if(sp.length>1) {
					(sp.length, sp(0) ) :: modifiedRunLengthEncode(rest)
				 } else { 
				 			sp(0) :: modifiedRunLengthEncode(rest)
				 		}
		}
	}

	def runLengthDecode(ls : List[(Int,Any)]) : List[Any] = ls match {
		case h :: t => List.fill(h._1)(h._2) ::: runLengthDecode(t)
		case h :: Nil => List.fill(h._1)(h._2)
		case Nil => List()
	}
}


