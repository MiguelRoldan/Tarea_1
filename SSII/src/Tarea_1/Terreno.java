package Tarea_1;

import java.io.FileNotFoundException;
import java.util.Random;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Terreno {
	// Tarea1
	private static final Scanner TECLADO = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException, IOException {
		int Xt, Yt, K, MAX, C, F;
		int terreno[][];
		Scanner fich = new Scanner(new FileReader("terreno2.txt"));
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
		Mostrar_Terreno(terreno);
		terreno = Generar_Terreno(C, F, K, MAX);
		Mostrar_Terreno(terreno);
	}

	public static int[][] Mostrar_Terreno(int[][] terreno) {
		for (int i = 0; i < terreno.length; i++) {
			for (int j = 0; j < terreno[i].length; j++) {
				System.out.print(terreno[i][j] + " ");
			}
			System.out.println();
		}
		return terreno;
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
}