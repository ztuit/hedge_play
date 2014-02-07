package problems


//A series of solutions for working with lists.
class casematch extends ptrait {
	def RunProblem() : Boolean = {

		val l = 7 to 1001 toList;
		println("\nFind the head of the list expecting value of 7: ");
		println(matchHead(l))
		println("\nFind the tail of the list expecting value of 1001: ");
		println(matchTail(l))
		println("\nFind the penultimate value expecting 1000: ")
		println(matchPenultimate(l))
		println("\nFind the number of elements in the list, expecting 995: ")
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
		println("\n\nEliminate consecutive duplicates:")
		val lcd = List('a', 'a', 'a', 'a', 'b', 'c', 'c', 'a', 'a', 'd', 'e', 'e', 'e', 'e')
		println(removeConsequtiveDupes(lcd))
		println("\n\nPack consecutive duplicates functional:")
		println(packConsequtiveDupesFunctional(lcd))
		println("\n\nRun length encode:")
		println(runLengthEncode(lcd))
		println("\n\nModified run length encoding.")
		println(modifiedRunLengthEncode(lcd))
		println("\n\nRun length decode:")
		println(runLengthDecode(runLengthEncode(lcd)))
		println("\n\ndirectRunLengthEncode ")
		println(directRunLengthEncode(lcd))
		println("\n\n List duplicate: ")
		val toduplicate = List(1,2,3,4)
		println(duplicateList(toduplicate, 3))
		println("\n\nDrop every nth element")
		println(dropEveryNthFromList(lcd,5))
		println("\n\nSplit into two parts as a tuple:")
		println(lcd.splitAt(4))
		println((lcd.take(4),lcd.drop(4)))
		var sliceUp = List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)
		println("\n\nSlice of list from 3 to 9 easy way:")
		val startSlice=3
		val endSlice=9
		println(sliceUp.slice(startSlice,endSlice))
		println("Hard way:")
		println(listSlice(sliceUp, startSlice,endSlice));
		println("Functional:")
		println((sliceUp.drop(startSlice)).take(endSlice-startSlice));
		println("\nRotate list left:")
		var toRotate = List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)
		println(rotateLeft(3,toRotate))
		println(rotateLeft(-2,toRotate))
		println(toRotate.drop(3) ::: toRotate.take(endSlice-startSlice))
		println("\nRemove kth element (kth=3):")
		println(toRotate)
		println(removeKthElement(3,toRotate))
		println("\nInsert element at:")
		var toInsertInto = List('a, 'b, 'c, 'd);
		println(insertAtRecursive(1,"new",0,toInsertInto))
		println("\nCreate list of integer range 4-9")
		println(createRangeListRecursive(4,9))
		println("\nExtract random list:")
		var toExtract = List('a, 'b, 'c, 'd, 'f, 'g, 'h)
		println(randomSelect(3,0,toExtract))
		println("\nLotto Draw, result:")
		println(lotto(6,49))
		println("\nRandom Permute:")
		var randomPermuteList = List('a, 'b, 'c, 'd, 'e, 'f)
		println("Using list: " + randomPermuteList)
		println(radomPermute(randomPermuteList))
		println("\nN choose K ")
		var nckl = List('a, 'b, 'c, 'd,'e, 'f)
		println("Using list: " + nckl)
		println(nChooseK(nckl, 3));
		println("\n Combinations of disjoint sublists ")
		var djsl = List('a, 'b, 'c)
		println("Using list: " + djsl)
		println(disjointSubLists(List(2,1), djsl))	
		println("Sort list be length of sublist.")
		var listOfLists = List(List(1,2,3), List(1,2), List(1,2,3),List(1,2,3,4,5), List(1), List(1,2,3,4))
		println("Using list " + listOfLists)
		println(sortByF(listOfLists,{(x,y) => x.head.length>y.head.length}))
		println(" By list frequency")
		println(sortByFrequencyOfItems(listOfLists))
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
		case Nil => List()
	}

	def directRunLengthEncode(ls : List[Any]) : List[Any] = {
		if(ls.isEmpty || ls == Nil) List()
		else {
			val (sp, rest) = ls span { _ == ls.head}
			(sp.length, sp(0) ) :: directRunLengthEncode(rest)
		}
	}

	def duplicateList(ls : List[Any], times : Int) : List[Any] =  {
			ls flatMap {  List.make(times,_)}	
	}

	def dropEveryNthFromList(ls : List[Any], nth : Int) : List[Any] = {
		(ls zipWithIndex) map (x => if((x._2%nth)!=0) x._1 else null) filter(x => x!=null)
	}

	def listSlice(ls : List[Any], start : Int, end : Int) : List[Any] = {

		def listSliceRecursive(ls : List[Any], index : Int) : List[Any] = (index, ls) match {
			case (i, h :: t) if(i >= start && i < end) =>   h :: listSliceRecursive(t, i+1)
			case (i, h :: t) if(i < start || i >= end) => listSliceRecursive(t, i+1)
			case (i, _ :: t) =>	Nil
			case (i,Nil) => Nil
		}
		listSliceRecursive(ls, 0)
	}

	def rotateLeft(positions : Int, ls : List[Any]) : List[Any] = positions match {
		case (positions) if(positions >=0) => listSlice(ls, positions, ls.length) ::: listSlice(ls, 0, positions)
		case (positions) if(positions< 0) => listSlice(ls, (ls.length+positions), ls.length) ::: listSlice(ls, 0, (ls.length+positions))
	}

	def removeKthElement( kth : Int,ls : List[Any]) : (List[Any], Any) = {
		((ls zipWithIndex) map (x => if(x._2!=kth) x._1 else null) filter(x => x!=null), ls(kth))
	}

	def insertAtRecursive( at : Int, value: Any, index : Int, ls : List[Any]) : List[Any] = (at, index, ls) match {
		case (at, index, h::t) if(index==at) => value :: ls
		case (at, index, h::t) => h :: insertAtRecursive(at, value, index+1, ls.tail)
	}

	def createRangeListRecursive(from: Int, end: Int) : List[Int] = (from,end) match {
		case (from,end) if(from<end) => from :: createRangeListRecursive(from+1, end)
		case (_,_) => List(from)
	}

	def randomSelect(number : Int, count : Int,  ls : List[Any]) : List[Any] = (number, count, ls ) match {
		case(n,c,ls) if(c==n) => List()
		case(n,c,ls) if(c<n) => val (rest, e) = removeKthElement(scala.util.Random.nextInt(ls.length),ls) ; e :: randomSelect(n,c+1,rest) 
		case (_,_,_) => Nil
	}

	def lotto(number : Int, size : Int) : List[Any] = {
		randomSelect(number, 0, 0 to size toList)	
	}

	def radomPermute(ls:List[Any]) : List[Any] = {
		randomSelect(ls.length, 0, ls)
	}

	def nChooseK(ls:List[Any], k:Int) : List[List[Any]] = {
		return recursiveNChooseK(ls, List(), k);
	}

	def recursiveNChooseK(subList:List[Any], newPermutation : List[Any], k:Int) : List[List[Any]] = {
		var toReturn = List[List[Any]]();
		if(newPermutation.length==k) {
			toReturn ::= newPermutation
			return toReturn;
		}
		var i = 1;
		for(a <- subList) {
			var currentState = List(a)
			if(newPermutation.length>0) {
				currentState = a :: newPermutation.slice(0,newPermutation.length);
			}
			toReturn = toReturn ++ recursiveNChooseK(subList.slice(i,subList.length),currentState,k);
			i += 1;
		} 
		toReturn;
	}

	def disjointSubLists(groups:List[Int], values : List[Any]) : List[List[List[Any]]] = {
		
		//for the head of groups, generate all permutations p for i from values
		var Ks = nChooseK(values,groups.head);
		if(groups.length==1) return List(Ks);
		//for each generated permutation p, remove all items of p from values, and recurse with the tail 
		//of groups
		var toReturn = List[List[List[Any]]]();
		Ks foreach {(x) => disjointSubLists(groups.tail, values diff (x)) foreach {(y)=> toReturn = toReturn ++ List(x::y)} }
		toReturn;
	}

	def sortByF(values : List[List[Any]], f : (List[List[Any]], List[List[Any]]) => Boolean) : List[List[Any]] = {
		if(values.length<2) return values;
		var subLists = values splitAt (values.length / 2)
		mergeLists(sortByF(subLists._1,f),  sortByF(subLists._2,f), f);
	}

	def mergeLists(leftList : List[List[Any]], rightList : List[List[Any]], f : (List[List[Any]], List[List[Any]]) => Boolean) : List[List[Any]] = {
		
		(leftList, rightList) match {
			case(Nil,_) => rightList;
			case(_,Nil) => leftList;
			case(_,_) if(f(leftList,rightList)) => leftList.head :: mergeLists(leftList.tail, rightList,f)
			case(_,_) if(!f(leftList,rightList)) => rightList.head :: mergeLists(rightList.tail, leftList,f)

		}
	}

	def sortByFrequencyOfItems(values : List[List[Any]]) : List[List[Any]] = {
		var valueMap = (values groupBy { (x) => x.length}) 
		//println(valueMap)
		sortByF(values, {(x,y)=>  (((valueMap get x.head.length) get).length)>(((valueMap get y.head.length) get).length)});
		//sortByF(values, {(x,y)=> println(x.length + ">" + y.length); (x.length>y.length)});
	}

}


