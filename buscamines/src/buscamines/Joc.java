package buscamines;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

enum dificultat {
	FACIL, MIG, DIFICIL
};

enum estat {
	MINA, LLIURE, DESCOBERT
};

public class Joc {

	private dificultat dificultat;
	private estat[][] tauler;
	private int casellesLliures;
	private boolean explosio;

	public boolean getExplosio() {
		return this.explosio;
	}
	
	public estat[][] getTauler() {
		return this.tauler;
	}

	public void setTauler(estat[][] tauler) {
		this.tauler = tauler;
	}

	public int getCasellesLliures() {
		return this.casellesLliures;
	}

	public Joc(dificultat d) {
		this.dificultat = d;
		this.explosio = false;

		switch (this.dificultat) {
		case FACIL:
			this.tauler = new estat[10][10];
			break;
		case MIG:
			this.tauler = new estat[16][16];
			break;
		case DIFICIL:
			this.tauler = new estat[16][30];
			break;
		default:
			this.tauler = null;
			System.err.println("Cas no tractat");
			break;
		}
	}

	public void initTauler() {
		// Posem totes les caselles a lliures
		for (int i = 0; i < this.tauler.length; i++) {
			for (int j = 0; j < this.tauler[0].length; j++) {
				this.tauler[i][j] = estat.LLIURE;
			}
		}
	}

	// Per trobar posició de vaixell: fila = i/n (part baixa); columna = i mod n
	public void initMines() {
		int random, nCaselles, nMines = 0;
		Random generadorAleatori = new Random();

		nCaselles = this.tauler.length * this.tauler[0].length;

		// Definim el valor del nombre de mines segons nivell de dificultat
		switch (this.dificultat) {
		case FACIL:
			nMines = 10;
			break;
		case MIG:
			nMines = 40;
			break;
		case DIFICIL:
			nMines = 99;
			break;
		default:
			System.err.println("Cas no tractat");
			break;
		}

		ArrayList<Integer> valorsRandom = new ArrayList<>();

		do {
			random = generadorAleatori.nextInt(nCaselles);
			if (!(valorsRandom.contains(random))) {
				valorsRandom.add(random);
			}
		} while (valorsRandom.size() != nMines);

		for (int i = 0; i < valorsRandom.size(); i++) {
			int valor = valorsRandom.get(i);
			int fila = valor / this.tauler[0].length;
			int columna = valor % this.tauler[0].length;
			this.tauler[fila][columna] = estat.MINA;
		}
		this.casellesLliures = nCaselles - nMines;
	}

	public void printTauler() {
		System.out.println("Caselles lliures: " + this.casellesLliures);
		System.out.print("\t");
		// Imprimim fila superior amb índex de números
		for (int iter = 0; iter < this.tauler[0].length; iter++) {
			System.out.print(iter + 1 + "\t");
		}
		System.out.println();
		for (int fila = 0; fila < this.tauler.length; fila++) {
			System.out.print(fila + 1 + "\t"); // Imprimim núm. de fila
			for (int columna = 0; columna < this.tauler[0].length; columna++) {
				switch (this.tauler[fila][columna]) {
				case MINA:
					System.out.print("X \t");
					break;
				case LLIURE:
					System.out.print("O \t");
					break;
				case DESCOBERT:
					if (this.recompteMines(fila, columna) == 0) {
						System.out.print(" \t");
					} else {
						System.out.print(this.recompteMines(fila, columna) + " \t");
					}
					break;
				default:
					break;
				}
			}
			System.out.println();
		}
	}

	public int recompteMines(int a, int b) {
		int suma = 0;
		int limitFila = this.tauler.length - 1;
		int limitColumna = this.tauler[0].length - 1;

		for (int x = Math.max(0, a - 1); x <= Math.min(a + 1, limitFila); x++) {
			for (int y = Math.max(0, b - 1); y <= Math.min(b + 1, limitColumna); y++) {
				if (x != a || y != b) {
					if (tauler[x][y] == estat.MINA) {
						suma++;
					}
				}
			}
		}
		return suma;
	}

	public void trepitja(int fila, int columna) {
		if (fila > this.tauler.length - 1 || columna > this.tauler[0].length - 1) {
			System.out.println("==================================");
			System.out.println("Coordenada referenciada incorrecta");
			System.out.println("==================================");
		} else {
			switch (this.tauler[fila][columna]) {
			case MINA:
				this.explosio = true;
				break;
			case DESCOBERT:
				break;
			case LLIURE:
				this.tauler[fila][columna] = estat.DESCOBERT;
				this.casellesLliures--;
				// Funció descobriment recursiva
				if (this.recompteMines(fila, columna) == 0) {
					for (int x = Math.max(0, fila - 1); x <= Math.min(fila + 1, this.tauler.length - 1); x++) {
						for (int y = Math.max(0, columna - 1); y <= Math.min(columna + 1,
								this.tauler[0].length - 1); y++) {
							if (x != fila || y != columna) {
								if (tauler[x][y] != estat.MINA) {
									this.trepitja(x, y);
								}
							}
						}
					}
				}
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
		int fila, columna;

		this.initTauler();
		this.initMines();

		if (this.tauler != null) {
			do {
				this.printTauler();
				System.out.println("Introdueix la fila a trepitxar: ");
				fila = entrada.nextInt() - 1;
				System.out.println("Introdueix la columna a trepitxar: ");
				columna = entrada.nextInt() - 1;

				this.trepitja(fila, columna);
			} while (this.casellesLliures > 0 && !explosio);
			this.printTauler();
			if (!explosio) {
				System.out.println("Enhorabona, has guanyat la partida!");
			} else {
				System.out.println("Has trepitjat una bomba :(");
			}
			entrada.close();
		} else {
			System.out.println("Error als paràmetres d'entrada del joc.");
		}
	}

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		dificultat dificultat = null;
		System.out.println("Introdueix la dificultat (1 FACIL; 2 MIG; 3 DIFÍCIL): ");
		int d = entrada.nextInt();

		switch (d) {
		case 1:
			dificultat = buscamines.dificultat.FACIL;
			break;
		case 2:
			dificultat = buscamines.dificultat.MIG;
			break;
		case 3:
			dificultat = buscamines.dificultat.DIFICIL;
			break;
		default:
			System.err.println("Cas no tractat");
			break;
		}

		Joc j = new Joc(dificultat);
		j.juga();

		entrada.close();
	}
}
