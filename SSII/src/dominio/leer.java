/** Clase para poder leer del teclado cualquier dato del tipo que sea de los siguientes:
  * Entero, Double, Float, Cadena, Carácter, Boolean
  */
package dominio;

import java.util.*;

public class leer {
	private static final Scanner TECLADO = new Scanner(System.in);

	/**
	 * Define el teclado como estático para que no haya que crear ningún objeto
	 * de la clase Leer y así poder utilizar sus métodos
	 */
	public static void p(String s) {
		System.out.println(s);
	}

	public static int entero() throws Exception {
		return entero("");
	}

	public static int entero(String s) {
		int res = 0;
		boolean vale = true;
		do {
			vale = true;
			p(s);
			try {
				res = TECLADO.nextInt();
				p(res + "");
			} catch (Exception e) {
				vale = false;
				TECLADO.next();
			}
		} while (!vale);
		TECLADO.nextLine();
		return res;
	}

	public static int entero(String s, int min, int max) throws Exception {
		int res;
		do {
			res = entero(s + " [" + min + "," + max + "]");
		} while (res < min || res > max);
		return res;
	}

	public static String cadena(String s) {
		String res = "";
		boolean vale = true;
		do {
			p(s);
			vale = true;
			try {
				res = res + TECLADO.nextLine();
				p(res);
			} catch (Exception e) {
				vale = false;
				TECLADO.next();
			}
		} while (!vale);
		return res;
	}

	public static String cadena() {
		return cadena("");
	}

	public static char caracter() {
		return caracter("");
	}

	public static char caracter(String cad) {
		return cadena(cad).charAt(0);
	}
}