package Performance;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Lecture {

	private String mapName ;
	private ArrayList<Integer> listeOrigine;
	private ArrayList<Integer> listeDestination;

	public Lecture(String fileName) {
		this.listeOrigine = new ArrayList<Integer>();
		this.listeDestination = new ArrayList<Integer>();
		this.LectureFichier(fileName);
	}

	public void LectureFichier (String fileName) {
		try {
			Scanner scan = new Scanner(new File(fileName));
			int origine;
			int destination;
			
			if (scan.hasNext()) {
				mapName = scan.nextLine();
			}
			
			while (scan.hasNextInt()) {
				origine = scan.nextInt();
				this.listeOrigine.add(origine);
				if (scan.hasNextInt()) {
					destination = scan.nextInt();
					this.listeDestination.add(destination);
				} 
				else {
					throw new OddAmountOfPointsException("Le fichier contient un nombre impair de points.");
				}
			}
			scan.close();
		}
		catch(Exception e) {
				System.out.println(e.getMessage());
		}
	}

	public String getMapName() {
		return this.mapName;
	}

	public ArrayList<Integer> getListeOrigine(){
		return this.listeOrigine;
	}

	public ArrayList<Integer> getListeDestination(){
		return this.listeDestination;
	}
}

