package grado.sti.hash;

import grado.sti.auxiliar.Datos;

/**
 * Funci�n Resumen que opera sobre el mensaje aplicando un XOR a nivel binario (bit) en cada bloque, de la siguiente manera:
 * <p>
 *  Para cada bloque j de tama�o n del mensaje:
 * <p>  
 *    C = C << 1 (circular)
 * <p>
 *    C = C XOR bj
 * <p>
 * siendo:
 * <p>
 * - C el valor del hash (inicialmente 0) de tama�o n
 * <p>
 * - << la operaci�n de rotaci�n o desplazamiento circular a la izquierda de 1 bit del valor del hash
 * <p>
 * - bj el j-en�simo bloque
 * <p>
 * - XOR la operaci�n XOR a nivel de bit
 * <p> 
 * Esta funci�n resumen rota el valor del hash 1 bit a la izquierda de manera circular en cada iteraci�n
 * <p>
 * El tama�o del resumen generado es de 8 bytes.
 *  
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public class FuncionResumenXORDesplazamiento implements FuncionResumenI {

	private Datos datos = null;
	
	/**
	 * M�todo para la inicializaci�n de la funci�n resumen. Se deber� invocar antes de poder operar con la funci�n resumen.
	 */
	public void inicializar()
	{
		datos = new Datos();
	}
	
	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que calcula el resumen del mensaje aplicando un XOR a los bloques del mensaje (de longitud 8 bytes), y desplazando 1 bit el valor del 
	 * resumen en cada etapa. El tama�o del resumen resultante es de 8 bytes.
	 * 
	 * Se aplica un padding PADDING_0x0n en caso que el tama�o del mensaje no sea m�ltiplo de 8 bytes.
	 * 
	 * @param mensaje Mensaje sobre el cual calcular el resumen
	 * @return Resumen generado
	 * @throws Exception
	 */	
	public byte[] calcularResumen(byte[] mensaje) throws Exception {
		byte[] aux = new byte[mensaje.length];
		if (mensaje.length > 255){
			System.out.println("Too long message. Exiting...");
			System.exit(0);
		}
		//Check if the message is a multiple of 8
		if((mensaje.length % 8) == 0){
//			System.out.println("Message length is a multiple of 8");
		} else{
//			System.out.println("Message length is not a multiple of 8");
			//Complete the message with the padding 0x0n
			mensaje = datos.anyadirPadding(mensaje, 8, 0);
		}
		byte[] hashValue = {0,0,0,0,0,0,0,0};
		//Get hash value
		for(int i = 0; i < mensaje.length/TAMANYO_BLOQUE; i++){
			//Shift the hash value 1 bit to the left
			hashValue = datos.rotarIzquierdaBit(hashValue);
			//XOR between the block and the hash
			aux=datos.obtenerBloque(mensaje, i*TAMANYO_BLOQUE, (i*TAMANYO_BLOQUE+TAMANYO_BLOQUE));
			hashValue = datos.ejecutarXOR(hashValue, aux); 
		}
		return hashValue;
	}
}
