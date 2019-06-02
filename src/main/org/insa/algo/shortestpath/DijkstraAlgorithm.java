package org.insa.algo.shortestpath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.algo.utils.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.*;
import org.insa.algo.shortestpath.ShortestPathSolution;


public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	protected int NbNodeMark;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.NbNodeMark = 0;
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        	
        boolean fin = false;
        boolean possible = true;
        
        Graph graph = data.getGraph();

        List<Node> NodesGraph = graph.getNodes();
        ArrayList<Label> labels = Label.ReturnNodes(NodesGraph);

        Node origin = data.getOrigin();
        Node dest  = data.getDestination();
        

        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        tas.insert(labels.get(origin.getId()));		
        labels.get(origin.getId()).setCost(0);
        labels.get(origin.getId()).setCurrent(origin);	
        
        //labels.get(origin.getId()).setCurrent(origin);

        notifyOriginProcessed(data.getOrigin());
        
        if (origin == dest)
        {
        	possible = false;
        }

        //int nbIteration = 0;
        while (!tas.isEmpty() && fin == false && possible == true) 
        {
        			
        	//System.out.println("Nombre d'itération : "+nbIteration);
			//nbIteration ++;
			Label label1 = tas.deleteMin();
			if( label1.getCost() == Double.MAX_VALUE)
			{
				possible = false;
				
			}
			else if (label1.getCurrent() == dest) 
			{
				fin = true;
			}
			else
			{
				
				label1.setMark(true);
				NbNodeMark++;
				//System.out.println("Cout des labels marques :"+label1.getCost());
				//System.out.println("Taille du tas (debut): "+bh.size());
				
			
				for (int i = 0; i < label1.getCurrent().getNumberOfSuccessors(); i++) 
				{	
					Arc arc = label1.getCurrent().getSuccessors().get(i);
					Node node = arc.getDestination();
					Label label2 = labels.get(node.getId());
					label2.setCurrent(node);
					Label label1bis = labels.get(arc.getOrigin().getId()); 

					if (label2.isMark() == false) 
					{
						//System.out.println("Label1bis cout : " + label1bis.getCost());
						double newCost = label1bis.getCost() + data.getCost(arc);
															
						if (label2.getCost() > newCost) 
						{
							
                        	notifyNodeReached(arc.getDestination());
						
							
							label2.setCost(newCost);
							label2.setFather(arc);
							tas.insert(label2);
						}
					} 
				}
			}
			//System.out.println("Taille du tas (fin): "+tas.size());
		}
        		
        		
        		
         if (!fin) 
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
                Label labelOrigin = labels.get(nodeOrigin.getId());
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
            	solution = null;
            	
            }
         }

         return solution;
    }
    
    
    public int returnNodeMark()
    {
    	return this.NbNodeMark ;
    	
    }
    
}
       