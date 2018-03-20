<!--[if lt IE 9]>
	<p class="alert alert-warning">Estás usando un navegador <strong>desactualizado</strong>. Por favor <a href="http://browsehappy.com/">actualiza tu navegador</a> o <a href="http://www.google.com/chromeframe/?redirect=true">activa Google Chrome Frame</a> para mejorar tu experiencia.</p>
<![endif]-->
 
<% if(isDelegados) { %>
<p class="alert">Solo hay elección de delegados para alumnos de pregrado.</p>

<% } else { %>
<% if(isNivel) { %>
<p class="alert">No hay elección de delegados para su nivel,programa y campus.</p>

<% } else { %>
<% if(!verresultadosvotacion) { %>
<p>Ejerce tus derechos y elige a tus delegados. Para participar selecciona la opción que deseas y luego da click en el botón "Registrar Voto" ubicado a la derecha de cada candidato.</p>
<h2>CANDIDATOS DE <%= alumnoEstudio.edicionestudio.estudio.nombre %>
</h2>
 <% if(fechaInicio){ %>
      <div class="alert">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>Las votaciones inician : <%=fechaDelegados %></strong>
    </div>
 <% } %>
 <% if(yaVoto){ %>
      <div class="alert alert-info">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>Tu voto fue registrado,Gracias por participar,los resultados se publicarán el día : <%=fechaResultados %></strong>
    </div>
 <% } %>
 
	<table class="table table-hover">
            <tbody>
              <% candidatos.forEach(function(item){ %>
              <tr>
		<td>
                    <img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= item.alumno.foto %>" width = "100px"/>
                </td>
		<td>
                    <div class="row">
                        <span class="head span2">Alumno </span>
                        <span><%= item.alumno.apellidoPaterno %>  <%= item.alumno.apellidoMaterno %>,<%= item.alumno.nombres %></span>
                    </div>
                    <div class="row">
                        <span class="head span2">Nivel </span>
                        <span><%=item.nivel%></span>
                    </div>
                </td>
                <td>
                      <% if(verBoton){ %>
                        <center ><a href="#/delegados/confirmar/<%= item.idalumno%>/<%= item.idedicionetudio%>/<%= item.nivel%>/<%= item.idalumnocandidato%>" class="btn " title="Votar"> <i class="icon-external-link-sign"></i> Registrar Voto</a></center> 
                       <% } %>
                </td>
		</tr>
	
	      <%  }); %>   
                
            </tbody>
        </table>

<% } else { %> 
     <h2>RESULTADOS DE <%= alumnoEstudio.edicionestudio.estudio.nombre %> - Nivel <%=alumnoEstudio.nivel%> <%=Ciclos %>
     </h2>
     <div class="alert alert-info">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>En caso haya igualdad de votos,la Direccion de Estudios será la encargada de elegir al Delegado.</strong>
    </div>
     <table class="table table-hover">
            <tbody>
              <% candidatos.forEach(function(item){ %>
              <tr>
		<td>
                    <img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= item.alumno.foto %>" width = "100px"/>
                </td>
		<td>
                    <div class="row">
                        <span class="head span2">Alumno </span>
                        <span><%= item.alumno.apellidoPaterno %>  <%= item.alumno.apellidoMaterno %>,<%= item.alumno.nombres %></span>
                    </div>
                    <div class="row">
                        <span class="head span2">Nivel </span>
                        <span><%=item.nivel%></span>
                    </div>
                </td>
                <td>
                    <h1> <%= item.cantidadvotos %> Votos</h1>
                </td>
		</tr>
	
	      <%  }); %>   
                
            </tbody>
        </table>
<% } %>
<% } %>
<% } %>
