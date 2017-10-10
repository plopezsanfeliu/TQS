package buscamines;

import static org.junit.Assert.*;

import org.junit.Test;

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
		dificultat[] dificultats = { dificultat.FACIL, dificultat.MIG, dificultat.DIFICIL };

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
					assertEquals(estat.LLIURE, this.j.getTauler()[i][j]);
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
		int m = 10;
		int n = 10;
		int esperat = 10;
		int suma = 0;
		this.j = new Joc(dificultat.FACIL);
		this.j.initTauler();
		this.j.initMines();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (this.j.getTauler()[i][j] == estat.MINA) {
					suma++;
				}
			}
		}
		assertEquals(esperat, suma);

		// Reiniciem tauler fàcil
		suma = 0;
		this.j.initTauler();
		this.j.initMines();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (this.j.getTauler()[i][j] == estat.MINA) {
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

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (this.j.getTauler()[i][j] == estat.MINA) {
					suma++;
				}
			}
		}
		assertEquals(esperat, suma);

		// Reiniciem tauler mig
		suma = 0;
		this.j.initTauler();
		this.j.initMines();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (this.j.getTauler()[i][j] == estat.MINA) {
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

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (this.j.getTauler()[i][j] == estat.MINA) {
					suma++;
				}
			}
		}
		assertEquals(esperat, suma);

		// Reiniciem tauler dificil
		suma = 0;
		this.j.initTauler();
		this.j.initMines();

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (this.j.getTauler()[i][j] == estat.MINA) {
					suma++;
				}
			}
		}
		assertEquals(esperat, suma);

	}

	/**
	 * Creem uns quants taulers hard-coded i comprovem que les caselles son les que
	 * toquen.
	 */
	@Test
	public void testComprovarMines() {
		/*
		 * Tauler 1: 
		 * O X O O O O O O O O
		 * O O O O O O X O X O
		 * O X O O O O X O O O
		 * O O O O O O O O O O
		 * O O O O O O O O O O
		 * O O O X O O O O O O
		 * O O O O O O O X O O
		 * O O O O O O O O O O
		 * O X O O O O O O X O
		 * O O O O O O O O O X
		 */
		this.j = new Joc(dificultat.FACIL);
		this.j.initTauler();

		estat[][] tauler1 = { { estat.LLIURE, estat.MINA, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE }, 
				{ estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.MINA, estat.LLIURE, estat.MINA, estat.LLIURE }, 
				{ estat.LLIURE, estat.MINA, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.MINA, estat.LLIURE, estat.LLIURE, estat.LLIURE }, 
				{ estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE }, 
				{ estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE }, 
				{ estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.MINA, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE }, 
				{ estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.MINA, estat.LLIURE, estat.LLIURE }, 
				{ estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE }, 
				{ estat.LLIURE, estat.MINA, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.MINA, estat.LLIURE }, 
				{ estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.LLIURE, estat.MINA }, };

		j.initMines(); // Fa set a l'atribut casellesLliures
		j.setTauler(tauler1);

		// Comprovem en primer lloc que hi ha 90 caselles lliures
		assertEquals(100-10, j.getCasellesLliures());

		// Repetim comprovació després d'haver trepitxat una casella lliure i
		// veiem que decrementa
		j.trepitja(1, 1);
		assertEquals(100-10-1, j.getCasellesLliures());

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
		assertEquals(100-10, j.getCasellesLliures());
		j.trepitja(0, 0);
		assertEquals(100-10-1, j.getCasellesLliures());
		j.trepitja(0, 9);
		assertEquals(100-10-2, j.getCasellesLliures());
		j.trepitja(9, 0);
		assertEquals(100-10-3, j.getCasellesLliures());
		// Assegurem que no ha acabat el joc
		assertFalse(j.getExplosio());
		j.trepitja(9, 9); // Finalment trepitxem la mina de la cantonada
		assertTrue(j.getExplosio());

	}

}
