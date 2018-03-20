<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/consultas/pendientes">Pendientes</a></li>
		<li><a href="#/consultas/finalizados">Finalizados</a></li>
	</ul>
</div>

<% if (Object.prototype.toString.call(solicitudes) === '[object Array]' && solicitudes.length > 0) { %>

	<div class="btn-group section-subheader">
		<a href="#/consultas/nuevo" class="btn btn-mini btn-success"><i class="icon-pencil"></i> Nueva</a>
	</div>
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>

	<div class="accordion acoordion-mini" id="suggestions">

		<% solicitudes.forEach( function( solicitud ) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#suggestions" href="#suggestions-<%= solicitud.id %>">
						<span class="pre-title"><%= solicitud.fecha %></span> <%= solicitud.categoria.nombre %> <small class="pull-right"><%= solicitud.estadoConsulta.nombre %></small>
					</a>
				</div>
				<div id="suggestions-<%= solicitud.id %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<h4><i class="icon-comment-alt"></i> Yo el <%= solicitud.fecha %></h4>
						<p><%= solicitud.consulta %></p>

						<% if ( solicitud.fechaRespuesta ) { %>
							<h4><i class="icon-comments-alt"></i> Respuesta el <%= solicitud.fechaRespuesta %></h4>
							<p><%= solicitud.respuesta %></p>
                                                        <% if ( solicitud.estadoConsulta.id === 3 ) { %>
                                                                <% if ( solicitud.positivo ) { %>
                                                                        <p>Positivo: Sí</p>
                                                                <% } else { %> <p>Positivo: No</p> <% } %>
                                                        <% } %>
						<% } %>
					</div>
				</div>
			</div>
		<% }); %>

	</div>
	
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>

<% } else { %>

	<p class="alert">No hay reclamos y/o sugerencias registradas.</p>
	<div class="btn-group section-subheader">
		<a href="#/consultas/nuevo" class="btn btn-success"><i class="icon-pencil"></i> Nuevo</a>
	</div>

<% } %>