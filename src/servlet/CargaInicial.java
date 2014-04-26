package servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Busca;
import modelo.Grupo;
import modeloBD.GrupoEntityDS;
import modeloBD.IGrupoEntityDS;
import aws.BuscaVotos;

import com.google.gson.Gson;

public class CargaInicial extends HttpServlet {

	private static final long serialVersionUID = 2L;
	
	private IGrupoEntityDS db = new GrupoEntityDS();
	
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
			Busca[] busca = gson.fromJson(jsonString, Busca[].class);
			ArrayList<Grupo> votosGrupos = new ArrayList<Grupo>();
			votosGrupos = BuscaVotos.encuentraVotos(busca);
			for(int i = 0; i< votosGrupos.size(); i++){
				db.addGrupo(votosGrupos.get(i));
			}
			resp.setStatus(201);
		}
		catch(Exception e){
			e.getStackTrace();
			System.out.println("Error");
			System.out.println(jsonString);
			resp.sendError(404);
		}
	}
	
}
