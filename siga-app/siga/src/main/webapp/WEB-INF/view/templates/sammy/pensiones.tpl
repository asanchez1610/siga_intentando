<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/pensiones/cronogramas">Cronograma de pagos</a></li>
                <% if (pagoEspecialActivo){ %>
                    <li><a href="#/pensiones/pagosEspeciales">Pagos especiales</a></li>
                <% } %>		
	</ul>
</div>

<div class="btn-group section-subheader">
	<!--<a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar</a>
	<a class="btn btn-mini btn-success" href="javascript:if(window.print)window.print()"><i class="icon-print"></i>Imprimir</a>-->
</div>
<% if(moroso){ %>
<p class="alert">Se ha bloqueado el acceso a las notas de sus asignaturas y a trámites académicos debido a que presenta deuda.</p>
<% } %>
<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>

<div class="accordion acoordion-mini" id="payments">
    <div class="accordion-group">
            <div class="accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#payments" href="#payments-0">
                        <%=tipoestudio %> <%= periodo.periodoAcademico %>
                    </a>
            </div>
            <div id="payments-0" class="accordion-body collapse">
                    <div class="accordion-inner">	
                            <% if ( pregrado !== null && pregrado.length > 0) { %>
                                <table class="table table-condensed table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Estado</th>
                                            <th>Vencimiento</th>
                                            <th>Importe (S/.)</th>
                                            <th>A cuenta (S/.)</th>
                                            <th>Ver pagos</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% pregrado.forEach(function(cuota) { %>
                                            <tr>
                                                <td><%= cuota.numcuota %></td>
                                                <td><%= cuota.estado %></td>
                                                <td><%= cuota.fechavencim %></td>
                                                <td><%= cuota.monto %></td>
                                                <td><%= cuota.acuenta %></td>
                                                 <td> <% if ( cuota.estado!=='Pendiente') { %>
                                                     <button class="btn btn-mini open-modal" title="ver pagos" data-id="<%= cuota.cpgp %>" data-fechav="<%= cuota.fechavencim %>" data-cuota="<%= cuota.numcuota%>"><i class="icon-search"></i></button>                                           
                                                      <% } %>
                                                 </td>
                                            </tr>
                                        <% }); %>
                                    </tbody>
                                </table>
                            <% } else { %>
                                <p class="alert alert-error">No encontramos cuotas.</p>
                            <% } %>
                    </div>
            </div>
    </div>
    <div class="accordion-group">
            <div class="accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#payments" href="#payments-1">
                        Idiomas
                    </a>
            </div>
            <div id="payments-1" class="accordion-body collapse">
                <div class="accordion-inner">
                    <% if ( idiomas !== null && idiomas.length > 0) { %>
                    <% idiomas.forEach(function(idioma) { %>
                    <h4><%= idioma[0].curso %></h4>
                    <div class="row-fluid">
                        <div class="accordion-inner">

                                <table class="table table-condensed table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Estado</th>
                                            <th>Vencimiento</th>
                                            <th>Importe (S/.)</th>
                                            <th>A cuenta (S/.)</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% idioma.forEach(function(cuota) { %>
                                            <tr>
                                                <td><%= cuota.numcuota %></td>
                                                <td><%= cuota.estado %></td>
                                                <td><%= cuota.fechavencim %></td>
                                                <td><%= cuota.monto %></td>
                                                <td><%= cuota.acuenta %></td>
                                            </tr>
                                        <% }); %>
                                    </tbody>
                                </table>

                        </div>
                    </div>
                    <% }); %>
                    <% } else { %>
                        <p class="alert alert-error">No encontramos cuotas.</p>
                    <% } %>
                </div>
            </div>
    </div>
    <div class="accordion-group">
            <div class="accordion-heading">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#payments" href="#payments-2">
                        Otras deudas
                    </a>
            </div>
            <div id="payments-2" class="accordion-body collapse">
                    <div class="accordion-inner">	
                        <% if ( otras_deudas !== null && otras_deudas.length > 0) { %>
                            <table class="table table-condensed table-hover">
                                <thead>
                                    <tr>
                                        <th>Semestre</th>
                                        <th>Concepto</th>
                                        <th>Importe (S/.)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% otras_deudas.forEach(function(cuota) { %>
                                        <tr>
                                            <td><%= cuota.semestre %></td>
                                            <td><%= cuota.concepto %></td>
                                            <td><%= cuota.monto %></td>
                                        </tr>
                                    <% }); %>
                                </tbody>
                            </table>
                        <% } else { %>
                            <p class="alert alert-error">No encontramos cuotas.</p>
                        <% } %>
                    </div>
            </div>
    </div>
</div>

<div class="btn-group section-subheader">
	<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
	<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
</div>

<p class="alert alert-info">En caso la fecha de pago se haya vencido, debe tener en cuenta que los importes mostrados no incluyen la mora.</p>

<div class="well">
	<ul class="meta">
		<li><i class="meta-icon icon-user"></i><strong>Nombres:</strong> <%= nombre %></li>
		<li><i class="meta-icon icon-list-alt"></i><strong>Carné:</strong> <%= carne %></li>
	</ul>
	<span class="label label-warning">Verifica tus datos al momento de cancelar tus deudas.</span>
</div>