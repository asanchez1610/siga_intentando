<h2><%= asignatura.nombre %> - <%= asignatura.sigla %></h2>

<p><a class="btn btn-link btn-small" href="#/buscar/asignaturas"><i class="icon-angle-left"></i> Ver otras asignaturas</a></p>

<div class="row-fluid">
	<table class="table table-condensed table-hover">
		<tbody>
			<tr>
				<td class="head span3">Créditos</td>
				<td class="span3"><%= asignatura.creditos %></td>
				<td class="head span3">P.A.</td>
				<td class="span3"><%= asignatura.estudio %></td>
			</tr>
			<tr>
				<td class="head span3">Sección</td>
				<td class="span3"><%= asignatura.asignaturaSeccion.nombreSeccion %></td>
				<td class="head span3">Campus</td>
				<td class="span3"><%= asignatura.campus %></td>
			</tr>
			<tr>
				<td class="head span3">Sección académica</td>
				<td class="span3"><%= asignatura.seccionAcademica %></td>
				<td class="head span3">Periodo</td>
				<td class="span3"><%= asignatura.periodoAcademico.nombre %></td>
			</tr>
			<tr>
				<td class="head span3">Profesor</td>
				<td class="span3">
                                <% var cantidadProfesores = asignatura.profesorList.length; %>
                                <% if (cantidadProfesores === 1) { %>
                                        <a href="#/profesores/<%= asignatura.profesorList[0].id %>/perfil">
                                        <%= asignatura.profesorList[0].nombres %> <%= asignatura.profesorList[0].apellidoPaterno %></a>
                                <% }else if (cantidadProfesores > 1) { %>
                                        <% asignatura.profesorList.forEach(function(item, index) { %>
                                                <a href="#/profesores/<%= item.id %>/perfil"><%= item.nombres %> <%= item.apellidoPaterno %></a>
                                                <% if (index !== cantidadProfesores - 1) { %>, <% } %>
                                        <% }); %>
                                <% } %>                                    
                                </td>
				<td class="head span3">Nro. matriculados</td>
				<td class="span3"><%= countAlumnos %></td>
			</tr>
		</tbody>
	</table>
</div>

<hr/>

<div class="clearfix"></div>

<ul id="subject-nav-tabs" class="nav nav-tabs">
	<li><a href="#/asignaturas/<%= asignatura.sigla %>/<%= asignatura.id %>/<%= asignatura.asignaturaSeccion.idSeccion %>/horario" data-target="#subject-tab-horario" data-toggle="tab" data-id="horario" id="nav-tab-horario">Horario</a></li>
        <% if (isAlumno) { %>
	<li><a href="#/asignaturas/<%= asignatura.sigla %>/<%= asignatura.id %>/<%= asignatura.asignaturaSeccion.idSeccion %>/material" data-target="#subject-tab-material" data-toggle="tab" data-id="material" id="nav-tab-material">Material</a></li>
        <% } %>
</ul>
<div id="subject-tab-content" class="tab-content">
	<div class="tab-pane" id="subject-tab-horario"></div>
        <% if (isAlumno) { %>
	<div class="tab-pane" id="subject-tab-material"></div>
        <% } %>
</div>