package org.insa.algo.utils;

import org.insa.graph.*;
import java.util.ArrayList;
import java.util.List;


public class Label implements Comparable<Label> {
	private Node currentNode;
	private Arc Father;
	private boolean mark;
	protected double cost;
	//private boolean dansLeTas; 
	
	public Label(Node n) {
		this.currentNode = n;
		this.Father = null;
		this.mark = false;
		//this.dansLeTas = false;
		this.cost = Double.POSITIVE_INFINITY;
	}
	
	public Node getCurrent() {
		return this.currentNode;
	}
	
	public void setCurrent(Node current) {
		this.currentNode = current;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost (double cost) {
		this.cost = cost;
	}
	
	public Arc getFather() {
		return this.Father;
	}
	
	public void setFather (Arc father) {
		this.Father = father;
	}

	public void setMark(boolean a) {
		this.mark = true;
	}
	
	public boolean isMark() {
		return this.mark;
	}
	
	
	/* Compare le coûts des labels entre eux */
	public int compareTo(Label autre) {
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
	
	
	
	
	static public ArrayList<Label> ReturnNodes(List<Node> nodes)
	{
		ArrayList<Label> ListNodes = new ArrayList<Label>();
		int i;
		for(i=0;i<nodes.size();i++)
		{
			ListNodes.add(new Label(nodes.get(i)));
		}
		return ListNodes;
	}
	
	
}
