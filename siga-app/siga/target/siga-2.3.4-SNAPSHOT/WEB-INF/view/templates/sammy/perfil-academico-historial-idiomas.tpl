<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/perfil/academico">Perfil</a></li>
                <% if(estudioActivo){ %>
		<li><a href="#/perfil/academico/actual">Periodo actual</a></li>
                <% } %>
		<li><a href="#/perfil/academico/indicadores">Indicadores</a></li>
		<li><a href="#/perfil/academico/historial">Historial</a></li>
                <li class="active"><a href="#/perfil/academico/historial">Historial idiomas</a></li>
                <% if(estudioActivo){ %>
		<li><a href="#/perfil/academico/solicitudes">Solicitudes</a></li>
                <% } %>
	</ul>
</div>

<% if (Object.prototype.toString.call(historial_idiomas) === '[object Array]' && historial_idiomas.length > 0) { %>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>

<div class="accordion acoordion-mini" id="academicprof-options-historial">
    <div class="accordion-group">
        <div class="accordion-heading active">
            <a class="accordion-toggle" data-toggle="collapse" href="#academicprof-historial-idiomas">
                    Idiomas
            </a>
        </div>
            <div id="academicprof-historial-idiomas" class="accordion-body in collapse">
                    <div class="accordion-inner">
                        <div class="row-fluid">
                            <table class="table table-condensed table-hover">
                                <thead>
                                    <tr>
                                        <th>Semestre</th>
                                        <th>Tipo</th>
                                        <th>Sigla</th>
                                        <th>Nivel</th>
                                        <th>Nota</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% historial_idiomas.forEach(function(periodo) { %>
                                        <tr>
                                            <td><%= periodo.semestre %></td>
                                            <td><%= periodo.tipo %></td>
                                            <td><%= periodo.sigla %></td>
                                            <td><%= periodo.nivel %></td>
                                            <% if (periodo.nota < 10.5) { %>
                                                <td style="color: red;"><%= periodo.nota %></td>
                                            <% } else {%> 
                                                <td style="color: blue;"><%= periodo.nota %></td>
                                            <% }%> 
                                        </tr>
                                    <% }); %>
                                </tbody>
                            </table>
                        </div>
                    </div>
            </div>
    </div>
</div>

<% } else { %>

	<p class="alert">No hay registros.</p>

<% } %>


