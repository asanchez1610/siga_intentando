<% if ( typeof asignaturaList !== 'undefined' && Object.prototype.toString.call(asignaturaList) === '[object Array]' && asignaturaList.length > 0) { %>

	<table id="search-subjects-results" class="table table-condensed table-hover">
		<thead>
			<tr>
				<th>Sigla</th>
				<th>Nombre</th>
				<th>Créd.</th>
				<th>Sección</th>
				<th>P.A.</th>
				<th>Campus</th>
				<th>Período</th>
				<th>N° Matric.</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<% asignaturaList.forEach( function ( item ) { %>
				<tr>
					<td><%= item.sigla %></td>
					<td><%= item.nombre %></td>
					<td><%= item.creditos %></td>
					<td><%= item.asignaturaSeccion.nombreSeccion %></td>
					<td><%= item.estudio %></td>
					<td><%= item.campus %></td>
					<td><%= item.periodoAcademico.nombre %></td>
					<td><%= item.totalMatriculado %></td>
					<td><a class="btn btn-mini" href="#/asignaturas/<%= item.sigla %>/<%= item.id %>/<%= item.asignaturaSeccion.idSeccion %>"><i class="icon-search"></i></a></td>
				</tr>
			<% }); %>
		</tbody>
	</table>

<% }else{ %>
    <p class="alert">No hay resultados.</p>
<% } %>