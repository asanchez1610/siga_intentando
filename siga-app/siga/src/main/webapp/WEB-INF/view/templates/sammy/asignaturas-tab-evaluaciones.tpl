<% if (tipos.length !== 0) { %>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>         
</div>    
<% } %>
<div>
    <% if (promedio) { %>
        <% if(!promedio.aprobada){ %>
            <p class="alert alert-error">
                <% if(notaSinDcto){ %>
                    <strong>Promedio sin dscto.:</strong> <%= notaSinDcto %>&nbsp; &nbsp;
                <% } %>
                <% if(totalDescuento){ %>
                    <strong>Descuento:</strong> <%= totalDescuento %>&nbsp;&nbsp;  
                 <% } %>
                 <strong>Promedio Final:</strong> <%= promedio.descripcion %>
            </p>
	<% } else { %>
            <p class="alert alert-info">
                <% if(notaSinDcto){ %>
                    <strong>Promedio sin dscto.:</strong> <%= notaSinDcto %>&nbsp; &nbsp;
                <% } %>
                <% if(totalDescuento){ %>
                    <strong>Descuento:</strong> <%= totalDescuento %>&nbsp;&nbsp;  
                 <% } %>
                 <strong>Promedio Final:</strong> <%= promedio.descripcion %>
            </p>
        <% } %>
    <% } %>
</div> 
<div class="accordion acoordion-mini" id="subject-options-evaluaciones">
	
	<% tipos.forEach(function(tipo) { %>
		
		<% if (Object.prototype.toString.call(tipo.evaluacionList) === '[object Array]' && tipo.evaluacionList.length > 0) { %>

			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#subject-evaluaciones-<%= tipo.id %>">
						<%= tipo.nombre %>
					</a>
				</div>
				<div id="subject-evaluaciones-<%= tipo.id %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<table class="table table-condensed table-hover">
							<thead>
								<tr>
                                                                        <th style="width:10px;">&nbsp;</th>
									<th>Fecha</th>
									<th>Evaluación</th>
									<th>Peso</th>
									<th>Anulable</th>
									<th>Informativa</th>
									<th>Estado</th>
									<th>Nota</th>
								</tr>
							</thead>
							<tbody>
								<% tipo.evaluacionList.forEach( function( item ) { 
										var checkError = '';
										if (item.evaluacionAlumno !== null && !moroso && !item.anulada && !item.informativa) {
											if(item.evaluacionAlumno.nota !== null){ 
												if(item.evaluacionAlumno.nota.descripcion < 10.5){
													checkError = 'error';
												}	
											}
										} %>
										<tr class='<%= checkError %>'>
                                                                                <td>
                                                                                    <% if (item.notifyTooltip !== null) { %>
                                                                                    <i class="icon-flag" rel="tooltip" title="<%= item.notifyTooltip %>"></i>
                                                                                    <% } %>
                                                                                </td>
										<td><%= item.fechaEvaluacion %></td>
										<td><button class="btn btn-link btn-link-inline open-modal" title="Temas" data-id="<%= item.idRandom %>"><%= item.nombre %></button></td>
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
													<%  } else { %>
														<td>&nbsp;</td>
													<% }
												} else { %>
													 <% if(item.evaluacionAlumno.notaInformativa!==null){ %>
									                                  <td><%= item.evaluacionAlumno.notaInformativa %></td>
								                                         <% } else { %>
                                                                                                          <td>&nbsp;</td>
                                                                                                          <% } %>
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
					</div>
				</div>
			</div>			
		<% } %>

	<% }); %>
</div>

<% if ( formula.length > 0 ) { %>
	<h4>Detalle de la evaluación</h4>
	<div class="well">
		<% if(promedioEstandar) { %>
			<p><strong>Estándar (Promedio ponderado)</strong></p>
			<p>El cálculo de promedio estándar considerará el peso de cada evaluación anulando la práctica con la menor nota y reemplazando la nota menor obtenida en un examen por la del sustitutorio si es que fuera el caso.</p>
			<p>Para esta asignatura la fórmula estándar es:</p>
			<p class="alert"><%= formula %></p>
			<% if(anulables.length > 0) { %>
				<p class="alert alert-info">Donde [NotaMenor] solo se restará si favorece al alumno y puede ser cualquiera de las siguientes:
				<%= anulables %></p>
			<% }%>
		<% } else { %>	
                        <p><strong>Personalizada</strong></p>
                        <% if(formula) { %>
                            <p>Para esta asignatura la fórmula personalizada es:</p>
                            <p class="alert"><%= formula %></p>
                        <% }%>
			<p class="alert alert-info">Esta fórmula es referencial.</p>			
		<% } %>
	</div>
<% } %>

<% if (tipos.length == 0) { %>
	<p class="alert">Aún no hay evaluaciones registradas.</p>
<% } else { %>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>
<% } %>