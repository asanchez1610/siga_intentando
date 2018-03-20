<h2>Resumen de evaluaciones
<% if(anulaCiclo) { %>
<span class="label label-important">Anuló ciclo</span>
<% } %>
</h2>
<% if(asignaturas.length > 0){ %>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>

<div class="accordion" id="subjects">
	<% asignaturas.forEach( function( item ) { %>
		<div class="accordion-group">
			<% if (!item.retiroCurso && !anulaCiclo) { %>
				<div class="accordion-heading">
					<a class="accordion-toggle collapsed" data-toggle="collapse" href="#subject-<%= item.id %>-<%= item.idSeccion %>">
						<button class="btn btn-mini btn-info btn-subject view-subject-detail" data-initials="<%= item.sigla %>" data-id="<%= item.id %>" data-idseccion="<%= item.idSeccion %>" rel="tooltip" data-toggle="tooltip" title="Ver en detalle">
							<i class="icon-zoom-in"></i>
							<%= item.sigla %>
						</button>
						<span><%= item.nombre %> (<%= item.nombreSeccion %>) <%= item.descripcionSeccion %>  </span>
                                                
                                                <% if (item.evaluacion) { %>
                                                    <i class="icon-flag pull-right icon-1-5x" rel="tooltip" title="Notificación de evaluación"></i>
                                                <% }%>                                                
                                                <% if (item.material) { %>
                                                    <i class="icon-file pull-right icon-1-5x" rel="tooltip" title="Nuevo material"></i>
                                                <% }%>
                                                <% if (item.aviso) { %>
                                                    <i class="icon-envelope-alt pull-right icon-1-5x" rel="tooltip" title="Nuevo aviso"></i>
                                                <% }%>
					</a>
				</div>
				<div id="subject-<%= item.id %>-<%= item.idSeccion %>" data-id="<%= item.id %>" data-idseccion="<%= item.idSeccion %>" data-ee="<%= currentPlan %>" class="accordion-body collapse">
					<div class="accordion-inner"></div>
				</div>
			<% } else { %>
				<div class="accordion-heading">
					<a class="accordion-toggle collapsed">
						<button class="btn btn-mini btn-info btn-subject disabled">
							<i class="icon-zoom-in"></i>
							<%= item.sigla %>
						</button>
						<span><%= item.nombre %> (<%= item.nombreSeccion %>) <%= item.descripcionSeccion %></span>
                                                <% if (!anulaCiclo) { %>
						<span class="label label-important">Retirado</span>
                                                <% } %>
					</a>
				</div>
			<% } %>
		</div>
	<% }); %>
</div>

<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>
<% } else{ %>
    <p class="alert alert-info">No se ha matriculado en ninguna asignatura.</p>
<% } %>