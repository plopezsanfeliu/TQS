package vaixells;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Joc {

	public enum estat {AIGUA, VAIXELL, TOCAT};
	private estat[][] tauler;
	private int vaixellsRestants;

	public estat[][] getTauler() {
		return this.tauler;
	}
	
	public void setTauler(estat[][] tauler) {
		this.tauler = tauler;
	}
	
	public int getVaixellsRestants() {
		return this.vaixellsRestants;
	}

	public Joc(int mida) {
		if (mida != 0) {
			this.tauler = new estat[mida][mida];
		} else {
			this.tauler = null;
		}
	}

	public void initTauler() {
		for (int i = 0; i < this.tauler.length; i++) {
			for (int j = 0; j < this.tauler.length; j++) {
				this.tauler[i][j] = estat.AIGUA;
			}
		}
	}

	// Per trobar posició de vaixell: fila = i/n (part baixa); columna = i mod n
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
				this.tauler[fila][columna] = estat.VAIXELL;
			}
			this.vaixellsRestants = nVaixells;
		} else {
			this.tauler = null;
		}
	}

	public void printTauler() {
		System.out.print("\t");
		// Imprimim fila superior amb índex de números
		for(int iter = 0; iter < this.tauler.length; iter++) {
			System.out.print(iter+1 + "\t");
		}
		System.out.println();
		for(int fila = 0; fila < this.tauler.length; fila++) {
			System.out.print(fila+1 + "\t"); // Imprimim núm. de fila
			for(int columna = 0; columna < this.tauler.length; columna++) {
				switch(this.tauler[fila][columna]) {
				case AIGUA:
					System.out.print("= \t");
					break;
				case VAIXELL:
					System.out.print("I \t");
					break;
				case TOCAT:
					System.out.print("X \t");
					break;
				default:
					break;
				}
			}
			System.out.println();
		}
	}

	public void dispara(int fila, int columna) {
		if(fila > this.tauler.length - 1 || columna > this.tauler.length - 1) {
			System.out.println("==================================");
			System.out.println("Coordenada referenciada incorrecta");
			System.out.println("==================================");
		}
		else {
			switch(this.tauler[fila][columna]) {
			case AIGUA:
				System.out.println("=================");
				System.out.println("Has donat a aigua");
				System.out.println("=================");
				break;
			case TOCAT:
				System.out.println("========================================");
				System.out.println("Ja has tocat un vaixell amb anterioritat");
				System.out.println("========================================");
				break;
			case VAIXELL:
				this.tauler[fila][columna] = estat.TOCAT;
				this.vaixellsRestants--;
				System.out.println("=====================");
				System.out.println("Has tocat un vaixell!");
				System.out.println("=====================");
				break;
			default:
				System.out.println("================");
				System.out.println("Error no tractat");
				System.out.println("================");
				break;
			}
		}		
	}

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
				this.printTauler();
				System.out.println("Introdueix la fila a disparar: ");
				fila = entrada.nextInt() - 1;
				System.out.println("Introdueix la columna a disparar: ");
				columna = entrada.nextInt() - 1;

				this.dispara(fila, columna);
			} while(vaixellsRestants > 0);
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
	}
}
