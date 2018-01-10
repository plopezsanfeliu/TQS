package buscamines;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

enum dificultat {
	FACIL, MIG, DIFICIL
};

enum casella {
	MINA, LLIURE, DESCOBERT
};

public class Joc {

	private dificultat dificultat;
	private casella[][] tauler;
	private int casellesLliures;
	private boolean explosio;

	public boolean getExplosio() {
		return this.explosio;
	}
	
	public casella[][] getTauler() {
		return this.tauler;
	}

	public void setTauler(casella[][] tauler) {
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
			this.tauler = new casella[10][10];
			break;
		case MIG:
			this.tauler = new casella[16][16];
			break;
		case DIFICIL:
			this.tauler = new casella[16][30];
			break;
		}
	}

	public void initTauler() {
		// Posem totes les caselles a lliures
		for (int i = 0; i < this.tauler.length; i++) {
			for (int j = 0; j < this.tauler[0].length; j++) {
				this.tauler[i][j] = casella.LLIURE;
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
			this.tauler[fila][columna] = casella.MINA;
		}
		this.casellesLliures = nCaselles - nMines;
	}

	public String printTauler() {
		String print = "";

		print = print.concat("\t");
		// Imprimim fila superior amb índex de números
		for (int iter = 0; iter < this.tauler[0].length; iter++) {
			print = print.concat(iter + 1 + "\t");
		}
		print = print.concat("\n");
		for (int fila = 0; fila < this.tauler.length; fila++) {
			print = print.concat(fila + 1 + "\t"); // Imprimim núm. de fila
			for (int columna = 0; columna < this.tauler[0].length; columna++) {
				switch (this.tauler[fila][columna]) {
				case MINA:
					print = print.concat("X \t");
					break;
				case LLIURE:
					print = print.concat("O \t");
					break;
				case DESCOBERT:
					if (this.recompteMines(fila, columna) == 0) {
						print = print.concat(" \t");
					} else {
						print = print.concat(this.recompteMines(fila, columna) + " \t");
					}
					break;
				}
			}
			print = print.concat("\n");
		}
		return print;
	}

	public int recompteMines(int a, int b) {
		int suma = 0;
		int limitFila = this.tauler.length - 1;
		int limitColumna = this.tauler[0].length - 1;

		for (int x = Math.max(0, a - 1); x <= Math.min(a + 1, limitFila); x++) {
			for (int y = Math.max(0, b - 1); y <= Math.min(b + 1, limitColumna); y++) {
				if (x != a || y != b) {
					if (tauler[x][y] == casella.MINA) {
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
				this.tauler[fila][columna] = casella.DESCOBERT;
				this.casellesLliures--;
				// Funció descobriment recursiva
				if (this.recompteMines(fila, columna) == 0) {
					for (int x = Math.max(0, fila - 1); x <= Math.min(fila + 1, this.tauler.length - 1); x++) {
						for (int y = Math.max(0, columna - 1); y <= Math.min(columna + 1,
								this.tauler[0].length - 1); y++) {
							if (x != fila || y != columna) {
								if (tauler[x][y] != casella.MINA) {
									this.trepitja(x, y);
								}
							}
						}
					}
				}
				break;
			}
		}
	}
/*
	public void juga() {
		Scanner entrada = new Scanner(System.in);
		int fila, columna;

		this.initTauler();
		this.initMines();

		if (this.tauler != null) {
			do {
				System.out.println(this.printTauler());
				System.out.println("Introdueix la fila a trepitxar: ");
				fila = entrada.nextInt() - 1;
				System.out.println("Introdueix la columna a trepitxar: ");
				columna = entrada.nextInt() - 1;

				this.trepitja(fila, columna);
			} while (this.casellesLliures > 0 && !explosio);
			System.out.println(this.printTauler());
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
	}*/
}
