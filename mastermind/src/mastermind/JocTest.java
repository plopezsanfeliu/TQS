package mastermind;

import static org.junit.Assert.*;

import org.junit.Test;

public class JocTest {

	private Joc j;

	/**
	 * Inicialitzem unes apostes d'usuari, entre les quals n'hi ha de correctes i
	 * que no són vàlides, comprovem que s'accepten o no correctament.
	 */
	@Test
	public void testSetUsuari() {
		this.j = new Joc();

		// Comencem amb apostes incorrectes, comprovem que la aposta és null i,
		// per tant, se surt del joc.
		j.setUsuari(8, 7, 9, 6);
		assertNull(j.getUsuari());

		// Cas negatiu
		this.j = new Joc();
		j.setUsuari(5, 5, -1, 2);
		assertNull(j.getUsuari());

		// Cas límit, només un calor incorrecte a extrem
		this.j = new Joc();
		j.setUsuari(2, 1, 5, 6);
		assertNull(j.getUsuari());

		// Valor incorrecte al mig
		this.j = new Joc();
		j.setUsuari(3, 2, 9, 2);
		assertNull(j.getUsuari());

		// Tots valors negatius
		this.j = new Joc();
		j.setUsuari(-1, -2, -9, -2);
		assertNull(j.getUsuari());

		// Provem ara amb valors correctes
		// Mínims
		this.j = new Joc();
		j.setUsuari(0, 0, 0, 0);
		assertNotNull(j.getUsuari());

		// Màxims
		this.j = new Joc();
		j.setUsuari(5, 5, 5, 5);
		assertNotNull(j.getUsuari());

		// Valors aleatoris
		this.j = new Joc();
		j.setUsuari(2, 0, 5, 3);
		assertNotNull(j.getUsuari());
	}

	/**
	 * Comprovem que, generant combinacions a endevinar, totes són correctes agafant
	 * valors entre 0 i 5, en cap cas majors o negatius.
	 */
	@Test
	public void testGenerarCombinacio() {
		final int MAX_COMBI = 100;
		final int POSICIONS = 4;

		this.j = new Joc();

		for (int i = 0; i < MAX_COMBI; i++) {
			this.j.generarCombinacio(false); // False indica que no es mostra
			// el valor de la combinació per pantalla.
			int[] combinacio = this.j.getCombinacio();

			for (int j = 0; j < POSICIONS; j++) {
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
		final int POS = 4;

		this.j = new Joc();

		// Creem cas en que no s'encerti ni una, pel que el resultat haurà de ser
		// 4 buits.
		int[] combinacio1 = { 0, 1, 2, 3 };
		int[] usuari1 = { 4, 5, 4, 5 };
		this.j.setCombinacio(combinacio1);
		this.j.setUsuari(usuari1);
		this.j.comprobarPins();

		for (int i = 0; i < POS; i++) {
			assertEquals(pin.BUIT, this.j.getResultat()[i]);
		}

		// Cas en que totes les respostes estan desubicades, pel que hi ha
		// d'haver 4 blancs.
		int[] combinacio2 = { 0, 1, 2, 3 };
		int[] usuari2 = { 3, 2, 1, 0 };
		this.j.setCombinacio(combinacio2);
		this.j.setUsuari(usuari2);
		this.j.comprobarPins();

		for (int i = 0; i < POS; i++) {
			assertEquals(pin.BLANC, this.j.getResultat()[i]);
		}

		// Combinació encertada, resultant pins negres.
		int[] combinacio3 = { 5, 4, 3, 2 };
		int[] usuari3 = { 5, 4, 3, 2 };
		this.j.setCombinacio(combinacio3);
		this.j.setUsuari(usuari3);
		this.j.comprobarPins();

		for (int i = 0; i < POS; i++) {
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
}
