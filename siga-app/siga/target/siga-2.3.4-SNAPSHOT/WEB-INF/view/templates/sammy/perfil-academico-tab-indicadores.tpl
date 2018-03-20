<h4>Información al cierre del periodo <%= periodo %></h4>

<div class="row-fluid">
	<div class="span5">
		<table class="table table-condensed table-hover">
			<thead>
				<tr>
					<th>Índice</th>
					<th>Valor</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Indice acumulado</td>
					<td class="number"><%= indiceAcumulado %></td>
				</tr>
				<tr>
					<td>Indice último periodo</td>
					<td class="number"><%= indiceUltimoPeriodo %></td>
				</tr>
				<tr>
					<td>Indice biperiodo (<%= biperiodo.fin %> - <%= biperiodo.inicio %>)</td>
					<td class="number"><%= biperiodo.indice %></td>
				</tr>
				<tr>
					<td>Orden de mérito</td>
					<td class="number"><%= ordenMerito %></td>
				</tr>
				<tr>
					<td>Rendimiento</td>
					<td class="number"><%= rendimiento %></td>
				</tr>
			</tbody>
		</table>

		<table class="table table-condensed table-hover">
			<thead>
				<tr>
					<th>Créditos</th>
					<th>Número</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Matriculados (A+D)*</td>
					<td class="number"><%= matriculados %></td>
				</tr>
				<tr>
					<td>Aprobados (A)</td>
					<td class="number"><%= aprobados %></td>
				<tr>
					<td>Desaprobados (D)</td>
					<td class="number"><%= desaprobados %></td>
				</tr>
				<tr>
					<td>Reconocidos (R)</td>
					<td class="number"><%= reconocidos %></td>
				</tr>
				<tr>
					<td>Cumplidos (A+R)</td>
					<td class="number"><%= cumplidos %></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="span7">
		<img src="http://placehold.it/600x360" />
	</div>
</div>

<% if (Object.prototype.toString.call(grados) === '[object Array]' && grados.length > 0) { %>

	<hr />

	<table class="table table-condensed">
		<thead>
			<tr>
				<th>Grado/título</th>
				<th>Requisito</th>
				<th>Actual</th>
				<th>Cumple</th>
			</tr>
		</thead>
		<tbody>
			<% grados.forEach(function(item) { %>
				<tr>
					<td><%= item.titulo %></td>
					<td><strong>Créditos totales</strong> <%= item.requisito %></td>
					<td><%= item.actual %></td>
					<% if(item.cumple) { %>
						<td><i class="icon-ok"></i></td>
					<% } else { %>
						<td><i class="icon-error"></i></td>
					<% } %>
				</tr>
			<% }); %>
		</tbody>
	</table>

<% } %>