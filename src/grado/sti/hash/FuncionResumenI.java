package grado.sti.hash;

/**
 * Interfaz que define el API de una Funci�n Resumen.
 * <p>
 * Se limita el tama�o del bloque a procesar por la funci�n a 8 bytes.
 * <p>
 * El resumen generado ser� tambi�n de 8 bytes.
 * 
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public interface FuncionResumenI {
	
	public static final int TAMANYO_BLOQUE = 8;
	
	/**
	 * M�todo para la inicializaci�n de la funci�n resumen. Se deber� invocar antes de poder operar con la funci�n resumen.
	 */
	public void inicializar();
	
	/**
	 * M�todo que calcula el resumen del mensaje de acuerdo al modo de operaci�n particular.
	 * 
	 * @param mensaje Mensaje sobre el cual calcular el resumen
	 * @return Resumen generado, de 8 bytes de longitud
	 * @throws Exception
	 */
	public byte[] calcularResumen(byte[] mensaje) throws Exception;
}
