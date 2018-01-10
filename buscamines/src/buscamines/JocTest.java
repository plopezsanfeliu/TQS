package buscamines;

import static org.junit.Assert.*;

import org.junit.Test;

import buscamines.MockJoc;

/**
 * Classe de test del joc del buscamines.
 * 
 * @author Álex Fernández Escarabajal
 */
public class JocTest {

	private Joc j;

	/**
	 * Creem taulers amb diferents dificultats i comprovem que s'ha inicialitzat el
	 * joc amb la mida de tauler correcte en cada cas.
	 */
	@Test
	public void testInit() {
		// Comprovem que les mides dels taulers siguin les apropiades en cada
		// dificultat
		this.j = new Joc(dificultat.FACIL);
		this.j.initTauler();
		// Mirem l'alçada i l'amplada, que han de ser iguals entre ells en
		// aquest cas
		assertEquals(10, this.j.getTauler()[0].length, this.j.getTauler().length);

		// El mateix amb intermig (16x16)
		this.j = new Joc(dificultat.MIG);
		this.j.initTauler();
		assertEquals(16, this.j.getTauler()[0].length, this.j.getTauler().length);

		// En últim cas, mirem per separat amplada i llargada doncs en nivell
		// difícil aquests són diferents
		this.j = new Joc(dificultat.DIFICIL);
		this.j.initTauler();
		assertEquals(16, this.j.getTauler().length);
		assertEquals(30, this.j.getTauler()[0].length);
	}

	/**
	 * Creem taulers de diferentes dificultats (mides) i comprovem que la
	 * inicialització (totes les caselles a LLIURE) es realitza correctament.
	 */
	@Test
	public void testInitTauler() {
		final int MAX_LOOPS = 100;
		dificultat[] dificultats = { dificultat.FACIL, dificultat.MIG, dificultat.DIFICIL };

		// INICI LOOP TEST
		for (int iter = 0; iter < MAX_LOOPS; iter++) {
			for (dificultat dificultat_actual : dificultats) {
				this.j = new Joc(dificultat_actual);
				j.initTauler();

				int m = 0, n = 0;
				switch (dificultat_actual) {
				case FACIL:
					m = 10;
					n = 10;
					break;
				case MIG:
					m = 16;
					n = 16;
					break;
				case DIFICIL:
					m = 16;
					n = 30;
					break;
				}

				for (int i = 0; i < m; i++) {
					for (int j = 0; j < n; j++) {
						assertEquals(casella.LLIURE, this.j.getTauler()[i][j]);
					}
				}
			}
		}

	}

	/**
	 * Comprovem que el nombre de mines és el que li indiquem, tot fent un sumatori
	 * manual recorrent el tauler buscant MINAs.
	 */
	@Test
	public void testInitMines() {
		// Comencem amb dificultat fàcil
		int m, n, esperat, suma, i;
		final int MAX_LOOPS = 100;

		// INICI LOOP TEST
		for (int iter = 0; iter < MAX_LOOPS; iter++) {
			suma = 0;
			esperat = 10;
			m = 10;
			n = 10;

			this.j = new Joc(dificultat.FACIL);
			this.j.initTauler();
			this.j.initMines();

			for (i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (this.j.getTauler()[i][j] == casella.MINA) {
						suma++;
					}
				}
			}
			assertEquals(esperat, suma);

			// Cambiem a tauler mig
			suma = 0;
			esperat = 40;
			m = 16;
			n = 16;

			this.j = new Joc(dificultat.MIG);
			this.j.initTauler();
			this.j.initMines();

			for (i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (this.j.getTauler()[i][j] == casella.MINA) {
						suma++;
					}
				}
			}
			assertEquals(esperat, suma);

			// Cambiem a tauler difícil
			suma = 0;
			esperat = 99;
			m = 16;
			n = 30;

			this.j = new Joc(dificultat.DIFICIL);
			this.j.initTauler();
			this.j.initMines();

