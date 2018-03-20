<% if ( typeof profesorList !== 'undefined' && Object.prototype.toString.call(profesorList) === '[object Array]' && profesorList.length > 0) { %>

	<table id="search-people-results" class="table table-condensed table-hover">
		<thead>
			<tr>
				<th>Apellido paterno</th>
				<th>Apellido materno</th>
				<th>Nombres</th>
				<th>Facultad</th>
				<th>Departamento</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<% profesorList.forEach( function ( person ) { %>
				<tr>
					<td><%= person.apellidoPaterno %></td>
					<td><%= person.apellidoMaterno %></td>
					<td><%= person.nombres %></td>
					<td><%= person.centroAcademicoNombre %></td>
					<td><%= person.departamentoNombre %></td>
					<td><a class="btn btn-mini" href="#/profesores/<%= person.id %>/perfil"><i class="icon-search"></i></a></td>
				</tr>
			<% }); %>
		</tbody>
	</table>

<% }else{ %>
    <p class="alert">No hay resultados.</p>
<% } %>