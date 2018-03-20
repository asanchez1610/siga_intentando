<a class="btn btn-link btn-small" href="#/cursos"><i class="icon-angle-left"></i> Ir al resumen</a>

<h2 class="subject-title"><%= nombre %></h2>

<ul class="subject-properties meta">
	<li>
		<i class="meta-icon icon-group"></i>
		<strong>Sección:</strong> <%= nombreSeccion %>
	</li>
	<% var cantidadProfesores = profesorList.length; %>
	<% if (cantidadProfesores === 1) { %>
		<li>
			<i class="meta-icon icon-bookmark"></i>
			<strong>Profesor(a):</strong>
			<a href="#/profesores/<%= profesorList[0].id %>/perfil">
                        <%= profesorList[0].nombre %></a>
		</li>
	<% } %>
	<% if (cantidadProfesores > 1) { %>
		<li>
			<i class="meta-icon icon-bookmark"></i>
			<strong>Profesor(es):</strong>
			<% profesorList.forEach(function(item, index) { %>
				<a href="#/profesores/<%= item.id %>"><%= item.nombre %></a>
				<% if (index !== cantidadProfesores - 1) { %>, <% } %>
			<% }); %>
		</li>
	<% } %>
	<li>
		<i class="meta-icon icon-th"></i>
		<strong>Nro. de alumnos:</strong> <%= countAlumnos %>
	</li>
</ul>

<div class="clearfix"></div>

<ul id="subject-nav-tabs" class="nav nav-tabs">
	<li><a href="#/cursos/<%= sigla %>/<%= id %>/<%= idSeccion %>/evaluaciones" data-target="#subject-tab-evaluaciones" data-toggle="tab" data-id="evaluaciones" id="nav-tab-evaluaciones">Evaluaciones</a></li>
	<li><a href="#/cursos/<%= sigla %>/<%= id %>/<%= idSeccion %>/material" data-target="#subject-tab-material" data-toggle="tab" data-id="material" id="nav-tab-material">Material</a></li>
	<li><a href="#/cursos/<%= sigla %>/<%= id %>/<%= idSeccion %>/asistencia" data-target="#subject-tab-asistencia" data-toggle="tab" data-id="asistencia" id="nav-tab-asistencia">Asistencia</a></li>
	<li><a href="#/cursos/<%= sigla %>/<%= id %>/<%= idSeccion %>/avisos" data-target="#subject-tab-avisos" data-toggle="tab" data-id="avisos" id="nav-tab-avisos">Avisos</a></li>
	<li><a href="#/cursos/<%= sigla %>/<%= id %>/<%= idSeccion %>/alumnos" data-target="#subject-tab-alumnos" data-toggle="tab" data-id="alumnos" id="nav-tab-alumnos">Alumnos</a></li>
	<li><a href="#/cursos/<%= sigla %>/<%= id %>/<%= idSeccion %>/silabo" data-target="#subject-tab-silabo" data-toggle="tab" data-id="silabo" id="nav-tab-silabo">Sílabo</a></li>
</ul>
<div id="subject-tab-content" class="tab-content">
	<div class="tab-pane" id="subject-tab-evaluaciones"></div>
	<div class="tab-pane" id="subject-tab-material"></div>
	<div class="tab-pane" id="subject-tab-asistencia"></div>
	<div class="tab-pane" id="subject-tab-avisos"></div>
	<div class="tab-pane" id="subject-tab-alumnos"></div>
	<div class="tab-pane" id="subject-tab-silabo"></div>
</div>