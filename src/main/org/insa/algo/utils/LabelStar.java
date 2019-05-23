package org.insa.algo.utils;

import org.insa.graph.*;


import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.graph.Node;

public class LabelStar extends Label implements Comparable<Label>{
	
		private double estimatedCost;
		
		
	public LabelStar(Node n, ShortestPathData data)
	{
		super(n);
		
		estimatedCost = Point.distance(n.getPoint(), data.getDestination().getPoint());
	}
	
	public double getCost()
	{
		return this.cost + this.estimatedCost;
	}


}
