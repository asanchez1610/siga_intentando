<h4><%= modo %></h4>

<p><%= descripcion %></p>
<p class="alert"><%= descuentos %></p>
<p>
	<strong>Horas de clase:</strong> <%= horas %>&nbsp;&nbsp; 
	<strong>Inasistencias:</strong> <%= inasistencias %>&nbsp;&nbsp; 
        <% if ( totalDescuento !== null) { %>
                <strong>Descuento:</strong> <%= totalDescuento %>
        <% } %>
</p>

<table class="table table-condensed table-hover">
	<thead>
		<tr>
			<th>#</th>
			<th>Fecha</th>
			<th>Hora</th>
			<th>Asistencia</th>
		</tr>
	</thead>
	<tbody>
		<% asistenciaList.forEach(function(item, index) { %>
			<% if ( item.asistio ) { %>
				<tr>
					<td><%= index + 1 %></td>
					<td><%= item.fecha %></td>
					<td><%= item.hora %></td>
					<td><i class="icon-ok"></i></td>
				</tr>
			<% } else { %>
				<tr class="warning">
					<td><%= index + 1 %></td>
					<td><%= item.fecha %></td>
					<td><%= item.hora %></td>
					<td><i class="icon-remove"></i></td>
				</tr>
			<% } %>
		<% }); %>	
	</tbody>
</table>