<p><a class="btn btn-link btn-small btn-back hidden" href="javascript:history.back();"><i class="icon-angle-left"></i> Volver al perfil del profesor</a></p>
<% if ( tesis !== null) { %>
    <h3><%= tesis.titulo %></h3>
    <div class="accordion acoordion-mini" id="research-docs">
            <div class="accordion-group">
                    <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-general">
                                    Informacion General
                            </a>
                    </div>
                    <div id="research-docs-general" class="accordion-body collapse">
                            <div class="accordion-inner">
                                    <table class="table table-hover">
                                        <tbody>
                                                <tr>
                                                        <td class="head">Facultad</td>
                                                        <td><%= tesis.centroAcademico %></td>
                                                </tr>
                                                <% if (tesis.estudio.length > 0) { %>
                                                    <tr>
                                                            <td class="head">Programa Académico</td>
                                                            <td><%= tesis.estudio %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Departamento</td>
                                                        <td><%= tesis.departamento %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Área Académica</td>
                                                        <td><%= tesis.areaInvestigacion %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Campo de investigación</td>
                                                        <td><%= tesis.campoInvestigacion %></td>
                                                </tr>
                                                <% if (tesis.lineaInvestigacion.length > 0)  { %>
                                                    <tr>
                                                        <td class="head">Linea de investigación</td>
                                                        <td><%= tesis.lineaInvestigacion %></td>
                                                    </tr>
                                                <% } %>
                                        </tbody>
                                   </table>
                            </div>
                    </div>
            </div>
            <div class="accordion-group">
                    <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-detalle">
                                    Detalles de la investigación (<%= tesis.tipoTesis %>)
                            </a>
                    </div>
                    <div id="research-docs-detalle" class="accordion-body collapse">
                            <div class="accordion-inner">
                                   <table class="table table-hover">
                                        <tbody>
                                                <tr>
                                                        <td class="head">Estado</td>
                                                        <td><%= tesis.estado %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Autor(es)</td>
                                                        <td><%= tesis.autor %></td>
                                                </tr>
                                                <% if (tesis.asesor.length > 0) { %>
                                                    <tr>
                                                            <td class="head">Asesor</td>
                                                            <td><%= tesis.asesor %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Grado obtenido</td>
                                                        <td><%= tesis.gradoObtenido %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Universidad</td>
                                                        <td><%= tesis.universidad %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Elaboración</td>
                                                        <td>
                                                            <strong>Inicio: </strong><%= tesis.fechaInicio %>
                                                            <strong>Fin: </strong><%= tesis.fechaFin %>
                                                        </td>
                                                </tr>
                                                <% if (tesis.fechaSustentacion.length > 0) { %>
                                                    <tr>
                                                            <td class="head">Fecha de sustentación</td>
                                                            <td>
                                                                <%= tesis.fechaSustentacion %>
                                                            </td>
                                                    </tr>
                                                <% } %>
                                                <% if (tesis.isbn.length > 0 || tesis.paginas > 0 || tesis.depositoLegal.length > 0 ) { %>
                                                    <tr>
                                                            <td class="head">Datos de publicación</td>
                                                            <td>
                                                                <% if (tesis.isbn.length > 0) { %>
                                                                    <strong>ISBN: </strong> <%= tesis.isbn %>
                                                                <% } %>
                                                                <% if (tesis.paginas > 0 ) { %>
                                                                    <strong> / N° pág: </strong> <%= tesis.paginas %>
                                                                <% } %>
                                                                <% if (tesis.depositoLegal.length > 0 ) { %>
                                                                    <strong> / N° de depósito legal: </strong> <%= tesis.depositoLegal %>
                                                                <% } %>
                                                            </td>
                                                    </tr>
                                                <% } %>
                                                <% if (tesis.palabraClave.length > 0) { %>
                                                    <tr>
                                                            <td class="head">Palabras clave</td>
                                                            <td><%= tesis.palabraClave %></td>
                                                    </tr>
                                                <% } %>
                                        </tbody>
                                   </table> 
                            </div>
                    </div>
            </div>
            <% if (tesis.resumen.length > 0) { %>
                            <div class="accordion-group">
                                    <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-resumen">
                                                    Resumen
                                            </a>
                                    </div>
                                    <div id="research-docs-resumen" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                    <%= tesis.resumen %>
                                            </div>
                                    </div>
                            </div>
            <% } %>
            <% if (tesis.englishResumen.length > 0) { %>
                            <div class="accordion-group">
                                    <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-englishResumen">
                                                    Abstract
                                            </a>
                                    </div>
                                    <div id="research-docs-englishResumen" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                    <%= tesis.englishResumen %>
                                            </div>
                                    </div>
                            </div>
            <% } %>            
            <div class="accordion-group">
                    <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-docs">
                                    Documentos de la investigación
                            </a>
                    </div>
                    <div id="research-docs-docs" class="accordion-body collapse">
                            <div class="accordion-inner">
                                    <% if (tesis.restriciones !== null) { %>
                                        <% tesis.restriciones.forEach( function( item) { %>
                                            <p class="alert alert-info"><%= item %></p>
                                        <% }); %>
                                    <% } %>
                                    <% if (tesis.documentos !== null) { %>                                        
                                        <table class="table table-condensed table-hover">
                                            <thead>
                                                    <tr>
                                                            <th>Nombre</th>
                                                            <th>Detalles</th>
                                                    </tr>
                                            </thead>                                                
                                            <tbody>
                                                <% tesis.documentos.forEach( function( itemDoc) { %>
                                                    <tr>
                                                        <td><%= itemDoc.nombre %></td>
                                                        <td>
                                                            <strong>Peso: </strong><%= itemDoc.peso %>
                                                            <strong> / Formato: </strong><%= itemDoc.formato %>
                                                            <strong> / Programa requerido: </strong><%= itemDoc.programa %>
                                                             <% if (itemDoc.comentario.length > 0 ) { %>
                                                                <p><strong>Comentario: </strong><%= itemDoc.comentario %></p>
                                                             <% } %>
                                                        </td>
                                                    </tr>
                                                 <% }); %>
                                            </tbody>
                                        </table>                                        
                                    <% } else { %>
                                        <p class="alert alert-info">No hay documentos disponibles.</p>
                                    <% } %>
                                    <table class="table table-hover">
                                        <tbody>
                                            <% if (tesis.titularDerechos.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">Titular de los Derechos</td>
                                                        <td><%= tesis.titularDerechos %></td>
                                                </tr>
                                            <% } %>
                                            <% if (tesis.derechos.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">Licencia de Derechos</td>
                                                        <td><%= tesis.derechos %></td>
                                                </tr>
                                            <% } %>
                                            <% if (tesis.urlLicenciaDerecho.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">URL Licencia de Derechos</td>
                                                        <td><%= tesis.urlLicenciaDerecho %></td>
                                                </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                            </div>
                    </div>
            </div>
    </div>
<% } else { %>

	<p class="alert alert-info">La información de la tesis no est&aacute; disponible.</p>

<% } %>
