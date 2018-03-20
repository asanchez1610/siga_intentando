<% if ( pregrado && posgrado ) { %>
	<ul class="nav nav-tabs" id="schedules">
		<li class="active"><a data-toggle="tab" href="#pregrado">Pregrado</a></li>
		<li><a data-toggle="tab" href="#posgrado">Posgrado</a></li>
	</ul>
<% } %>


<div id="schedule-tabs" class="tab-content">

	<% if ( pregrado ) { %>
		<div class="tab-pane active" id="pregrado">

			<h2>Horario de clases</h2>
                            <% if(ishorarioevento){ %>
                               <a class="btn btn-small" href="#/horario/<%= sufix %>/2"><i class="icon-arrow-left"></i> Anterior</a>
                                <a class="btn btn-small" href="#/horario"><i class="icon-calendar"></i> Actual</a>
                                <a class="btn btn-small" href="#/horario/<%= sufix %>/1"><i class="icon-arrow-right"></i> Posterior</a>
                                <br>
                            <% }%>
			<table class="schedule table table-condensed table-bordered">
				<thead>
					<tr>
						<th></th>
                                                <% if(ishorarioevento){ %>
                                                <%  listafechas.forEach(function(fecha) {%>
                                                <th width="16.6%"><%= fecha %></th>
                                                <% }); %>

                                                <% } else{ %>
						<th width="16.6%">Lunes</th>
						<th width="16.6%">Martes</th>
						<th width="16.6%">Miércoles</th>
						<th width="16.6%">Jueves</th>
						<th width="16.6%">Viernes</th>
						<th width="16.6%">Sábado</th>
                                                <% }%>
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

			<table class="schedule table table-condensed table-bordered">
				<thead>
					<tr>
						<th></th>
						<th width="16.6%">Lunes</th>
						<th width="16.6%">Martes</th>
						<th width="16.6%">Miércoles</th>
						<th width="16.6%">Jueves</th>
						<th width="16.6%">Viernes</th>
						<th width="16.6%">Sábado</th>
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

		</div>
	<% } %>

	<% if ( posgrado ) { %>           
		<div class="tab-pane <%= pregrado? '' : 'active' %>" id="posgrado">
                     <% if ( posgradoRegularList.length > 0 ) { %>
			<h2>Horario de clases</h2>
			<ul class="schedule">
				<% posgradoRegularList.forEach(function(horario) { %>
					<li>
						<h4><i class="icon-calendar"></i><%= horario.diaNombre %> <small><%= horario.mesNombre %></small></h4>
						<ul class="map-list">
							<% horario.horaList.forEach(function(bloque) { %>
								<li><strong class="head"><%= bloque.bloqueHorario.descripcion %> </strong> <%= bloque.asignaturaDictada.sigla %> (<%= bloque.aula.id == 0? 'No definida' : bloque.aula.nombre %>)</li>						
							<% }); %>
						</ul>
					</li>
				<% }); %>
			</ul>
                     <% } else { %>
                        <p class="alert alert-info">No hay  información de horario de clases registrada.</p>
                     <% } %>
                     <% if ( posgradoPracticaList.length > 0 ) { %>
			<h2>Horario de prácticas</h2>
			<ul class="schedule">
				<% posgradoPracticaList.forEach(function(horario) { %>
					<li>
						<h4><i class="icon-calendar"></i><%= horario.diaNombre %> <small><%= horario.mesNombre %></small></h4>
						<ul class="map-list">
							<% horario.horaList.forEach(function(bloque) { %>
								<li><strong class="head"><%= bloque.bloqueHorario.descripcion %> </strong> <%= bloque.asignaturaDictada.sigla %> (<%= bloque.aula.nombre %>)</li>						
							<% }); %>
						</ul>
					</li>
				<% }); %>
			</ul>
                     <% } else { %>
                        <p class="alert alert-info">No hay  información de horario de prácticas registrada.</p>
                    <% } %>
		</div>
	<% } %>

</div>