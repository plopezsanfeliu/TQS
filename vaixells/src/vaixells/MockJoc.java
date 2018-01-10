package vaixells;

import vaixells.Joc.casella;

public class MockJoc {

	private casella[][] tauler = { { casella.VAIXELL, casella.AIGUA, casella.AIGUA },
			{ casella.AIGUA, casella.VAIXELL, casella.AIGUA }, { casella.AIGUA, casella.AIGUA, casella.VAIXELL } };

	public String printTauler() {
		String print = "";
		print = print.concat("\t");
		for (int iter = 0; iter < this.tauler.length; iter++) {
			print = print.concat(iter + 1 + "\t");
		}
		print = print.concat("\n");
		for (int fila = 0; fila < this.tauler.length; fila++) {
			print = print.concat(fila + 1 + "\t"); // Imprimim núm. de fila
			for (int columna = 0; columna < this.tauler.length; columna++) {
				switch (this.tauler[fila][columna]) {
				case AIGUA:
					print = print.concat("= \t");
					break;
				default:
					print = print.concat("I \t");
					break;
				}
			}
			print = print.concat("\n");
		}
		return print;
	}

}
