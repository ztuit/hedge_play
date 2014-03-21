package problems {
  

	object Graph {

			def RunProblems() = {

				println(fromString("[b-c, f-c, g-h, d, f-b, k-f, h-g]").toTermForm)
			}

			//from a string like "[b-c, f-c, g-h, d, f-b, k-f, h-g]"
			def fromString(graph : String) : Graph[Char, String] = {
				val nodes : Map[Char, GNode[Char]] = (((graph.tail dropRight(1) split ",\\s+" flatten) filter {(x) => x != '-'} distinct) map {(x) => (x,GNode(x)) }).toMap
				val edges : List[Edge[Char,String]] = ((graph.tail dropRight(1) split ",\\s+" filter {(x)=>x.length>1}) map { (x) => Edge[Char,String]("1", nodes(x(0)), nodes(x(2)))}).toList
				Graph(edges, nodes)
			}

			
			
	}

	case class Graph[T, +V](items : List[Edge[T,V]], nodes : Map[T, GNode[T]]){
	  
	  override def toString() : String = {
	  	items.foldLeft("")((a,x) => a + x + "\n")
	  }

	  def toTermForm : (List[T], List[(T, T, String)]) = {
	  	(nodes.keys.toList, items map {(x) => (x.start.value, x.end.value, "()")})
	  }
	}
	
	case class GNode[+T](value : T){

		override def toString() : String = {
			"N[" + value + "]"
		}
	}

	case class Edge[+T,+V](value : V, start : GNode[T], end : GNode[T], directed : Boolean = false) {
		override def toString() : String = {
			"E[" + start + "->" + value + "->" + end + "]"
		}


	}
}