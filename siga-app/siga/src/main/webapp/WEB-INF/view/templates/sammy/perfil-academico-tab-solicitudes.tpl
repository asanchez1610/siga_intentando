<% if (Object.prototype.toString.call(solicitudes) === '[object Array]' && solicitudes.length > 0) { %>

	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i>Expandir todo</button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i>Contraer todo</button>
	</div>

	<div class="accordion acoordion-mini" id="academicprof-options-solicitudes">

		<% solicitudes.forEach(function(solicitud) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#academicprof-solicitud-<%= solicitud.id %>">
						<span class="pre-title"><%= solicitud.fecha %></span> <%= solicitud.tipoSolicitud.nombre %> <small class="pull-right"><%= solicitud.estadoSolicitud.nombre %></small>
					</a>
				</div>
				<div id="academicprof-solicitud-<%= solicitud.id %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<h4><i class="icon-comment-alt"></i> Yo el <%= solicitud.fecha %></h4>
						<p><%= solicitud.obsAlumno %></p>

						<% if(solicitud.fechaConfirmacion) { %>
							<h4><i class="icon-comments-alt"></i> Respuesta el <%= solicitud.fechaConfirmacion %></h4>
							<p><%= solicitud.respuesta %></p>
						<% } %>
					</div>
				</div>
			</div>
		<% }); %>

	</div>
	
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i>Expandir todo</button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i>Contraer todo</button>
	</div>

<% } else { %>

	<p class="alert">No has emitido ninguna solicitud.</p>

<% } %>