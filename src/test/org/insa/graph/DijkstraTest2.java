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
		//public void testScenario(String mapName, int typeEvaluation, Node origine, Node destination) throws Exception {

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
					System.out.println("Cout solution: 0");
					
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
						System.out.println("Cout solution: " + costSolution);
					}
				}
			}
		}
		System.out.println();
		System.out.println();
	}
}

/****************************************************************************************************************************
	@Test
	// Verifie que le temps du chemin le plus rapide est inferieur au temps du chemin le plus court
	// Et verifie que la distance du chemin le plus rapide est superieur a la distance du chemin le plus court
	public void testScenarioSansOracle(String mapName, int origine, int destination) throws Exception {

		double costFastestSolutionInTime = Double.POSITIVE_INFINITY;
		double costFastestSolutionInDistance = Double.POSITIVE_INFINITY;
		double costShortestSolutionInTime = Double.POSITIVE_INFINITY;
		double costShortestSolutionInDistance = Double.POSITIVE_INFINITY;

		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();

		if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { // On est hors du graphe. / Sommets inexistants
			System.out.println("ERREUR : Paramètres invalides ");
			
		} else {
			System.out.println("Origine : " + origine);
			System.out.println("Destination : " + destination);
			
			if(origine==destination) {
				System.out.println("Origine et Destination identiques");
				System.out.println("Tous les couts sont � 0.");
				
			} else {
		
				// Recherche du chemin le plus rapide
				ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		
				ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		
				// Recuperation de la solution de Dijkstra 
				ShortestPathSolution solution = D.run();
		
				/* Pas de chemin trouve 
				if (solution.getPath() == null) {
					System.out.println("PAS DE CHEMIN SOLUTION EN TEMPS");
				}
				//Un plus court chemin trouve 
				else {
					/* Calcul du cout de la solution (en temps et en distance) 
					costFastestSolutionInTime = solution.getPath().getMinimumTravelTime();
					costFastestSolutionInDistance = solution.getPath().getLength();
				}
		
		
				/** Recherche du chemin le plus court *
		
				arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		
				data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				D = new DijkstraAlgorithm(data);
		
				/* Recuperation de la solution de Dijkstra 
				solution = D.run();
	
				/* Pas de chemin trouve 
				if (solution.getPath() == null) {
					System.out.println("PAS DE CHEMIN SOLUTION EN DISTANCE");
				}
				/* Un plus court chemin trouve 
				else {				
					/* Calcul du cout de la solution (en temps et en distance) 
					costShortestSolutionInTime = solution.getPath().getMinimumTravelTime();
					costShortestSolutionInDistance = solution.getPath().getLength();
				}
			
			
				/* Verifie que le temps du chemin le plus rapide est inferieur au temps du chemin le plus court 
				assertTrue(costFastestSolutionInTime <= costShortestSolutionInTime);
				System.out.println("Cout en temps du chemin le plus rapide : " + costFastestSolutionInTime);
				System.out.println("Cout en temps du chemin le plus court  : " + costShortestSolutionInTime);
		
				/* Et verifie que la distance du chemin le plus rapide est superieur a la distance du chemin le plus court 
				assertTrue(costFastestSolutionInDistance >= costShortestSolutionInDistance);
				System.out.println("Cout en distance du chemin le plus rapide : " + costFastestSolutionInDistance);
				System.out.println("Cout en distance du chemin le plus court  : " + costShortestSolutionInDistance);
	
			}
		}
		System.out.println();
		System.out.println();
	}
}

***/