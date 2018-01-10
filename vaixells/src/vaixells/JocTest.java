package vaixells;

import static org.junit.Assert.*;

import org.junit.Test;

import vaixells.Joc.casella;
import vaixells.Joc.estat;

/**
 * Classe de test del joc de guerra de vaixells.
 * @author Álex Fernández Escarabajal
 */
public class JocTest {

	private Joc j;

	/**
	 * Testejem que els Enums no siguin nulls.
	 */
	@Test
	public void testEnums() {
		assertNotNull(estat.valueOf("INIT"));
		assertNotNull(estat.valueOf("JUGANT"));
		assertNotNull(estat.valueOf("GUANYAT"));
		assertNotNull(casella.valueOf("AIGUA"));
		assertNotNull(casella.valueOf("VAIXELL"));
		assertNotNull(casella.valueOf("TOCAT"));
	}
	
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
		
		this.j = new Joc(-1);
		assertNull(this.j.getTauler());

		// LOOP TEST - comprovem altres casos < 0
		for(int i = 1; i < 100; i++ ) {
			this.j = new Joc(i);
			// mirem l'alçada i l'amplada, que han de ser iguals entre ells
			assertEquals(i, this.j.getTauler()[0].length, this.j.getTauler().length);
		}
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
					assertEquals(casella.AIGUA, this.j.getTauler()[i][j]);
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
		assertTrue(j.getEstat().equals(estat.INIT));
		this.j.initVaixells(v);
		assertFalse(j.getEstat().equals(estat.JUGANT));
		assertFalse(j.getEstat().equals(estat.GUANYAT));

		assertNull(this.j.getTauler());

		// Repetim operació amb tauler més gran (tauler de 5x5 amb 26 vaixells)
		v = 26;
		m = 5;

		this.j = new Joc(m);
		this.j.initTauler();
		assertTrue(j.getEstat().equals(estat.INIT));
		this.j.initVaixells(v);
		assertFalse(j.getEstat().equals(estat.JUGANT));
		assertFalse(j.getEstat().equals(estat.GUANYAT));
		assertNull(this.j.getTauler());

		// Provem cas límit amb totes les caselles vaixells (cas acceptable,
		// tauler no a null, tauler de 3x3 amb 9 vaixells)
		v = 9;
		m = 3;

		this.j = new Joc(m);
		this.j.initTauler();
		assertTrue(j.getEstat().equals(estat.INIT));
		this.j.initVaixells(v);
		assertTrue(j.getEstat().equals(estat.JUGANT));
		assertFalse(j.getEstat().equals(estat.GUANYAT));
		assertNotNull(this.j.getTauler());

		// Finalment provem un cas estàndard amb un tauler de 5x5 amb 5
		// vaixells
		v = 5;
		m = 5;

