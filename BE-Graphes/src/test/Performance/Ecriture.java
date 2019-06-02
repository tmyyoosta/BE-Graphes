package Performance;

import java.io.FileWriter;
import java.util.ArrayList;

public class Ecriture {
 public Ecriture(String fileNameWrite, String mapName, ArrayList<ResultatExecutionAlgo>listeResultatPerformance) {
	 String commaDelimiter = ",";
	 String newLineSeparator ="\n";
	 String fileHeader ="Carte, Origine, Destination, Temps CPU pour Dijkstra (en ms), Nombre de Sommets, Temps CPU pour A* (en ms), Nombre de Sommets"; // Nb sommets
	 try {
	//ICI CHANGEZ LE DOSSIER DE DESTINATION QUE VOULEZ
	 FileWriter fileWriter = new FileWriter("C:\\Users\\Thomas\\Downloads\\"+fileNameWrite);
	 fileWriter.append(fileHeader);
	 	for (ResultatExecutionAlgo p : listeResultatPerformance) {
	 		fileWriter.append(newLineSeparator);
	 		fileWriter.append(mapName);
	 		fileWriter.append(commaDelimiter);
	 		fileWriter.append(String.valueOf(p.getOrigine()));
	 		fileWriter.append(commaDelimiter);
	 		fileWriter.append(String.valueOf(p.getDestination()));
	 		fileWriter.append(commaDelimiter);
	 		fileWriter.append(String.valueOf(p.getTempsExecutionDijkstra()));
	 		fileWriter.append(commaDelimiter);
	 		fileWriter.append(String.valueOf(p.getNbSommetsVisitesDijkstra()));
	 		fileWriter.append(commaDelimiter);
	 		fileWriter.append(String.valueOf(p.getTempsExecutionAStar()));
	 		fileWriter.append(commaDelimiter);
	 		fileWriter.append(String.valueOf(p.getNbSommetsVisitesAStar()));
	 	}
 		fileWriter.flush();
 		fileWriter.close();
 		System.out.println("Done");	
 		} 
	 catch (Exception e){
		 System.out.println(e.getMessage());
	 }
 }
}
