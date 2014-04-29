$(document).ready(function(){
	$("#botonCargaInicial").click(function(){
		$("#grupo").html('');
		var text = $("#textoCargaInicial").val();
		if (!text){
			alert("Datos vacios");
		}
		else{
			$.post("api/carga", text, function(){
				$("#textoCargaInicial").val('');
			}).done(function(){
				$("#grupo").append($("<p>Insertado Grupos Correctamente</p>"));
			});
			
		}
	});
	
	$("#botonGrupo").click(function(){
	$("#grupo").html('');
		var text = $("#textoGrupo").val();
		if (!text){
			alert("Datos vacios");
		}
		else{
			$.getJSON("/api/grupos/"+text, function(data){
				
				$("#grupo").empty();
				$.each(data, function(index, value) {
					$("#grupo").append($("<p><b>"+ index + "</b>: " + value +"</p>"));
				});
				
				$("#textoGrupo").val('');
			});
		}
	});
	
	$("#botonGrupos").click(function(){
		$("#grupo").html('');
			$.getJSON("/api/grupos", function(data){
				$.each(data, function(key,val){
						$("#grupo").append($("<p>Nombre: "+ val.nombre +"<br/>Votos Positivos: "+val.votosPositivos+"<br/>Votos Negativos:"+val.votosNegativos+"</p>"));
						
				});
				
			});
		
	});
	
		
	$("#botonGrupoBorrarT").click(function(){
		
			$("#grupo").html('');
			$.ajax({
				url: '/api/grupos',
				type: 'DELETE',
				success: function(response) {
						$("#grupo").append($("<p>Borrado correctamente todos los grupos</p>"));
				},
				error: function(response){
						alert("Fallo al borrar los datos");
					}
			});
		
	});
	
	$("#botonGrupoBorrar").click(function(){
		$("#grupo").html('');
		var text = $("#textoGrupoBor").val();
		if (!text){
			alert("Datos vacios");
		}
		else{
			$.ajax({
				url: '/api/grupos/'+text,
				type: 'DELETE',
				success: function(response) {
						$("#grupo").append($("<p>Borrado correctamente el grupo "+text+"</p>"));
						$("#textoGrupoBor").val('');
				},
				error: function(response){
						alert("Fallo al borrar los datos");
					}
			});
		}
	});
	
	$("#botonGrupoInser").click(function(){
		$("#grupo").html('');
		var text = $("#textoGrupoInser").val();
		if (!text){
			alert("Datos vacios");
		}
		else{
			$.post("/api/grupos", text, function(){
				$("#textoGrupoInser").val('');
			}).done(function(){
				$("#grupo").append($("<p>Grupo Insertado Correctamente</p>"));
			}).error(function(){
				alert("Error al insertar datos");
			});
			
		}
	});
	
	$("#botonGrupoAct").click(function(){
		$("#grupo").html('');
		var text = $("#textoGrupoAct").val();
		var data = $("#textoActualizar").val();
		if (!text || !data){
			alert("Datos vacios");
		}
		else{
			$.ajax({
				url: '/api/grupos/'+text,
				type: 'PUT',
				data: data,
				success: function(response) {
						$("#grupo").append($("<p>Actualizado correctamente el grupo "+text+"</p>"));
						$("#textoGrupoAct").val('');
						 $("#textoActualizar").val('');
				},
				error: function(response){
						alert("Fallo al actualizar los datos");
					}
			});
		}
	});
});
