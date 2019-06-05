package org.insa.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.insa.algo.*;
import org.insa.algo.shortestpath.*;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
//import org.insa.graph.RoadInformation;
//import org.insa.graph.RoadInformation.RoadType;

import org.junit.BeforeClass;


public class DijkstraTest {

	// Small graph use for tests
	private static Graph graph;

	// List of nodes
	private static Node[] nodes;

	// List of arcs in the graph, a2b is the arc from node A (0) to B (1).
	@SuppressWarnings("unused")
	private static Arc a2b, a2c, b2d, b2e, b2f, c2a, c2b, c2f, e2c, e2d, e2f, f2e;

	@BeforeClass
	public static void initAll() throws IOException {

		// Define roads
		RoadInformation RoadInfo = new RoadInformation(RoadInformation.RoadType.UNCLASSIFIED, null, true, 1, null);

		// Create nodes
		nodes = new Node[6];
		for (int i = 0; i < nodes.length; ++i) {
			nodes[i] = new Node(i, null);
		}

		// Add arcs...
		a2b = Node.linkNodes(nodes[0], nodes[1], 7, RoadInfo, null);
		a2c = Node.linkNodes(nodes[0], nodes[2], 8, RoadInfo, null);
		b2d = Node.linkNodes(nodes[1], nodes[3], 4, RoadInfo, null);
		b2e = Node.linkNodes(nodes[1], nodes[4], 1, RoadInfo, null);
		b2f = Node.linkNodes(nodes[1], nodes[5], 5, RoadInfo, null);
		c2a = Node.linkNodes(nodes[2], nodes[0], 7, RoadInfo, null);
		c2b = Node.linkNodes(nodes[2], nodes[1], 2, RoadInfo, null);
		c2f = Node.linkNodes(nodes[2], nodes[5], 2, RoadInfo, null);
		e2c = Node.linkNodes(nodes[4], nodes[2], 2, RoadInfo, null);
		e2d = Node.linkNodes(nodes[4], nodes[3], 2, RoadInfo, null);
		e2f = Node.linkNodes(nodes[4], nodes[5], 3, RoadInfo, null);
		f2e = Node.linkNodes(nodes[5], nodes[4], 3, RoadInfo, null);

		// Initialize the graph
		graph = new Graph("ID", "", Arrays.asList(nodes), null);

	}

	@Test
	public void testDoRun() 
	{
		System.out.println("Test de validité sur un exemple simple (avec oracle) ");
		

		for (int i=0;  i < nodes.length; ++i) 
		{

			/* Affichage du point de départ */
			System.out.print("x"+(nodes[i].getId()+1) + ":");

			for (int j=0;  j < nodes.length; ++j) 
			{

				if(nodes[i]==nodes[j]) 
				{
					System.out.print("     -    ");
				}
				else
				{

					ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
					ShortestPathData data = new ShortestPathData(graph, nodes[i],nodes[j], arcInspectorDijkstra);

					BellmanFordAlgorithm BF = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);

					/* Récupération des solutions de Bellman et Dijkstra pour comparer */
					ShortestPathSolution solutionD = D.run();
					ShortestPathSolution solutionBF = BF.run();
				
					
					/* Pas de chemin trouvé */
					if (solutionD.getPath() == null) 
					{
						//System.out.print("OKAY" + solutionBF.getPath());
						assertTrue(solutionBF.getPath() == null);
						System.out.print("(infini)  ");
					}
					/* Un plus court chemin trouvé */
					else 
					{
						/* Calcul du coût de la solution */
						float costSolution = solutionD.getPath().getLength();
						float costExpected = solutionBF.getPath().getLength();
						assertEquals(costExpected, costSolution, 0);

						/* On récupère l'avant dernier sommet du chemin de la solution (=sommet père de la destination) */
						List<Arc> arcs = solutionD.getPath().getArcs();
						Node originOfLastArc = arcs.get(arcs.size()-1).getOrigin();

						/* Affiche le couple (coût, sommet père du Dest) */
						System.out.print("("+costSolution+ ", x" + (originOfLastArc.getId()+1) + ") ");
					}			
				}
			}
			System.out.println(); // Retour à la ligne 
		}
		System.out.println();
	}

	@Test
	public void testDistance() throws Exception {
		String mapName = "C:\\Users\\Thomas\\Downloads\\toulouse.mapgr";

		DijkstraTest2 test = new  DijkstraTest2();
		int origine;
		int destination;
		
		System.out.println("Test de validité avec oracle sur une carte   -   Mode : DISTANCE");
		System.out.println();
		
		System.out.println("	Cas avec des sommets non connexes ");
		origine = 115402 ;
		destination = 420148;
		test.testScenario(mapName, 1,origine,destination);    
		
		System.out.println("	Cas d'un chemin simple - Carte : Toulouse ");
		origine = 7946;
		destination = 5090;
		test.testScenario(mapName, 1,origine,destination);    	
	
		
		System.out.println("	Cas de sommets inexistants - Origine invalide");
		origine = -1;
		destination = 1000;
		test.testScenario(mapName, 1,origine,destination);    	

		System.out.println("	Cas de sommets inexistants - Destination invalide");
		origine = 1000;
		destination = 200000000;
		test.testScenario(mapName, 1,origine,destination);    	
		
		System.out.println("	Cas de sommets inexistants - Origine & Destination invalide");
		origine = -1;
		destination = 2000000000;
		test.testScenario(mapName, 1,origine,destination);    	
	}

	
	@Test
	public void testTemps() throws Exception {
		String mapName = "C:\\Users\\Thomas\\Downloads\\toulouse.mapgr";

		DijkstraTest2 test = new  DijkstraTest2();
		int origine;
		int destination;
		
		System.out.println("Test de validité avec oracle sur une carte - Mode : TEMPS");
		System.out.println();
		
		System.out.println("	Cas d'un chemin impossible ");
		origine = 100 ;
		destination = 100;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("	Cas d'un chemin simple - Carte : Toulouse");
		origine = 7946;
		destination = 5090;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("	Cas de sommets inexistants - Origine invalide");
		origine = -1;
		destination = 1000;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("	Cas de sommets inexistants - Destination invalide");
		origine = 1000;
		destination = -1;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("	Cas de sommets inexistants - Origine & Destination invalide");
		origine = -1;
		destination = -265;
		test.testScenario(mapName, 0,origine,destination);    	
	}
	
}
	
