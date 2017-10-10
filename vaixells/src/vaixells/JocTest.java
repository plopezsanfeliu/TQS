package vaixells;

import static org.junit.Assert.*;

import org.junit.Test;

import vaixells.Joc.estat;

/**
 * Classe de test del joc de guerra de vaixells.
 * @author Álex Fernández Escarabajal
 */
public class JocTest {

	private Joc j;

	/**
	 * Creem taulers de diferentes mides i comprovem que s'ha inicialitzat el
	 * joc amb la mida correcte en cada cas.
	 */
	@Test
	public void testInit() {
		// El constructor de Game ha de crear un tauler null si li passem 0 com
		// a paràmetre
		this.j = new Joc(0);
		assertNull(this.j.getTauler());

		// Comprovem altres casos != 0
		this.j = new Joc(1);
		// mirem l'alçada i l'amplada, que han de ser iguals entre ells
		assertEquals(1, this.j.getTauler()[0].length, this.j.getTauler().length);

		this.j = new Joc(2);
		assertEquals(2, this.j.getTauler()[0].length, this.j.getTauler().length);

		this.j = new Joc(5);
		assertEquals(5, this.j.getTauler()[0].length, this.j.getTauler().length);

		this.j = new Joc(100);
		assertEquals(100, this.j.getTauler()[0].length, this.j.getTauler().length);
	}

	/**
	 * Creem taulers de diferentes mides i comprovem que la inicialització (totes
	 * les caselles a AIGUA) es realitza correctament.
	 */
	@Test
	public void testInitTauler() {
		int[] mides = { 1, 2, 5, 100 };

		for (int m : mides) {
			this.j = new Joc(m);
			j.initTauler();

			for (int i = 0; i < m; i++) {
				for (int j = 0; j < m; j++) {
					assertEquals(estat.AIGUA, this.j.getTauler()[i][j]);
				}
			}
		}
	}

	/**
	 * Inicialitzem amb diferent nombre de vaixells i comprovem que ho fa
	 * correctament. També mirem de posar més vaixells que caselles i comprovem
	 * error.
	 */
	@Test
	public void testInitVaixells() {
		// En primer lloc programem taulers erronis (més vaixells que caselles)
		// i comprovem que el tauler resultant apunta a null (tauler de 2x2 amb
		// 5 vaixells)
		int v = 5;
		int m = 2;

		this.j = new Joc(m);
		this.j.initTauler();
		this.j.initVaixells(v);

		assertNull(this.j.getTauler());

		// Repetim operació amb tauler més gran (tauler de 5x5 amb 26 vaixells)
		v = 26;
		m = 5;

		this.j = new Joc(m);
		this.j.initTauler();
		this.j.initVaixells(v);

		assertNull(this.j.getTauler());

		// Provem cas límit amb totes les caselles vaixells (cas acceptable,
		// tauler no a null, tauler de 3x3 amb 9 vaixells)
		v = 9;
		m = 3;

		this.j = new Joc(m);
		this.j.initTauler();
		this.j.initVaixells(v);

		assertNotNull(this.j.getTauler());

		// Finalment provem un cas estàndard amb un tauler de 5x5 amb 5
		// vaixells
		v = 5;
		m = 5;

		this.j = new Joc(m);
		this.j.initTauler();
		this.j.initVaixells(v);

		assertNotNull(this.j.getTauler());
	}

