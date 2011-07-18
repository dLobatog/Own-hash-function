package grado.sti.cifrador;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Clase que implementa un Cifrador DES empleando el Proveedor Criptogr�fico SunJCE inclu�do a partir de la versi�n Java SDK 1.6.
 * 
 * Tras instanciar la clase, se debe invocar al m�todo <b>inicializar()</b> con el modo de operaci�n deseado (modo cifrar o modo descifrar).
 * 
 * Posteriormente podr� invocarse al m�todo operarModoCBC para los datos concretos.
 * 
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 */
public class CifradorDES {
	
    /**
     * Modo de operaci�n del cifrador en cifrado 
     */
	public static final int MODO_CIFRADO = Cipher.ENCRYPT_MODE;
	/**
	 * Modo de operaci�n del cifrador en descifrado
	 */
	public static final int MODO_DESCIFRADO = Cipher.DECRYPT_MODE;

	private SecretKey claveDES = null;
	
	private Cipher cifrador = null;
	
	private SecretKeyFactory factoriaClavesDES = null;
	
	private byte[] desKeyData = { (byte)0x02, (byte)0x03, (byte)0x04, (byte)0x05, (byte)0x06, (byte)0x07, (byte)0x08, (byte)0x09 };
	
	private byte[] iv = null;

	/**
	 * Instancia el Cifrador DES en modo CBC. No aplica un padding interno, por lo que la clase que utilice
	 * este cifrador deber� asegurarse que posteriormente se invoca al m�todo <b>operarModoCBC</b> con un conjunto de datos
	 * m�ltiplo de 8 bytes (64 bits).
	 * <p>
	 * La clave sim�trica empleada es interna al cifrador.
	 * <p>
	 * El vector de inicializaci�n se generar� internamente. Se puede recuperar invocando al m�todo getIV(), aunque
	 * s�lo es necesario cuando se requiera operar en modo descifrado con el mismo vector de inicializaci�n.
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public CifradorDES() throws NoSuchAlgorithmException, NoSuchPaddingException {		
	
		// El cifrador DES operar� en modo CBC sin padding.
		cifrador = Cipher.getInstance("DES/CBC/NoPadding");
		factoriaClavesDES = SecretKeyFactory.getInstance("DES");
	}
	
	/**
	 * Se libera la memoria que pueda contener informaci�n sensible de la clave DES generada.
	 * <p>
	 * Este m�todo se invoca por el Recolector de Basura cuando �ste estima que la instancia no se emplear� m�s (no quedan referencias
	 * en memoria a este objeto) 
	 */
	protected void finalize() throws Throwable {
		try
		{
			claveDES = null;
			cifrador = null;
			factoriaClavesDES = null;
		}
		finally
		{
			super.finalize();	
		}		
	}
	
	/**
	 * M�todo que inicializa el cifrador DES-CBC para operar en el modo indicado. 
	 * <p>
	 * La clave empleada es interna al cifrador, as� como el vector IV utilizado.
	 * 
	 * @param modo Modo de operaci�n (CifradorDES.MODO_CIFRADO � CifradorDES.MODO_DESCIFRADO) 
	 * @throws Exception 
	 */
	public void inicializar(int modo) throws Exception {
		if (modo != MODO_CIFRADO && modo != MODO_DESCIFRADO)
			throw new Exception("["+CifradorDES.class+"][inicializar]: Error en el modo de operaci�n indicado");
			
		claveDES = factoriaClavesDES.generateSecret(new DESKeySpec(desKeyData));
	    			
		// Inicializaci�n del m�dulo para el modo indicado y con un IV por defecto
		cifrador.init(modo, claveDES,new IvParameterSpec(new byte[]{
				-121, -99, -118, 93, -64, -85, -20, -4}));
			
		// Obtenci�n del vector IV para poder operador en modo descifrado
		this.iv = cifrador.getIV();		
	}
	
	/**
	 * Metodo que cifra o descifra (en base al modo de inicializaci�n del cifrador) todo un array de bytes segun el modo CBC, 
	 * y utilizando la clave sim�trica interna y el vector IV generado tambi�n internamente.
	 * 
	 * @param datos Array de bytes a cifrar/descifrar
	 * @return Resultado del cifrado/descifrado
	 * @throws Exception 
	 */
	public byte [] operarModoCBC(byte[] datos) throws Exception {
		if (datos.length % 8 != 0)
			throw new Exception("["+CifradorDES.class+"][inicializar]: Los datos a cifrar deben ser m�ltiplo de 8 bytes (64 bits)");
		
		return cifrador.doFinal(datos);
	}
	
	/**
	 * M�todo que devuelve el vector de inicializaci�n IV empleado.
	 * 
	 * @return Vector de Inicializaci�n IV
	 */
	public byte[] getIV() {
		return this.iv;
	}
}
