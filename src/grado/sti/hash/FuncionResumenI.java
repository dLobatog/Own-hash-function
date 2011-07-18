package grado.sti.hash;

/**
 * Interfaz que define el API de una Función Resumen.
 * <p>
 * Se limita el tamaño del bloque a procesar por la función a 8 bytes.
 * <p>
 * El resumen generado será también de 8 bytes.
 * 
 * @author Jorge López Hernández-Ardieta / adaptaci—n 2011 Iv‡n Rivera
 *
 */
public interface FuncionResumenI {
	
	public static final int TAMANYO_BLOQUE = 8;
	
	/**
	 * Método para la inicialización de la función resumen. Se deberá invocar antes de poder operar con la función resumen.
	 */
	public void inicializar();
	
	/**
	 * Método que calcula el resumen del mensaje de acuerdo al modo de operación particular.
	 * 
	 * @param mensaje Mensaje sobre el cual calcular el resumen
	 * @return Resumen generado, de 8 bytes de longitud
	 * @throws Exception
	 */
	public byte[] calcularResumen(byte[] mensaje) throws Exception;
}