	/**
	 * Comprovem que el nombre de vaixells és el que li indiquem, tot fent un
	 * sumatori manual recorrent el tauler buscant VAIXELLs.
	 */
	@Test
	public void testNombreVaixells() {
		int v = 2;
		int m = 3;
		int suma = 0;

		this.j = new Joc(m);
		this.j.initTauler();
		this.j.initVaixells(v);

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if (this.j.getTauler()[i][j] == estat.VAIXELL) {
					suma++;
				}
			}
		}
		assertEquals(v, suma);

		// Comprovem cas límit amb el sumatori d'un tauler amb totes les
		// caselles amb vaixells (cas límit, tauler de 5x5)
		v = 25;
		m = 5;
		suma = 0;

		this.j = new Joc(m);
		this.j.initTauler();
		this.j.initVaixells(v);

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				if (this.j.getTauler()[i][j] == estat.VAIXELL) {
					suma++;
				}
			}
		}
		assertEquals(v, suma);

		// Comprovem cas límit amb un tauler amb cap vaixell, el tauler
		// resultant ha de ser null
		v = 0;
		m = 5;

		this.j = new Joc(m);
		this.j.initTauler();
		this.j.initVaixells(v);

		assertNull(this.j.getTauler());
	}

	/**
	 * Creem uns quants taulers hard-coded i comprovem que les caselles son les que
	 * toquen.
	 */
	@Test
	public void testComprovarVaixells() {
		/*
		 * Tauler 1: 
		 * I O O
		 * O I O
		 * O O I
		 */
		this.j = new Joc(3);
		this.j.initTauler();
		this.j.initVaixells(3); // per inicialitzar variable vaixellsRestants,
		// a continuació resetejem el tauler

		estat[][] tauler1 = { { estat.VAIXELL, estat.AIGUA, estat.AIGUA }, 
				{ estat.AIGUA, estat.VAIXELL, estat.AIGUA },
				{ estat.AIGUA, estat.AIGUA, estat.VAIXELL } };

		this.j.setTauler(tauler1);

		assertEquals(3, this.j.getVaixellsRestants());

		j.dispara(0, 1); // Disparem a aigua i comprovem que no decrementa
		assertEquals(3, this.j.getVaixellsRestants());

		j.dispara(0, 0); // Disparem a baixell i comprovem que decrementa
		assertEquals(2, this.j.getVaixellsRestants());

		j.dispara(0, 0); // Tornem a disparar al mateix lloc i comprovem que no
		// decrementa el comptador per error
		assertEquals(2, this.j.getVaixellsRestants());

		j.dispara(2, 1); // Tornem a disparar aigua i comprovem que no decrem.
		assertEquals(2, this.j.getVaixellsRestants());

		// Disparem als dos vaixells que queden i comprovem final de partida
		j.dispara(1, 1);
		j.dispara(2, 2);
		assertEquals(0, this.j.getVaixellsRestants());

		/*
		 * Tauler 2 (cas límit, una posició i 1 vaixell): 
		 * I
		 */
		this.j = new Joc(1);
		this.j.initTauler();
		this.j.initVaixells(1); // per inicialitzar variable vaixellsRestants,

		assertEquals(1, this.j.getVaixellsRestants());

		j.dispara(0, 0); // Disparem a baixell i comprovem que fi de joc
		assertEquals(0, this.j.getVaixellsRestants());

		/*
		 * Tauler 3 (tauler ple):
		 * I I
		 * I I
		 */
		this.j = new Joc(2);
		this.j.initTauler();
		this.j.initVaixells(4); // queda ple

		assertEquals(4, this.j.getVaixellsRestants()); // Comprovem inicial

		j.dispara(0, 0); // Comprovem vaixell a vaixell el decrement
		assertEquals(3, this.j.getVaixellsRestants());
		j.dispara(0, 1);
		assertEquals(2, this.j.getVaixellsRestants());
		j.dispara(1, 0);
		assertEquals(1, this.j.getVaixellsRestants());
		j.dispara(1, 1);
		assertEquals(0, this.j.getVaixellsRestants());

		/*
		 * Tauler 4 (tauler estàndard de joc 5x5 amb 8 vaixells): 
		 * O X O X O
		 * X O O O X
		 * O O O O X
		 * X O O O O
		 * X O O X O
		 */
		this.j = new Joc(5);
		this.j.initTauler();
		this.j.initVaixells(8);

		estat[][] tauler2 = { { estat.AIGUA, estat.VAIXELL, estat.AIGUA, estat.VAIXELL, estat.AIGUA },
				{ estat.VAIXELL, estat.AIGUA, estat.AIGUA, estat.AIGUA, estat.VAIXELL },
				{ estat.AIGUA, estat.AIGUA, estat.AIGUA, estat.AIGUA, estat.VAIXELL },
				{ estat.VAIXELL, estat.AIGUA, estat.AIGUA, estat.AIGUA, estat.AIGUA },
				{ estat.VAIXELL, estat.AIGUA, estat.AIGUA, estat.VAIXELL, estat.AIGUA },};

		this.j.setTauler(tauler2);

		assertEquals(8, this.j.getVaixellsRestants()); // Comprovem inicial

		// Disparem a totes les aigues i comprovem que no s'ha decrementat
		j.dispara(0, 0);
		j.dispara(0, 2);
		j.dispara(0, 4);
		j.dispara(1, 1);
		j.dispara(1, 2);
		j.dispara(1, 3);
		j.dispara(2, 0);
		j.dispara(2, 1);
		j.dispara(2, 2);
		j.dispara(2, 3);
		j.dispara(3, 1);
		j.dispara(3, 2);
		j.dispara(3, 3);
		j.dispara(3, 4);
		j.dispara(4, 1);
		j.dispara(4, 2);
		j.dispara(4, 4);
		assertEquals(8, this.j.getVaixellsRestants()); // comprovem no decre.

		// Disparem ara a tots els vaixells i comprovem final del joc
		j.dispara(0, 1);
		j.dispara(0, 3);
		j.dispara(1, 0);
		j.dispara(1, 4);
		j.dispara(2, 4);
		j.dispara(3, 0);
		j.dispara(4, 0);
		j.dispara(4, 3);
		assertEquals(0, this.j.getVaixellsRestants()); // comprovem final joc
	}

}
