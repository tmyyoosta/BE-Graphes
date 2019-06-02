package Performance;


import org.junit.Test;

public class PerfTest {
	
	@Test
	public void testsPerformance() {
		//ICI CHANGEZ LE CHEMIN VERS LES FICHIERS TEXTE CONTENANT EN 1ERE LIGNE LE CHEMIN DE LA CARTE ET EN DEUXIEME LA PAIRE D'ORIGINE-DESTINATION
		TestPerformance test1Temps = new TestPerformance();
		test1Temps.doRun("C:/Users/Thomas/Desktop/bretagne_1.txt", "resultat_performances_bretagne_1_temps.csv", 0);
		
		TestPerformance test1Distance = new TestPerformance();
		test1Distance.doRun("C:/Users/Thomas/Desktop/bretagne_1.txt", "resultat_performances_bretagne_1_distance.csv", 1);
		
		TestPerformance test2Temps = new TestPerformance();
		test2Temps.doRun("C:/Users/Thomas/Desktop/bretagne_2.txt", "resultat_performances_bretagne_2_temps.csv", 0);
		
		TestPerformance test2Distance = new TestPerformance();
		test2Distance.doRun("C:/Users/Thomas/Desktop/bretagne_2.txt", "resultat_performances_bretagne_2_distance.csv", 1);

		TestPerformance test3Temps = new TestPerformance();
		test3Temps.doRun("C:/Users/Thomas/Desktop/bretagne_3.txt", "resultat_performances_bretagne_3_temps.csv", 0);
		
		TestPerformance test3Distance = new TestPerformance();
		test3Distance.doRun("C:/Users/Thomas/Desktop/bretagne_3.txt", "resultat_performances_bretagne_3_distance.csv", 1);
	}

}
