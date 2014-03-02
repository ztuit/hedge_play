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
				println("P62 Collect the nodes at level 2 of Node('a', Node('b'), Node('c', Node('d'), Node('e'))) : " + Node('a', Node('b'), Node('c', Node('d'), Node('e'))).nodesAtLevel(2))
				var tc = Tree.completeBinaryTree3(6, "x");
				println("P63 Complete tree : " + tc)
				println(" Append address " + tc.appendAddress)
				println("                    " + tc.addValueAt(tc.appendAddress,"z"))

				var tsorted = End.addValue('g');
				tsorted=tsorted.addValue('w')
				tsorted=tsorted.addValue('d')
				tsorted=tsorted.addValue('c')
				tsorted=tsorted.addValue('a')
				println("Tree: " + tsorted)
				println("Collect in order " + tsorted.collectInOrder(1, 1)._1)

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

					def heightBalancedTreesInternal(currentHeight : Int, parentAdress : Int, value : V) : List[Tree[V]] = {
							(height-currentHeight) match {
							case 0 => List(End)
							case 1 => List(Node(parentAdress, value))
							case 2 => return List(Node(parentAdress,value), Node(value, Node((parentAdress*2),value), Node((parentAdress*2)+1,value),parentAdress), Node(value, Node((parentAdress*2),value), End,parentAdress), Node(value,End, Node((parentAdress*2)+1,value),parentAdress))
							case _ => {
								var rightSubtrees = heightBalancedTreesInternal(currentHeight+1, (parentAdress*2) + 1, value);
								var leftSubtrees =  heightBalancedTreesInternal(currentHeight+1, (parentAdress*2), value)
								leftSubtrees map {(l) => rightSubtrees map {(r) => Node(value, l, r, parentAdress)} } flatten 
							}
						}
					}
					heightBalancedTreesInternal(0,1,value)
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

			def maxNodesForHeight(height : Int) : Int = {
				Math.pow(2, height).toInt - 1
			}

			//P60 - c
			def hbalTreesWithNodes[V](nodes : Int, value: V) : List[Tree[V]] = {
				heightBalancedTrees(maxHeightForNumberOfNodes(nodes), value) filter {_.nodeCount==nodes}
			}

			//p63 - complete binary tree
			def completeBinaryTree3[V <% Ordered[V]](nodes : Int, value : V) : Tree[V] = {

					def completeBinaryTree3Internal(parentAdress : Int, value : V) : Tree[V] = {
						
							if(parentAdress>nodes) {
								return End
							} else { 
								var r = completeBinaryTree3Internal((parentAdress*2) + 1, value);
								var l =  completeBinaryTree3Internal((parentAdress*2), value)
								return Node(value, l, r, parentAdress)
							}
						}
					
					completeBinaryTree3Internal(1,value)
			}


		}

		/******************************
		 * Class Defintions
		 ******************************/

		//Tree
		sealed abstract class Tree[+T] {
			def isSymmetric() : Boolean = true
			def addValue[U >: T <% Ordered[U]](newvalue : U) : Tree[U]
			def addValueAt[U >: T <% Ordered[U]](address : Int, newvalue : U) : Tree[U]
			def isStructureMirroredBy[V](other : Tree[V]) : Boolean = true
			def nodeCount : Int = 0
			def leafCount : Int = 0
			def leafList : List[T] = List[T]()
			def internalList : List[T] = List[T]()
			def nodesAtLevel(level : Int) : List[T] = List[T]()
			def appendAddress : Int = Int.MaxValue
			def collectInOrder(height : Int, vistor : Int) : (Tree[T], Int)
			
		}

		//Node
		case class Node[+T](value: T, left: Tree[T], right: Tree[T], address : Int = 1) extends Tree[T] {
			
			override def toString = "T[" + address.toString +"](" + value.toString + " " + left.toString + " " + right.toString + ")"

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

			override def nodesAtLevel(level : Int) : List[T] = {

					if(level==1) return List(value)
					left.nodesAtLevel(level-1) ::: right.nodesAtLevel(level-1)
			}

			override def addValueAt[U >: T <% Ordered[U]](a : Int, newvalue : U) : Tree[U] = {
				
					(left,right) match {
						case (End, _) if(a==address) => Node(value, Node((address*2), value), right, address)
						case (_, End) if(a==address) => Node(value, left, Node((address*2)+1, value), address)
						case _ => Node(value, left.addValueAt(a,newvalue), right.addValueAt(a,newvalue),address)
					}
			}

			override def appendAddress : Int = {
				val thisAppend = (left,right) match {
					case (End,_) => address
					case (_,End) => address
					case _ => Int.MaxValue
				}
				Math.min(thisAppend, Math.min(left.appendAddress,right.appendAddress))
			}


			override def collectInOrder(height : Int, visitor : Int) : (Tree[T], Int) = {

					if(isLeaf) return (new PositionedNode(value, End,End,visitor,height),visitor+1)
					var leftP = left.collectInOrder(height+1, visitor);
					var rightP = right.collectInOrder(height+1, leftP._2+1)
					return (new PositionedNode(value,leftP._1, rightP._1, leftP._2, height), rightP._2)
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
		  override def addValueAt[U <% Ordered[U]](a : Int, newvalue : U) : Tree[U] = {
		  	End
		  }
		  override def collectInOrder(height : Int, visitor : Int) : (Tree[Nothing], Int) = {
		  		(End,visitor)
		  }

		}

		/**
		 * Positioning for the tree
		 **/
		class PositionedNode[+T](override val value: T, override val left: Tree[T], override val right: Tree[T], x : Int, y : Int) extends Node[T](value, left, right) {
  			override def toString = "T[" + x.toString + "," + y.toString + "](" + value.toString + " " + left.toString + " " + right.toString + ")"
  			//object PositionedNode {
				//def apply[T](value : T, x : Int, y : Int) : PositionedNode[T] = PositionedNode(value, End, End, 0,0)
			//	def unapply[T](n : PositionedNode[T]): Option[T] = Some((n.value),(n.x))
			//}
		}

		//Extractors since
		object Node {
		  def apply[T](value: T): Node[T] = Node(value, End, End)
		  def apply[T](address : Int, value : T) : Node[T] = Node(value, End, End, address)
		}




}