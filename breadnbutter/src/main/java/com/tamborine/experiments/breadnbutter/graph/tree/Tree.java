package com.tamborine.experiments.breadnbutter.graph.tree;

import java.util.List;

public class Tree<T extends Comparable<T>> {

	private TreeNode<T> root;
	
	public Tree(TreeNode<T> root) {
		this.root=root;
	}
	
	public  TreeNode<T> findNode(T value) {
		return this.findNode(root, value);
	}
	
	public static <T extends Comparable<T>> TreeNode<T> findNode(TreeNode node, T value) {
		int cmp = node.getValue().compareTo(value);
		if(cmp==0) return node;
		return (cmp>0) ? findNode(node.getLeft(), value) : findNode(node.getRight(), value);
	}
	
	public static <T extends Comparable<T>> TreeNode buildTree(List<T> list) {
		if(list==null || list.size()==0) return null;
		if(list.size()==1) return new TreeNode(list.get(0));
		int centerNode = (list.size()/2)-1;
		T centerValue = list.get(centerNode);
		return new TreeNode(centerValue, buildTree(list.subList(0, centerNode)), buildTree(list.subList(centerNode+1, list.size()  )));
	}
	
	
}
