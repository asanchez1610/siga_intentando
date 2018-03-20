<div class="section-subheader">
        <ul class="nav nav-pills">
                <li><a href="#/perfil/academico">Perfil</a></li>
                <% if(periodoAcademicoVigente !== null){ %>
                <li><a href="#/perfil/academico/actual">Periodo actual</a></li>
                <% } %>
                <li class="active"><a href="#/perfil/academico/indicadores">Indicadores</a></li>
                <li><a href="#/perfil/academico/historial">Historial</a></li>
                <li><a href="#/perfil/academico/historial-idiomas">Historial idiomas</a></li>
                <% if(periodoAcademicoVigente !== null){ %>
                <li><a href="#/perfil/academico/solicitudes">Solicitudes</a></li>
                <% } %>
        </ul>
</div>
<% if(idTipoEstudio != 2) { %>
    <h4>Información al cierre del periodo <%= periodo %></h4>

    <div class="row-fluid">
            <div class="span5">
                    <table class="table table-condensed table-hover">
                            <thead>
                                    <tr>
                                            <th>Índice</th>
                                            <th>Valor</th>
                                    </tr>
                            </thead>
                            <tbody>
                                    <tr>
                                            <td>Indice acumulado</td>
                                            <td class="number"><%= aepa.indiceAcumulado %></td>
                                    </tr>
                                    <tr>
                                            <td>Indice último periodo</td>
                                            <td class="number"><%= aepa.indicePeriodo %></td>
                                    </tr>
                                    <tr>
                                            <td>Indice biperiodo <%= biperiodo %></td>
                                            <td class="number"><%= aepa.indiceBiperiodo %></td>
                                    </tr>
                                    <tr>
                                            <td>Orden de mérito</td>
                                            <td class="number"><%= ordenMerito %></td>
                                    </tr>
                                    <tr>
                                            <td>Rendimiento</td>
                                            <td class="number"><%= rendimiento %></td>
                                    </tr>
                            </tbody>
                    </table>
                    <% if(showCreditos) { %>

                        <table class="table table-condensed table-hover">
                                <thead>
                                        <tr>
                                                <th>Créditos</th>
                                                <th>Número</th>
                                        </tr>
                                </thead>
                                <tbody>
                                        <tr>
                                                <td>Matriculados (A+D)*</td>
                                                <td class="number"><%= aepa != null? aepa.creditosTotalesMatriculados : '-' %></td>
                                        </tr>
                                        <tr>
                                                <td>Aprobados (A)</td>
                                                <td class="number"><%= aepa != null? aepa.creditosTotalesAprobados : '-' %></td>
                                        <tr>
                                                <td>Desaprobados (D)</td>
                                                <td class="number"><%= aepa != null? aepa.creditosTotalesDesaprobados : '-' %></td>
                                        </tr>
                                        <tr>
                                                <td>Reconocidos (R)</td>
                                                <td class="number"><%= aepa != null? aepa.creditosTotalesConvalidados : '-' %></td>
                                        </tr>
                                        <tr>
                                                <td>Cumplidos (A+R)</td>
                                                <td class="number"><%= aepa != null? aepa.creditosTotalesCumplidos : '-' %></td>
                                        </tr>
                                        <tr>
                                                <td colspan="2">(*) No incluye los créditos del periodo actual.</td>
                                        </tr>
                                </tbody>
                        </table>
                    <% } %>
            </div>
            <div class="span7">
                    <canvas id="the-chart" width="600" height="360"></canvas>
            </div>
    </div>

    <% if (Object.prototype.toString.call(grados) === '[object Array]' && grados.length > 0) { %>

            <hr />

            <table class="table table-condensed">
                    <thead>
                            <tr>
                                    <th>Grado/título</th>
                                    <th>Requisito</th>
                                    <th>Actual</th>
                                    <th>Cumple</th>
                            </tr>
                    </thead>
                    <tbody>
                            <% grados.forEach(function(item) { %>
                                    <tr>
                                            <td><%= item.titulo %></td>

                                            <% var colrequisito = ''; %>
                                            <% var colactual = ''; %>
                                            <% var colcumplido = ''; %>
                                            <% item.requisitoList.forEach(function(req) { 
                                                colactual += req.actual + '@'; 
                                                if(req.valor != null){
                                                    colrequisito += req.requisito + ' (' + req.valor + ')' + '@';
                                                   }else{
                                                    colrequisito += req.requisito + '@';
                                                   }
                                                %>
                                                <% if(req.cumple) { %>
                                                        <% colcumplido += 'ok@'; %>
                                                <% } else { %>
                                                        <% colcumplido += 'bad@'; %>                                                    
                                                <% } %>

                                            <% }); %>
                                            <td>
                                                <% var colrequisitosplit = colrequisito.split('@'); %>
                                                <% for (var i = 0; i < (colrequisitosplit.length - 1); i++) { %>
                                                    <%=  colrequisitosplit[i] %><br/>
                                                <% } %>
                                            </td>
                                            <td>
                                                <% var colactualsplit = colactual.split('@'); %>
                                                <% for (var i = 0; i < (colactualsplit.length - 1); i++) { %>
                                                    <%=  colactualsplit[i] %><br/>
                                                <% } %>
                                            </td>
                                            <td>
                                                <% var colcumplidosplit = colcumplido.split('@'); %>
                                                <% for (var i = 0; i < (colcumplidosplit.length - 1); i++) { %>
                                                    <% if(colcumplidosplit[i] == 'ok') { %>
                                                            <i class="icon-ok"></i><br/>
                                                    <% } else { if(colcumplidosplit[i] == 'bad'){ %>
                                                            <i class="icon-remove"></i><br/>                                         
                                                    <% } } %>
                                                <% } %>
                                            </td>
                                    </tr>
                            <% }); %>
                    </tbody>
            </table>

    <% } %>
    <% if(planEstudio !== '-') { %>
    <p>
            <a class="btn" href="./planestudio/malla.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Plan de estudios</a>
            <!--<a class="btn" href="./planestudio/avancePlan.htm?id=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Avance del plan</a>-->
    </p>
    <% } %>
<% } else { %>
<p class="alert alert-info">No hay  información registrada.</p>
<% } %>