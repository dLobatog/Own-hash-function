package grado.sti.hash.test;

import grado.sti.auxiliar.Clase;
import grado.sti.auxiliar.Datos;
import grado.sti.auxiliar.Fichero;
import grado.sti.hash.FuncionResumenI;

/**
 * Clase para probar una funci�n resumen.
 * 
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public class PruebaFuncionResumen {	
	
	private static Fichero fichero = null;
	private static Datos datos = null; 
	
	public PruebaFuncionResumen() {
		
	}
	
	public static void main(String[] args) {
		if (args.length != 2)
		{
			System.out.println("USO: java PruebaFuncionResumen funcion_resumen nombre_fichero_entrada");
			System.out.println("");
			System.out.println("  funcion_resumen: nombre de la clase con la ruta completa de paquetes (p. ej. grado.sti.auxiliar.Datos.FuncionResumenXORBasica)");
			System.out.println("  nombre_fichero_entrada: nombre sin ruta. Deber� estar localizado en /STIgrado10_p3/datos/");
			System.out.println("");
			System.out.println("Cerrando...");

			System.exit(-1);
		}
		
		fichero = new Fichero();
		datos = new Datos();

		try {
			String funcionResumen = args[0];
			String nombreFicheroEntrada = args[1];

			calcularHash(nombreFicheroEntrada, funcionResumen);					
		}
		catch (Exception e) {
			System.err.println("Ha ocurrido un error. Saliendo...");
			e.printStackTrace();
			System.out.println("");
			System.out.println("USO: java PruebaFuncionResumen funcion_resumen nombre_fichero_entrada");
			System.out.println("");
			System.out.println("  funcion_resumen: nombre de la clase con la ruta completa de paquetes (p. ej. grado.sti.auxiliar.Datos.FuncionResumenXORBasica)");
			System.out.println("  nombre_fichero_entrada: nombre sin ruta. Deber� estar localizado en /STIgrado10_p3/datos/");
			System.out.println("");
			System.out.println("Cerrando...");

			System.exit(-1);
		}
	}
	
	/**
	 * M�todo para el c�lculo del resumen de los datos indicados en el fichero y en base a la funci�n resumen indicada.
	 * 
	 * @param nombreFichero Nombre del fichero del cual se quiere calcular el resumen
	 * @param classFuncionResumen Clase (con la ruta completa de paquetes) de la funci�n resumen a probar
	 */
	public static void calcularHash(String nombreFichero, String classFuncionResumen) { 
		FuncionResumenI funcionResumen = null;
		byte[] hash = null;

		try	{
			// Instanciaci�n del objeto que representa la funci�n hash concreta mediante reflexi�n
			funcionResumen = Clase.instanciarFuncionResumen(classFuncionResumen);
			
			// Lectura completa del fichero
			byte[] contenidoFichero = fichero.fichero2Array(nombreFichero);			
			
			hash = funcionResumen.calcularResumen(contenidoFichero);

			System.out.println("El hash calculado mediante la funci�n resumen " + classFuncionResumen + " es: " + datos.bytesToHex(hash));
		}
		catch (Exception e) {
			System.err.println("Ha ocurrido un error. Saliendo...");
			e.printStackTrace();
		}
	}
}

