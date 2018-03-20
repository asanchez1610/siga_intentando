<% if (Object.prototype.toString.call(asignaturas) === '[object Array]' && asignaturas.length > 0) { %>

	<table id="subject-list" class="table table-condensed table-hover table-mini">
		<tbody>
			<% asignaturas.forEach(function(item) { %>
				<tr>
					<td class="head"><%= item.sigla %></td>
					<% if(item.retiroCurso) { %>
						<td style='text-decoration: line-through;'><%= item.nombre %> <%= item.descripcionSeccion %></td>
						<td></td>
					<% } else { %>
						<td><%= item.nombre %> <%= item.descripcionSeccion %></td>
						<td>
							<a class="btn btn-mini" href="#/cursos/<%= item.sigla %>/<%= item.id %>/<%= item.idSeccion %>" rel="tooltip" data-toggle="tooltip" title="Ver el curso en detalle">
								<i class="icon-zoom-in"></i>
							</a>
						</td>
					<% } %>
				</tr>
			<% }); %>
		</tbody>
	</table>

<% } %>