package org.insa.algo.shortestpath;

import org.insa.graph.Node;


public class Label {
	private Node currentNode;
	private Node nodeFather;
	private boolean mark;
	private int cost;
	private boolean dansLeTas; 
	
	public Label(Node n) {
		this.currentNode = n;
		this.nodeFather = null;
		this.mark = false;
		this.dansLeTas = false;
		
	}
	
	public Node getNode() {
		return this.currentNode;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public Node getFatherNode() {
		return this.nodeFather;
	}

	public void setMark() {
		this.mark = true;
	}
	
	/* Compare le coûts des labels entre eux */
	public int compare(Label autre) {
		int resultat;
		if(this.getCost() < autre.getCost()) {
			resultat = -1;
		}
		else if (this.getCost() == autre.getCost()) {
			resultat = 0;
		}
		else {
			resultat = 1; 
		}
		return resultat;
	}
	
}
