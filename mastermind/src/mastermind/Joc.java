package mastermind;

import java.util.*;

enum pin {
	BLANC, NEGRE, BUIT
};

public class Joc {
	private pin[] resultat;
	private int[] combinacio;
	private int[] usuari;
	private int torn = 0;

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
	}

	public void setUsuari(int i, int j, int k, int l) {
		if (i >= 0 && i < 6 && j >= 0 && j < 6 && k >= 0 && k < 6 && l >= 0 && l < 6) {
			this.usuari[0] = i;
			this.usuari[1] = j;
			this.usuari[2] = k;
			this.usuari[3] = l;
		} else {
			this.usuari = null;
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

	public void juga() {
		Scanner entrada = new Scanner(System.in);

		this.generarCombinacio(true);

		do {
			int d;

			do {
				System.out.println("\nIntrodueix la combinació (nombres entre 0 i 5): ");
				d = entrada.nextInt();
			} while (d / 1000 > 5 || (d % 1000) / 100 > 5 || (d % 100) / 10 > 5 || (d % 10) > 5);

			this.setUsuari(d / 1000, (d % 1000) / 100, (d % 100) / 10, (d % 10));

			this.comprobarPins();
			this.imprimirPins();

			this.torn++;

		} while ((this.combinacio[0] != this.usuari[0] || this.combinacio[1] != this.usuari[1]
				|| this.combinacio[2] != this.usuari[2] || this.combinacio[3] != this.usuari[3]) && this.torn < 8
				&& this.usuari != null);

		if(this.torn < 9) {
			System.out.println("Has guanyat la partida en " + this.torn + " torns!");
		}
		else {
			System.out.println("S'han esgotat els 8 torns :(");
		}

		entrada.close();
	}

	public static void main(String[] args) {
		Joc j = new Joc();
		j.juga();
	}

}