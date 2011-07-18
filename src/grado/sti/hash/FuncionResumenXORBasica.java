package grado.sti.hash;

import grado.sti.auxiliar.Datos;

/**
 * Funci�n Resumen que opera sobre el mensaje aplicando un XOR a nivel binario (bit) en cada bloque, de la siguiente manera:
 * <p>
 *  Ci = bi,1 XOR bi,2 XOR ... XOR bi,m
 * <p>
 * siendo:
 * <p>
 * - Ci el i-en�simo bit del hash, con 1<= i <= n
 * <p>
 * - m el n�mero de bloques de tama�o n bits del mensaje
 * <p>
 * - bi,j el i-en�simo bit del j-en�simo bloque
 * <p>
 * - n el tama�o del bloque en que se divide el mensaje, y que coincide con el tama�o del hash generado
 * <p>
 * - XOR la operaci�n XOR a nivel de bit
 * <p>
 * El tama�o del resumen generado es de 8 bytes.
 * 
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public class FuncionResumenXORBasica implements FuncionResumenI {

	private Datos datos = null;
	
	/**
	 * M�todo para la inicializaci�n de la funci�n resumen. Se deber� invocar antes de poder operar con la funci�n resumen.
	 */
	public void inicializar() {
		datos = new Datos();
	}

	//////////////////////////////IMPLEMENTAR/////////////////////////////
	/**
	 * M�todo que calcula el resumen del mensaje aplicando un XOR a los bloques del mensaje.
	 * 
	 * El mensaje se trocea en segmentos (bloques) de tama�o 8 bytes. El tama�o del resumen resultante es de 8 bytes.
	 * 
	 * Se aplica un padding PADDING_0x0n en caso que el tama�o del mensaje no sea m�ltiplo de 8 bytes.
	 * 
	 * @param mensaje Mensaje sobre el cual calcular el resumen
	 * @return Resumen generado
	 * @throws Exception
	 */
	public byte[] calcularResumen(byte[] mensaje) throws Exception	{
		if (mensaje.length > 255){
	//		System.out.println("Too long message. Exiting...");
			System.exit(0);
		}
		//Check if the message is a multiple of 8
		if((mensaje.length % 8) == 0){
	//		System.out.println("Message length is a multiple of 8");
		} else{
	//		System.out.println("Message length is not a multiple of 8");
			//Complete the message with the padding 0x0n
			mensaje = datos.anyadirPadding(mensaje, 8, 0);
		}
		byte[] hashValue = new byte[8];
		byte[][] mensajeBlock = new byte[mensaje.length][8];
 		//Bring up all 8 bytes blocks
		for(int i = 0; i < mensaje.length; i+=8){
			if(datos.obtenerBloque(mensaje, i, i+8) != null){
				mensajeBlock[i/8] = datos.obtenerBloque(mensaje, i, i+8);
			}
		}
		//Get hash value
		for(int m = 0; m < 8; m++){
			for(int i = 0; i < mensaje.length; i++){
				hashValue[m] = (byte) (mensajeBlock[i][m]^hashValue[m]); 
			}
		}
		return hashValue;
	}
}
