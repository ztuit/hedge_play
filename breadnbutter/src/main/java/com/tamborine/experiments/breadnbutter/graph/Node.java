package com.tamborine.experiments.breadnbutter.graph;

import java.util.ArrayList;
import java.util.List;

public class Node<T extends Comparable<? super T>> {

	private final T value;
	private List<Edge> edges  = new ArrayList<Edge>();

	public Node(T t) {
		this.value=t;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public T getValue() {
		return value;
	}
}
