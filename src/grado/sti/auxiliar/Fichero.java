package grado.sti.auxiliar;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Clase que contiene utilidades para el tratamiento de ficheros.
 * 
 * @author Jorge López Hernández-Ardieta / adaptaci—n 2011 Iv‡n Rivera
 *
 */
public class Fichero {
	
	private static final String RUTA_DATOS = "/datos/";
	
	/**
	 * Método que escribe el contenido de un array de bytes a un fichero.
	 * 
	 * @param bytes Array de bytes a escribir en fichero
	 * @param fichero Nombre del fichero
	 * @throws IOException 
	 */
	public void array2Fichero(byte [] bytes, String fichero) throws IOException {		
		OutputStream os = new FileOutputStream(RUTA_DATOS + fichero);
		os.write(bytes);
		os.close();
	}
	
	/**
	 * Método que lee toda la información de un fichero y la escribe en un array de bytes
	 *   
	 * @param fichero Nombre del fichero que se quiere leer
	 * @return array de bytes con el contenido de dicho fichero
	 * @throws IOException 
	 */
	public byte[] fichero2Array (String fichero) throws IOException {		
		byte[] buff = new byte[1024];
		
		InputStream is = Fichero.class.getResourceAsStream("/"+fichero);
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
		int r = -1;
		while ((r = is.read(buff)) > 0)
			baos.write(buff, 0, r);
	
		is.close();
		baos.close();
		
		return baos.toByteArray();
	}
}