		this.j = new Joc(m);
		this.j.initTauler();
		assertTrue(j.getEstat().equals(estat.INIT));
		this.j.initVaixells(v);
		assertTrue(j.getEstat().equals(estat.JUGANT));
		assertFalse(j.getEstat().equals(estat.GUANYAT));
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
				if (this.j.getTauler()[i][j] == casella.VAIXELL) {
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
					suma++;
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
		//SORTIDES POSSIBLES
		String sortidaError = "==============ERROR===============\n"
				+ "Coordenada referenciada incorrecta\n"
				+ "==============ERROR===============\n";
		String sortidaAigua = "Has donat a l'aigua\n"
				+ "=================";
		String sortidaJaTocat = "Ja has tocat un vaixell amb anterioritat\n"
				+ "========================================";
		String sortidaTocat = "Has tocat un vaixell!\n"
				+ "=====================";
		String sortidaGuanyat = "============\n"
				+ "Has Guanyat!\n"
				+ "============";
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

		casella[][] tauler1 = { { casella.VAIXELL, casella.AIGUA, casella.AIGUA }, 
				{ casella.AIGUA, casella.VAIXELL, casella.AIGUA },
				{ casella.AIGUA, casella.AIGUA, casella.VAIXELL } };

		this.j.setTauler(tauler1);
		assertNull("", this.j.getSortida());
		assertEquals(3, this.j.getVaixellsRestants());
		
		// Disparem fora del tauler i comprovem que no passa res, nomes mostra
		// error.
		j.dispara(3, 0); // Branch 1 (comprovem els dos casos del IF)
		j.dispara(0, 3);  // Branch 2
		assertEquals(3, this.j.getVaixellsRestants());
		
		j.dispara(-1, 0); // Disparem a aigua en negatiu per la fila i comprovem que no decrementa
		assertEquals(3, this.j.getVaixellsRestants());
		assertEquals(sortidaError, this.j.getSortida());
		j.dispara(0, -1); // Disparem a aigua en negatiu per la columna i comprovem que no decrementa
		assertEquals(3, this.j.getVaixellsRestants());
		assertEquals(sortidaError, this.j.getSortida());

		j.dispara(0, 1); // Disparem a aigua i comprovem que no decrementa
		assertEquals(3, this.j.getVaixellsRestants());
		assertEquals(sortidaAigua, this.j.getSortida());

		j.dispara(0, 0); // Disparem a baixell i comprovem que decrementa
		assertEquals(2, this.j.getVaixellsRestants());
		assertEquals(sortidaTocat, this.j.getSortida());

		j.dispara(0, 0); // Tornem a disparar al mateix lloc i comprovem que no
						// decrementa el comptador per error
		assertEquals(2, this.j.getVaixellsRestants());
		assertEquals(sortidaJaTocat, this.j.getSortida());

		j.dispara(2, 1); // Tornem a disparar aigua i comprovem que no decrem.
		assertEquals(2, this.j.getVaixellsRestants());
		assertEquals(sortidaAigua, this.j.getSortida());

		// Disparem als dos vaixells que queden i comprovem final de partida
		j.dispara(1, 1);
		j.dispara(2, 2);
		assertEquals(0, this.j.getVaixellsRestants());
		assertEquals(sortidaGuanyat, this.j.getSortida());

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

		casella[][] tauler2 = { { casella.AIGUA, casella.VAIXELL, casella.AIGUA, casella.VAIXELL, casella.AIGUA },
				{ casella.VAIXELL, casella.AIGUA, casella.AIGUA, casella.AIGUA, casella.VAIXELL },
				{ casella.AIGUA, casella.AIGUA, casella.AIGUA, casella.AIGUA, casella.VAIXELL },
				{ casella.VAIXELL, casella.AIGUA, casella.AIGUA, casella.AIGUA, casella.AIGUA },
				{ casella.VAIXELL, casella.AIGUA, casella.AIGUA, casella.VAIXELL, casella.AIGUA },};

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
	
	/**
	 * Fem Test de la Funcio Dispara amb metode de Caixa Blanca calculant el
	 * Path Coverage 
	 * 			1		(esquerra True, dret False)
	 * 		   / \
	 * 		  /	  3
	 * 		 /	 / \
	 * 		/   /   5		Arcs: 	13
	 * 	   /    4  / \		Nodes:	10
	 *    2   	 \ 6  7		V(G):	13-10+2 = 5
	 * 	   \  	  \ \ |		Path: 	5
	 * 	    \      \_\|
	 *       \        8		Path1: !1,!3,!5,7,8,9,10
	 * 	      \      / \	Path2: !1,!3,!5,7,!8,10
	 * 	       \    /  /	Path3: !1,!3,5,6,8,10
	 *          \  9  /		Path4: !1,!3,4,8,10
	 *           \ | /		Path5: 1,2,10
	 *            \|/
	 * 		       10 
	 */
	@Test
	public void testDispara() {
		
		/*
		 * Path1: !1,!3,!5,7,8,9,10
		 * Tauler: 
		 * I
		 */
		this.j = new Joc(1);
		this.j.initTauler();
		this.j.initVaixells(1);
		casella[][] taulerPath1 = { { casella.VAIXELL } };
		this.j.setTauler(taulerPath1);
		assertEquals(1, this.j.getVaixellsRestants());
		j.dispara(0, 0); 
		assertEquals(0, this.j.getVaixellsRestants());
		assertTrue(j.getEstat().equals(estat.GUANYAT));
		
		/*
		 * Path2: !1,!3,!5,7,!8,10
		 * Tauler: 
		 * I I
		 * O O
		 */
		this.j = new Joc(2);
		this.j.initTauler();
		this.j.initVaixells(2);
		casella[][] taulerPath2 = { { casella.VAIXELL,casella.VAIXELL },
				{ casella.AIGUA,casella.AIGUA }};
		this.j.setTauler(taulerPath2);
		assertEquals(2, this.j.getVaixellsRestants());
		j.dispara(0, 0); 
		assertEquals(1, this.j.getVaixellsRestants());
		assertTrue(j.getEstat().equals(estat.JUGANT));
		
		/*
		 * Path3: !1,!3,5,6,8,10
		 * Tauler: 
		 * I I
		 * O O
		 */
		this.j = new Joc(2);
		this.j.initTauler();
		this.j.initVaixells(2);
		casella[][] taulerPath3 = { { casella.VAIXELL,casella.VAIXELL },
				{ casella.AIGUA,casella.AIGUA }};
		this.j.setTauler(taulerPath3);
		assertEquals(2, this.j.getVaixellsRestants());
		j.dispara(1, 0); 
		assertEquals(2, this.j.getVaixellsRestants());
		assertTrue(j.getEstat().equals(estat.JUGANT));
		
		/*
		 * Path4: !1,!3,4,8,10
		 * Tauler: 
		 * I O
		 * O O
		 */
		this.j = new Joc(2);
		this.j.initTauler();
		this.j.initVaixells(1);
		casella[][] taulerPath4 = { { casella.VAIXELL,casella.AIGUA },
				{ casella.AIGUA,casella.AIGUA }};
		this.j.setTauler(taulerPath4);
		assertEquals(1, this.j.getVaixellsRestants());
		j.dispara(1, 0); 
		assertEquals(1, this.j.getVaixellsRestants());
		assertTrue(j.getEstat().equals(estat.JUGANT));
		
		/*
		 * Path5: 1,2,10
		 * Tauler: 
		 * I
		 */
		this.j = new Joc(1);
		this.j.initTauler();
		this.j.initVaixells(1);
		casella[][] taulerPath5 = { { casella.VAIXELL }};
		this.j.setTauler(taulerPath5);
		assertEquals(1, this.j.getVaixellsRestants());
		j.dispara(1, 0); 
		assertEquals(1, this.j.getVaixellsRestants());
		assertTrue(j.getEstat().equals(estat.JUGANT));
		
	}
	
	/**
	 * Creem una partida hardcoded i comprovem que el que es printa del tauler
	 * (String print) correspon amb el que toca, fent ús del "equals".
	 */
	@Test
	public void testPrintTauler() {
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

		casella[][] tauler = { { casella.VAIXELL, casella.AIGUA, casella.AIGUA }, 
				{ casella.AIGUA, casella.VAIXELL, casella.AIGUA },
				{ casella.AIGUA, casella.AIGUA, casella.VAIXELL } };

		this.j.setTauler(tauler);
				
		MockJoc m = new MockJoc();
		
		assertEquals(m.printTauler(), this.j.printTauler());
		
		// Disparem a un vaixell i comprovem que s'imprimeix correctament.
		j.dispara(0, 0);
		
		String tauler2 = "	1	2	3	\n" + 
				"1	X 	= 	= 	\n" + 
				"2	= 	I 	= 	\n" + 
				"3	= 	= 	I 	\n";
		
		assertEquals(tauler2, this.j.printTauler());
		
		// Finalment disparem a aigua i comprovem que el tauler no s'inmuta.
		j.dispara(0, 1);
		assertEquals(tauler2, this.j.printTauler());
	}
	
	@Test
	public void testEstatJoc() {
		// Testegem estat INIT, cada cop que iniciem un joc es compliran els
		// següents 3 estats
		this.j = new Joc(3);
		assertEquals(estat.INIT, j.getEstat());
		
		this.j.initTauler();
		assertEquals(estat.INIT, j.getEstat());
		
		this.j.initVaixells(3);
		assertEquals(estat.JUGANT, j.getEstat());
		
		// Creem un tauler amb 4 caselles i 4 vaixells i anem disparant 1 a 1
		// i comprovem que no es guanya fina a l'últim.
		this.j = new Joc(2);
		this.j.initTauler();
		this.j.initVaixells(4);

		j.dispara(0, 0);
		assertEquals(estat.JUGANT, j.getEstat());
		j.dispara(0, 1);
		assertEquals(estat.JUGANT, j.getEstat());
		j.dispara(1, 0);
		assertEquals(estat.JUGANT, j.getEstat());
		// Tornem a disparar a una casella on ja li hem donat per veure que
		// seguim jugant
		j.dispara(0, 1);
		assertEquals(estat.JUGANT, j.getEstat());
		
		// disparem a últim vaixell i comprovem guanyada
		j.dispara(1, 1);
		assertEquals(estat.GUANYAT, j.getEstat());
		
		
		// Cas límit, creem un tauler de 4 posicions i 1 vaixell, disparem a
		// les 3 aigues, comprovem estar, disparem al vaixell i comprovem final.
		this.j = new Joc(2);
		this.j.initTauler();
		this.j.initVaixells(1); // per inicialitzar variable vaixellsRestants,
		// a continuació resetejem el tauler
		
		casella[][] tauler = { { casella.VAIXELL, casella.AIGUA }, 
				{ casella.AIGUA, casella.AIGUA } };

		this.j.setTauler(tauler);
		
		// Disparem a les 3 aigues
		j.dispara(0, 1);
		j.dispara(1, 0);
		j.dispara(1, 1);
		assertEquals(estat.JUGANT, j.getEstat());
		
		j.dispara(0, 0);
		assertEquals(estat.GUANYAT, j.getEstat());
	}

}
