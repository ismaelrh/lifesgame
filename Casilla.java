package lifesgame;

public class Casilla {

	/* ATRIBUTOS */
	private boolean alive;
	private int row;
	private int column;

	/* M�TODOS */

	/* Constructor */
	public Casilla(boolean alive, int row, int column) {

		this.alive = alive;
		this.row = row;
		this.column = column;
	}

	/*
	 * Devuelve <true> si la casilla representa una c�lula viva, o <false> si
	 * est� vac�a
	 */
	public boolean isAlive() {
		return alive;
	}

	/* Devuelve el n�mero de fila, desde 0, en el que se encuentra la casilla */
	public int row() {
		return row;
	}

	/* Devuelve el n�mero de columna,desde 0, en el que se encuentra la casilla */
	public int column() {
		return column;
	}

	/* <true> si est� en el borde izquierdo. <false> en caso contrario. */
	public boolean leftEdge(Tablero t) {
		return column() == 0;
	}

	/* <true> si est� en el borde superior. <false> en caso contrario. */
	public boolean upperEdge(Tablero t) {
		return row() == 0;
	}

	/* <true> si est� en el borde derecho. <false> en caso contrario. */
	public boolean rightEdge(Tablero t) {
		return (column() == t.columns() - 1);
	}

	/* <true> si est� en el borde inferior. <false> en caso contrario. */
	public boolean lowerEdge(Tablero t) {
		return (row() == t.rows() - 1);
	}

	/* Devuelve el n�mero de "vecinos" que tiene la c�lula */
	public int getNear(Tablero actual) {

		int neighbours = 0;
		int myRow = row(), myCol = column();
		int totalRows = actual.rows(), totalCols = actual.columns();
		int supRow, infRow;
		int leftCol, rightCol;

		if (upperEdge(actual))
			supRow = totalRows - 1;
		else
			supRow = myRow - 1;

		if (lowerEdge(actual))
			infRow = 0;
		else
			infRow = myRow + 1;

		if (leftEdge(actual))
			leftCol = totalCols - 1;
		else
			leftCol = myCol - 1;

		if (rightEdge(actual))
			rightCol = 0;
		else
			rightCol = myCol + 1;

		// Arriba, izquierda
		if (actual.getCell(supRow, leftCol).isAlive())
			neighbours++;
		// Arriba, centro
		if (actual.getCell(supRow, myCol).isAlive())
			neighbours++;
		// Arriba, derecha
		if (actual.getCell(supRow, rightCol).isAlive())
			neighbours++;

		// Abajo, izquierda
		if (actual.getCell(infRow, leftCol).isAlive())
			neighbours++;
		// Abajo, centro
		if (actual.getCell(infRow, myCol).isAlive())
			neighbours++;
		// Abajo, derecha
		if (actual.getCell(infRow, rightCol).isAlive())
			neighbours++;

		// vecino izquierdo
		if (actual.getCell(myRow, leftCol).isAlive())
			neighbours++;
		// vecino derecho
		if (actual.getCell(myRow, rightCol).isAlive())
			neighbours++;

		return neighbours;
	}

}
