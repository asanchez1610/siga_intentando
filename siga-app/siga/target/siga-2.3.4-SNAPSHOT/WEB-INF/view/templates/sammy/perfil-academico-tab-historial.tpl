<div class="btn-group section-subheader">
	<button class="btn btn-mini btn-success dropdown-toggle" data-toggle="dropdown">Descargar por periodo <span class="caret"></span></button>
	<ul class="dropdown-menu">
		<% periodos.forEach(function(periodo) { %>
			<li><a href="https://siga.udep.edu.pe/files/asdkl.pdf?periodo=<%= periodo.id %>"><%= periodo.nombre %></a></li>
		<% }); %>
	</ul>
</div>
<div class="btn-group section-subheader">
	<a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar como lista</a>
	<a class="btn btn-mini btn-success" href="javascript:if(window.print)window.print()"><i class="icon-print"></i>Imprimir</a>
</div>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i>Expandir todo</button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i>Contraer todo</button>
</div>

<div class="accordion acoordion-mini" id="academicprof-options-historial">
	<% periodos.forEach(function(periodo) { %>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse" href="#academicprof-historial-<%= periodo.id %>">
					Periodo <%= periodo.nombre %>
				</a>
			</div>
			<div id="academicprof-historial-<%= periodo.id %>" class="accordion-body collapse">
				<div class="accordion-inner">

					<h4>Indicadores</h4>
					<div class="row-fluid">
						<table class="table table-condensed">
							<tbody>
								<tr>
									<td class="head span2">Índice periodo</td>
									<td class="span2"><%= periodo.indice.periodo %></td>
									<td class="head span2">Nivel</td>
									<td class="span2"><%= periodo.nivel %></td>
									<td class="head span2">Créditos desaprobados</td>
									<td class="span2"><%= periodo.creditos.desaprobados %></td>
								</tr>
								<tr>
									<td class="head span2">Índice biperiodo</td>
									<td class="span2"><%= periodo.indice.biperiodo %></td>
									<td class="head span2">Rendimiento</td>
									<td class="span2"><%= periodo.rendimiento %></td>
									<td class="head span2">Créditos matriculados</td>
									<td class="span2"><%= periodo.creditos.matriculados %></td>
								</tr>
								<tr>
									<td class="head span2">Índice acumulado</td>
									<td class="span2"><%= periodo.indice.acumulado %></td>
									<td class="head span2">Orden de mérito</td>
									<td class="span2"><%= periodo.ordenMerito %></td>
									<td class="head span2">Créditos convalidados</td>
									<td class="span2"><%= periodo.creditos.convalidados %></td>
								</tr>
								<tr>
									<td class="head span2">Ciclo</td>
									<td class="span2"><%= periodo.ciclo %></td>
									<td class="head span2">Créditos aprobados</td>
									<td class="span2"><%= periodo.creditos.aprobados %></td>
								</tr>
							</tbody>
						</table>
					</div>

					<h4>Observaciones</h4>
					<ul class="map-list well">
						<% periodo.observaciones.forEach(function(observacion) { %>
							<li><%= observacion %></li>
						<% }); %>
					</ul>

					<h4>Asignaturas</h4>
					<table class="table table-condensed table-hover">
						<thead>
							<tr>
								<th>Sigla</th>
								<th>Asignatura</th>
								<th>Tipo</th>
								<th>Plan E.</th>
								<th>Créditos</th>
								<th>Nota</th>
							</tr>
						</thead>
						<tbody>
							<% periodo.asignaturas.forEach(function(asignatura) { %>
								<tr>
									<td><%= asignatura.sigla %></td>
									<td><%= asignatura.asignatura %></td>
									<td><%= asignatura.tipo %></td>
									<td><%= asignatura.plan %></td>
									<td><%= asignatura.creditos %></td>
									<td><%= asignatura.nota %></td>
								</tr>
							<% }); %>
						</tbody>
					</table>

				</div>
			</div>
		</div>
	<% }); %>
</div>

<div class="btn-group dropup section-subheader">
	<button class="btn btn-mini btn-success dropdown-toggle" data-toggle="dropdown">Descargar por periodo <span class="caret"></span></button>
	<ul class="dropdown-menu">
		<% periodos.forEach(function(periodo) { %>
			<li><a href="https://siga.udep.edu.pe/files/asdkl.pdf?periodo=<%= periodo.id %>"><%= periodo.nombre %></a></li>
		<% }); %>
	</ul>
</div>
<div class="btn-group section-subheader">
	<a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar como lista</a>
	<a class="btn btn-mini btn-success" href="javascript:if(window.print)window.print()"><i class="icon-print"></i>Imprimir</a>
</div>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i>Expandir todo</button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i>Contraer todo</button>
</div>