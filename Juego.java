/**
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
package lifesgame;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Juego {

	// CONSTANTES
	private static final String LOAD = "LOAD";
	private static final String SAVE = "SAVE";
	private static final String NEWGAME = "NEW";
	private static final String EXIT = "EXIT";
	private static final String HELP = "HELP";
	private static final String NEXT = "n";
	private static final String SIMULA = "SIM";
	private static final String PROMPT = "ORDEN>>";
	private static int generation;
	private static Tablero actual;

	public static void draw(Tablero t) {
		int rows = t.rows();
		int columns = t.columns();
		String cell = "()";
		String none = "██";

		clearConsole();
		// Imprimir la cabecera
		System.out.printf("    ");
		for (int i = 0; i < t.columns(); i++) {
			System.out.printf("%-2d", (i + 1) % 10);
		}

		// Imprimir el tablero
		for (int i = 0; i < t.rows(); i++) {
			System.out.println();
			System.out.printf("%-4d", (i + 1) % 10);
			for (int j = 0; j < t.columns(); j++) {
				if (t.getCell(i, j).isAlive()) {
					System.out.print(cell);
				} else {
					System.out.print(none);
				}
			}
		}

	}

	private static Tablero tick(Tablero actual) {
		/*
		 * SI ESTÁ VIVA: < 2 vecinos -> muere 2 o 3 vecinos -> sobrevive > 3
		 * vecinos -> muere SI ESTÁ MUERTE: 3 vecinos -> nace
		 */

		// NUEVO TABLERO QUE ALMACENARÁ LA SITUACIÓN FUTURA
		int rows = actual.rows();
		int columns = actual.columns();

		Tablero futuro = new Tablero(0, rows, columns);

		/* Recorrido del tablero actual y creacion del tablero futuro */
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {

				Casilla leida = actual.getCell(i, j);

				// Casos en los que está viva
				if (leida.isAlive()) {
					// Solo si tiene 2 o 3 vecinos, en el futuro estará
					if (leida.getNear(actual) == 2
							|| leida.getNear(actual) == 3) {
						futuro.put(true, i, j);
					}
				} else {
					// Solo si tiene exactamente 3 vecinos, renace en el futuro.
					if (leida.getNear(actual) == 3) {
						futuro.put(true, i, j);
					}
				}

			}
		}

		// Ya está construido el nuevo tablero
		return futuro;

	}

	private static void showHelp() {

		System.out.println("-----AYUDA-----");
		System.out.println("*n : Pasar generación.");
		System.out
				.println("*SIM [generaciones] [miliSegundos] : Simula generaciones deseadas, espacio indicado en ms");
		System.out.println("*LOAD [archivo] : Carga un tablero desde archivo");
		System.out.println("*SAVE [archivo] : Guarda el tablero en un archivo");
		System.out.println("*NEW [celulas_vivas] [filas] [columnas]");
		System.out.println("          Crea un nuevo tablero aleatorio.");

		System.out.println("*HELP : Muestra la ayuda");
		System.out.println("*EXIT: Acabar programa");
	}

	public static void main(String[] args) {

		actual = new Tablero(0, 0, 0);
		generation = 0;

		Scanner teclado = new Scanner(System.in);
		showHelp();
		System.out.println();
		System.out.print(PROMPT);
		String leido = teclado.next();

		while (!(leido.compareToIgnoreCase(EXIT) == 0)) {

			/* PASAR A SIGUIENTE GENERACION */
			if (leido.compareToIgnoreCase(NEXT) == 0) { // Si está vacio, paso a
														// siguiente gen
				simulaPasos(1, 0);
			}

			else if (leido.compareToIgnoreCase(SIMULA) == 0) { // Simular T
																// pasos
				simulaPasos(teclado.nextInt(), teclado.nextInt());

			}

			/* CARGAR TABLERO DESDE ARCHIVO */
			else if (leido.compareToIgnoreCase(LOAD) == 0) {
				actual = new Tablero(teclado.nextLine().substring(1));
				System.out.println("Tablero cargado.");
				draw(actual);
			}
			/* GUARDAR ARCHIVO EN TABLERO */
			else if (leido.compareToIgnoreCase(SAVE) == 0) {
				actual.save(teclado.nextLine().substring(1));
			}

			/* CREAR NUEVO TABLERO ALEATORIO */
			else if (leido.compareToIgnoreCase(NEWGAME) == 0) {
				Scanner orden = new Scanner(teclado.nextLine().substring(1));
				// La orden es NEWGAME nºvivos filas columnas
				int numero = orden.nextInt();
				int rows = orden.nextInt();
				int columns = orden.nextInt();

				actual = new Tablero(numero, rows, columns);
				System.out
						.printf("Tablero aleatorio de %d x %d con %d celulas creado!%n",
								rows, columns, numero);

				draw(actual);
			}

			else if (leido.compareToIgnoreCase(HELP) == 0) {
				showHelp();
			}
			System.out.println();
			System.out.print(PROMPT);
			leido = teclado.next();

		}
	}

	private static void simulaPasos(int steps, int milis) {

		for (int j = 0; j < steps; j++) {
			for (int i = 0; i < actual.rows(); ++i) {
				System.out.println();
			}
			System.out.printf(">>Generación %d%n", generation);
			actual = tick(actual);
			draw(actual);
			generation++;
			try {
				Thread.sleep(milis);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void clearConsole() {
		try {
			String os = System.getProperty("os.name");

			if (os.contains("Windows")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (Exception exception) {
			// Handle exception.
		}
	}

}
