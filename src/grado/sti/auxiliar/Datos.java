package grado.sti.auxiliar;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Clase que contiene utilidades para la operaci�n con datos a nivel binario y de byte.
 * 
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public class Datos {
	
	/**  
	 * 	 PADDING_0x0n: Se a�aden tantos 0's como sea necesario
	 */
	public static final int PADDING_0x0n = 0;
	/**  
	 * 	 PADDING_0x1n: Se a�aden tantos 1's como sea necesario
	 */
	public static final int PADDING_0x1n = 1;
	/**  
	 * 	 PADDING_0x10n: Se a�aden tantos 0's como sea necesario, excepto el bit m�s significativo (MSB) que se pone a '1'
	 */
	public static final int PADDING_0x10n = 2; 
	private static final String pseudo[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

	
	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que a�ade el tipo de padding indicado a los datos al final de los mismos (MSB), siempre y cuando la longitud de �stos no sea 
	 * m�ltiplo del tama�o de bloque pasado como argumento, en cuyo caso se mantiene igual.
	 * <p>
	 * Este m�todo no modifica el array original, sino que devuelve uno nuevo.
	 * 
	 * @param datos Datos a los que a�adir el padding
	 * @param tamanyoBloque Tama�o de bloque de referencia para el padding
	 * @param tipoPadding Tipo de padding a a�adir. Debe estar soportado (PADDING_0x0n, PADDING_0x1n, PADDING_0x10n). En caso contrario se tomar�
	 * PADDING_0x0n como tipo de padding
	 * @return Los datos con el padding a�adido en los bits m�s significativos
	 * @throws Exception En caso de error
	 */
	public byte[] anyadirPadding(byte[] datos, int tamanyoBloque, int tipoPadding) throws Exception	{	
		int bytesLeft = tamanyoBloque - (datos.length % tamanyoBloque);
		//There are 'substraction' bytes missing
		//if(bytesLeft == 0){
			//Do nothing
		//}
		if(bytesLeft != 0){
			//Handle the different cases
			if(tipoPadding == PADDING_0x1n){
				for(int i = 0; i < bytesLeft; i++)
					datos = appendByteToByteArray(datos, (byte) 1);
			}
			if(tipoPadding == PADDING_0x10n){
				datos = appendByteToByteArray(datos, (byte) 0x80);
				for(int i = 0; i < bytesLeft-1; i++)
					datos = appendByteToByteArray(datos, (byte) 0);
			}
			//This is the required padding to make encadenada suit with the values to verify
			//the correct implementation in the annex of the wording of the assignment
			if(tipoPadding == 3){
				for(int i = 0; i < bytesLeft-1; i++)
					datos = appendByteToByteArray(datos, (byte) 0);
				datos = appendByteToByteArray(datos, (byte) 0x80);
			}
			else{
				for(int i = 0; i < bytesLeft; i++)
					datos = appendByteToByteArray(datos, (byte) 0);
			}
		}
		return datos;  
	}
	
	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que a�ade la longitud de los datos al final de los mismos (MSB).
	 * <p>
	 * Este m�todo no modifica el array original, sino que devuelve uno nuevo.
	 * 
	 * @param datos Datos a los cuales se debe incorporar su longitud
	 * @return Datos con la longitud a�adida en los MSB
	 * @throws Exception
	 */
	public byte[] anyadirLongitud(byte[] datos) throws Exception {
		//datos must be at most 255 bytes long, hence the max value for datos.length would be 255 and the casting
		//from int to byte will be done properly
		Byte length = (byte) datos.length;
		datos = appendByteToByteArray(datos,length);
		return datos;
	}
	
	/**
	 * Creates an arraylist out of array of bytes
	 */
	public static ArrayList<Byte> createByteList(Byte ... values)
	{
	    ArrayList<Byte> results = new ArrayList<Byte>();
	    Collections.addAll(results, values);
	    return results;
	}
	
	/**
	 * Creates an array out of an arraylist
	 */
	public static Byte[] convertToByteArray(ArrayList<Byte> list)
	{
	    return (Byte[])list.toArray(new Byte[0]);
	}
	
	/**
	 * Append data to byte array
	 */
	public byte[] appendByteToByteArray(byte[] datos, byte dataToAppend){
		Byte[] auxiliarArray = new Byte[datos.length];
		for(int i = 0; i < datos.length; i++)
			auxiliarArray[i] = Byte.valueOf(datos[i]);
		ArrayList<Byte> datosList = createByteList(auxiliarArray);
		datosList.add(dataToAppend);
		auxiliarArray = convertToByteArray(datosList);
		byte[] result = new byte[auxiliarArray.length];
		for(int i = 0; i < auxiliarArray.length; i++)
			result[i] = auxiliarArray[i].byteValue();
		return result;
	}
	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que rota un bit (desplazamiento circular) en un array de bytes.
	 * 
	 * @param a Array de bytes
	 * @return El array de bytes de entrada con 1 bit rotado 
	 * @throws Exception
	 */

	public byte[] rotarIzquierdaBit(byte[] a) throws Exception{
		// Get a byte array
		 byte[] result = new byte[a.length];
		 String[] bytesString = new String[a.length];
		 String totalString = "";
		 for(int i=0; i<a.length; i++){
			 //Convert each of the bytes to a binary string
			 bytesString[i] = toBinaryString(a[i]);
			 //Join all the bytes in a string
			 totalString += bytesString[i];
		 }
		 //Move the first character to the last one
		 totalString += totalString.charAt(0);
		 //Take the string without the first character in order to get the rotation
		 totalString = totalString.substring(1);
		 for(int i = 0; i < a.length; i++){
			 //Separate the bytes of the string, convert it to byte and put it inside the array
			 result[i] = (byte) ((Integer.parseInt(totalString.substring(i*8, (i*8)+8), 2)) & 0xff);
		 }
		 return result;
	}
	
	 public static String toBinaryString(byte n) {
	     StringBuilder sb = new StringBuilder("00000000");
	     for (int bit = 0; bit < 8; bit++) {
	         if (((n >> bit) & 1) > 0) {
	             sb.setCharAt(7 - bit, '1');
	         }
	     }
	     return sb.toString();
	 }
	  
	/**
	 * M�todo que aplica un XOR (OR exclusivo) entre 2 arrays, a nivel de byte. Los tama�os de los arrays deben ser iguales.
	 * <p>
	 * Este m�todo no modifica el array original, sino que devuelve uno nuevo.
	 * 
	 * @param a Primer arrray
	 * @param b Segundo array
	 * @return Array resultante de aplicar un XOR entre a y b
	 * @throws Exception En caso de error
	 */
	public byte [] ejecutarXOR (byte[] a, byte[] b) throws Exception {		
		if (a.length != b.length)
			throw new Exception("Los tama�os de los arrays no coinciden");
		
		// Se usa como base el array a
		byte[] resultado = Arrays.copyOf(a, a.length);
		
		// Para cada byte del array a se aplica un XOR con el byte correspondiente del array b
		for(int i=0; i<resultado.length; i++) {
			resultado[i] ^= (byte)b[i];
//			System.out.printf("Byte calculado: %x", resultado[i]);
//			System.out.println();
		}
		
		return resultado;
	}
	
	/**
	 * M�todo que permite obtener un segmento del bloque de datos indicado.
	 * <p>
	 * Este m�todo no modifica el array original, sino que devuelve uno nuevo.
	 * 
	 * @param datos Array de bytes del que se quiere obtener un bloque concreto.
	 * @param inicio �ndice de inicio del segmento a obtener (debe ser menor que la longitud de los datos)
	 * @param fin �ndice del byte final del segmento a obtener (debe ser, como mucho, la longitud de los datos)
	 * @return array de bytes del bloque
	 * @throws Exception En caso que los �ndices indicados no sean correctos respecto a los datos
	 */
	public byte[] obtenerBloque(byte [] datos, int inicio, int fin) throws Exception {	
		if (inicio < 0 || inicio > fin || fin <= 0 || fin > datos.length)
			throw new Exception("Los �ndices indicados no son correctos");
		
		return Arrays.copyOfRange(datos, inicio, fin);
	}
	
	/**
	 * M�todo que formatea un string en hexadecimal.
	 * 
	 * @param base String de entrada
	 * @return
	 */
	public String stringToHex(String base) {
		StringBuffer buffer = new StringBuffer();
     
		int intValue;
		for(int x = 0; x < base.length(); x++) {
			int cursor = 0;
			intValue = base.charAt(x);
			String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
			
			for(int i = 0; i < binaryChar.length(); i++)
				if(binaryChar.charAt(i) == '1')
					cursor += 1;

			if((cursor % 2) > 0)
				intValue += 128;
         
			if (x == base.length() -1)
				buffer.append(Integer.toHexString(intValue).toUpperCase());
			else
				buffer.append(Integer.toHexString(intValue).toUpperCase() + ":");
		}
		
		return buffer.toString();
	}
	
	/**
	 * M�todo que formatea un byte en hexadecimal.
	 * 
	 * @param in
	 * @return
	 */
	public String byteToHex(byte in) {
		byte ch = 0x00;

		StringBuffer out = new StringBuffer(2);		    

		ch = (byte) (in & 0xF0);
		ch = (byte) (ch >>> 4);
		ch = (byte) (ch & 0x0F);
		out.append(pseudo[ (int) ch]);
			
		ch = (byte) (in & 0x0F);
		out.append(pseudo[ (int) ch]);
			
		String rslt = new String(out);

		return rslt;
	} 
	
	/**
	 * M�todo que formatea un array de bytes en hexadecimal.
	 * 
	 * @param in
	 * @return
	 */
	public String bytesToHex(byte[] in) {
		byte ch = 0x00;
		int i = 0; 

		if (in == null || in.length <= 0)
			return null;				    

		StringBuffer out = new StringBuffer(in.length * 2);		    

		while (i < in.length) {
			ch = (byte) (in[i] & 0xF0);
			ch = (byte) (ch >>> 4);
			ch = (byte) (ch & 0x0F);
			out.append(pseudo[ (int) ch]);
			
			ch = (byte) (in[i] & 0x0F);
			out.append(pseudo[ (int) ch]);
			
			if (i < in.length -1 )
				out.append(":");

			i++;
		}

		String rslt = new String(out);

		return rslt;
	} 
}
