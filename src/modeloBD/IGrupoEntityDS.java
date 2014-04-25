package modeloBD;
/**
 * Manipulación del Almacenamiento de Objetos en el Store de Google
 */
import java.util.List;

import modelo.Grupo;

public interface IGrupoEntityDS {
	
	/**
	 * Actualiza un grupo
	 * @param nombreGrupo nombre del grupo a actualizar
	 * @param grupoActualizado datos del grupo actualizado
	 * @return indica si se ha podido actualizar o no
	 */
	boolean updateGrupo(String nombreGrupo, Grupo grupoActualizado);
	
	/**
	 * Devolvemos todos los grupos
	 * @return lista de grupos
	 */
	List<Grupo> getGrupos();
	
	/**
	 * Elimina todos los grupos
	 */
	void delGrupos();
	
	/**
	 * Crea un grupo nuevo
	 */
	void addGrupo(Grupo grupo);

	/**
	 * Buscar un grupo a traves de su nombre
	 * @param nombreGrupo nombre del grupo
	 * @return Grupo devuelto o null si no se encuentra
	 */
	Grupo buscarGrupo(String nombreGrupo);

	/**
	 * Borra un grupo especificado por su nombre
	 * @param nombreGrupo nombre del grupo
	 * @return indica si se ha podido borrar o no
	 */
	boolean delGrupo(String nombreGrupo);
}