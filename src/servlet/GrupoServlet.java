package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Grupo;
import almacen.MiAlmacen;

import com.google.gson.Gson;

public class GrupoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Devolvemos todos los grupos que existen en la aplicacion
		Gson gson = new Gson();
		String jsonString = gson.toJson(MiAlmacen.getGrupos());
		resp.getWriter().println(jsonString);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
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
			// Creamos el grupo
			Gson gson = new Gson();
			Grupo grupo = gson.fromJson(jsonString, Grupo.class);
			MiAlmacen.addGrupo(grupo);
		}
		catch(Exception e){
			System.out.println("ERROR");
			resp.sendError(404);
		}
		
		resp.setStatus(201);
	}
	
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// Borramos todos los grupos que existan
		MiAlmacen.clearGrupos();
		resp.setStatus(204);
	}

}
