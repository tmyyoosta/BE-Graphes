


package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.*;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm 
{

    public AStarAlgorithm(ShortestPathData data) 
    {
        super(data);
        this.NbNodeMark = 0;
    }

    public ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        	
        boolean fin = false;
        boolean possible = true;
        
        Graph graph = data.getGraph();
        Node origin = data.getOrigin();
        Node dest  = data.getDestination();

        List<Node> NodesGraph = graph.getNodes();
        ArrayList<LabelStar> labels = LabelStar.ReturnNodesAstar(NodesGraph);

       
        
        //int nbNodesUnmarqued = graph.size();

        BinaryHeap<Label> tas = new BinaryHeap<Label>();

        tas.insert(labels.get(origin.getId()));		
        labels.get(origin.getId()).setCost(0);
        labels.get(origin.getId()).setCostVD(origin.getPoint().distanceTo(dest.getPoint()), data);	
        labels.get(origin.getId()).setCurrent(origin);
        
        notifyOriginProcessed(data.getOrigin());
        
        if (origin == dest )
        {
        	possible = false;
        }

        //int nbIteration = 0;
        while (!tas.isEmpty() && fin == false && possible == true) 
        {
			//System.out.println("Nombre d'itération : "+nbIteration);
			//nbIteration ++;
			Label label1 = tas.deleteMin();
			if ( label1.getCost() == Double.POSITIVE_INFINITY)
			{
				possible = false;
				
			}
			if(label1.getCurrent() == dest) 
			{
				fin = true;
				notifyDestinationReached(dest);
			}
			else
			{
				
				label1.setMark(true);
				NbNodeMark++;
				//System.out.println("Cout des labels marques :"+label1.getCost());
				//System.out.println("Taille du tas (debut): "+tas.size());
				//nbNodesUnmarqued--;
				notifyNodeMarked(label1.getCurrent());
				
				for (int i = 0; i < label1.getCurrent().getNumberOfSuccessors(); i++) 
				{	
					Arc arc = label1.getCurrent().getSuccessors().get(i);
					Node node = arc.getDestination();
					LabelStar label2 = labels.get(node.getId());
					if (label2.getCost() == Double.MAX_VALUE) {
						label2.setCostVD(node.getPoint().distanceTo(dest.getPoint()),data);
					}
					
					label2.setCurrent(node);
					LabelStar label1bis = labels.get(arc.getOrigin().getId());
	
					if (label2.isMark() == false) 
					{
					
						double newCost = label1bis.getCost() + data.getCost(arc);
															
						if (label2.getCost() > newCost) 
						{
						
	                    	label2.setCost(newCost);
							label2.setFather(arc);
							tas.insert(label2);
						} 
					}
				}
			//System.out.println("Taille du tas (fin): "+tas.size());
			}
        		
		}
		if (labels.get(data.getDestination().getId()) == null) 
		{
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} 
		else if (possible == false) 
		{
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} 
		else if (fin == false && tas.isEmpty()) 
		{
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} 
		else 
		{

            ArrayList<Arc> arcs = new ArrayList<>();
            
            Arc arc = labels.get(data.getDestination().getId()).getFather();
            
            while (arc != null) 
            {
                arcs.add(arc);
                Node nodeOrigin = arc.getOrigin();
                LabelStar labelOrigin = labels.get(nodeOrigin.getId());
                arc = labelOrigin.getFather();
            }

            Collections.reverse(arcs);
            //System.out.println("Nombre d'arc pour le plus court chemin : "+ arcs.size());
            Path path = new Path(graph, arcs);
            //System.out.println("Path travel time = " + path.getMinimumTravelTime()/60.0 + " min");
            //System.out.println("Path length = " + path.getLength()/1000.0 + " km");
            if(path.isValid()) 
            {
            	//System.out.println("Path is valid");
                solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
            }
            else 
            {
            	//System.out.println("Path not valid");
            	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
            	
            }
        }

	return solution;
}

    
}
