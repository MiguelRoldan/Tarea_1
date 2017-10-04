package dominio;

import java.io.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Principal {
	public static void main(String[] args) throws IOException, Exception {
		int Xt, Yt, K, MAX, C, F;
		int terreno[][];
		char forma;
		forma = Character.toLowerCase(leer.caracter("Cálculo del campo:\n F - Leer fichero\n R - Aleatorio"));
		switch (forma) {
		case 'f':
			try {
				Scanner fich = new Scanner(new FileReader("Terreno.txt"));
				Xt = fich.nextInt();
				Yt = fich.nextInt();
				K = fich.nextInt();
				MAX = fich.nextInt();
				C = fich.nextInt();
				F = fich.nextInt();
				terreno = new int[C][F];
				fich.hasNextLine();
				for (int i = 0; i < C; i++) {
					for (int j = 0; j < F; j++) {
						terreno[i][j] = fich.nextInt();
					}
					fich.hasNextLine();
				}
				fich.close();
				System.out.println("Terreno creado a partir de Terreno.txt:");
				Mostrar_Terreno(terreno);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 'r':
			System.out.println();
			System.out.println("Introduzca los valores del nuevo terreno");
			K = leer.entero("K (media de arena)\t-:- ");
			C = leer.entero("Columnas\t\t-:- ");
			F = leer.entero("Filas\t\t\t-:-");
			MAX = leer.entero("Máxima arena\t\t-:- ", K, K * C * F);
			System.out.println("Introduzca posición del tractor");
			Xt = leer.entero("X\t\t\t-:- ", 0, C - 1);
			Yt = leer.entero("Y\t\t\t-:- ", 0, F - 1);
			System.out.println("Terreno generado:");
			terreno = Generar_Terreno(C, F, K, MAX);
			Mostrar_Terreno(terreno);
			System.out.println();
			String nombre = leer.cadena("Nombre del terreno\n\t\t('Terreno' por defecto)");
			if (nombre.equals("")) {
				nombre = "Terreno";
			}
			Escribir_Terreno(terreno, C, F, K, MAX, Xt, Yt, nombre);
			break;
		}
	}

	public static void Mostrar_Terreno(int[][] terreno) {
		for (int i = 0; i < terreno.length; i++) {
			for (int j = 0; j < terreno[i].length; j++) {
				System.out.print(terreno[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static int[][] Generar_Terreno(int C, int F, int K, int MAX) {
		int total = C * F * K;
		int aux = 0;
		int terreno[][] = new int[C][F];
		Random random = new Random();
		while (total != aux) {
			aux = 0;
			for (int i = 0; i < terreno.length; i++) {
				for (int j = 0; j < terreno[i].length; j++) {
					terreno[i][j] = random.nextInt(MAX + 1);
					aux += terreno[i][j];
				}
			}
		}
		return terreno;
	}

	public static void Escribir_Terreno(int[][] terreno, int C, int F, int K, int MAX, int Xt, int Yt, String nombre)
			throws IOException {
		boolean comprobar = true;
		int n = 0;
		File nomb = new File(nombre + ".txt");
		while (comprobar) {
			if (n == 0) {
				nomb = new File(nombre + ".txt");
			} else {
				nomb = new File(nombre + " (" + n + ").txt");
			}
			if (!nomb.exists()) {
				comprobar = false;
			}
			n++;
		}
		FileWriter fich = new FileWriter(nomb);
		BufferedWriter bw = new BufferedWriter(fich);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(fich);
			pw.print(Xt + " " + Yt + " " + K + " " + MAX + " " + C + " " + F);
			pw.println();
			for (int i = 0; i < terreno.length; i++) {
				for (int j = 0; j < terreno[i].length; j++) {
					pw.print(terreno[i][j] + " ");
				}
				if (i != terreno.length - 1) {
					pw.println();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fich)
					fich.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		bw.close();
	}
}