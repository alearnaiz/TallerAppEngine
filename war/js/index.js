$(document).ready(function(){
	$("#botonCargaInicial").click(function(){
		var text = $("#textoCargaInicial").val();
		if (!text){
			alert("Datos vacios");
		}
		else{
			$.post("http://localhost:8888/api/carga", text, function(){
				$("#textoCargaInicial").val('');
			});
			
		}
	});
	
	$("#botonGrupo").click(function(){
		var text = $("#textoGrupo").val();
		if (!text){
			alert("Datos vacios");
		}
		else{
			$.getJSON("http://localhost:8888/api/grupos/"+text, function(data){
				
				$("#grupo").empty();
				$.each(data, function(index, value) {
					$("#grupo").append($("<p>"+ index + ": " + value +"</p>"));
				});
				
				$("#textoGrupo").val('');
			});
		}
	});
});