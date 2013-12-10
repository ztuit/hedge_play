package problems



class one extends ptrait {
	def RunProblem() : Boolean = {
		println("problem one, last element of list");
		var list = List[Integer](1,2,3,4);
		val lastVal = list.last;
		println("Last val of list is 4? " + (lastVal==4));
		//println("last element of list = " + last + " This is correct? " + last==4);
		return true;
	}
}


