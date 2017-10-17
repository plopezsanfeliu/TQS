package mastermind;

import java.util.*;

enum pin {
	BLANC, NEGRE, BUIT
};

enum estat {
	JUGANT, GUANYAT, PERDUT
};

public class Joc {
	private pin[] resultat;
	private int[] combinacio;
	private int[] usuari;
	private int torn = 0;
	private estat estatJoc;

	public int[] getCombinacio() {
		return combinacio;
	}

	public void setCombinacio(int[] combinacio) {
		this.combinacio = combinacio;
	}

	public int[] getUsuari() {
		return usuari;
	}

	public void setUsuari(int[] usuari) {
		this.usuari = usuari;
	}

	public pin[] getResultat() {
		return resultat;
	}

	public Joc() {
		this.combinacio = new int[4];
		this.usuari = new int[4];
		this.resultat = new pin[4];
		this.estatJoc = estat.JUGANT;
	}
	
	public estat getEstat() {
		return this.estatJoc;
	}

	public void intentUsuari(int i, int j, int k, int l) {
		final int MAX = 6;
		final int MIN = 0;
		if (i >= MIN && i < MAX && j >= MIN && j < MAX && k >= MIN && k < MAX && l >= MIN && l < MAX) {
			this.usuari[0] = i;
			this.usuari[1] = j;
			this.usuari[2] = k;
			this.usuari[3] = l;
		} else {
			this.usuari = null;
		}
		
		this.torn++;
		if(this.torn > 7) {
			this.estatJoc = estat.PERDUT;
		}
	}

	public void generarCombinacio(boolean imprimir) {
		for (int i = 0; i < this.combinacio.length; i++)
			combinacio[i] = new Random().nextInt(6);

		if (imprimir) {
			System.out.println("La combinació és: " + this.combinacio[0] + this.combinacio[1] + this.combinacio[2]
					+ this.combinacio[3]);
		}

	}

	public void comprobarPins() {
		final int MAX_TORNS = 9;
		int suma = 0;
		for (int i = 0; i < this.resultat.length; i++) {
			this.resultat[i] = pin.BUIT;
			if (this.usuari[i] == this.combinacio[i]) {
				this.resultat[i] = pin.NEGRE;
			} else {
				int j = 0;
				for (int k = 0; k < 3; k++, j++) {
					if (j == i) {
						j++;
					}
					if (this.resultat[i] == pin.BLANC || this.usuari[i] == this.combinacio[j]) {
						this.resultat[i] = pin.BLANC;
					}
				}
			}
		}
		
		for (int i = 0; i < this.resultat.length; i++) {
			if(resultat[i] == pin.NEGRE) {
				suma++;
			}
		}
		
		if(suma == 4 && this.torn < MAX_TORNS) {
			this.estatJoc = estat.GUANYAT;
		}
	}

	public void imprimirPins() {
		System.out.println("Pin1\tPin2\tPin3\tPin4");
		for (int i = 0; i < this.resultat.length; i++) {
			if (resultat[i] == pin.BLANC) {
				System.out.print("B\t");
			} else {
				if (resultat[i] == pin.NEGRE) {
					System.out.print("N\t");
				} else {
					System.out.print("\t");
				}
			}
		}
	}

	public boolean comprovarEntrada(int usuari) {
		boolean valid = false;

		if(usuari > -1 && usuari < 5556) { 
			valid = true;
		}
		return valid;
	}

	public void juga() {
		Scanner entrada = new Scanner(System.in);

		this.generarCombinacio(true);

		do {
			String numero;
			int d = 0;

			do {
				System.out.println("\nIntrodueix la combinació de 4 nombres (entre 0 i 5): ");				
				numero = entrada.nextLine();
				d = Integer.parseInt(numero);
			} while (!comprovarEntrada(d) || numero.length() != 4);

			this.intentUsuari(d / 1000, (d % 1000) / 100, (d % 100) / 10, (d % 10));

			this.comprobarPins();
			this.imprimirPins();

		} while (this.estatJoc == estat.JUGANT && this.usuari != null);

		if(this.estatJoc == estat.GUANYAT) {
			System.out.println("Has guanyat la partida en el torn " + this.torn + "!");
		}
		else {
			if(this.estatJoc == estat.PERDUT) {
				System.out.println("S'han esgotat els 8 torns :(");
			}
			else {
				System.out.println("Error no tractat");
			}
		}

		entrada.close();
	}

	public static void main(String[] args) {
		Joc j = new Joc();
		j.juga();
	}

}