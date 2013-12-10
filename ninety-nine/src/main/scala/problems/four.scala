package problems



class four extends ptrait {
	def RunProblem() : Boolean = {
		println("problem three, kth element of list");
		val k = 1;
		var list = List[Integer](1,2,3,4);
		val lastVal = list(k);
		println("Last val of list is 2? " + (lastVal==2));
		//println("last element of list = " + last + " This is correct? " + last==4);
		return true;
	}
}


