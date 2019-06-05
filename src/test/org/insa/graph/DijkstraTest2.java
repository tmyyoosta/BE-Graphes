package org.insa.graph;

import static org.junit.Assert.assertEquals;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.shortestpath.BellmanFordAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;
import org.insa.algo.shortestpath.ShortestPathSolution;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;



public class DijkstraTest2 {

	
	// type : 0 = temps, 1 = distance
	public void testScenario(String mapName, int type, int origine, int destination) throws Exception 
	{

		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();

		if (type!=0 && type!=1) 
		{
			System.out.println("Argument invalide");
		} 
		else 
		{
			if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) // On est hors du graphe. / Sommets inexistants
			{ 
				System.out.println("ERREUR : Paramètres invalides ");
				
			} 
			else 
			{
				ArcInspector arcInspectorDijkstra;
				
				if (type == 0) { //Temps
					System.out.println("Mode : Temps");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
				} 
				else 
				{
					System.out.println("Mode : Distance");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
				}
				
				
				//System.out.println("Chemin de la carte : "+mapName);
				System.out.println("Origine : " + origine);
				System.out.println("Destination : " + destination);
				
				if(origine==destination) {
					System.out.println("Origine et Destination identiques");
					System.out.println("Coût solution: 0");
					
				} 
				else 
				{			
					ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
					BellmanFordAlgorithm BF = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);
					
					// Recuperation des solutions de Bellman et Dijkstra pour comparer 
					ShortestPathSolution solution = D.run();
					ShortestPathSolution solutionBF = BF.run();
	
					
					if (solution.getPath() == null) 
					{
						assertEquals(solutionBF.getPath(), solution.getPath());
						System.out.println("PAS DE SOLUTION");
						System.out.println("(infini) ");
					}
					// Un plus court chemin trouve 
					else 
					{
						double costSolution;
						double costExpected;
						if(type == 0) 
						{ //Temps
							//Calcul du cout de la solution 
							costSolution = solution.getPath().getMinimumTravelTime();
							costExpected = solutionBF.getPath().getMinimumTravelTime();
						} 
						else 
						{
							costSolution = solution.getPath().getLength();
							costExpected = solutionBF.getPath().getLength();
						}
						assertEquals(costExpected, costSolution, 0.001);
						System.out.println("Coût solution: " + costSolution);
					}
				}
			}
		}
		System.out.println();
		System.out.println();
	}
}
