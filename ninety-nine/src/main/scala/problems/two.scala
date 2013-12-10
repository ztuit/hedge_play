package problems



class two extends ptrait {
	def RunProblem() : Boolean = {
		println("problem two, penultimate element of list");
		var list = List[Integer](1,2,3,4);
		val lastVal = list(list.length-2);
		println("Last val of list is 3? " + (lastVal==3));
		//println("last element of list = " + last + " This is correct? " + last==4);
		return true;
	}
}


