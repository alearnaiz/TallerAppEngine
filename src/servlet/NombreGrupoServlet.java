package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import modelo.Grupo;
import modeloBD.GrupoEntityDS;
import modeloBD.IGrupoEntityDS;


public class NombreGrupoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2L;
	
	private IGrupoEntityDS db = new GrupoEntityDS();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Obtenemos el ultimo patch de /api/grupos/{nombre}
		String url = req.getRequestURI();
		String nombre = "";
		String[] paths = url.split("/");
		if (paths.length == 4){
			nombre = paths[3];
		}
		
		// Comprobamos si existe el grupo elegido
		Grupo grupo = null;
		if (!nombre.isEmpty()){
			grupo = db.buscarGrupo(nombre);
			if (grupo == null){
				resp.sendError(404);
			}
			else{
				// Devolvemos en formato json el grupo buscado
				Gson gson = new Gson();
				String jsonString = gson.toJson(grupo);
				resp.getWriter().println(jsonString);
			}
		}
		else{
			resp.setStatus(404);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Obtenemos el ultimo patch de /api/grupos/{nombre}
		String url = req.getRequestURI();
		String nombre = "";
		String[] paths = url.split("/");
		if (paths.length == 4){
			nombre = paths[3];
		}
		
		// Comprobamos si existe el grupo elegido para actualizar
		if (!nombre.isEmpty()){
			// Obtenemos los datos que se envian
			StringBuilder sb = new StringBuilder();
			BufferedReader bf = req.getReader();
			String jsonString;
			while ((jsonString = bf.readLine())!=null){
				sb.append(jsonString);
			}
			jsonString = sb.toString();
			if (jsonString == null || jsonString.isEmpty()){
				resp.sendError(404);
			}
			else{
				try{
					// Actualizamos los datos si existen
					Gson gson = new Gson();
					Grupo grupoActualizado = gson.fromJson(jsonString, Grupo.class);
					if (db.updateGrupo(nombre, grupoActualizado)){
						resp.setStatus(204);
					}
					else{
						resp.setStatus(404);
					}
				}
				catch(Exception e){
					System.out.println("ERROR");
					resp.sendError(404);
				}
			}
		}
		else{
			resp.setStatus(404);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Obtenemos el ultimo patch de /api/grupos/{nombre}
		String url = req.getRequestURI();
		String nombre = "";
		String[] paths = url.split("/");
		if (paths.length == 4){
			nombre = paths[3];
		}
		
		// Borramos el grupo si se encuentra
		if (db.delGrupo(nombre)){
			resp.setStatus(204);
		}
		else{
			resp.setStatus(404);
		}
	}
	
}
