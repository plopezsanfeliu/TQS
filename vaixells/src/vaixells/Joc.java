package vaixells;

import java.util.ArrayList;
import java.util.Random;

public class Joc {

	public enum estat {INIT, JUGANT, GUANYAT};
	public enum casella {AIGUA, VAIXELL, TOCAT};
	private estat e;
	private casella[][] tauler;
	private int vaixellsRestants;
	
	public String getSortida() {
		return sortida;
	}

	public void setSortida(String sortida) {
		this.sortida = sortida;
	}

	public String sortida;
	
	public estat getEstat() {
		return this.e;
	}

	public casella[][] getTauler() {
		return this.tauler;
	}
	
	public void setTauler(casella[][] tauler) {
		this.tauler = tauler;
	}
	
	public int getVaixellsRestants() {
		return this.vaixellsRestants;
	}

	public Joc(int mida) {
		this.e = estat.INIT;
		if (mida > 0) {
			this.tauler = new casella[mida][mida];
		} else {
			this.tauler = null;
		}
	}

	public void initTauler() {
		for (int i = 0; i < this.tauler.length; i++) {
			for (int j = 0; j < this.tauler.length; j++) {
				this.tauler[i][j] = casella.AIGUA;
			}
		}
	}

	// Per trobar posició de vaixell: fila = i\n (part baixa); columna = i mod n
	public void initVaixells(int nVaixells) {
		int random, nCaselles;
		Random generadorAleatori = new Random();

		nCaselles = this.tauler.length * this.tauler.length;

		if (nVaixells <= nCaselles && nVaixells > 0) {
			ArrayList<Integer> valorsRandom = new ArrayList<>();

			do {
				random = generadorAleatori.nextInt(nCaselles);
				if (!(valorsRandom.contains(random))) {
					valorsRandom.add(random);
				}
			} while (valorsRandom.size() != nVaixells);

			for (int i = 0; i < valorsRandom.size(); i++) {
				int valor = valorsRandom.get(i);
				int fila = valor / this.tauler.length;
				int columna = valor % this.tauler.length;
				this.tauler[fila][columna] = casella.VAIXELL;
			}
			this.vaixellsRestants = nVaixells;
			
			this.e = estat.JUGANT;
		} else {
			this.tauler = null;
		}
	}

	public String printTauler() {
		String print = "";
		print = print.concat("\t");
		// Imprimim fila superior amb índex de números
		for(int iter = 0; iter < this.tauler.length; iter++) {
			print = print.concat(iter+1 + "\t");
		}
		print = print.concat("\n");
		for(int fila = 0; fila < this.tauler.length; fila++) {
			print = print.concat(fila+1 + "\t"); // Imprimim núm. de fila
			for(int columna = 0; columna < this.tauler.length; columna++) {
				switch(this.tauler[fila][columna]) {
				case AIGUA:
					print = print.concat("= \t");
					break;
				case VAIXELL:
					print = print.concat("I \t");
					break;
				default: // case TOCAT
					print = print.concat("X \t");
					break;
				}
			}
			print = print.concat("\n");
		}
		return print;
	}

	public void dispara(int fila, int columna) {
		//System.out.println("Dispar a Fila "+(fila+1)+" i columna "+(columna+1)+".("+fila+","+columna+")");
		final int MIN = 0;
		//1 DECISION COVERAGE
		if(fila < MIN || columna < MIN || fila > this.tauler.length - 1 || columna > this.tauler.length - 1) {
		//2
			this.setSortida("==============ERROR===============\n"
					+ "Coordenada referenciada incorrecta\n"
					+ "==============ERROR===============\n");
			System.out.println(getSortida());
		} else {
			//3
			if(this.tauler[fila][columna] == casella.AIGUA){
				//4
				this.setSortida("Has donat a l'aigua\n"
						+ "=================");
				System.out.println(getSortida());
				//5
			} else if (this.tauler[fila][columna] == casella.TOCAT){
				//6
				this.setSortida("Ja has tocat un vaixell amb anterioritat\n"
						+ "========================================");
				System.out.println(getSortida());
			} else { //if (this.tauler[fila][columna] == casella.VAIXELL)
				//7
				this.tauler[fila][columna] = casella.TOCAT;
				this.vaixellsRestants--;
				this.setSortida("Has tocat un vaixell!\n"
						+ "=====================");
				System.out.println(getSortida());
			}
			/*//comentat per a poder fer una prova de caixa blanca ben feta sense el switch case
			switch(this.tauler[fila][columna]) {
			case AIGUA:
				System.out.println("Has donat a aigua");
				System.out.println("=================");
				break;
			case TOCAT:
				System.out.println("Ja has tocat un vaixell amb anterioritat");
				System.out.println("========================================");
				break;
			case VAIXELL:
				this.tauler[fila][columna] = casella.TOCAT;
				this.vaixellsRestants--;
				System.out.println("Has tocat un vaixell!");
				System.out.println("=====================");
				break;
			}
			*/
			//System.out.println(printTauler());
			//8
			if(this.vaixellsRestants == 0) {
				//9
				this.e = estat.GUANYAT;
				this.setSortida("============\n"
						+ "Has Guanyat!\n"
						+ "============");
				System.out.println(getSortida());
			}
		}
		//10
	}
	/*
	public void juga() {
		Scanner entrada = new Scanner(System.in);
		int vaixells;
		int fila, columna;

		System.out.println("Introdueix el nombre de vaixells: ");
		vaixells = entrada.nextInt();

		this.initTauler();
		this.initVaixells(vaixells);

		if(this.tauler != null) {
			do {
				System.out.println(this.printTauler());
				System.out.println("Introdueix la fila a disparar: ");
				fila = entrada.nextInt() - 1;
				System.out.println("Introdueix la columna a disparar: ");
				columna = entrada.nextInt() - 1;

				this.dispara(fila, columna);
			} while(this.e != estat.GUANYAT);
			this.printTauler();
			System.out.println("Enhorabona, has guanyat la partida!");
			entrada.close();
		}
		else {
			System.out.println("Error als paràmetres d'entrada del joc.");
		}
	}

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		System.out.println("Introdueix la longitud del tauler: ");
		int m = entrada.nextInt();
		
		Joc j = new Joc(m);
		j.juga();
		
		entrada.close();
	} */
}
