package mastermind;

import static org.junit.Assert.*;

import org.junit.Test;

public class JocTest {

	private Joc j;

	/**
	 * Les entrades de l'usuari poden anar de 0000 a 5555, que es parseja a int al
	 * programa. Es comproven els casos límit i valors incorrectes.
	 */
	@Test
	public void testComprovarEntrada() {
		this.j = new Joc();
		// Valor negatiu
		assertFalse(j.comprovarEntrada(-985));

		// Cas límit inferior
		assertFalse(j.comprovarEntrada(-1));

		// Cas límit inferior correcte
		assertTrue(j.comprovarEntrada(0000));
		
		// Cas intermig
		assertTrue(j.comprovarEntrada(1253));

		// Cas límit superior
		assertTrue(j.comprovarEntrada(5555));

		// Cas no acceptable límit
		assertFalse(j.comprovarEntrada(5556));

		// Cas molt superior a l'acceptat
		assertFalse(j.comprovarEntrada(4522120));

	}

	/**
	 * Inicialitzem unes apostes d'usuari, entre les quals n'hi ha de correctes i
	 * que no són vàlides, comprovem que s'accepten o no correctament. En test
	 * anterior simulavem entrada per teclat i en aquest mirem de crear el tauler
	 * mitjançant línies de codi.
	 */
	@Test
	public void testSetUsuari() {
		this.j = new Joc();

		// Comencem amb apostes incorrectes, comprovem que la aposta és null i,
		// per tant, se surt del joc.
		j.intentUsuari(8, 7, 9, 6);
		assertNull(j.getUsuari());

		// Cas negatiu
		this.j = new Joc();
		j.intentUsuari(5, 5, -1, 2);
		assertNull(j.getUsuari());

		// Cas límit, només un calor incorrecte a extrem
		this.j = new Joc();
		j.intentUsuari(2, 1, 5, 6);
		assertNull(j.getUsuari());

		// Valor incorrecte al mig
		this.j = new Joc();
		j.intentUsuari(3, 2, 9, 2);
		assertNull(j.getUsuari());

		// Tots valors negatius
		this.j = new Joc();
		j.intentUsuari(-1, -2, -9, -2);
		assertNull(j.getUsuari());

		// Provem ara amb valors correctes
		// Mínims
		this.j = new Joc();
		j.intentUsuari(0, 0, 0, 0);
		assertNotNull(j.getUsuari());

		// Màxims
		this.j = new Joc();
		j.intentUsuari(5, 5, 5, 5);
		assertNotNull(j.getUsuari());

		// Valors aleatoris
		this.j = new Joc();
		j.intentUsuari(2, 0, 5, 3);
		assertNotNull(j.getUsuari());
	}

	/**
	 * Comprovem que, generant combinacions a endevinar, totes són correctes agafant
	 * valors entre 0 i 5, en cap cas majors o negatius.
	 */
	@Test
	public void testGenerarCombinacio() {
		final int MAX_COMBI = 100;

		this.j = new Joc();

		for (int i = 0; i < MAX_COMBI; i++) {
			this.j.generarCombinacio(false); // False indica que no es mostra
			// el valor de la combinació per pantalla.
			int[] combinacio = this.j.getCombinacio();

			for (int j = 0; j < combinacio.length; j++) {
				assertTrue(combinacio[j] > -1);
				assertTrue(combinacio[j] < 6);
			}
		}
	}

