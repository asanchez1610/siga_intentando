<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/perfil/academico">Perfil</a></li>
		<li><a href="#/perfil/academico/actual">Periodo actual</a></li>
		<li><a href="#/perfil/academico/indicadores">Indicadores</a></li>
		<li><a href="#/perfil/academico/historial">Historial</a></li>
                <li><a href="#/perfil/academico/historial-idiomas">Historial idiomas</a></li>
		<li class="active"><a href="#/perfil/academico/solicitudes">Solicitudes</a></li>
	</ul>
</div>

<% if (Object.prototype.toString.call(solicitudes) === '[object Array]' && solicitudes.length > 0) { %>

	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>

	<div class="accordion acoordion-mini" id="academicprof-options-solicitudes">

		<% solicitudes.forEach(function(solicitud) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#academicprof-solicitud-<%= solicitud.id %>">
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
				<div id="academicprof-solicitud-<%= solicitud.id %>" class="accordion-body collapse">
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

	<p class="alert">No has emitido ninguna solicitud.</p>

<% } %>

<p>
	<a class="btn" href="./planestudio/malla.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Plan de estudios</a>
	<!--<a class="btn" href="./planestudio/avancePlan.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Avance del plan</a>-->
</p>