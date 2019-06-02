package Performance;


import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

public class TestPerformance {
	private ArrayList<ResultatExecutionAlgo> listeResultatPerformance;

	public TestPerformance() {
		this.listeResultatPerformance = new ArrayList<ResultatExecutionAlgo>();
	}


	public void doRun(String fileNameRead, String fileNameWrite, int typeEvaluation) {
	
		Lecture lect = new Lecture(fileNameRead);

		String mapName = lect.getMapName();

		Iterator<Integer> origineIter = lect.getListeOrigine().iterator();
		Iterator<Integer> destinationIter = lect.getListeDestination().iterator();


		while(origineIter.hasNext()) {
			//ICI CHANGEZ LE CHEMIN DE LA CARTE CHOISIE
			ResultatExecutionAlgo  resultat = new ResultatExecutionAlgo ("C:/Users/Thomas/Downloads/bretagne.mapgr", typeEvaluation, origineIter.next(), destinationIter.next());
			this.listeResultatPerformance.add(resultat);
			}

	
		Ecriture write = new Ecriture(fileNameWrite, mapName, this.listeResultatPerformance);
	}


	@Test
	public void testLecture() {
		//ICI CHANGEZ LE CHEMIN DU FICHIER UTILISE DANS PERFTEST
		Lecture lect = new Lecture("C:/Users/Thomas/Desktop/bretagne_15_25.txt");
		System.out.println("Carte utilisée : "+lect.getMapName());
		System.out.println("Première origine : "+lect.getListeOrigine().get(0));
	}
}

