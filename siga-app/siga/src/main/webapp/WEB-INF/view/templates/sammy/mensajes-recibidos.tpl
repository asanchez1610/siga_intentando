<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/mensajes/inbox">Recibidos <span class="badge message-inbox-count"></span></a></li>
		<li><a href="#/mensajes/outbox">Enviados</a></li>
	</ul>
</div>

<% if ( mensajes.length > 0 ) { %>

	<div class="btn-group section-subheader">
		<a class="btn btn-mini btn-success" href="#/mensajes/nuevo"><i class="icon-edit"></i>Nuevo</a>
	</div>

	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>

	<div class="accordion acoordion-mini" id="messages-inbox">

		<% mensajes.forEach(function(mensaje) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#mensaje-<%= mensaje.id %>">
						<span class="pre-title"><%= mensaje.fechaEnvio %></span>
						<span class="pre-title message-from"><%= mensaje.personaDe.nombres %> <%= mensaje.personaDe.apellidoPaterno %></span>
						<%= mensaje.asunto %>
						<% if ( mensaje.estadoLeido ) { %>
							<i class="message-status icon-envelope pull-right"></i>
						<% } else { %>
							<i class="message-status icon-envelope-alt pull-right"></i>
						<% } %>
					</a>
				</div>
				<div id="mensaje-<%= mensaje.id %>" data-id="<%= mensaje.id %>" data-read="<%= mensaje.estadoLeido %>" data-fechaleido="<%= mensaje.fechaLeido %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<div class="content"><%! mensaje.mensaje %></div>
					</div>
				</div>
			</div>
		<% }); %>

	</div>

        <a class="btn" href="#/mensajes/inbox/all"><i class="icon-external-link-sign"></i> Ver todos</a>
<% } else { %>

	<p class="alert">Tu bandeja de entrada está vacía.</p>
	<div class="btn-group section-subheader">
		<a class="btn btn-success" href="#/mensajes/nuevo"><i class="icon-edit"></i>Nuevo</a>
	</div>

<% } %>