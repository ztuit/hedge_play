package problems {

		object BinaryTrees {
			def RunProblems() {
				var t =  Tree.balancedTrees('x', 5)
				println(" Tree: " + t(1));
				println(" Is isSymetric : " + t(1).isSymmetric)
				var t1 = End.addValue('a');
				println("Tree: " + t1)
				var t2 = t1.addValue('c')
				println(t2 + " Is symetric : " + t2.isSymmetric)

				println("From list symetric: "  + Tree.fromList(List(5, 3, 18, 1, 4, 12, 21) reverse).isSymmetric)
				//println(" symmetric balenced trees : " + Tree.symmetricBalancedTrees('x', 5))
				val index=7
				//println("Height balanced tree:   ")
				//Tree.heightBalancedTrees(3,'x') foreach {(x)=> println(x);}
				//println("Solution height tree:   ")
				//Tree.hbalTrees(3,'x') foreach {(x)=> println(x);}
				//println("For node set of size 4: " + Tree.hbalTreesWithNodes(4, "x"))
				//println("Min nodes for height balanced tree of height 3: " + Tree.minHeightBalanced(3))
				println("P60 All height balanced trees for node set of size 4: ")
				Tree.hbalTreesWithNodes(4,"x") foreach {(x)=>println(x);}
				println("P61 Leaf count of Node('x', Node('x'), End): " + Node('x', Node('x'), End).leafCount);
				println("P61A Collect the leafs of Node('a', Node('b'), Node('c', Node('d'), Node('e'))): " + Node('a', Node('b'), Node('c', Node('d'), Node('e'))).leafList);
				println("P62 Collect the internal nodes of Node('a', Node('b'), Node('c', Node('d'), Node('e'))) : " + Node('a', Node('b'), Node('c', Node('d'), Node('e'))).internalList)
			}



		}


		/****************************
		 * Namespace singleton
		 ****************************/
		object Tree {
			//for an leaf method will return 1 node, or two if called with 2 to give
			//each permutation
			def balancedTrees( value : Char, nodeCount : Int) : List[Node[Char]] = {
				(nodeCount) match {
					case 1 => List(Node(value))
					case 2 => List(Node(value, balancedTrees(value, 1)(0), End), Node(value, End, balancedTrees(value, 1)(0)))
					case _ => {
						val newNodeCount = (nodeCount-1)/2; 
						var leftNodes = balancedTrees(value, newNodeCount) 
						var rightNodes = balancedTrees(value, newNodeCount+((nodeCount-1)%2))
						leftNodes map {(l) => rightNodes map {(r) => Node(value,l,r)}} flatten
					}
				}
			}

			def fromList(values : List[Int]) : Tree[Int] = {
				(values) match {
					case h :: t if(values.length>1) => Tree.fromList(values.tail).addValue(values.head)
					case h :: t => Node(values.head)
				}	
			}

			def symmetricBalancedTrees(value : Char, nodeCount : Int) : List[Node[Char]] = {
					balancedTrees(value, nodeCount) filter (_.isSymmetric)
			}

			//P59 - generate all balanced trees for a given height
			def heightBalancedTrees[V](height : Int, value : V) : List[Tree[V]] = {

					(height) match {
					case 0 => List(End)
					case 1 => List(Node(value))
					case 2 => return List(Node(value), Node(value, Node(value), Node(value)), Node(value, Node(value), End), Node(value,End, Node(value)))
					case _ => {
						var rightSubtrees = heightBalancedTrees(height-1, value);
						var leftSubtrees = heightBalancedTrees(height-1, value)
						leftSubtrees map {(l) => rightSubtrees map {(r) => Node(value, l, r)} } flatten 
					}
				}
			}
  			

			//p60 - a Min number of nodes for height
			def minHeightBalanced(height: Int) : Int = {

				def recurseMHB(height: Int) : Int = {
					(height) match {
						case 0 => 0
						case 1 => 1
						case _ =>  1 + recurseMHB(height-1) + recurseMHB(height-1)
					}
				}
				recurseMHB(height-1) + 1;
			}

			//p60 - b Inverse of the equation MaxNodes = 2^h -1
			def maxHeightForNumberOfNodes(nodes : Int) : Int = {
					(Math.log(nodes+1)/Math.log(2)).toInt + 1
			}

			//P60 - c
			def hbalTreesWithNodes[V](nodes : Int, value: V) : List[Tree[V]] = {
				heightBalancedTrees(maxHeightForNumberOfNodes(nodes), value) filter {_.nodeCount==nodes}
			}

		}

		/******************************
		 * Class Defintions
		 ******************************/

		//Tree
		sealed abstract class Tree[+T] {
			def isSymmetric() : Boolean = true
			def addValue[U >: T <% Ordered[U]](newvalue : U) : Tree[U]
			def isStructureMirroredBy[V](other : Tree[V]) : Boolean = true
			def nodeCount : Int = 0
			def leafCount : Int = 0
			def leafList : List[T] = List[T]()
			def internalList : List[T] = List[T]()
		}

		//Node
		case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {
			
			override def toString = "T(" + value.toString + " " + left.toString + " " + right.toString + ")"

			override def isSymmetric() : Boolean = {
			  	//Is this node symmetric and are the child nodes
			  	left.isStructureMirroredBy(right) //&& left.isSymmetric && right.isSymmetric	
			  }

			override def isStructureMirroredBy[V](other : Tree[V]) : Boolean = {

					(other) match {
						case o : Node[V] => left.isStructureMirroredBy(o.right) && right.isStructureMirroredBy(o.left)
						case _ => false
					}
			  }

		  	def isLeaf() : Boolean = {
		  		(left, right) match {
		  			case (End, End ) => true
		  			case _ => false
		  		}
		  	}

			override def addValue[U >: T <% Ordered[U]](newvalue : U) : Tree[U] = {
				if(newvalue<value) 
					return Node(value, left.addValue(newvalue), right);
				else 
					return Node(value, left, right.addValue(newvalue));

		  	}

		 	override def nodeCount() : Int = {
		  		left.nodeCount + right.nodeCount + 1
			}		

			override def leafCount() : Int = {
				(isLeaf) match {
					case (true) => 1
					case _ => left.leafCount + right.leafCount
				}
			}

			override def leafList() : List[T] = {
				(isLeaf) match {
					case (true) => List(value)
					case _ => left.leafList ::: right.leafList
				}
			}

			override def internalList() : List[T] = {
				(isLeaf) match {
					case (false) => List(value) ::: left.internalList ::: right.internalList
					case _ => left.internalList ::: right.internalList
				}
			}
		}

		//End class
		case object End extends Tree[Nothing] {
		  override def toString = "."
		  override def addValue[U <% Ordered[U]](newvalue : U) : Tree[U] = {
		  		Node(newvalue)
		  }
		  override def isStructureMirroredBy[V](other : Tree[V]) : Boolean = {
		  	other == End
		  }

		}

		//Implicit
		object Node {
		  def apply[T](value: T): Node[T] = Node(value, End, End)
		}
}