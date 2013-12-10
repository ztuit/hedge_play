package problems



class foldLeft extends ptrait {
	def RunProblem() : Boolean = {
		println("experiment, folding Left");
		val l = 1 to 4 toList;
		println(l.foldLeft("")((b,a) => "X"));
		return true;
	}
}


