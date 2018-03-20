<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/asesor/perfil">Mi asesor</a></li>
<!--
                <% if(isSugeridos) { %>
                    <li><a href="#/asesor/sugeridos">Selección de asesores</a></li>    
                <% } %>
-->
	</ul>
</div>

<% if(asesor !== null) { %>
<h2><%= asesor.apellidoPaterno %> <%= asesor.apellidoMaterno %>, <%= asesor.nombres %></h2>

<div class="section-subheader">
	<ul class="nav nav-pills category-filter">
		<li class="active"><a href="#/asesor">Perfil</a></li>
		<li><a href="#/asesor/horario">Horario</a></li>
		<li><a href="#/asesor/entrevistas">Entrevistas</a></li>
	</ul>
</div>

<div class="btn-group section-subheader hidden-phone">
	<a class="btn" href="#/mensajes/nuevo"><i class="icon-envelope "></i> Enviar mensaje</a>
	<a class="btn" href="#/profesores/<%= asesor.id %>/perfil"><i class="icon-book"></i> Perfil de investigación</a>
</div>
<div class="btn-group section-subheader visible-phone">
	<a class="btn" href="#/mensajes/nuevo"><i class="icon-envelope "></i> Mensaje</a>
	<a class="btn" href="#/profesores/<%= asesor.id %>/perfil"><i class="icon-book"></i> Investigación</a>
</div>

<div class="partial-layout">
	<div class="partial-fluid">
		<div class="partial-fluid-content">
			<table class="table table-hover">
				<tbody>
					<tr>
						<td class="head span3">Centro académico</td>
						<td class="span9"><%= centroacademico %></td>
					</tr>
					<tr>
						<td class="head span3">Departamento</td>
						<td class="span9"><%= departamento %></td>
					</tr>
					<tr>
						<td class="head span3">Cumpleaños</td>
						<td class="span9"><%= fechaCumpleanios %></td>
					</tr>
					<tr>
						<td class="head span3">Oficina</td>
						<td class="span9"><%= oficina %> <%= edificio.length > 0? '- Edif. ' + edificio : '' %></td>
					</tr>
					<tr>
						<td class="head span3">Anexo</td>
						<td class="span9"><%= anexo %></td>
					</tr>
					<tr>
						<td class="head span3">Email</td>
						<td class="span9">
                                                <% if(asesorEmail !== null) { %>
                                                    <a href="mailto:<%= asesorEmail %>"><%= asesorEmail %></a>
                                                <% } else { %>
                                                    No registrado.
                                                <% } %>
                                                </td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="partial-fixed">
		<img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= asesor.foto %>" alt="<%= asesor.apellidoPaterno %> <%= asesor.apellidoMaterno %>" />
	</div>
</div>
<% } else {%>
    <p class="alert alert-error">Usted no tiene un Asesor asignado.</p>
<% }%>