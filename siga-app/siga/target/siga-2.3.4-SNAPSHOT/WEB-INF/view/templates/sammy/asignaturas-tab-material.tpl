<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>

<div class="accordion acoordion-mini" id="subject-options-materiales">

	<% tipos.forEach(function(tipo) { %>
		
		<% if (Object.prototype.toString.call(tipo.archivos) === '[object Array]' && tipo.archivos.length > 0) { %>

			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#subject-materiales-<%= tipo.tipo.id %>">
						<%= tipo.tipo.nombre %>
					</a>
				</div>
				<div id="subject-materiales-<%= tipo.tipo.id %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<table class="table table-condensed table-hover">
							<thead>
								<tr>
									<th>Título</th>
									<th>Descripción</th>
									<th>Fecha</th>
									<th>Publicado por</th>
									<th>Tamaño</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<% tipo.archivos.forEach(function(item) { %>
									<tr>
										<td><%= item.titulo %></td>
										<td><%= item.descripcion %></td>
										<td><%= item.fecha %></td>
										<td><%= item.publicador %></td>
										<td><%= item.tamanio %></td>
										<td><a href="json/asignatura/downloadMaterial/<%= item.id %>" class="btn btn-mini" rel="tooltip" data-toggle="tooltip" title="Descargar">
                                                                                        <i class="icon-download-alt"></i></a>
                                                                                        <% if (item.toCentroCopiado) { %>
                                                                                            <a  class="btn btn-mini" href="#" onclick="return false;" rel="tooltip" data-toggle="tooltip" title="Disponible en centro de copiado">
                                                                                                <i class="icon-external-link"></i>
                                                                                            </a>
                                                                                        <% } %>
                                                                                </td>
									</tr>
								<% }); %>
							</tbody>
						</table>
						<!-- <p><a href="https://siga.udep.edu.pe/files/archivo.zip" class="btn btn-small">Descargar todos los archivos de esta sección</a></p> -->
					</div>
				</div>
			</div>
		<% } %>

	<% }); %>
</div>

<% if (tipos.length == 0) { %>
	<p class="alert">A&uacute;n no se ha colocado material.</p>
<% } %>

<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>