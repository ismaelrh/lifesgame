package lifesgame;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Pruebas {

	public static void main(String[] args){
		
		try{
			Scanner entrada = new Scanner(new File("D:\\tablero.txt"));
			System.out.println(entrada.nextLine());
			System.out.println(entrada.nextLine());
			System.out.println(entrada.nextLine());
			System.out.println(entrada.nextLine());
		}
		catch(IOException ex){
			System.out.println(ex.getMessage());
		}
}
}
