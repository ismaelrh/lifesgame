package lifesgame;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class Tablero {

	/*ATRIBUTOS*/
	private int rows;
	private int columns;
	Casilla[][] tablero;
	
	/*MÉTODOS*/
	//Constructor a partir de un array de casillas ya creado
	public Tablero(Casilla[][] tablero){
		
		this.tablero = new Casilla[tablero.length][tablero[1].length];
		for(int i = 0; i < tablero.length; i++){
			for(int j = 0; j < tablero[1].length;j++){
				this.tablero[i][j] = tablero[i][j];
			}
		}
		
	}
	
	/*Inicializa el tablero, con el numero de celulas dispuestas aleatoriamente
	 * especificadas por el método <number>, de tamaño <rows>*<columns>*/
	public Tablero(int number, int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		tablero = new Casilla[rows][columns];
		
		//Inicializa el tablero con casillas vacías.
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns;j++){
				tablero[i][j] = new Casilla(false,i,j);
			}
	    }
		
		//Factor aleatorio
		if(number>0){
			Random generator = new Random();
			int generadas = 0;
			
			for(int i = 0; i < number;i++){
			//Generación de casilla
			int generY = generator.nextInt(rows);
			int generX = generator.nextInt(columns);
			
			//Si la casilla elegida ya está ocupada, vuelvo a generar.
			while(getCell(generY,generX).isAlive()){
				generY = generator.nextInt(rows);
				generX = generator.nextInt(columns);
			}
			
			//Pongo en el lugar aleatorio la célula viva.
			put(true,generY,generX);
			
			}
			
		}
		
	}
	
	/*
	 * Inicializa el tablero según el contenido del archivo indicado por <archivo>. 
	 * Si no existe o no ha podido leerse el archivo, no lo crea, dando un mensaje de error.
	 * 
	 * Estructura del archivo:
	 * FILAS [INT]
	 * COLUMNAS [INT]
	 * Las siguientes líneas representan las filas, siendo:
	 * X: casilla vacía
	 * O: célula
	 */
	public Tablero(String archivo){
		try{
			Scanner entrada = new Scanner(new File(archivo));
			
			this.rows =   new Integer(entrada.nextLine()).intValue();
			this.columns = new Integer(entrada.nextLine()).intValue();
			
			tablero = new Casilla[rows][columns];
			
			//Procedo a la lectura de las células
			for(int i = 0; i < rows; i++){
						
				String fila = entrada.nextLine(); //Lee toda la fila
				for(int j = 0; j < columns; j++){
						if(fila.charAt(j)=='X'){ //No hay célula
							tablero[i][j] = new Casilla(false,i,j); 
						}
						else{ //Hay célula
							tablero[i][j] = new Casilla(true,i,j);
						}
				}
			}
			
			//Terminada lectura del fichero
			entrada.close();
			
			System.out.println("*Tablero leído del fichero");
			
		}
		
		catch(IOException ex){
			System.out.println("Se ha producido un error al leer el fichero: " + ex.getMessage());
			
		}
		
	}
	
	/*Devuelve el numero de filas del tablero*/
	public int rows(){
		return rows;
	}
	
	/*Devuelve el numero de columnas del tablero*/
	public int columns(){
		return columns;
	}
	
	/*Modifica la casilla indicada, según el parámetro <alive>*/
	public void put(boolean alive, int row, int column){
		tablero[row][column] = new Casilla(alive,row,column);
	}
	
	/*Devuelve una referencia a un objeto <Casilla> situado en la posición <row>,<column>
	 * del tablero.
	 */
	public Casilla getCell(int row, int column){
		return tablero[row][column];
	}
	
	/*
	 * Guarda en el archivo de nombre <archivo> el tablero actual.
	 * Devuelve <true> si se ha guardado correctamente, 
	 * <false> en caso contrario.
	 */
	public boolean save(String archivo){
		try{
			PrintStream salida = new PrintStream(archivo);
			
			//Escritura de las dimensiones
			salida.println(this.rows);
			salida.println(this.columns);
			
			//Escritura de los datos
			for(int i = 0; i < rows; i++){
				
				//Creación del buffer
				String buffer = "";
				for(int j = 0; j < columns; j++){
					if(getCell(i,j).isAlive()){ //Si está viva, es 'O'
						buffer+= "O";
					}
					else{ //Si está muerta, es 'X'
						buffer+="X";
					}
				}
				
				//Escritura del buffer (línea)
				salida.println(buffer);
				
			}
			
			salida.close();
			System.out.println("Archivo creado correctamente.");
			return true;
		}
		catch(IOException ex){
			System.out.println("Error:" +  ex.getMessage());
			return false;
			
		}
		
	}
	
}