	/**
	 * Programem codis hard-coded i anirem endenvinant, tot comprovant que els
	 * valors dels pins de resposta són correctes.
	 */
	@Test
	public void testComprovarPins() {
		this.j = new Joc();

		// Creem cas en que no s'encerti ni una, pel que el resultat haurà de ser
		// 4 buits.
		int[] combinacio1 = { 0, 1, 2, 3 };
		int[] usuari1 = { 4, 5, 4, 5 };
		this.j.setCombinacio(combinacio1);
		this.j.setUsuari(usuari1);
		this.j.comprobarPins();

		for (int i = 0; i < this.j.getResultat().length; i++) {
			assertEquals(pin.BUIT, this.j.getResultat()[i]);
		}

		// Cas en que totes les respostes estan desubicades, pel que hi ha
		// d'haver 4 blancs.
		int[] combinacio2 = { 0, 1, 2, 3 };
		int[] usuari2 = { 3, 2, 1, 0 };
		this.j.setCombinacio(combinacio2);
		this.j.setUsuari(usuari2);
		this.j.comprobarPins();

		for (int i = 0; i < this.j.getResultat().length; i++) {
			assertEquals(pin.BLANC, this.j.getResultat()[i]);
		}

		// Combinació encertada, resultant pins negres.
		int[] combinacio3 = { 5, 4, 3, 2 };
		int[] usuari3 = { 5, 4, 3, 2 };
		this.j.setCombinacio(combinacio3);
		this.j.setUsuari(usuari3);
		this.j.comprobarPins();

		for (int i = 0; i < this.j.getResultat().length; i++) {
			assertEquals(pin.NEGRE, this.j.getResultat()[i]);
		}

		// Combinat dels casos anteriors.
		int[] combinacio4 = { 5, 3, 1, 2 };
		int[] usuari4 = { 3, 5, 0, 2 };
		this.j.setCombinacio(combinacio4);
		this.j.setUsuari(usuari4);
		this.j.comprobarPins();

		assertEquals(pin.BLANC, this.j.getResultat()[0]);
		assertEquals(pin.BLANC, this.j.getResultat()[1]);
		assertEquals(pin.BUIT, this.j.getResultat()[2]);
		assertEquals(pin.NEGRE, this.j.getResultat()[3]);

	}

	/**
	 * Comprovacions de l'estat del joc en funció de diferentes situacions.
	 * Assegurem compliment de màquina d'estats.
	 */
	@Test
	public void testEstatJoc() {
		// Creem jugada senzilla on comprovem estat inicial i un cop guanyat
		// que s'ha guanyat.
		this.j = new Joc();
		assertEquals(estat.JUGANT, this.j.getEstat());
		int[] combinacio1 = { 0, 1, 2, 3 };

		this.j.setCombinacio(combinacio1);
		this.j.intentUsuari(0, 1, 2, 3);
		this.j.comprobarPins();
		assertEquals(estat.GUANYAT, this.j.getEstat());

		// Comprovem ara un joc on fallem 8 vegades i que l'estat és perdut,
		// cas límit.
		this.j = new Joc();
		this.j.setCombinacio(combinacio1);

		for (int i = 0; i < 8; i++) {
			this.j.intentUsuari(3, 2, 1, 0);
			this.j.comprobarPins();
		}
		assertEquals(estat.PERDUT, this.j.getEstat());

		// Comprovem cas límit inferior, on fallem 7 vegades, comprovem que no
		// està perdut, guanyem, i comprovem estat guanyat.
		this.j = new Joc();
		this.j.setCombinacio(combinacio1);

		for (int i = 0; i < 7; i++) {
			this.j.intentUsuari(3, 2, 1, 0);
			this.j.comprobarPins();
		}
		assertEquals(estat.JUGANT, this.j.getEstat());

		// Procedim a encertar a l'últim intent
		this.j.intentUsuari(0, 1, 2, 3);
		this.j.comprobarPins();

		// I comprovem que hem guanyat la partida.
		assertEquals(estat.GUANYAT, this.j.getEstat());

		// Procedim a tractar un cas especial, comprovem que, un cop perdut, no
		// és possible guanyar fent un altre intent i encertant (fer trampes).
		this.j = new Joc();
		this.j.setCombinacio(combinacio1);

		for (int i = 0; i < 8; i++) {
			this.j.intentUsuari(3, 2, 1, 0);
			this.j.comprobarPins();
		}

		this.j.intentUsuari(0, 1, 2, 3);
		this.j.comprobarPins();
		assertEquals(estat.PERDUT, this.j.getEstat());
	}

}
