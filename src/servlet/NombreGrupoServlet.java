package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import almacen.MiAlmacen;

import com.google.gson.Gson;

import modelo.Grupo;


public class NombreGrupoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 2L;

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
			grupo = MiAlmacen.buscarGrupo(nombre);
			if (grupo == null){
				resp.setStatus(404);
			}
		}
		else{
			resp.setStatus(404);
		}
		
		// Devolvemos en formato json el grupo buscado
		Gson gson = new Gson();
		String jsonString = gson.toJson(grupo);
		resp.getWriter().println(jsonString);
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
			
			try{
				// Actualizamos los datos si existen
				Gson gson = new Gson();
				Grupo grupoActualizado = gson.fromJson(jsonString, Grupo.class);
				if (MiAlmacen.updateGrupo(nombre, grupoActualizado)){
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
		if (MiAlmacen.removeGrupo(nombre)){
			resp.setStatus(204);
		}
		else{
			resp.setStatus(404);
		}
	}
	
}
