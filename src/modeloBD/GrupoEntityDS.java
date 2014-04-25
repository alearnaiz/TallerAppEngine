package modeloBD;
/**
 * Tomando como modelo un Grupo con un nombre (String), votospositivos y votos negativos (enteros)
 * Las entidades serán tratadas con el nombre del grupo como clave
 */
import java.util.ArrayList;
import java.util.Iterator;

import modelo.Grupo;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class GrupoEntityDS implements IGrupoEntityDS{
	private DatastoreService db;
	
	public GrupoEntityDS(){
		db= DatastoreServiceFactory.getDatastoreService();
	}
	
	@Override
	public ArrayList<Grupo> getGrupos(){
		ArrayList<Grupo> l= new ArrayList<Grupo>();
		Query q=new Query("Grupo");
		PreparedQuery pq=db.prepare(q);
		Iterator<Entity> it=pq.asIterator();
		while (it.hasNext()){
			Entity e=it.next();
			l.add(getGrupo(e));
		}
		
		return l;
	}
	
	@Override
	public void delGrupos() {
		Query q=new Query("Grupo");
		PreparedQuery pq=db.prepare(q);
		Iterator<Entity> it=pq.asIterator();
		while (it.hasNext()){
			Entity e=it.next();
			db.delete(e.getKey());
		}
	}
	
	@Override
	public boolean updateGrupo(String nombreGrupo, Grupo grupoActualizado){
		Iterable<Entity> it= buscar("Grupo", "nombre", nombreGrupo);
		
		boolean enc=false;
		Iterator<Entity> i=it.iterator();
		while (i.hasNext() && !enc){
			Entity aux=(Entity)i.next();
			
			if (nombreGrupo.compareTo((String)aux.getProperty("nombre"))==0){
				enc=true;
				db.delete(aux.getKey());
				db.put(getEntidad(grupoActualizado));
			}
		}
		
		return enc;
	}
	
	@Override
	public void addGrupo(Grupo grupo){
		addEntidad(getEntidad(grupo));
	}
	
	@Override
	public Grupo buscarGrupo(String nombreGrupo){
		Iterable<Entity> it= buscar("Grupo", "nombre", nombreGrupo);
		
		Iterator<Entity> i=it.iterator();
		while (i.hasNext()){
			Entity aux=(Entity)i.next();
			
			if (nombreGrupo.compareTo((String)aux.getProperty("nombre"))==0){
				return getGrupo(aux);
			}
		}
		
		return null;
	}
	
	
	@Override
	public boolean delGrupo(String nombreGrupo){
		return delEntidad(nombreGrupo);
	}
	
	private boolean existe(Entity e){
		Iterable<Entity> it=buscar(e.getKind(), "nombre", (String)e.getProperty("nombre"));
		boolean enc=false;
		Iterator<Entity> i=it.iterator();
		while (i.hasNext() && !enc){
			Entity aux=(Entity)i.next();
			String titulo=(String)e.getProperty("nombre");
			if (titulo.compareTo((String)aux.getProperty("nombre"))==0){
				enc=true;
			}
		}
		return enc;
	}
	
	
	private Entity getEntidad(Grupo o){
		Entity e=new Entity(o.getClass().getSimpleName());
		e.setProperty("nombre", o.getNombre());
		e.setProperty("votosPositivos",""+ o.getVotosPositivos());
		e.setProperty("votosNegativos",""+ o.getVotosNegativos());
		return e;
	}
	
	private Grupo getGrupo(Entity e){
		Grupo o=new Grupo();
		o.setNombre((String)e.getProperty("nombre"));
		o.setVotosPositivos(Integer.parseInt(String.valueOf(e.getProperty("votosPositivos"))));
		o.setVotosNegativos(Integer.parseInt(String.valueOf(e.getProperty("votosNegativos"))));		
		return o;
	}

	private void addEntidad(Entity e){
		if (!existe(e)){
			db.put(e);
		}
	}
	
	private boolean delEntidad(String nombre){
		Iterable<Entity> it=buscar("Grupo", "nombre", nombre);

		boolean enc=false;
		Iterator<Entity> i=it.iterator();

		while (i.hasNext() && !enc){
			Entity aux=(Entity)i.next();
			
			if (nombre.compareTo((String)aux.getProperty("nombre"))==0){
				enc=true;
				db.delete(aux.getKey());
			}
		}
		
		return enc;
	}

	private Iterable<Entity> buscar(String entidad, String prop, String val){
		Query q= new Query(entidad).setFilter(
				new FilterPredicate (prop, Query.FilterOperator.EQUAL, val));
		PreparedQuery pq=db.prepare(q);
		Iterable<Entity> e=pq.asIterable();
		return e;
	}
}
