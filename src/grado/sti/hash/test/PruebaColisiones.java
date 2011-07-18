package grado.sti.hash.test;

import java.util.ArrayList;
import java.util.HashMap;

import grado.sti.auxiliar.Clase;
import grado.sti.auxiliar.Datos;
import grado.sti.auxiliar.Fichero;
import grado.sti.hash.FuncionResumenI;

/**
 * Clase que realiza el c�lculo de las colisiones para una funci�n resumen dada y bas�ndose en un fichero de entrada.
 *  
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public class PruebaColisiones {	
	
	private static Fichero fichero = null;
	private static Datos datos = null; 
	
	public PruebaColisiones() {
		
	}
	
	public static void main(String[] args) {
		if (args.length < 3)
		{
			System.out.println("USO: java PruebaColisiones funcion_resumen nombre_fichero_entrada tama�o_buffer_lectura [nombre_fichero_salida]");
			System.out.println("");
			System.out.println("  funcion_resumen: nombre de la clase con la ruta completa de paquetes (p. ej. grado.sti.auxiliar.Datos.FuncionResumenXORBasica)");
			System.out.println("  nombre_fichero_entrada: nombre sin ruta. Deber� estar localizado en /STIgrado10_p3/datos/");
			System.out.println("  tama�o_buffer_lectura: tama�o del buffer empleado para leer el fichero de entrada (bytes)");
			System.out.println("  nombre_fichero_salida: nombre sin ruta. Se guardar� en /STIgrado10_p3/datos/. Opcional");
			System.out.println("");
			System.out.println("Cerrando...");

			System.exit(-1);
		}
		
		fichero = new Fichero();
		datos = new Datos();

		try {
			String funcionResumen = args[0];
			String nombreFicheroEntrada = args[1];
			String tamayoBufferLectura = args[2];
			String nombreFicheroSalida = (args.length == 4)?args[3]:null;
		
			if (new Integer(tamayoBufferLectura).intValue() > 255) {
				System.err.println("El tama�o del buffer de lectura debe ser como m�ximo de 255 bytes");
				System.exit(-1);
			}
			
			calcularTablaColisiones(
					nombreFicheroEntrada, 
					funcionResumen, 
					new Integer(tamayoBufferLectura).intValue(),
					nombreFicheroSalida);
		}
		catch (Exception e) {
			System.err.println("Ha ocurrido un error. Saliendo...");
			e.printStackTrace();
			System.out.println("");
			System.out.println("USO: java PruebaColisiones funcion_resumen nombre_fichero_entrada tama�o_buffer_lectura [nombre_fichero_salida]");
			System.out.println("");
			System.out.println("  funcion_resumen: nombre de la clase con la ruta completa de paquetes (p. ej. grado.sti.auxiliar.Datos.FuncionResumenXORBasica)");
			System.out.println("  nombre_fichero_entrada: nombre sin ruta. Deber� estar localizado en /STIgrado09_p2/datos/");
			System.out.println("  tama�o_buffer_lectura: tama�o del buffer empleado para leer el fichero de entrada (bytes)");
			System.out.println("  nombre_fichero_salida: nombre sin ruta. Se guardar� en /STIgrado09_p2/datos/. Opcional");
			System.out.println("");
			System.out.println("Cerrando...");

			System.exit(-1);
		}
	}
	
	
	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que genera una tabla de colisiones donde se debe poder conocer los hashes generados y el n�mero de colisiones para cada uno de ellos. 
	 * 
	 * @param nombreFichero Nombre del fichero que contiene la informaci�n
	 * @param classFuncionResumen Clase (con la ruta completa de paquetes) de la funci�n resumen a probar
	 * @param tamayoBloqueLeido Longitud del segmento
	 * @param salida Nombre del fichero de salida, en caso de querer volcar el resultado a disco
	 */
	public static void calcularTablaColisiones(String nombreFichero, String classFuncionResumen, int tamayoBloqueLeido, String salida) {
		FuncionResumenI funcionResumen = null;
		byte[] hash = null;
		HashMap<String, Integer> myHashMap = new HashMap<String, Integer>();
		String hashString;
		int differentHashValueCounter = 0;
		int hashValuesCollisionCounter = 0;
		int totalCollisions = 0;
		int totalHashValues = 0;
		try	{
			funcionResumen = Clase.instanciarFuncionResumen(classFuncionResumen);
			byte[] contenidoFichero = fichero.fichero2Array(nombreFichero);
			for(int i = 0; i<contenidoFichero.length/tamayoBloqueLeido ;i++){
				hash = funcionResumen.calcularResumen(datos.obtenerBloque(contenidoFichero, i*tamayoBloqueLeido, (i*tamayoBloqueLeido)+tamayoBloqueLeido ));
				hashString = datos.bytesToHex(hash);
				if(myHashMap.containsKey(hashString)){
					if(myHashMap.get(hashString) == 0){
						myHashMap.put(hashString, myHashMap.get(hashString)+1);
						hashValuesCollisionCounter++;
						totalCollisions++;
					}else{
						myHashMap.put(hashString, myHashMap.get(hashString)+1);
						totalCollisions++;
					}
				}else{
					myHashMap.put(hashString, 0);
					differentHashValueCounter++;
				}
				totalHashValues++;
			}
			
		}
		catch (Exception e) {
			System.err.println("Ha ocurrido un error. Saliendo...");
			e.printStackTrace();
		}
			double percentage = (double)hashValuesCollisionCounter/totalHashValues;
			System.out.println("N�mero total de entradas: " + totalHashValues);
			System.out.println("N�mero total de entradas con colisiones (n�mero de coincidencias > 1): " + hashValuesCollisionCounter  + " (" + 100*percentage + "%)");
			System.out.println("N�mero total de colisiones encontradas: " + totalCollisions);
	}
}

