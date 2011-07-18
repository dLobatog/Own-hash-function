package grado.sti.auxiliar;

import grado.sti.hash.FuncionResumenI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jorge L�pez Hern�ndez-Ardieta / adaptaci�n 2011 Iv�n Rivera
 *
 */
public class Clase {
	
	/**
	 * M�todo que instancia (e inicializa), mediante reflexi�n, la funci�n resumen indicada por la clase pasada como argumento.
	 * El nombre de la clase debe incluir la ruta completa de paquetes hasta la clase dada.
	 * 
	 * @param classFuncionResumen Nombre de la clase concreta (p. ej. grado.sti.hash.FuncionResumenXORBasica)
	 * @return Instancia de la funci�n resumen dada, ya inicializada.
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static synchronized FuncionResumenI instanciarFuncionResumen(String classFuncionResumen) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		// Instanciaci�n del objeto que representa la funci�n hash concreta mediante reflexi�n
		Class<?> clase = Class.forName(classFuncionResumen);		
		Constructor<?> constructor = clase.getConstructor((Class<?>[])null);		
		FuncionResumenI funcionResumen = (FuncionResumenI)constructor.newInstance((Object[])null);
		
		funcionResumen.inicializar();
		
		return funcionResumen;
	}
}
