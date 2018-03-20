<% if (Object.prototype.toString.call(resumenAsignaturas) === '[object Array]' && resumenAsignaturas.length > 0) { %>

	<div class="btn-group section-subheader">
		<a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar</a>
		<a class="btn btn-mini btn-success" href="javascript:if(window.print)window.print()"><i class="icon-print"></i>Imprimir</a>
	</div>
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i>Expandir todo</button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i>Contraer todo</button>
	</div>

	<div class="accordion acoordion-mini" id="academicprof-options-periodo">

		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse" href="#academicprof-periodo-resumen">
					Asignaturas del periodo <%= periodo %>
				</a>
			</div>
			<div id="academicprof-periodo-resumen" class="accordion-body collapse">
				<div class="accordion-inner">
					<table class="table table-condensed table-hover">
						<thead>
							<tr>
								<th>Sigla</th>
								<th>Asignatura</th>
								<th>Sección</th>
								<th>Créditos</th>
								<th>Docente</th>
								<th>Promedio</th>
							</tr>
						</thead>
						<tbody>
							<% resumenAsignaturas.forEach(function(asignatura) { %>
								<tr>
									<td><%= asignatura.sigla %></td>
									<td><%= asignatura.nombre %></td>
									<td><%= asignatura.seccion %></td>
									<td><%= asignatura.creditos %></td>
									<td><a href="#/profesores/<%= asignatura.docente.alias %>"><%= asignatura.docente.nombre %></a></td>
									<td><%= asignatura.promedio %></td>
								</tr>
							<% }); %>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<% asignaturas.forEach(function(item) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#academicprof-periodo-<%= item.sigla %>">
						<button class="btn btn-mini btn-info view-subject-detail" data-initials="<%= item.sigla %>" rel="tooltip" data-toggle="tooltip" title="Ver curso en detalle">
							<i class="icon-zoom-in"></i>
							<%= item.sigla %>
						</button>
						<span><%= item.nombre %></span>
					</a>
				</div>
				<div id="academicprof-periodo-<%= item.sigla %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<% if(item.promedio > 0) { %>
							<p><span class="label label-warning">Promedio</span> <%= item.promedio %></p>
						<% } %>
						<% if (Object.prototype.toString.call(item.evaluaciones) === '[object Array]' && item.evaluaciones.length > 0) { %>
							<table class="table table-condensed table-hover">
								<thead>
									<tr>
										<th>Fecha</th>
										<th>Tipo de evaluación</th>
										<th>Nombre</th>
										<th>Peso</th>
										<th>Anulable</th>
										<th>Informativa</th>
										<th>Estado</th>
										<th>Nota</th>
									</tr>
								</thead>
								<tbody>
									<% item.evaluaciones.forEach(function(item) { %>
										<tr>
											<td><%= item.fecha %></td>
											<td><%= item.tipo %></td>
											<td><%= item.nombre %></td>
											<td><%= item.peso %></td>
											<td>
												<% if(item.anulable) { %>
													<i class="icon-ok"></i>
												<% } else { %>
													<i class="icon-remove"></i>
												<% } %>
											</td>
											<td>
												<% if(item.informativa) { %>
													<i class="icon-ok"></i>
												<% } else { %>
													<i class="icon-remove"></i>
												<% } %>
											</td>
											<td><%= item.estado %></td>
											<td><%= item.nota %></td>
										</tr>
									<% }); %>
								</tbody>
							</table>
						<% } else { %>
							<p class="alert">Aún no se han registrado evaluaciones.</p>
						<% } %>
					</div>
				</div>
			</div>
		<% }); %>

	</div>

	<div class="btn-group section-subheader">
		<a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar</a>
		<a class="btn btn-mini btn-success" href="javascript:if(window.print)window.print()"><i class="icon-print"></i>Imprimir</a>
	</div>
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i>Expandir todo</button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i>Contraer todo</button>
	</div>

<% } else { %>

	<p class="alert alert-error">Ocurrió un error al cargar los datos.</p>

<% } %>