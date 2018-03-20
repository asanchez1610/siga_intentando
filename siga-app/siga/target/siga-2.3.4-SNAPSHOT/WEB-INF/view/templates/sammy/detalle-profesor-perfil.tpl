<% if ( profesor !== null ) { %>

	<h2><%= profesor.apellidoPaterno %> <%= profesor.apellidoMaterno %>, <%= profesor.nombres %></h2>

	<p><a class="btn btn-link btn-small" href="#/buscar/profesores"><i class="icon-angle-left"></i> Ver otros profesores</a></p>

	<div class="section-subheader">
		<ul class="nav nav-pills category-filter">
			<li class="active"><a href="#/profesores/<%= profesor.id %>/perfil">Perfil</a></li>
			<li><a href="#/profesores/<%= profesor.id %>/investigacion">Trabajos de investigaci&oacute;n</a></li>
			<li><a href="#/profesores/<%= profesor.id %>/cv">CV</a></li>
		</ul>
	</div>

	<div class="partial-layout">
		<div class="partial-fluid">
			<div class="partial-fluid-content">
				<table class="table table-hover">
					<tbody>
						<tr>
							<td class="head span3">Centro académico</td>
							<td class="span9"><%= centroacademico %></td>
						</tr>
						<tr>
							<td class="head span3">Departamento</td>
							<td class="span9"><%= departamento %></td>
						</tr>
						<tr>
							<td class="head span3">Cumpleaños</td>
							<td class="span9"><%= fechaCumpleanios %></td>
						</tr>
						<tr>
							<td class="head span3">Oficina</td>
							<td class="span9"><%= oficina %> <%= edificio.length > 0? '- Edif. ' + edificio : '' %></td>
						</tr>
						<tr>
							<td class="head span3">Anexo</td>
							<td class="span9"><%= anexo %></td>
						</tr>
						<tr>
							<td class="head span3">Email</td>
							<td class="span9">
								<% if ( asesorEmail !== null ) { %>
									<a href="mailto:<%= asesorEmail %>"><%= asesorEmail %></a>
								<% } else { %>
									No registrado.
								<% } %>
							</td>
						</tr>
                        <% if (pagPersonal.length > 0) { %>
                            <tr>
                                <td class="head">Página Personal</td>
                                <td><a href="<%= pagPersonal %>"><%= pagPersonal %></a></td>
                            </tr>
                        <% } %>
                        <% if (blog.length > 0) { %>
                            <tr>
                                <td class="head">Blog</td>
                                <td><%= blog %></td>
                            </tr>
                        <% } %>
                        <% if (twitter.length > 0) { %>
                            <tr>
                                <td class="head">Twitter</td>
                                <td><%= twitter %></td>
                            </tr>
                        <% } %>
                        <% if (googleScholar.length > 0) { %>
                            <tr>
                                <td class="head">Google Scholar</td>
                                <td><%= googleScholar %></td>
                            </tr>
                        <% } %>
					</tbody>
				</table>
			</div>
		</div>
		<div class="partial-fixed">
			<img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= profesor.foto %>" alt="<%= profesor.apellidoPaterno %> <%= profesor.apellidoMaterno %>" />
		</div>
	</div>

	<div class="clearfix"></div>
	<h2>Horario de clases</h2>
	<table class="schedule table table-condensed">
		<thead>
			<tr>
				<th></th>
				<th>Lunes</th>
				<th>Martes</th>
				<th>Miércoles</th>
				<th>Jueves</th>
				<th>Viernes</th>
				<th>Sábado</th>
			</tr>
		</thead>
		<tbody>
			<% for ( var hour = 1; hour < 17; hour++ ) { %>
				<tr data-row="<%= hour %>">
					<td class="hour"><%= hour + 6 %>:00</td>
					<% for ( var day = 1; day < 7; day++ ) { %>
						<td id="ge-<%= hour %>-<%= day %>" class="day" data-col="<%= day %>"></td>
					<% } %>
				</tr>
			<% } %>
		</tbody>		
	</table>

	<h2>Horario de prácticas</h2>

	<table class="schedule table table-condensed">
		<thead>
			<tr>
				<th></th>
				<th>Lunes</th>
				<th>Martes</th>
				<th>Miércoles</th>
				<th>Jueves</th>
				<th>Viernes</th>
				<th>Sábado</th>
			</tr>
		</thead>
		<tbody>
			<% for ( var hour = 1; hour < 17; hour++ ) { %>
				<tr data-row="<%= hour %>">
					<td class="hour"><%= hour + 6 %>:00</td>
					<% for ( var day = 1; day < 7; day++ ) { %>
						<td id="pr-<%= hour %>-<%= day %>" class="day" data-col="<%= day %>"></td>
					<% } %>
				</tr>
			<% } %>
		</tbody>
	</table>

<% } else {%>

	<p class="alert alert-error">La información del profesor no est&acute; disponible.</p>

<% }%>