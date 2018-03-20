<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/pensiones/cronogramas">Cronograma de pagos</a></li>
		<li class="active"><a href="#/pensiones/pagosEspeciales">Pagos especiales</a></li>
	</ul>
</div>
<%if(campus===1 && cachimbo){ %>
<div class="alert alert-block">
    <p >
        Para el periodo académico actual 
        <% if ( periodo !== null) { %>
            <strong><%= periodo.nombre %></strong>
        <% } %>
        , la Universidad ya ha considerado la fecha de pago de sus pensiones 
        <% if ( tipoPago !== null) { %>
            <strong><%= tipoPago.nombre %></strong>.
        <% } %>
        A partir del siguiente semestre académico podrá elegir sus fechas de pago. 
    </p>
</div>

<% } else if(campus===2 && ciclo<2){ %>
<div class="alert alert-block">
    <p >
        Para el periodo académico actual 
        <% if ( periodo !== null) { %>
            <strong><%= periodo.nombre %></strong>
        <% } %>
        , la Universidad ya ha considerado la fecha de pago de sus pensiones 
        <% if ( tipoPago !== null) { %>
            <strong><%= tipoPago.nombre %></strong>.
        <% } %>
        A partir del 2° ciclo podrá elegir sus fechas de pago. 
    </p>
</div>

<% } else { %>

<div>
    <p>
        Para el periodo académico actual 
        <% if ( periodo !== null) { %>
            <strong><%= periodo.nombre %></strong>
        <% } %>
        , la Universidad ya ha considerado la fecha de pago de sus pensiones 
        <% if ( tipoPago !== null) { %>
            <strong><%= tipoPago.nombre %></strong>.
        <% } %>
         <% if ( campus===1) { %>
        de acuerdo a lo elegido por usted el periodo académico pasado 
        o por defecto en caso no lo haya modificado. 
        <% } %>
        Sin embargo, si usted deseara modificar esta fecha, 
        puede hacerlo seleccionando las fechas de pagos que desee. <br/><br/>
        Seleccione la opción que desee y se mostrará su cronograma de pagos correspondiente en la parte inferior.
    </p>
</div>

<div class="well">
        <strong>Información personal</strong>
	<ul class="meta">
		<li><i class="meta-icon icon-user"></i><strong>Nombres:</strong> <%= nombre %></li>
		<li><i class="meta-icon icon-list-alt"></i><strong>Carné:</strong> <%= carne %></li>
	</ul>
</div>

<div id="tipo" data-procesado="<%= procesado %>">
        <% if ( !procesado ) { %>
            <form class="form-horizontal" action="#/pensiones/cronogramas" method="post" id="new-pagoEsp">
                <input name="idedicionestudio" id="idedicionestudio" type="hidden" value="<%= currentPlan %>" />		
                <h4>Cronograma fecha de pagos | Periodo Académico: <%= periodo.nombre %></h4>
                <div class="controls">
                    <% if ( listTipoPago !== null  && listTipoPago.length > 0) { %>
                        <% listTipoPago.forEach(function(tipo) { %>
                            <% if ( tipoPago !== null ) { %>
                                <% if ( tipo.id === tipoPago.id ) { %>
                                    <label class="radio">
                                            <input type="radio" name="tipo" id="<%= tipo.id %>" value="<%= tipo.id %>" checked>
                                            <%= tipo.nombre %>
                                    </label>
                               <% } else { %>
                                    <label class="radio">
                                            <input type="radio" name="tipo" id="<%= tipo.id %>" value="<%= tipo.id %>">
                                            <%= tipo.nombre %>
                                    </label>
                                <% } %>
                            <% } else { %>
                                <% if ( tipo.id === 1 ) { %>
                                    <label class="radio">
                                            <input type="radio" name="tipo" id="<%= tipo.id %>" value="<%= tipo.id %>" checked>
                                            <%= tipo.nombre %>
                                    </label>
                               <% } else { %>
                                    <label class="radio">
                                            <input type="radio" name="tipo" id="<%= tipo.id %>" value="<%= tipo.id %>">
                                            <%= tipo.nombre %>
                                    </label>
                                <% } %>
                            <% } %>
                        <% }); %>
                   <% } %>
                </div>

                <div>
                    <h4>Cronograma de pagos </h4>
                    Periodo académico vigente desde: <%= periodo.fechaInicio %>  
                    <% if ( periodo.fechaFin !== null ) { %>
                        hasta: <%= periodo.fechaFin %> 
                    <% } %>
                    <table class="table table-condensed table-hover" id="fechaCuotaTable">
                        <thead>
                                <tr>
                                        <th>Cuota</th>
                                        <th>Fecha de pago</th>
                                </tr>
                        </thead>
                        <tbody>
                            <% if ( fechaPagoListDefault !== null  && fechaPagoListDefault.length > 0) { %>
                                <% fechaPagoListDefault.forEach(function(pago) { %>
                                    <tr>
                                        <td>Cuota # <%= pago.cuota %></td>
                                        <td><%= pago.fecha %></td>
                                    </tr>
                                <% }); %>
                            <% } %>
                        </tbody>
                    </table>                
                </div>
                <div class="form-actions">
                        <button id="submit-pago-esp" data-loading-text="Cargando..." type="submit" class="btn btn-primary">Enviar</button>
                        <a class="btn" href="#/pensiones/cronogramas">Cancelar</a>
                </div>
            </form>
        <% } else { %>
            <div>
                    <h4>Cronograma de pagos </h4>
                    Periodo académico vigente desde: <%= periodo.fechaInicio %> hasta: <%= periodo.fechaFin %> 
                    <table class="table table-condensed table-hover" id="fechaCuotaActualTable">
                        <thead>
                                <tr>
                                        <th>Cuota</th>
                                        <th>Fecha de pago</th>
                                </tr>
                        </thead>
                            <% fechaPagoListDefault.forEach(function(pago) { %>
                                <tr>
                                    <td>Cuota # <%= pago.cuota %></td>
                                    <td><%= pago.fecha %></td>
                                </tr>
                            <% }); %>
                        <tbody>
                        </tbody>
                    </table>  
                    <p class="alert alert-info">Su solicitud de pagos especiales ya ha sido procesada, 
                    si desea cambiar las fechas de pago debe acercarse a oficinas de pensiones.</p>
                </div>
        <% } %>
</div>
<% } %>