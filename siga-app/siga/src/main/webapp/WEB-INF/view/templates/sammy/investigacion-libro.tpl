<p><a class="btn btn-link btn-small btn-back hidden" href="javascript:history.back();"><i class="icon-angle-left"></i> Volver al perfil del profesor</a></p>
<% if ( libro !== null ) { %>
    <h3><%= libro.titulo %></h3>
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
                                                        <td><%= libro.centroAcademico %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Programa Académico</td>
                                                        <td><%= libro.estudio %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Departamento</td>
                                                        <td><%= libro.departamento %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Área Académica</td>
                                                        <td><%= libro.areaInvestigacion %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Campo de investigación</td>
                                                        <td><%= libro.campoInvestigacion %></td>
                                                </tr>
                                                <% if (libro.lineaInvestigacion.length > 0)  { %>
                                                    <tr>
                                                        <td class="head">Linea de investigación</td>
                                                        <td><%= libro.lineaInvestigacion %></td>
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
                                    Detalles de la investigación (<% if( libro.colectivo ) { %>Cap&iacute;tulos de Libro<% } else { %>Libro<% }%>)
                            </a>
                    </div>
                    <div id="research-docs-detalle" class="accordion-body collapse">
                            <div class="accordion-inner">
                                   <table class="table table-hover">
                                        <tbody>
                                                <tr>
                                                        <td class="head">Estado</td>
                                                        <td><%= libro.estado %></td>
                                                </tr>
                                                <% if (libro.tituloAlt.length > 0) { %>
                                                    <tr>
                                                            <td class="head">Título alternativo</td>
                                                            <td><%= libro.tituloAlt %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Coordinador</td>
                                                        <td><%= libro.coordinador %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Autor(es)</td>
                                                        <td><%= libro.autor %></td>
                                                </tr>
                                                <% if (libro.colaboradores.length > 0) { %>
                                                    <tr>
                                                            <td class="head">Colaboradores</td>
                                                            <td><%= libro.colaboradores %></td>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Serie o Colección y N°</td>
                                                        <td><%= libro.serieColeccion %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Editorial</td>
                                                        <td><%= libro.editorial %></td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Datos de publicación</td>
                                                        <td>
                                                            <% if (libro.isbn.length > 0) { %>
                                                                <strong>ISBN: </strong> <%= libro.isbn %>
                                                            <% } %>
                                                            <% if (libro.edicion.length > 0 ) { %>
                                                                <strong> / Edición: </strong> <%= libro.edicion %>
                                                            <% } %>
                                                            <% if (libro.paginas > 0 ) { %>
                                                                <strong> / N° pag: </strong> <%= libro.paginas %>
                                                            <% } %>
                                                            <% if (libro.depositoLegal.length > 0 ) { %>
                                                                <strong> / N° de depósito legal: </strong> <%= libro.depositoLegal %>
                                                            <% } %>
                                                        </td>
                                                </tr>
                                                <tr>
                                                        <td class="head">Fecha y lugar de publicación</td>
                                                        <td>
                                                            <% if (libro.fechaPublica.length > 0 ) { %>
                                                                ( <%= libro.fechaPublica %> )
                                                            <% } %>
                                                            <% if (libro.ciudad.length > 0 ) { %>
                                                                <%= libro.ciudad %>,
                                                            <% } %>
                                                            <% if (libro.pais.length > 0 ) { %>
                                                                <%= libro.pais %>
                                                            <% } %>
                                                        </td>
                                                </tr>
                                                <% if (libro.otrasVersiones.length > 0) { %>
                                                    <tr>
                                                        <td class="head">Otras versiones publicadas en (URL)</td>
                                                        <td><%= libro.otrasVersiones %></td>
                                                    </tr>
                                                <% } %>
                                                <% if (libro.capitulos !== null) { %>
                                                    <tr>
                                                        <td class="head">Capítulos escritos</td>                                                    
                                                        <% libro.capitulos.forEach( function( itemlibro) { %>
                                                            <td>
                                                                Cap. <%= itemlibro.numero %> <%= itemlibro.nombre %> (<%= itemlibro.paginaInicio %> - <%= itemlibro.paginaFin %>)
                                                            </td>
                                                        <% }); %>
                                                    </tr>
                                                <% } %>
                                                <tr>
                                                        <td class="head">Palabras clave</td>
                                                        <td><%= libro.palabraClave %></td>
                                                </tr>

                                        </tbody>
                                   </table> 
                            </div>
                    </div>
            </div>
            <% if (libro.resumen.length > 0) { %>
                            <div class="accordion-group">
                                    <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-resumen">
                                                    Resumen
                                            </a>
                                    </div>
                                    <div id="research-docs-resumen" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                    <%= libro.resumen %>
                                            </div>
                                    </div>
                            </div>
            <% } %>
            <% if (libro.englishResumen.length > 0) { %>
                            <div class="accordion-group">
                                    <div class="accordion-heading">
                                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-englishResumen">
                                                    Abstract
                                            </a>
                                    </div>
                                    <div id="research-docs-englishResumen" class="accordion-body collapse">
                                            <div class="accordion-inner">
                                                    <%= libro.englishResumen %>
                                            </div>
                                    </div>
                            </div>
            <% } %>
            <% if (libro.evento !== null) { %>
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
                                                                        <td><%= libro.evento.tituloPonencia %></td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Número y nombre del evento</td>
                                                                        <td>
                                                                            <% if (libro.evento.numEvento.length > 0 ) { %>
                                                                                <%= libro.evento.numEvento %> 
                                                                            <% } %>
                                                                            <%= libro.evento.nombre %></td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Organizado por</td>
                                                                        <td><%= libro.evento.organizadoPor %></td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Ámbito</td>
                                                                        <td><%= libro.evento.ambito %>: 
                                                                            <%= libro.evento.ciudad %>,
                                                                            <%= libro.evento.pais %>
                                                                        </td>
                                                                </tr>
                                                                <tr>
                                                                        <td class="head">Fecha</td>
                                                                        <td>
                                                                            <% if (libro.evento.fecha.length > 0 ) { %>
                                                                                <%= libro.evento.fecha %> 
                                                                            <% } %>
                                                                        </td>
                                                                </tr>
                                                                <% if (libro.evento.descripcion.length > 0) { %>
                                                                    <tr>
                                                                        <td class="head">Descripción del evento</td>
                                                                        <td><%= libro.evento.descripcion %></td>
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
                                    <% if (libro.restriciones !== null) { %>
                                        <% libro.restriciones.forEach( function( item) { %>
                                            <p class="alert alert-info"><%= item %></p>
                                        <% }); %>
                                    <% } %>
                                    <% if (libro.documentos !== null) { %>                                        
                                        <table class="table table-condensed table-hover">
                                            <thead>
                                                    <tr>
                                                            <th>Nombre</th>
                                                            <th>Detalles</th>
                                                    </tr>
                                            </thead>                                                
                                            <tbody>
                                                <% libro.documentos.forEach( function( itemDoc) { %>
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
                                            <% if (libro.titularDerechos.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">Titular de los Derechos</td>
                                                        <td><%= libro.titularDerechos %></td>
                                                </tr>
                                            <% } %>
                                            <% if (libro.derechos.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">Licencia de Derechos</td>
                                                        <td><%= libro.derechos %></td>
                                                </tr>
                                            <% } %>
                                            <% if (libro.urlLicenciaDerecho.length > 0 ) { %>
                                                <tr>
                                                        <td class="head">URL Licencia de Derechos</td>
                                                        <td><%= libro.urlLicenciaDerecho %></td>
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
