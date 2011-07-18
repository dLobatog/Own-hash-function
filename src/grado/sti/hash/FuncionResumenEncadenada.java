package grado.sti.hash;

import javax.crypto.Cipher;

import grado.sti.auxiliar.Datos;
import grado.sti.cifrador.CifradorDES;

/**
 * Función resumen que opera sobre sobre el mensaje aplicando una funci�n de compresi�n a lo largo de diferentes etapas.
 * <p>
 * Cada etapa depende del resultado de la etapa anterior y del bloque del mensaje de entrada que corresponda.
 * <p>
 * El tama�o del resumen generado es de 8 bytes.
 *  
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public class FuncionResumenEncadenada implements FuncionResumenI {

	private Datos datos = null;
	private CifradorDES cifrador = null;
	
	/**
	 * Vector de Inicializaci�n (IV) para la Funci�n Resumen
	 */
	private static final byte[] IV = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
	
	/**
	 * M�todo para la inicializaci�n de la funci�n resumen. Se deber� invocar antes de poder operar con la funci�n resumen.
	 */
	public void inicializar() {
		datos = new Datos();		

		try {
			cifrador = new CifradorDES();
		
			cifrador.inicializar(CifradorDES.MODO_CIFRADO);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}		
	}
	
	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que calcula el resumen del mensaje aplicando el esquema Merkle-Damgard simplificado.
	 * 
	 * El mensaje se trocea en segmentos (bloques) de tama�o 8 bytes. El tama�o del resumen resultante es de 8 bytes.
	 * 
	 * La funci�n de compresi�n calcula el XOR entre la salida de la etapa anterior (o el vector de inicializaci�n IV en caso de ser la primera
	 * etapa) y el bloque correspondiente a la etapa dada. Posteriormente cifra mediante DES-ECB el resultado obtenido.
	 * 
	 * @param mensaje Mensaje sobre el cual calcular el resumen
	 * @return Resumen generado
	 * @throws Exception
	 */
	public byte[] calcularResumen(byte[] mensaje) throws Exception {
		byte[] message = datos.anyadirLongitud(mensaje);
		if (message.length > 255){
			System.out.println("Too long message. Remember that in FuncionResumenEncadenada" +
					" the maximum size of the block has to be 248 as the length of" +
					" the message needs to be added  Exiting...");
			System.exit(0);
		}
		//Check if the message is a multiple of 8
		if((message.length % 8) == 0){
	//		System.out.println("Message length is a multiple of 8");
		} else{
	//		System.out.println("Message length is not a multiple of 8");
			//Complete the message with the reversed 0x10 padding
			message = datos.anyadirPadding(message, 8, 3);
		}
		byte[] hashValue = new byte[8];
		byte[] previousHashValue = new byte[8];
		byte[][] messageBlock = new byte[8][8];
 		//Bring up all 8 bytes blocks
		for(int i = 0; i < message.length; i+=8){
			if(datos.obtenerBloque(message, i, i+8) != null){
				messageBlock[i/8] = datos.obtenerBloque(message, i, i+8);
			}
		}
		//Get hash value
		for(int i = 0; i < message.length/TAMANYO_BLOQUE; i++){
			if(i==0){
				hashValue = funcionF(messageBlock[i], IV);
			}
			else{ 
				hashValue = funcionF(messageBlock[i], hashValue);
			}
		}
		return hashValue;
	}
	
	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que implementa la funci�n de compresi�n de la funci�n resumen encadenada.
	 * 
	 * @param bloque Bloque perteneciente a la etapa concreta
	 * @param salidaEtapaAnterior Resultado de la funci�n de compresi�n de la etapa anterior, o vector de inicializaci�n IV en caso 
	 *							  de ser la primera etapa         
	 * @return Resultado de aplicar un XOR a ambos valores y cifrar el bloque resultante mediante el modo ECB del cifrador DES.
	 * @throws Exception
	 */
	private byte[] funcionF(byte[] bloque, byte[] salidaEtapaAnterior) throws Exception {
		byte[] hashValue = new byte[8];
		//Apply an XOR between the block  and the previous output
		hashValue = datos.ejecutarXOR(bloque, salidaEtapaAnterior);
		hashValue = cifrador.operarModoCBC(hashValue);

		return hashValue;
	}	
}
