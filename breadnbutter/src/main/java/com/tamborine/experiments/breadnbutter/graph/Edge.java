package com.tamborine.experiments.breadnbutter.graph;

public class Edge<T,V extends Comparable<? super V>> {

	private Node<V> start;
	private Node<V> end;
	private final T value;
	
	public Edge(T v, Node<V> s, Node<V> e){
		this.value=v;
		this.start = s;
		this.end = e;
	}
	
	public Node<V> getStart() {
		return start;
	}
	public void setStart(Node<V> start) {
		this.start = start;
	}
	public Node<V> getEnd() {
		return end;
	}
	public void setEnd(Node<V> end) {
		this.end = end;
	}

	public T getValue() {
		return value;
	}

}
