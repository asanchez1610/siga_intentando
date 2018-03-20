<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/perfil/academico">Perfil</a></li>
                <% if(estudioActivo){ %>
		<li><a href="#/perfil/academico/actual">Periodo actual</a></li>
                <% } %>
		<li><a href="#/perfil/academico/indicadores">Indicadores</a></li>
		<li class="active"><a href="#/perfil/academico/historial">Historial</a></li>
                <li><a href="#/perfil/academico/historial-idiomas">Historial idiomas</a></li>
                <% if(estudioActivo){ %>
		<li><a href="#/perfil/academico/solicitudes">Solicitudes</a></li>
                <% } %>
	</ul>
</div>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
            <a href="json/perfilacademico/descargaHistorialToPDF/<%= currentPlan %>" class="btn btn-mini" rel="tooltip" data-toggle="tooltip" title="Descargar">
                Descargar en PDF <i class="icon-download-alt"></i></a>
</div>


<div class="accordion acoordion-mini" id="academicprof-options-historial">
	<% periodos.forEach(function(periodo) { %>
		<div class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse" href="#academicprof-historial-<%= periodo.periodoAcademico.id %>">
					Periodo <%= periodo.periodoAcademico.nombre %>
				</a>
			</div>
			<div id="academicprof-historial-<%= periodo.periodoAcademico.id %>" class="accordion-body collapse">
				<div class="accordion-inner">

					<h4>Indicadores</h4>
					<div class="row-fluid">
						<table class="table table-condensed">
							<tbody>
								<tr>
									<td class="head span2">Índice periodo</td>
									<td class="span2"><%= periodo.indicePeriodo %></td>
									<td class="head span2">Nivel</td>
									<td class="span2"><%= periodo.nivel %></td>
									<td class="head span2">Créditos desaprobados</td>
									<td class="span2"><%= periodo.creditosPeriodoDesaprobados %></td>
								</tr>
								<tr>
									<td class="head span2">Índice biperiodo <%= periodo.biperiodoTituloShow %></td>
									<td class="span2"><%= periodo.indiceBiperiodo %></td>
									<td class="head span2">Rendimiento</td>
									<td class="span2"><%= periodo.rendimientoShow %></td>
									<td class="head span2">Créditos matriculados</td>
									<td class="span2"><%= periodo.creditosPeriodoMatriculados %></td>
								</tr>
								<tr>
									<td class="head span2">Índice acumulado</td>
									<td class="span2"><%= periodo.indiceAcumulado %></td>
									<td class="head span2">Orden de mérito</td>
									<td class="span2"><%= periodo.ordenMeritoShow %></td>
									<td class="head span2">Créditos convalidados</td>
									<td class="span2"><%= periodo.creditosPeriodoConvalidados %></td>
								</tr>
								<tr>
									<td class="head span2">Ciclo</td>
									<td class="span2"><%= periodo.ciclo %></td>
									<td class="head span2">Créditos aprobados</td>
									<td class="span2"><%= periodo.creditosPeriodoAprobados %></td>
                                                                        <td class="head span2">&nbsp;</td>
                                                                        <td class="head span2">&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</div>

					<h4>Observaciones</h4>
					<ul class="map-list well">
						<% periodo.observacionList.forEach(function(observacion) { %>
                                                        <% if(observacion.valor.length > 0){ %>
                                                            <li>(<%= observacion.valor %>) <%= observacion.descripcion %></li>
                                                        <%} else{ %>
                                                            <li><%= observacion.descripcion %></li>
                                                        <%} %>
						<% }); %>
					</ul>
                                        <h4>Asignaturas</h4>
					<table class="table table-condensed table-hover">
						<thead>
							<tr>
								<th>Sigla</th>
								<th>Asignatura</th>
								<th>Tipo</th>
								<th>Plan E.</th>
								<th>Créditos</th>
								<th>Nota</th>
							</tr>
						</thead>
						<tbody>
							<% periodo.asignaturaDictadaList.forEach(function(asignatura) { %>
								<tr>
									<td><%= asignatura.sigla %></td>
									<td><%= asignatura.nombre %></td>
									<td><%= asignatura.tipoAsignatura %></td>
									<td><%= asignatura.estudio %> - <%= asignatura.planEstudio %></td>
									<td><%= asignatura.creditos %></td>
                                                                        <% if (asignatura.retiroCurso) { %>
                                                                                <td style="color: red;">Retiro de curso</td>
                                                                        <% } else if (asignatura.anulaCiclo) { %>
                                                                                <td style="color: red;">Ciclo anulado</td>
                                                                        <% } else {%> 
                                                                                <td style="color: <%= asignatura.promedio.color %>;"><%= asignatura.promedio.descripcion %></td>
                                                                        <% }%> 
								</tr>
							<% }); %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	<% }); %>
        
        <% if(clcList.length > 0) { %>
        <div class="accordion-group">
                <div class="accordion-heading">
                        <a class="accordion-toggle" data-toggle="collapse" href="#academicprof-historial-clc">
                                Créditos de Libre Configuración
                        </a>
                </div>                
                <div id="academicprof-historial-clc" class="accordion-body collapse">
                        <div class="accordion-inner">
                            <% if(clcObtenidosCount === 0 ) { %>
                                <p class="alert alert-info">Ud. no tiene CLC registrados</p>
                            <% } %>
                                <% if(actividadArtisticaList !== null) { %>
                                <h4>Actividad artística destacada</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Actividad</th>
                                                        <th>Institución</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadArtisticaList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.actividad %></td>
                                                                <td><%= actividad.institucion %></td>
                                                                <td><%= actividad.horasDedicadas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>                                            
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[0].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[0].maxValor %> / <%= clcList[0].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[0].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadInvestigacionList !== null) { %>
                                <h4>Actividad de investigación</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Docente a cargo</th>
                                                        <th>Investigación</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadInvestigacionList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.profesor.apellidoPaterno %>
                                                                    <%= actividad.profesor.apellidoMaterno %>
                                                                    <%= actividad.profesor.nombres %></td>
                                                                <td><%= actividad.nombre %></td>
                                                                <td><%= actividad.horasReconocidas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[1].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[1].maxValor %> / <%= clcList[1].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[1].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadProySocialAsistList !== null) { %>
                                <h4>Actividad de proyección social (Voluntariado)</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Institución organizadora</th>
                                                        <th>Institución beneficiada</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadProySocialAsistList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.organizadora %></td>
                                                                <td><%= actividad.beneficiada %></td>
                                                                <td><%= actividad.horasDedicadas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[2].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[2].maxValor %> / <%= clcList[2].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[2].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadProySocialProfList !== null) { %>
                                <h4>Actividad de proyección social profesional</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Institución organizadora</th>
                                                        <th>Institución beneficiada</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadProySocialProfList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.organizadora %></td>
                                                                <td><%= actividad.beneficiada %></td>
                                                                <td><%= actividad.horasDedicadas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[3].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[3].maxValor %> / <%= clcList[3].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[3].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadDeportivaList !== null) { %>
                                <h4>Actividad deportiva destacada</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Deporte</th>
                                                        <th>Institución</th>
                                                        <th>Semestre</th>
                                                        <th>CLC</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadDeportivaList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.deporte %></td>
                                                                <td><%= actividad.institucion %></td>
                                                                <td><%= actividad.semestre %></td>
                                                                <td><%= actividad.credReconocido %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>                                         
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[4].maxValor %> / <%= clcList[4].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[4].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadOtrosList !== null  || actividadIntercambioList !== null) { %>
                                
                                <h4>Asignaturas organizadas por Facultad u otras Facultades y/o de Intercambio estudiantil</h4>
                                
                               
                                
                                <% if(actividadOtrosList !== null && actividadIntercambioList == null) { %>
                                         <p class="text-info">Facultad u otras Facultades.</p>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Periodo</th>
                                                        <th>Sigla</th>
                                                        <th>Asignatura</th>
                                                        <th>Nota</th>
                                                        <th>PA.</th>
                                                        <th>CLC</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadOtrosList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.periodoAcademico.nombre %></td>
                                                                <td><%= actividad.sigla %></td>
                                                                <td><%= actividad.nombre %>
                                                                     <% if (actividad.asignaturaSeccion.descripcion !== null) { %>
                                                                        - <%= actividad.asignaturaSeccion.descripcion %>
                                                                     <% } %>
                                                                </td>
                                                                <td style="color: <%= actividad.promedio.color %>;">
                                                                <% if (actividad.promedio.aprobada) { %>
                                                                       Aprobada ( <%= actividad.promedio.valor %> )
                                                                <% } else {%> 
                                                                       Desaprobada ( <%= actividad.promedio.valor %> )
                                                                <% }%>
                                                                </td>
                                                                <td><%= actividad.estudio %></td>
                                                                <td><%= actividad.creditos %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[5].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[5].maxValor %> / <%= clcList[6].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[5].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                 <% } %>
                                 <% if(actividadOtrosList == null && actividadIntercambioList !== null) { %>
                                    <p class="text-info">Intercambio Estudiantil.</p>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Asignatura</th>
                                                        <th>Institución</th>
                                                        <th>Semestre</th>
                                                        <th>CLC</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadIntercambioList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.asignatura %></td>
                                                                <td><%= actividad.institucion %></td>
                                                                <td><%= actividad.semestre %></td>
                                                                <td><%= actividad.credReconocido %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>                                          
                                                <tr>
                                                    <td colspan = "4">CLC Máximo </td>
                                                    <td><%= clcList[8].maxValor %> / <%= clcList[8].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "4">Créditos reconocidos </td>
                                                    <td><%= clcList[8].clcObtenidos %></td>
                                                </tr>
                                        </table>
                                   <% } %>
                                   <% if(actividadOtrosList !== null && actividadIntercambioList !== null) { %>
                                          <p class="text-info">Facultad u otras Facultades.</p>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Periodo</th>
                                                        <th>Sigla</th>
                                                        <th>Asignatura</th>
                                                        <th>Nota</th>
                                                        <th>PA.</th>
                                                        <th>CLC</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadOtrosList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.periodoAcademico.nombre %></td>
                                                                <td><%= actividad.sigla %></td>
                                                                <td><%= actividad.nombre %>
                                                                     <% if (actividad.asignaturaSeccion.descripcion !== null) { %>
                                                                        - <%= actividad.asignaturaSeccion.descripcion %>
                                                                     <% } %>
                                                                </td>
                                                                <td style="color: <%= actividad.promedio.color %>;">
                                                                <% if (actividad.promedio.aprobada) { %>
                                                                       Aprobada ( <%= actividad.promedio.valor %> )
                                                                <% } else {%> 
                                                                       Desaprobada ( <%= actividad.promedio.valor %> )
                                                                <% }%>
                                                                </td>
                                                                <td><%= actividad.estudio %></td>
                                                                <td><%= actividad.creditos %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                        </table> 
                                    <p class="text-info">Intercambio Estudiantil.</p>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Asignatura</th>
                                                        <th>Institución</th>
                                                        <th>Semestre</th>
                                                        <th>CLC</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadIntercambioList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.asignatura %></td>
                                                                <td><%= actividad.institucion %></td>
                                                                <td><%= actividad.semestre %></td>
                                                                <td><%= actividad.credReconocido %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>                                          
                                                <tr>
                                                    <td colspan = "4">CLC Máximo </td>
                                                    <td><%= clcList[8].maxValor %> / <%= clcList[8].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "4">Créditos reconocidos </td>
                                                    <td><%= clcList[8].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                       <% } %> 
                                 <% } %>

                                <% if(actividadExtraConvenioList !== null) { %>
                                <h4>Actividades extracurriculares por convenio</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Actividad</th>
                                                        <th>Institución</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadExtraConvenioList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.actividad %></td>
                                                                <td><%= actividad.institucion %></td>
                                                                <td><%= actividad.horasDedicadas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[6].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[6].maxValor %> / <%= clcList[6].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[6].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadDisneyList !== null) { %>
                                <h4>Actividades disney</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Asignatura</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadDisneyList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.asignatura %></td>
                                                                <td><%= actividad.horasReconocidas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                         
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "4">Total </td>
                                                    <td><%= clcList[7].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "4">CLC Máximo </td>
                                                    <td><%= clcList[7].maxValor %> / <%= clcList[7].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "4">Créditos reconocidos </td>
                                                    <td><%= clcList[7].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                               
                                <% if(actividadCapacitProfList !== null) { %>
                                <h4>Capacitación profesional (seminarios, cursos, congresos, etc)</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Tipo del evento</th>
                                                        <th>Nombre del evento</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadCapacitProfList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.tipoEvento %></td>
                                                                <td><%= actividad.nombreEvento %></td>
                                                                <td><%= actividad.horasReconocidas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                        
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[9].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[9].maxValor %> / <%= clcList[9].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[9].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadEstudioDirigidoList !== null) { %>
                                <h4>Horas de estudio dirigido</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Asignatura</th>
                                                        <th>Docente</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadEstudioDirigidoList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.asignatura %></td>
                                                                <td><%= actividad.profesor.apellidoPaterno %>
                                                                    <%= actividad.profesor.apellidoMaterno %>
                                                                    <%= actividad.profesor.nombres %></td>
                                                                <td><%= actividad.horasReconocidas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                        
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5">Total </td>
                                                    <td><%= clcList[10].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[10].maxValor %> / <%= clcList[10].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[10].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadIdiomaList !== null) { %>
                                <h4>Tercer o Cuarto idioma</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Nombre del idioma</th>
                                                        <th>Número de idioma</th>
                                                        <th>Nivel</th>
                                                        <th>CLC</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadIdiomaList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.idioma %></td>
                                                                <td><%= actividad.numero %></td>
                                                                <td><%= actividad.nivel %></td>
                                                                <td><%= actividad.credReconocido %></td>                                                                        
                                                            </tr>
                                                    <% }); %>
                                                </tbody>                                         
                                                <tr>
                                                    <td colspan = "4">CLC Máximo </td>
                                                    <td><%= clcList[11].maxValor %> / <%= clcList[11].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "4">Créditos reconocidos </td>
                                                    <td><%= clcList[11].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                                <% } %>
                                <% if(actividadPracticasList !== null) { %>
                                <h4>Horas de prácticas preprofesionales</h4>
                                        <table class="table table-condensed table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Registrada</th>
                                                        <th>Empresa</th>
                                                        <th>Periodo</th>
                                                        <th>Horas</th>
                                                        <th>Tasa</th>
                                                        <th>Créditos</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% actividadPracticasList.forEach(function(actividad) { %>
                                                            <tr>
                                                                <td><%= actividad.fecha %></td>
                                                                <td><%= actividad.empresa %></td>
                                                                <td><%= actividad.desde %> - <%= actividad.hasta %></td>
                                                                <td><%= actividad.horasReconocidas %></td>
                                                                <td><%= actividad.tasaCredito %> CLC/ <%= actividad.tasaHora %> H</td>
                                                                <td><%= actividad.tasaTotalCred %></td>                                                                       
                                                            </tr>
                                                    <% }); %>
                                                </tbody>
                                                <tr>
                                                    <td colspan = "5" style ="text-align:left;">Total </td>
                                                    <td><%= clcList[12].totalHoras %>
                                                    </td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">CLC Máximo </td>
                                                    <td><%= clcList[12].maxValor %> / <%= clcList[12].modalidad %></td>
                                                </tr>                                          
                                                <tr>
                                                    <td colspan = "5">Créditos reconocidos </td>
                                                    <td><%= clcList[12].clcObtenidos %></td>
                                                </tr>
                                        </table>  
                            <% } %>
                        </div>
                </div>
               
        </div>
	<% } %>
</div>

<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>
<% if(planEstudio !== '-') { %>
<p>
    <a class="btn" href="./planestudio/malla.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Plan de estudios</a>
    <!--<a class="btn" href="./planestudio/avancePlan.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Avance del plan</a>-->
</p>
<% } %>