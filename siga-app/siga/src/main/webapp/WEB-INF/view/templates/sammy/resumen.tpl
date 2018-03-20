<table class="table table-hover">
	<tbody>
		<% eventos.forEach( function( evento ) { %>
			<tr>
				<td class="head"><%= evento.tipo %></td>
				<% if( typeof evento.contenido === 'string' ) { %>
					<td><%= evento.contenido %></td>
				<% } else { %>
					<td>
						<ul>
							<% evento.contenido.forEach( function( item ) { %>
								<li><%! item %></li>
							<% }); %>
						</ul>
					</td>
				<% } %>
			</tr>
		<% }); %>
	</tbody>
</table>