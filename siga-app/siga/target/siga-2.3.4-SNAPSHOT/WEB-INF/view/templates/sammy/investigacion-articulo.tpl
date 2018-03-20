<p><a class="btn btn-link btn-small btn-back hidden" href="javascript:history.back();"><i class="icon-angle-left"></i> Volver al perfil del profesor</a></p>
<% if ( articulo !== null) { %>
    <h3><%= articulo.titulo %></h3>
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
                                                        <td><%= articulo.centroAcademico %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Programa Académico</td>
                                                        <td><%= articulo.estudio %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Departamento</td>
                                                        <td><%= articulo.departamento %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Área Académica</td>
                                                        <td><%= articulo.areaInvestigacion %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Campo de investigación</td>
                                                        <td><%= articulo.campoInvestigacion %></td>
                                                </tr>
                                                <% if (articulo.lineaInvestigacion.length > 0)  { %>
                                                    <tr>
                                                        <td class="head">Linea de investigación</td>
                                                        <td><%= articulo.lineaInvestigacion %></td>
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
                                    Detalles de la investigación (<%= articulo.tipoArticulo %>)
                            </a>
                    </div>
                    <div id="research-docs-detalle" class="accordion-body collapse">
                            <div class="accordion-inner">
                                   <table class="table table-hover">
                                        <tbody>
                                                <tr>
                                                        <td class="head">Estado</td>
                                                        <td><%= articulo.estado %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Autor(es)</td>
                                                        <td><%= articulo.autor %></td>
                                                </tr>
                                                <% if (articulo.colaboradores.length > 0) { %>
                                                    <tr>
                                                            <td class="head">Colaboradores</td>
                                                            <td><%= articulo.colaboradores %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Medio de publicación</td>
                                                        <td><%= articulo.medioPublicacion %> (<%= articulo.proceso %>)</td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Nombre del medio</td>
                                                        <td><%= articulo.nombreMedio %></td>
                                                </tr>
                                                <% if (articulo.medioPublicacion === 'Revista indexada') { %>
                                                    <tr>
                                                        <td class="head">Indexada en</td>
                                                        <td><%= articulo.indexada %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Datos de publicación</td>
                                                        <td>
                                                            <strong>Publicado en acta: </strong><% if (articulo.publicadoActa) { %> Sí <% } else {%> No <% } %>
                                                            <% if (articulo.isbn.length > 0 && articulo.publicadoActa) { %>
                                                                <strong>ISBN: </strong> <%= articulo.isbn %>
                                                            <% } %>
                                                            <% if (articulo.acta.length > 0 && articulo.publicadoActa) { %>
                                                                <strong>Nombre del Acta: </strong> <%= articulo.acta %>
                                                            <% } %>                                                        
                                                            <% if (articulo.issn.length > 0 ) { %>
                                                                <strong>ISSN: </strong> <%= articulo.issn %>
                                                            <% } %>
                                                            <% if (articulo.edicion.length > 0 ) { %>
                                                                <strong> / Edición: </strong> <%= articulo.edicion %>
                                                            <% } %>
                                                            <% if (articulo.tomoVolumen.length > 0 ) { %>
                                                                <strong> / Volumen: </strong> <%= articulo.tomoVolumen %>
                                                            <% } %>
                                                            <% if (articulo.numeroFasciculo.length > 0 ) { %>
                                                                <strong> / N° de fascículo: </strong> <%= articulo.numeroFasciculo %>
                                                            <% } %>
                                                            <% if (articulo.inicio.length > 0 ) { %>
                                                                <strong> / Pág. Inicio: </strong> <%= articulo.inicio %>
                                                            <% } %>                                                        
                                                            <% if (articulo.fin.length > 0 ) { %>
                                                                <strong> / Pág. Fin: </strong> <%= articulo.fin %>
                                                            <% } %>
                                                            <% if (articulo.depositoLegal.length > 0 ) { %>
                                                                <strong> / N° de depósito legal: </strong> <%= articulo.depositoLegal %>
                                                            <% } %>
                                                        </td>
                                                </tr>
                                                <% if (articulo.doi.length > 0) { %>
                                                    <tr>
                                                        <td class="head">Digital Object Identifier (DOI)</td>
                                                        <td><%= articulo.doi %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Fecha y lugar de publicación</td>
                                                        <td>
                                                            <% if (articulo.fecha.length > 0 ) { %>
                                                                ( <%= articulo.fecha %> )
                                                            <% } %>
                                                            <% if (articulo.ciudadPublica.length > 0 ) { %>
                                                                <%= articulo.ciudadPublica %>,
                                                            <% } %>
                                                            <% if (articulo.paisPublica.length > 0 ) { %>
                                                                <%= articulo.paisPublica %>
                                                            <% } %>
                                                        </td>
                                                </tr>
                                                <% if (articulo.otraVersion.length > 0) { %>
                                                    <tr>
                                                        <td class="head">Otras versiones publicadas en (URL)</td>
                                                        <td><%= articulo.otraVersion %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Palabras clave</td>
                                                        <td><%= articulo.palabraClave %></td>
                                                </tr>

                                        </tbody>
                                   </table> 
                            </div>
                    </div>
            </div>
            <% if (articulo.resumen.length > 0) { %>
                            <div class="accordion-group">
                                    <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-resumen">
                                                    Resumen
                                            </a>
                                    </div>
                                    <div id="research-docs-resumen" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                    <%= articulo.resumen %>
                                            </div>
                                    </div>
                            </div>
            <% } %>
            <% if (articulo.englishResumen.length > 0) { %>
                            <div class="accordion-group">
                                    <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-englishResumen">
                                                    Abstract
                                            </a>
                                    </div>
                                    <div id="research-docs-englishResumen" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                    <%= articulo.englishResumen %>
                                            </div>
                                    </div>
                            </div>
            <% } %>
            <% if (articulo.evento !== null) { %>
                            <div class="accordion-group">
                                    <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-evento">
                                                    Presentación en eventos
                                            </a>
                                    </div>
                                    <div id="research-docs-evento" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                    <table class="table table-hover">
                                                        <tbody>
                                                                <tr>
                                                                        <td class="head">Título de la ponencia</td>
                                                                        <td><%= articulo.evento.tituloPonencia %></td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Número y nombre del evento</td>
                                                                        <td>
                                                                            <% if (articulo.evento.numEvento.length > 0 ) { %>
                                                                                <%= articulo.evento.numEvento %> 
                                                                            <% } %>
                                                                            <%= articulo.evento.nombre %></td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Organizado por</td>
                                                                        <td><%= articulo.evento.organizadoPor %></td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Ámbito</td>
                                                                        <td><%= articulo.evento.ambito %>: 
                                                                            <%= articulo.evento.ciudad %>,
                                                                            <%= articulo.evento.pais %>
                                                                        </td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Fecha</td>
                                                                        <td>
                                                                            <% if (articulo.evento.fecha.length > 0 ) { %>
                                                                                <%= articulo.evento.fecha %> 
                                                                            <% } %>
                                                                        </td>
                                                                </tr>
                                                                <% if (articulo.evento.descripcion.length > 0) { %>
                                                                    <tr>
                                                                        <td class="head">Descripción del evento</td>
                                                                        <td><%= articulo.evento.descripcion %></td>
                                                                    </tr>
                                                                <% } %>
                                                        </tbody>
                                                   </table>
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
                                    <% if (articulo.restriciones !== null) { %>
                                        <% articulo.restriciones.forEach( function( item) { %>
                                            <p class="alert alert-info"><%= item %></p>
                                        <% }); %>
                                    <% } %>
                                    <% if (articulo.documentos !== null) { %>                                        
                                        <table class="table table-condensed table-hover">
                                            <thead>
                                                    <tr>
                                                            <th>Nombre</th>
                                                            <th>Detalles</th>
                                                    </tr>
                                            </thead>                                                
                                            <tbody>
                                                <% articulo.documentos.forEach( function( itemDoc) { %>
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
                                            <% if (articulo.titularDerechos.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">Titular de los Derechos</td>
                                                        <td><%= articulo.titularDerechos %></td>
                                                </tr>
                                            <% } %>
                                            <% if (articulo.derechos.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">Licencia de Derechos</td>
                                                        <td><%= articulo.derechos %></td>
                                                </tr>
                                            <% } %>
                                            <% if (articulo.urlLicenciaDerecho.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">URL Licencia de Derechos</td>
                                                        <td><%= articulo.urlLicenciaDerecho %></td>
                                                </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                            </div>
                    </div>
            </div>
    </div>
<% } else { %>

	<p class="alert alert-info">La información del artículo no est&aacute; disponible.</p>

<% } %>
