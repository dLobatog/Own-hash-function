package grado.sti.auxiliar;

import grado.sti.hash.FuncionResumenI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jorge López Hernández-Ardieta / adaptaci—n 2011 Iv‡n Rivera
 *
 */
public class Clase {
	
	/**
	 * Método que instancia (e inicializa), mediante reflexión, la función resumen indicada por la clase pasada como argumento.
	 * El nombre de la clase debe incluir la ruta completa de paquetes hasta la clase dada.
	 * 
	 * @param classFuncionResumen Nombre de la clase concreta (p. ej. grado.sti.hash.FuncionResumenXORBasica)
	 * @return Instancia de la función resumen dada, ya inicializada.
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static synchronized FuncionResumenI instanciarFuncionResumen(String classFuncionResumen) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		// Instanciación del objeto que representa la función hash concreta mediante reflexión
		Class<?> clase = Class.forName(classFuncionResumen);		
		Constructor<?> constructor = clase.getConstructor((Class<?>[])null);		
		FuncionResumenI funcionResumen = (FuncionResumenI)constructor.newInstance((Object[])null);
		
		funcionResumen.inicializar();
		
		return funcionResumen;
	}
}
