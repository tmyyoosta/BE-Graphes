package org.insa.algo.utils;

import org.insa.graph.*;
import java.util.ArrayList;
import java.util.List;


public class Label implements Comparable<Label> {
	private Node currentNode;
	private Arc Father;
	private boolean mark;
	private double cost; 
	
	public Label(Node n) {
		this.currentNode = n;
		this.Father = null;
		this.mark = false;
		this.cost = Double.MAX_VALUE;
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
		this.mark = a;
	}
	
	public boolean isMark() {
		return this.mark;
	}
	
	
	/* Compare le coûts des labels entre eux */
	public int compareTo (Label autre) {
		double cost = this.getTotalCost() - autre.getTotalCost();
		if ( cost == 0 ) 
		{
			cost = (this.getTotalCost() - this.getCost()) - (autre.getTotalCost() - autre.getCost());
		}
		return (int) cost;
	}
	
	public double getTotalCost()
	{
		return this.getCost();
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