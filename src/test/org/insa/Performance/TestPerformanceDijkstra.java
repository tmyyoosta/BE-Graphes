package org.insa.Performance;

import org.insa.graph.*;
import org.insa.algo.*;
import org.insa.algo.shortestpath.AStarAlgorithm;
import org.insa.algo.shortestpath.DijkstraAlgorithm;
import org.insa.algo.shortestpath.ShortestPathData;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPerformanceDijkstra {
    
	public static Graph BgGraph;
	
	public static Graph BtGraph;
	
	public static Graph CDGraph;
	
	public static ArcInspector ArcInspectorLength;
	
	public static ArcInspector ArcInspectorTime;
	
	public static ShortestPathData data1,data2,data3,data4,data5,data6,carre1,carre2,Bt1,Bt2;

	public static long Time,Time1,Time2,Time3,Time4,Time5,Time6;

	@BeforeClass
	public static void initAll() throws Exception {
		
		//cartes utilisées
		String Bgmap = "C:\\Users\\Thomas\\Downloads\\belgium.mapgr";
		String Btmap = "C:\\Users\\Thomas\\Downloads\\bretagne.mapgr";
		String CDmap = "C:\\Users\\Thomas\\Downloads\\carre-dense.mapgr";
		
		
        GraphReader reader1 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(Bgmap))));
        
        GraphReader reader2 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(Btmap))));

        GraphReader reader3 = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(CDmap))));
        
        // Graph de test
        BgGraph = reader1.read();
        BtGraph = reader2.read();
		CDGraph = reader3.read();
		
		//arc inspector
		ArcInspectorLength = ArcInspectorFactory.getAllFilters().get(0);
		ArcInspectorTime = ArcInspectorFactory.getAllFilters().get(2);
		
		//data HG
		//data distance
		data1 = new ShortestPathData(BgGraph,BgGraph.get(804491),BgGraph.get(628872),ArcInspectorLength);
		data2 = new ShortestPathData(BgGraph,BgGraph.get(804491),BgGraph.get(605361),ArcInspectorLength);
		data3 = new ShortestPathData(BgGraph,BgGraph.get(804491),BgGraph.get(865079),ArcInspectorLength);

		//data temps
		data4 = new ShortestPathData(BgGraph,BgGraph.get(804491),BgGraph.get(628872),ArcInspectorTime);
		data5 = new ShortestPathData(BgGraph,BgGraph.get(804491),BgGraph.get(605361),ArcInspectorTime);
		data6 = new ShortestPathData(BgGraph,BgGraph.get(804491),BgGraph.get(865079),ArcInspectorTime);
		
		//data carre distance
		carre1 = new ShortestPathData(CDGraph,CDGraph.get(83468),CDGraph.get(25557),ArcInspectorLength);
		
		//data carre temps
		carre2 = new ShortestPathData(CDGraph,CDGraph.get(83468),CDGraph.get(25557),ArcInspectorTime);
		
		//data Bretagne distance
		Bt1 = new ShortestPathData(BtGraph,BtGraph.get(8612),BtGraph.get(20565),ArcInspectorLength);
		
		//data Bretagne temps
		Bt2 = new ShortestPathData(BtGraph,BtGraph.get(8612),BtGraph.get(20565),ArcInspectorTime);
		
	}

	
	@Test
	public void testDistance() 
	{
		DijkstraAlgorithm dij = new DijkstraAlgorithm(data1);
		AStarAlgorithm star = new AStarAlgorithm(data1);
		
		DijkstraAlgorithm dij2 = new DijkstraAlgorithm(data2);
		AStarAlgorithm star2 = new AStarAlgorithm(data2);
		
		DijkstraAlgorithm dij3 = new DijkstraAlgorithm(data3);
		AStarAlgorithm star3 = new AStarAlgorithm(data3);
		
		DijkstraAlgorithm dijBt = new DijkstraAlgorithm(Bt1);
		AStarAlgorithm starBt = new AStarAlgorithm(Bt1);
		
		DijkstraAlgorithm dijc= new DijkstraAlgorithm(carre1);
		AStarAlgorithm starc = new AStarAlgorithm(carre1);
		

		System.out.println("\n#-----------Test en Distance-----------#\n" );

		System.out.println("--Test sur la carte de la Belgique--\n" );
		
		System.out.println("-Test sur une longue distance- \n" );
		
		//on démmare le timer 
		Time = System.currentTimeMillis();
		// on execute 10 fois l'algo
		for (int i = 0;i<10;i++) {
			dij.doRun();
		}
		//on calcule le temps écoulé divisé par 10
		Time1 = (System.currentTimeMillis() - Time)/10;
			
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			star.doRun();
		}
		Time2 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités pour Dijkstra : "+ dij.NbNodeMark);
		System.out.println("Nombre de sommets visités pour Astar : " + star.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dij.NbNodeMark/star.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution pour Dijkstra :" + Time1 +" ms");
		System.out.println("Temps d'éxécution pour Astar :" + Time2 +" ms");
		System.out.println("Astar est environ " + (float)Time1/Time2 +" fois plus rapide");
		
		//test moyenne echelle
		
		System.out.println("\nTest sur une moyenne distance : \n" );
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dij2.doRun();
		}
		Time3 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			star2.doRun();
		}
		Time4 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dij2.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + star2.NbNodeMark);
		System.out.println("Dijkstra visite environ " +	(float)dij2.NbNodeMark/star2.NbNodeMark + " fois plus de sommets");
		
		System.out.println("Temps d'éxécution pour Dijkstra :" + Time3 +" ms");
		System.out.println("Temps d'éxécution pour Astar :" + Time4 +" ms");
		System.out.println("Astar est environ " + (float)Time3/Time4 +" fois plus rapide");
		
		//test petite échelle
		
		System.out.println("\nTest sur une petite distance : \n" );
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dij3.doRun();
		}
		Time5 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			star3.doRun();
		}
		Time6 = (System.currentTimeMillis() - Time)/10;
	
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dij3.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + star3.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dij3.NbNodeMark/star3.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution pour Dijkstra :" + Time5 +" ms");
		System.out.println("Temps d'éxécution pour Astar :" + Time6 +" ms");
		System.out.println("Astar est environ " + (float)Time5/Time6 +" fois plus rapide");
		
		
		//test Haute Garonness
		
		System.out.println("\n#--Test sur la carte de Bretagne--# \n" );
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dijBt.doRun();
		}
		Time3 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			starBt.doRun();
		}
		Time4 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dijBt.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + starBt.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dijBt.NbNodeMark/starBt.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution pour Dijkstra :" + Time3 +" ms");
		System.out.println("Temps d'éxécution pour Astar :" + Time4 +" ms");
		System.out.println("Astar est environ " + (float)Time3/Time4 +" fois plus rapide");
		
		
		//test carré dense
		
		System.out.println("\n#--Test carré dense--#s \n" );
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dijc.doRun();
		}
		Time3 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			starc.doRun();
		}
		Time4 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dijc.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + starc.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dijc.NbNodeMark/starc.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution pour Dijkstra :" + Time3 +" ms");
		System.out.println("Temps d'éxécution pour Astar :" + Time4 +" ms");
		System.out.println("Astar est environ " + (float)Time3/Time4 +" fois plus rapide");
	}
	
	@Test 
	public void testTemps() 
	{
		
		DijkstraAlgorithm dij = new DijkstraAlgorithm(data4);
		AStarAlgorithm star = new AStarAlgorithm(data4);
		
		DijkstraAlgorithm dij2 = new DijkstraAlgorithm(data5);
		AStarAlgorithm star2 = new AStarAlgorithm(data5);
		
		DijkstraAlgorithm dij3 = new DijkstraAlgorithm(data6);
		AStarAlgorithm star3 = new AStarAlgorithm(data6);
		
		DijkstraAlgorithm dijt = new DijkstraAlgorithm(Bt2);
		AStarAlgorithm start = new AStarAlgorithm(Bt2);
		
		DijkstraAlgorithm dijc= new DijkstraAlgorithm(carre2);
		AStarAlgorithm starc = new AStarAlgorithm(carre2);
		
		System.out.println("\n#----------------Test en Temps----------------# \n" );

		System.out.println("#--Test sur la carte de la Belgique--#\n" );
		
		System.out.println("Test sur une longue distance : \n" );
		
		//on démare le timer 
		Time = System.currentTimeMillis();
		// on execute 10 fois l'algo
		for (int i = 0;i<10;i++) {
			dij.doRun();
		}
		//on calcule le temps écoulé divisé par 10
		Time1 = (System.currentTimeMillis() - Time)/10;
			
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			star.doRun();
		}
		Time2 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dij.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + star.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dij.NbNodeMark/star.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution de Dijkstra : " + Time1 +" ms");
		System.out.println("Temps d'éxécution de Astar :" + Time2 +" ms");
		System.out.println("Astar est environ "  + (float)Time1/Time2 +" fois plus rapide");
		
		//test moyenne echelle
		
		System.out.println("\nTest sur une moyenne distance : \n" );
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dij2.doRun();
		}
		Time3 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			star2.doRun();
		}
		Time4 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dij2.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + star2.NbNodeMark);
		System.out.println("Dijkstra visite environ " +	(float)dij2.NbNodeMark/star2.NbNodeMark + " fois plus de sommets");
		
		System.out.println("Temps d'éxécution Dijkstra :" + Time3 +" ms");
		System.out.println("Temps d'éxécution Astar :" + Time4 +" ms");
		System.out.println("Astar est environ " + (float)Time3/Time4 +" plus rapide");
		
		//test petite échelle
		
		System.out.println("\nTest sur une petite distance : \n" );
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dij3.doRun();
		}
		Time5 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			star3.doRun();
		}
		Time6 = (System.currentTimeMillis() - Time)/10;
	
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dij3.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + star3.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dij3.NbNodeMark/star3.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution Dijkstra :" + Time5 +" ms");
		System.out.println("Temps d'éxécution Astar :" + Time6 +" ms");
		System.out.println("Astar est environ " + (float)Time5/Time6 +" fois plus rapide");
		
		
		//test Haute Garonness
		
		System.out.println("\n#--Test sur la carte de Bretagne--#\n" );
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dijt.doRun();
		}
		Time3 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			start.doRun();
		}
		Time4 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dijt.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + start.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dijt.NbNodeMark/start.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution pour Dijkstra :" + Time3 +" ms");
		System.out.println("Temps d'éxécution pour Astar :" + Time4 +" ms");
		System.out.println("Astar est environ " + (float)Time3/Time4 +" fois plus rapide");
		
		
		//test carré dense
		
		System.out.println("\n#--Test carré dense--#\n" );
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			dijc.doRun();
		}
		Time3 = (System.currentTimeMillis() - Time)/10;
		
		Time = System.currentTimeMillis();
		for (int i = 0;i<10;i++) {
			starc.doRun();
		}
		Time4 = (System.currentTimeMillis() - Time)/10;
		
		System.out.println("Nombre de sommets visités par Dijkstra : "+ dijc.NbNodeMark);
		System.out.println("Nombre de sommets visités par Astar : " + starc.NbNodeMark);
		System.out.println("Dijkstra visite environ " + (float)dijc.NbNodeMark/starc.NbNodeMark +" fois plus de sommets");
		
		System.out.println("Temps d'éxécution pour Dijkstra :" + Time3 +" ms");
		System.out.println("Temps d'éxécution pour Astar :" + Time4 +" ms");
		System.out.println("Astar est environ " + (float)Time3/Time4 +" fois plus rapide");
	}
}