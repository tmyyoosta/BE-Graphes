package org.insa.algo.utils;
import java.util.ArrayList;
import java.util.List;

import org.insa.algo.AbstractInputData.Mode;
import org.insa.algo.shortestpath.*;

import org.insa.graph.*;

public class LabelStar extends Label {
	
	private double coutVD ;
	
	public LabelStar(Node n ) 
	{
		super(n);
		this.coutVD = Double.MAX_VALUE;
	}
	public double getCostVD()
	{
		return this.coutVD;
	}
	
	public void setCostVD(double coutDest, ShortestPathData data)
	{
		if ( data.getMode() == Mode.LENGTH)
		{
			this.coutVD = Point.distance(this.getCurrent().getPoint(),data.getDestination().getPoint()); 
		}
		else
		{
			int vitesse = Math.max(data.getMaximumSpeed(),data.getGraph().getGraphInformation().getMaximumSpeed());
			this.coutVD = Point.distance(this.getCurrent().getPoint(),data.getDestination().getPoint())/(vitesse/3.6);
		}
	}
	
	public double getTotalCost() {
		return this.getCost() + this.getCostVD();
	}
	
	
	static public ArrayList<LabelStar> ReturnNodesAstar(List<Node> nodes )
	{
		ArrayList<LabelStar> ListNodes = new ArrayList<LabelStar>();
		int i;
		for(i=0;i<nodes.size();i++)
		{
			ListNodes.add(new LabelStar(nodes.get(i)));
		}
		return ListNodes;
	}

}
