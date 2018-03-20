<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/perfil/academico">Perfil</a></li>
		<li class="active"><a href="#/perfil/academico/actual">Periodo actual</a></li>
		<li><a href="#/perfil/academico/indicadores">Indicadores</a></li>
		<li><a href="#/perfil/academico/historial">Historial</a></li>
                <li><a href="#/perfil/academico/historial-idiomas">Historial idiomas</a></li>
		<li><a href="#/perfil/academico/solicitudes">Solicitudes</a></li>
	</ul>
</div>

<% if (Object.prototype.toString.call(asignaturas) === '[object Array]' && asignaturas.length > 0) { %>

	<h3>Asignaturas del periodo <%= periodo %>&nbsp;
            <% if(anulaCiclo) { %>
                <span class="label label-important">Anuló ciclo</span>
            <% } %>
        </h3>
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
			<% asignaturas.forEach(function(asignatura) { %>
				<tr>
					<td><%= asignatura.sigla %></td>
					<td><%= asignatura.nombre %> <%= asignatura.asignaturaSeccion.descripcion %> 
                                        <% if (asignatura.retiroCurso) { %>
                                            <span class="label label-important">Retirado</span>
                                        <% } %>
                                        </td>
					<td><%= asignatura.asignaturaSeccion.nombreSeccion %></td>
					<td><%= asignatura.creditos %></td>
					<td>
						<% asignatura.profesorList.forEach(function(item, index) { %>
							<a href="#/profesores/<%= item.id %>/perfil"><%= item.nombres %> <%= item.apellidoPaterno %></a>
							<% if (index !== asignatura.profesorList.length - 1) { %>, <% } %>
						<% }); %>
					</td>
					
                                        <% if (asignatura.promedio) { %>
                                            <td style='color:<%= asignatura.promedio.color %>;'>
                                                <%= (asignatura.retiroCurso || anulaCiclo)? '-' : asignatura.promedio.descripcion %> 
                                            </td>
                                        <% } else { %> <td> - </td> <% } %>
                                        
				</tr>
			<% }); %>
		</tbody>
	</table>

        <div class="btn-group section-subheader" style='display:none;'>
                <a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar</a>
        </div>
        <div class="btn-group section-subheader">
                <button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
                <button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
        </div>

        <div class="accordion acoordion-mini" id="academicprof-options-periodo">

                <% asignaturas.forEach(function(item) { %>
                        <div class="accordion-group">
                                <% if (!(item.retiroCurso || anulaCiclo)) { %>
                                        <div class="accordion-heading">
                                                <a class="accordion-toggle collapsed" data-toggle="collapse" href="#academicprof-periodo-<%= item.sigla %>-<%= item.asignaturaSeccion.idSeccion %>">
                                                        <button class="btn btn-mini btn-info btn-subject view-subject-detail" data-initials="<%= item.sigla %>" data-id="<%= item.id %>" data-idseccion="<%= item.asignaturaSeccion.idSeccion %>" rel="tooltip" data-toggle="tooltip" title="Ver curso en detalle">
                                                                <i class="icon-zoom-in"></i>
                                                                <%= item.sigla %>
                                                        </button>
                                                        <span><%= item.nombre %> <%= item.asignaturaSeccion.descripcion %> </span>
                                                </a>
                                        </div>
                                        <div id="academicprof-periodo-<%= item.sigla %>-<%= item.asignaturaSeccion.idSeccion %>" class="accordion-body collapse">
                                                <div class="accordion-inner">
                                                        <% if(item.promedio > 0) { %>
                                                                <p><span class="label label-warning">Promedio</span> <%= item.promedio %></p>
                                                        <% } %>
                                                        <% if (Object.prototype.toString.call(item.evaluacionList) === '[object Array]' && item.evaluacionList.length > 0) { %>
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
                                                                                <% item.evaluacionList.forEach(function(item) { %>
                                                                                        <tr>
                                                                                                <td><%= item.fechaEvaluacion %></td>
                                                                                                <td><%= item.tipoEvaluacion.nombre %></td>
                                                                                                <td><%= item.nombre %></td>
                                                                                                <td><%= item.peso %></td>
                                                                                                <% if (item.anulable) { %>
                                                                                                                <td><i class="icon-ok"></i></td>
                                                                                                <% } else { %>
                                                                                                                <td><i class="icon-remove"></i></td>
                                                                                                <% } %>
                                                                                                <% if (item.informativa) { %>
                                                                                                                <td><i class="icon-ok"></i></td>
                                                                                                <% } else { %>
                                                                                                                <td><i class="icon-remove"></i></td>
                                                                                                <% } %>
                                                                                                <td><%= item.estadoEvaluacion.nombre %></td>
                                                                                                <% if (item.evaluacionAlumno !== null && !moroso) { %>
                                                                                                                <% if (!item.anulada) { %>
                                                                                                                                <% if (!item.informativa) { 
                                                                                                                                        if(item.evaluacionAlumno.nota !== null){ %>
                                                                                                                                           <td style='color:<%= item.evaluacionAlumno.nota.color %>;'><%= item.evaluacionAlumno.nota.descripcion %></td>
                                                                                                                                <%  } else{ %>
                                                                                                                                           <td></td>
                                                                                                                                <%  }
                                                                                                                                  } else { %>
                                                                                                                                                <td><%= item.evaluacionAlumno.notaInformativa %></td>
                                                                                                                                <% } %> 
                                                                                                                <% } else { %>
                                                                                                                                <td style='color:red;'>AxD</td>
                                                                                                                <% } %> 
                                                                                                <% } else { %>
                                                                                                                <% if (item.anulada) { %>
                                                                                                                                <td style='color:red;'>AxD</td>
                                                                                                                <% } else { %>
                                                                                                                                <td>&nbsp;</td>
                                                                                                                <% } %>													 
                                                                                                <% } %>
                                                                                        </tr>
                                                                                <% }); %>
                                                                        </tbody>
                                                                </table>
                                                        <% } else { %>
                                                                <p class="alert">A&uacte;n no se han registrado evaluaciones.</p>
                                                        <% } %>
                                                </div>
                                        </div>
                                <% } else { %>
                                        <div class="accordion-heading">
                                                <a class="accordion-toggle collapsed">
                                                        <button class="btn btn-mini btn-info btn-subject disabled">
                                                                <i class="icon-zoom-in"></i>
                                                                <%= item.sigla %>
                                                        </button>
                                                        <span><%= item.nombre %></span>
                                                        <% if(!anulaCiclo) { %>
                                                            <span class="label label-important">Retirado</span>
                                                        <% } %>
                                                </a>
                                        </div>
                                <% } %>
                        </div>
                <% }); %>

        </div>

        <div class="btn-group section-subheader" style='display:none;'>
                <a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar</a>
        </div>
        <div class="btn-group section-subheader">
                <button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
                <button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
        </div>
<% } else { %>

	<p class="alert alert-error">Asignaturas no disponibles.</p>

<% } %>

<p>
	<a class="btn" href="./planestudio/malla.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Plan de estudios</a>
	<!--<a class="btn" href="./planestudio/avancePlan.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Avance del plan</a>-->
</p>