			for (i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (this.j.getTauler()[i][j] == casella.MINA) {
						suma++;
					}
				}
			}
			assertEquals(esperat, suma);
		}
	}

	/**
	 * Creem uns quants taulers hard-coded i comprovem que les caselles son les que
	 * toquen.
	 */
	@Test
	public void testComprovarMines() {
		/*
		 * Tauler 1: O X O O O O O O O O O O O O O O X O X O O X O O O O X O O O O O O O
		 * O O O O O O O O O O O O O O O O O O O X O O O O O O O O O O O O O X O O O O O
		 * O O O O O O O O X O O O O O O X O O O O O O O O O O X
		 */
		this.j = new Joc(dificultat.FACIL);
		this.j.initTauler();

		casella[][] tauler1 = {
				{ casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
					casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.MINA,
						casella.LLIURE, casella.MINA, casella.LLIURE },
				{ casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.MINA,
							casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
								casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
									casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE,
										casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
											casella.MINA, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
												casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
													casella.LLIURE, casella.MINA, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
														casella.LLIURE, casella.LLIURE, casella.MINA }, };

		j.initMines(); // Fa set a l'atribut casellesLliures
		j.setTauler(tauler1);

		// Comprovem en primer lloc que hi ha 90 caselles lliures
		assertEquals(100 - 10, j.getCasellesLliures());

		// Repetim comprovació després d'haver trepitxat una casella lliure i
		// veiem que decrementa
		j.trepitja(1, 1);
		assertEquals(100 - 10 - 1, j.getCasellesLliures());
		
		// Com queda només una mina repetim la última trepitjada i comprovem que tot
		// i així no decrementa el comptador de mines al ser la mateixa.
		j.trepitja(1, 1);
		
		// Trepitgem una casella invàlida (fora de tauler) i comprovem que el
		// joc no peta.
		j.trepitja(1, 66);
		j.trepitja(66, 1);

		// Trepitxem mina i comprovem final de joc
		assertFalse(j.getExplosio()); // No final
		j.trepitja(8, 8);
		assertTrue(j.getExplosio()); // Final

		// Anem a provar casos limit, amb el mateix tauler trepitxarem caselles
		// que fan cantoneres al tauler, primer les 3 lliures i després la mina
		this.j = new Joc(dificultat.FACIL);
		this.j.initTauler();
		j.initMines();
		j.setTauler(tauler1);

		// Comprovem trepitjada a trepitjada que va decrementant el valor
		assertEquals(100 - 10, j.getCasellesLliures());
		j.trepitja(0, 0);
		assertEquals(100 - 10 - 1, j.getCasellesLliures());
		j.trepitja(0, 9);
		assertEquals(100 - 10 - 2, j.getCasellesLliures());
		j.trepitja(9, 0);
		assertEquals(100 - 10 - 3, j.getCasellesLliures());
		// Assegurem que no ha acabat el joc
		assertFalse(j.getExplosio());
		j.trepitja(9, 9); // Finalment trepitxem la mina de la cantonada
		assertTrue(j.getExplosio());

	}
	
	@Test
	public void testPrintTauler() {
		MockJoc m = new MockJoc(); // Tauler de referència de mock
		this.j = new Joc(dificultat.FACIL);
		this.j.initTauler();
		
		casella[][] tauler = {
				{ casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
					casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.MINA,
						casella.LLIURE, casella.MINA, casella.LLIURE },
				{ casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.MINA,
							casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
								casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
									casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE,
										casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
											casella.MINA, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
												casella.LLIURE, casella.LLIURE, casella.LLIURE },
				{ casella.LLIURE, casella.MINA, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
													casella.LLIURE, casella.MINA, casella.LLIURE },
				{ casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE, casella.LLIURE,
														casella.LLIURE, casella.LLIURE, casella.MINA }, };
		j.setTauler(tauler);
		
		assertEquals(m.printTauler(), this.j.printTauler());

		// Provem de trepitjar una casella i mirem que el resultat sigui l¡esperat
		j.trepitja(4, 5);
		String actual = "	1	2	3	4	5	6	7	8	9	10	\n" + 
				"1	O 	X 	1 	 	 	1 	O 	O 	O 	O 	\n" + 
				"2	O 	O 	2 	 	 	2 	X 	O 	X 	O 	\n" + 
				"3	O 	X 	1 	 	 	2 	X 	3 	1 	1 	\n" + 
				"4	O 	O 	1 	 	 	1 	1 	1 	 	 	\n" + 
				"5	O 	O 	1 	1 	1 	 	 	 	 	 	\n" + 
				"6	O 	O 	O 	X 	1 	 	1 	1 	1 	 	\n" + 
				"7	O 	O 	1 	1 	1 	 	1 	X 	1 	 	\n" + 
				"8	O 	O 	1 	 	 	 	1 	2 	2 	1 	\n" + 
				"9	O 	X 	1 	 	 	 	 	1 	X 	O 	\n" + 
				"10	O 	O 	1 	 	 	 	 	1 	O 	X \t\n";
		assertEquals(actual, this.j.printTauler());
		
	}

}

// Exploratory testing: 205-209
// Loop test: 55, 97
// Mock objects: 241, 268
