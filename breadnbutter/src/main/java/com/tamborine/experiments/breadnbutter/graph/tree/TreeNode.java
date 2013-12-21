package com.tamborine.experiments.breadnbutter.graph.tree;

import java.util.Arrays;
import java.util.List;

import com.tamborine.experiments.breadnbutter.graph.Edge;
import com.tamborine.experiments.breadnbutter.graph.Node;

public class TreeNode<T extends Comparable<? super T>> extends Node<T> {

	public TreeNode(T t) {
		super(t);
	}
	
	public TreeNode(T t, Node<T> left, Node<T> right) {
		super(t);
		Edge<String,T> leftEdge = new Edge<String,T>("left", this, left);
		Edge<String,T> rightEdge = new Edge<String,T>("right", this, right);
		Edge[] edges = {leftEdge, rightEdge};
		this.setEdges(Arrays.asList(edges));
	}
	
	public String toString() {
		List l = this.getEdges();
		Edge<String,T> leftEdge = null;
		Edge<String,T> rightEdge = null; 
		if(l!=null && l.size()==2) {
			leftEdge =  (Edge<String,T>)l.get(0);
			rightEdge =  (Edge<String,T>)l.get(1);
		}
		StringBuilder b = new StringBuilder();
		b.append("[ node id=" + this.getValue() + ", children={");
		if(leftEdge!=null) {
			b.append(leftEdge.getValue() + ": ");
			if(leftEdge.getEnd()!=null) {
				b.append(leftEdge.getEnd().toString() );
			}
			b.append(",");
		}
		if(rightEdge!=null) {
			b.append(rightEdge.getValue() + ": ");
			if(rightEdge.getEnd()!=null) {
				b.append(rightEdge.getEnd().toString() );
			}
		}
		b.append(" }]");
		 
		return b.substring(0);							
	}
	
	public TreeNode<T> getLeft() {
		if(this.getEdges()!=null && this.getEdges().size()>0)
			return (TreeNode<T>) this.getEdges().get(0).getEnd();
		else
			return null;
	}
	
	public TreeNode<T> getRight() {
		if(this.getEdges()!=null && this.getEdges().size()>0)
			return (TreeNode<T>) this.getEdges().get(1).getEnd();
		else
			return null;
	}

}
