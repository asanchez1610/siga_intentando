<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/asesor/horario">Mi asesor</a></li>
                <% if(isSugeridos) { %>
                    <li><a href="#/asesor/sugeridos">Selección de asesores</a></li>    
                <% } %>             	
	</ul>
</div>

<h2><%= asesor.apellidoPaterno %> <%= asesor.apellidoMaterno %>, <%= asesor.nombres %></h2>

<div class="section-subheader">
	<ul class="nav nav-pills category-filter">
		<li><a href="#/asesor">Perfil</a></li>
		<li class="active"><a href="#/asesor/horario">Horario</a></li>
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

<h2>Horario de clases</h2>

<table class="schedule table table-condensed">
	<thead>
		<tr>
			<th></th>
			<th>Lunes</th>
			<th>Martes</th>
			<th>Miércoles</th>
			<th>Jueves</th>
			<th>Viernes</th>
			<th>Sábado</th>
		</tr>
	</thead>
	<tbody>
		<% for ( var hour = 1; hour < 17; hour++ ) { %>
			<tr data-row="<%= hour %>">
				<td class="hour"><%= hour + 6 %>:00</td>
				<% for ( var day = 1; day < 7; day++ ) { %>
					<td id="ge-<%= hour %>-<%= day %>" class="day" data-col="<%= day %>"></td>
				<% } %>
			</tr>
		<% } %>
	</tbody>		
</table>

<h2>Horario de prácticas</h2>

<table class="schedule table table-condensed">
	<thead>
		<tr>
			<th></th>
			<th>Lunes</th>
			<th>Martes</th>
			<th>Miércoles</th>
			<th>Jueves</th>
			<th>Viernes</th>
			<th>Sábado</th>
		</tr>
	</thead>
	<tbody>
		<% for ( var hour = 1; hour < 17; hour++ ) { %>
			<tr data-row="<%= hour %>">
				<td class="hour"><%= hour + 6 %>:00</td>
				<% for ( var day = 1; day < 7; day++ ) { %>
					<td id="pr-<%= hour %>-<%= day %>" class="day" data-col="<%= day %>"></td>
				<% } %>
			</tr>
		<% } %>
	</tbody>
</table>