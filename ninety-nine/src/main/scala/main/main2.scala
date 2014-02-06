package main 

object P26 {
  // flatMapSublists is like list.flatMap, but instead of passing each element
  // to the function, it passes successive sublists of L.

  //if list is empty return nil
  //otherwise recurse placing the head against the mapped tail
  def flatMapSublists[A,B](ls: List[A])(f: (List[A]) => List[B]): List[B] = 
    ls match {
      case Nil => Nil
      case sublist@(_ :: tail) => f(sublist) ::: flatMapSublists(tail)(f)
    }

  def combinations[A](n: Int, ls: List[A]): List[List[A]] =
    if (n == 0) List(Nil)
    else flatMapSublists(ls) { sl => { println(sl);
      combinations(n - 1, sl.tail) map { x => {println(sl.head + "::" + x); sl.head :: x } } } 
      
    }
}

