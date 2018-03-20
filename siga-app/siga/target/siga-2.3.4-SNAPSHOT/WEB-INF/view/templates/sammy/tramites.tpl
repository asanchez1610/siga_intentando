<% if(moroso){ %>
    <p class="alert">Se ha bloqueado el acceso a nuevos trámites académicos debido a que presenta deuda.</p>
<% } %>
<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/tramites/pendientes">Pendientes</a></li>
		<li><a href="#/tramites/finalizados">Finalizados</a></li>
	</ul>
</div>

<% if (Object.prototype.toString.call(solicitudes) === '[object Array]' && solicitudes.length > 0) { %>
        <% if(!moroso){ %>
            <div class="btn-group section-subheader">
                    <a href="#/tramites/nuevo" class="btn btn-mini btn-success"><i class="icon-pencil"></i>Iniciar trámite</a>
            </div>
        <% } %>
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>

	<div class="accordion acoordion-mini" id="process">

		<% solicitudes.forEach(function(solicitud) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#process" href="#solicitud-<%= solicitud.id %>">
						<span class="pre-title"><%= solicitud.fecha %></span> <%= solicitud.tipoSolicitud.nombre %> 
                                                <small class="pull-right">
                                                    <% if(solicitud.estadoSolicitud.nombre === estadoFin) { %>
                                                        <% if(solicitud.aprobada) { %>
                                                            Aprobada
                                                        <% }else{ %> Rechazada<% } %>
                                                    <% }else{ %> <%= solicitud.estadoSolicitud.nombre %><% } %>
                                                </small>
					</a>
				</div>
				<div id="solicitud-<%= solicitud.id %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<h4><i class="icon-comment-alt"></i> Yo el <%= solicitud.fecha %></h4>
						<p><%= solicitud.obsAlumno %></p>

                                                <% if(solicitud.fechaConfirmacion || solicitud.respuesta) { %>
                                                    <h4><i class="icon-comments-alt"></i> Respuesta
                                                        <% if(solicitud.fechaConfirmacion) { %>
                                                                 el <%= solicitud.fechaConfirmacion %>							
                                                        <% } %>
                                                    </h4>
                                                    <% if(solicitud.respuesta) { %>
                                                            <p><%= solicitud.respuesta %></p>
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

	<p class="alert">No hay trámites registrados.</p>
        <% if(!moroso){ %>
            <div class="btn-group section-subheader">
                    <a href="#/tramites/nuevo" class="btn btn-success"><i class="icon-pencil"></i>Iniciar trámite</a>
            </div>
        <% } %>
<% } %>