<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/perfil/academico">Perfil</a></li>
                <% if(alumnoEstudio.periodoAcademicoVigente !== null){ %>
		<li><a href="#/perfil/academico/actual">Periodo actual</a></li>
                <% } %>
		<li><a href="#/perfil/academico/indicadores">Indicadores</a></li>
		<li><a href="#/perfil/academico/historial">Historial</a></li>
                <li><a href="#/perfil/academico/historial-idiomas">Historial idiomas</a></li>
                <% if(alumnoEstudio.periodoAcademicoVigente !== null){ %>
		<li><a href="#/perfil/academico/solicitudes">Solicitudes</a></li>
                <% } %>
	</ul>
</div>

<div class="row-fluid">
	<table class="table">
		<tbody>
			<tr>
				<td class="head span3">Centro académico</td>
				<td class="span3"><%= alumnoEstudio.edicionestudio.estudio.centroAcademico.nombre %></td>
				<td class="head span3">Estado actual</td>
				<td class="span3"><%= alumnoEstudio.estadoAlumno.nombre %> 
                                <% if(anulaCiclo){ %>
                                    <span class="label label-important">Anuló ciclo</span>
                                <% } %>
                                <% if(reincorporado){ %>
                                    &nbsp;<span class="label label-important">Reincorporado</span>
                                    <% if(condicion.length > 0){ %>
                                        <a title="" data-toggle="tooltip" rel="tooltip" href="#" onclick="return false;" data-original-title="<%= condicion %>">
                                            <i class="item-icon icon-flag"></i>
                                        </a>
                                    <% } %> 
                                <% } %>                                                                
                                </td>
			</tr>
			<tr>
				<td class="head span3">Periodo de ingreso</td>
				<!--<td class="span3"><%= alumnoEstudio.periodoAcademicoIngreso.id !== 0? alumnoEstudio.periodoAcademicoIngreso.nombre : '' %></td>-->
                                <td class="span3"><%= periodoIngreso %></td>
				<td class="head span3">Campus</td>
				<td class="span3"><%= alumnoEstudio.campus %></td>
			</tr>
			<tr>
				<td class="head span3">Ciclo actual</td>
				<td class="span3"><%= alumnoEstudio.ciclo %></td>
				<td class="head span3">Estado socioeconómico</td>
				<!--<td class="span3"><%= alumnoEstudio.moroso? 'Con deuda' : 'Sin deuda' %></td>-->
                                <td class="span3"><%= moroso? 'Con deuda' : 'Sin deuda' %></td>
			</tr>
			<tr>
				<td class="head span3">Nivel</td>
				<td class="span3"><%= alumnoEstudio.nivel %></td>
				<td class="head span3">Becado</td>
				<td class="span3"><%= becado %></td>
			</tr>
			<tr>
				<td class="head span3">Tipo de estudio</td>
				<td class="span3"><%= alumnoEstudio.edicionestudio.estudio.tipoEstudio.nombre %></td>
				<td class="head span3">Asesor</td>
				<td class="span3">
                                    <% if (asesor !== null) { %>
                                        <a href="#/profesores/<%= asesor.id %>/perfil"><%= asesor.nombres %> <%= asesor.apellidoPaterno %></a>
                                    <% } else { %>
                                        No registrado.
                                    <% } %>
                                </td>
			</tr>
			<tr>
				<td class="head span3">Plan de estudio</td>
				<td class="span3"><%= planEstudio %> <%= especialidad %></td>
				<td class="head span3">Correo del asesor</td>
				<td class="span3">
                                    <% if(asesorEmail !== null) { %>
                                        <a href="mailto:<%= asesorEmail %>"><%= asesorEmail %></a>
                                    <% } else { %>
                                        No registrado.
                                    <% } %>
                                </td>
			</tr>
                        <tr>
                            <td class="head span3">Colegio de procedencia</td>
                            <td class="span3" colspan="3"><%= colegioProcedencia %></td>
                        </tr>
		</tbody>
	</table>
</div>
<% if(planEstudio !== '-') { %>
<p>
	<a class="btn" href="./planestudio/malla.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Plan de estudios</a>
	<!--<a class="btn" href="./planestudio/avancePlan.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Avance del plan</a>-->
</p>
<% } %>