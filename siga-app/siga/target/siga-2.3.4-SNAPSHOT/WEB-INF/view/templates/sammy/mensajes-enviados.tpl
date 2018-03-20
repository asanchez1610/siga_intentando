<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/mensajes/inbox">Recibidos <span class="badge message-inbox-count"></span></a></li>
		<li class="active"><a href="#/mensajes/outbox">Enviados</a></li>
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

	<div class="accordion acoordion-mini" id="messages-outbox">

		<% mensajes.forEach(function(mensaje) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#mensaje-<%= mensaje.id %>">
						<span class="pre-title"><%= mensaje.fechaEnvio %></span>
						<span class="pre-title message-to">Para: <%= mensaje.personaPara.nombres %> <%= mensaje.personaPara.apellidoPaterno %></span>
						<span><%= mensaje.asunto %></span>
					</a>
				</div>
				<div id="mensaje-<%= mensaje.id %>" data-id="<%= mensaje.id %>" data-read="<%= mensaje.estadoLeido %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<div class="content"><%! mensaje.mensaje %></div>
					</div>
				</div>
			</div>
		<% }); %>

	</div>

<% } else { %>

	<p class="alert">Aún no has enviado mensajes.</p>
	<div class="btn-group section-subheader">
		<a class="btn btn-success" href="#/mensajes/nuevo"><i class="icon-edit"></i>Nuevo</a>
	</div>

<% } %>