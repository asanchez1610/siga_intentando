<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/asesor/entrevistas">Mi asesor</a></li>
                <% if(isSugeridos) { %>
                    <li><a href="#/asesor/sugeridos">Selección de asesores</a></li>    
                <% } %>               	
	</ul>
</div>
<h2><%= nombre %></h2>
<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/asesor/perfil">Perfil</a></li>
		<li><a href="#/asesor/horario">Horario</a></li>
		<li class="active"><a href="#/asesor/entrevistas">Entrevistas</a></li>
	</ul>
</div>
<div class="btn-group section-subheader hidden-phone">
	<a class="btn" href="#/mensajes/nuevo"><i class="icon-envelope "></i> Enviar mensaje</a>
	<a class="btn" href="#/profesores/<%= id %>/perfil"><i class="icon-book"></i> Perfil de investigación</a>
</div>
<div class="btn-group section-subheader visible-phone">
	<a class="btn" href="#/mensajes/nuevo"><i class="icon-envelope "></i> Mensaje</a>
	<a class="btn" href="#/profesores/<%= id %>/perfil"><i class="icon-book"></i> Investigación</a>
</div>
<% if ( historialList.length === 0 && pendienteList.length === 0 ) { %>
	
	<p class="alert alert-info">No has tenido entrevistas con tu asesor aún.</p>

<% } %>

<% if ( pendienteList.length > 0 ) { %>

	<h3>Entrevistas programadas</h3>
	<% pendienteList.forEach( function( entrevista ) { %>
		<p><span class="label"><%= entrevista.diaNombre %></span> El <strong><%= entrevista.mesNombre %></strong> tienes una entrevista con tu asesor.</p>
	<% }); %>

<% } %>

<% if ( historialList.length > 0 ) { %>

	<h3>Entrevistas pasadas</h3>
	<table class="table table-condensed table-hover">
		<thead>
			<tr>
				<th>Fecha</th>
				<th>Tipo</th>
				<th>Duración</th>
			</tr>
		</thead>
		<tbody>
			<% historialList.forEach( function( entrevista ) { %>
				<tr>
					<td><%= entrevista.fecha %></td>
					<td><%= entrevista.temaInteraccion %></td>
					<td><%= entrevista.duracion %> min.</td>
				</tr>
			<% }); %>
		</tbody>
	</table>

<% } %>
