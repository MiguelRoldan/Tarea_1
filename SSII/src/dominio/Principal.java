package dominio;

import java.io.*;
import java.util.*;

public class Principal {
	public static ArrayList<ArrayList<Integer>> combinaciones = new ArrayList<ArrayList<Integer>>();

	public static void main(String[] args) throws IOException, Exception {
		int Xt, Yt, K, MAX, C, F;
		int terreno[][];
		char forma;
		forma = Character.toLowerCase(leer.caracter("Cálculo del campo:\n F - Leer fichero\n R - Aleatorio"));
		switch (forma) {
		case 'f':
			try {
				String nombre = leer
						.cadena("Nombre del terreno que desea leer (sin .txt)\t-:-\n\t\t('Terreno' por defecto)");
				if (nombre.equals("")) {
					nombre = "Terreno";
				}
				Scanner fich = new Scanner(new FileReader(nombre + ".txt"));
				Xt = fich.nextInt();
				Yt = fich.nextInt();
				K = fich.nextInt();
				MAX = fich.nextInt();
				C = fich.nextInt();
				F = fich.nextInt();
				terreno = new int[C][F];
				fich.hasNextLine();
				for (int i = 0; i < F; i++) {
					for (int j = 0; j < C; j++) {
						terreno[i][j] = fich.nextInt();
					}
					fich.hasNextLine();
				}
				fich.close();
				System.out.println("Terreno creado a partir de "+nombre+".txt:");
				Mostrar_Terreno(terreno);
				System.out.println();
				System.out.println("Sucesores:");
				System.out.println();
				Generar_Sucesores(terreno, Xt, Yt, K, MAX);
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
		default:
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
		int terreno[][] = new int[F][C];
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

	public static void Generar_Sucesores(int[][] terreno, int Xt, int Yt, int K, int MAX) {
		ArrayList<ArrayList<Integer>> sucesores = new ArrayList<ArrayList<Integer>>();
		ArrayList[] cooSuc = new ArrayList[4];
		ArrayList<Integer> coor = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> sucesoresPosi = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> combinacionesK = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> coorPosi = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> combinacionesResultado = new ArrayList<ArrayList<Integer>>();
		int sobrante;
		int n = 0;
		int total = 0;
		for (int i = 0; i < terreno.length; i++) {
			for (int j = 0; j < terreno[i].length; j++) {
				if (Comprobar_Sucesor(terreno, Xt, Yt, i, j)) {
					cooSuc[n] = new ArrayList<Integer>();
					cooSuc[n].add(i);
					cooSuc[n].add(j);
					sucesores.add(cooSuc[n]);
					n++;
				}
			}
		}
		if (terreno[Xt][Yt] > K) {
			sobrante = terreno[Xt][Yt] - K;
			for (int i = 0; i < sucesores.size(); i++) {
				coor = sucesores.get(i);
				if (terreno[coor.get(0)][coor.get(1)] < K) {
					sucesoresPosi.add(coor);
				}
			}
			int[] aux = new int[sucesoresPosi.size()];
			Combinar(sobrante, 0, aux);
			for (int i = 0; i < combinaciones.size(); i++) {
				for (int j = 0; j < sucesoresPosi.size(); j++) {
					total += combinaciones.get(i).get(j);
				}
				if (total == sobrante) {
					combinacionesK.add(combinaciones.get(i));
				}
				total = 0;
			}
			/*
			 * for (int i = 0; i < combinacionesK.size(); i++) { for (int j = 0;
			 * j < sucesoresPosi.size(); j++) { coorPosi = sucesoresPosi.get(j);
			 * if (terreno[coor.get(0)][coor.get(1)] +
			 * combinacionesK.get(i).get(j) <= K) {
			 * combinacionesResultado.add(combinacionesK.get(i)); } } }
			 * System.out.println(combinacionesResultado.toString());
			 */
		}
		ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> resultado = new ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>>();
		ArrayList<ArrayList<ArrayList<Integer>>> elemento = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> elemento21 = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> elemento2 = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> elemento3 = new ArrayList<Integer>();
		for (int i = 0; i < sucesores.size(); i++) {
			for (int j = 0; j < combinacionesK.size(); j++) {
				elemento = new ArrayList<ArrayList<ArrayList<Integer>>>();
				elemento21 = new ArrayList<ArrayList<Integer>>();
				elemento2 = new ArrayList<ArrayList<Integer>>();
				elemento21.add(sucesores.get(i));
				elemento.add(elemento21);
				for (int k = 0; k < sucesoresPosi.size(); k++) {
					elemento3 = new ArrayList<Integer>();
					elemento3.add(combinacionesK.get(j).get(k));
					elemento2.add(elemento3);
					elemento2.add(sucesoresPosi.get(k));
				}
				elemento.add(elemento2);
				resultado.add(elemento);
			}
		}
		System.out.println("| Movi. | | Transporte de tierra");
		for (int i = 0; i < resultado.size(); i++) {
			System.out.println(resultado.get(i).toString());
		}
	}

	public static void Combinar(int sobrante, int i, int[] posiciones) {
		if (i != posiciones.length) {
			for (int j = 0; j <= sobrante; j++) {
				posiciones[i] = j;
				Combinar(sobrante, i + 1, posiciones);
			}
		} else {
			ArrayList<Integer> aux = new ArrayList<Integer>();
			for (int j = 0; j < posiciones.length; j++) {
				aux.add(posiciones[j]);
			}
			combinaciones.add(aux);
		}
	}

	public static boolean Comprobar_Sucesor(int[][] terreno, int Xt, int Yt, int Xs, int Ys) {
		boolean verdad = false;
		if (Xt == Xs + 1 && Yt == Ys && Xt != 0) {
			verdad = true;
		} else if (Xt == Xs && Yt == Ys + 1 && Yt != 0) {
			verdad = true;
		} else if (Xt == Xs && Yt == Ys - 1 && Yt != terreno[Xt].length - 1) {
			verdad = true;
		} else if (Xt == Xs - 1 && Yt == Ys && Xt != terreno.length - 1) {
			verdad = true;
		}
		return verdad;
	}
}