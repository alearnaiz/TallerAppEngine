package almacen;

import java.util.ArrayList;
import java.util.List;

import modelo.Grupo;

public class MiAlmacen {
	
	private static List<Grupo> grupos = new ArrayList<Grupo>();
	
	public static Grupo buscarGrupo (String nombreGrupo){
		for (Grupo grupo : grupos){
			if (grupo.getNombre().equalsIgnoreCase(nombreGrupo)){
				return grupo;
			}
		}
		return null;
	}
	
	public static void addGrupo(Grupo grupo){
		grupos.add(grupo);
	}
	
	public static void clearGrupos(){
		grupos.clear();
	}
	
	public static boolean removeGrupo(String nombreGrupo){
		for (Grupo grupo : grupos){
			if (grupo.getNombre().equalsIgnoreCase(nombreGrupo)){
				grupos.remove(grupo);
				return true;
			}
		}
		return false;
	}
	
	public static List<Grupo> getGrupos(){
		return grupos;
	}
	
	public static boolean updateGrupo(String nombreGrupo, Grupo grupoActualizado){
		boolean enc = false;
		for (Grupo grupo : grupos){
			if (grupo.getNombre().equalsIgnoreCase(nombreGrupo)){
				grupo.setNombre(grupoActualizado.getNombre());
				grupo.setVotosPositivos(grupoActualizado.getVotosPositivos());
				grupo.setVotosNegativos(grupoActualizado.getVotosNegativos());
				enc = true;
			}
		}
		return enc;
	}
}
