<% if ( profesor !== null ) { %>
    <h2><%= profesor.apellidoPaterno %> <%= profesor.apellidoMaterno %>, <%= profesor.nombres %></h2>

    <p><a class="btn btn-link btn-small" href="#/buscar/profesores"><i class="icon-angle-left"></i> Ver otros profesores</a></p>

    <div class="section-subheader">
            <ul class="nav nav-pills category-filter">
                    <li><a href="#/profesores/<%= profesor.id %>/perfil">Perfil</a></li>
                    <li><a href="#/profesores/<%= profesor.id %>/investigacion">Trabajos de investigaci&oacute;n</a></li>
                    <li class="active"><a href="#/profesores/<%= profesor.id %>/cv">CV</a></li>
            </ul>
    </div>
    <% if (tieneRegistros) { %>
    <div class="accordion" id="cv">
                    <div class="accordion-group">
                            <div class="accordion-heading">
                                    <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-formacion">
                                            Formación Académica
                                    </a>
                            </div>
                            <div id="cv-formacion" class="accordion-body collapse">
                                    <div class="accordion-inner">                                            
                                            <% if (faPregrado !== null) { %>
                                                <h4>Pregrado</h4>
                                                <% faPregrado.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. <%= item.gradoTitulo %>. 
                                                    <% if (item.tituloTesis.length > 0 ) { %>
                                                        Tesis:"<%= item.tituloTesis %>", 
                                                    <% } %>
                                                     <%= item.universidad %>, <%= item.pais %>.</p>                                            
                                                <% }); %>
                                            <% } %>
                                            <% if (faPosgrado !== null) { %>
                                                <h4>Posgrado</h4>
                                                <% faPosgrado.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. <%= item.gradoTitulo %>. 
                                                    <% if (item.tituloTesis.length > 0 ) { %>
                                                        Tesis:"<%= item.tituloTesis %>", 
                                                    <% } %>
                                                     <%= item.universidad %>, <%= item.pais %>.</p>   
                                                <% }); %>
                                            <% } %>
                                    </div>
                            </div>
                    </div>

                    <% if (complementarios !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-estudios">
                                                Estudios complementarios
                                        </a>
                                </div>
                                <div id="cv-estudios" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                                    <% if (complementarios !== null) { %>
                                                        <% complementarios.forEach( function( item) { %>
                                                            <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. "<%= item.evento %>", <%= item.lugar %>. 
                                                                <% if (item.institucion.length > 0 ) { %>
                                                                    [<%= item.institucion %> ]
                                                                <% } %> 
                                                                <% if (item.horas.length > 0 ) { %>
                                                                    <%= item.horas %>
                                                                <% } %>  </p>                                                      
                                                        <% }); %>
                                                    <% } %>
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (camposInvestiga !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-campos">
                                                Principales campos de investigaci&oacute;n
                                        </a>
                                </div>
                                <div id="cv-campos" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                            <% if (camposInvestiga !== null) { %>
                                                <% camposInvestiga.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> "<%= item.nombre %>". <%= item.descripcion %></p>                                            
                                                <% }); %>
                                            <% } %>
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <div class="accordion-group">
                            <div class="accordion-heading">
                                    <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-actividad">
                                            Actividad de investigaci&oacute;n acad&eacute;mica y profesional
                                    </a>
                            </div>
                            <div id="cv-actividad" class="accordion-body collapse">
                                    <div class="accordion-inner">
                                        <% if (workingPaper !== null) { %>
                                            <h4>Working Paper</h4>
                                            <% workingPaper.forEach( function( item) { %>
                                                <p><i class="icon-bookmark-empty"></i> <%= item.titulo %>. <%= item.autores %>. </p>                                            
                                            <% }); %>
                                        <% } %>

                                        <% if (tesisPregrado !== null) { %>
                                            <h4>Tesis asesoradas de pregrado</h4>
                                            <% tesisPregrado.forEach( function( item) { %>
                                                <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. "<%= item.titulo %>".<%= item.autor %>
                                                     [<%= item.gradoObtenido %>] - <%= item.centroAcademico %>
                                                 </p> 
                                            <% }); %>
                                        <% } %>

                                        <% if (tesisPostgrado !== null) { %>
                                            <h4>Tesis asesoradas de posgrado</h4>
                                            <% tesisPostgrado.forEach( function( item) { %>
                                                <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. "<%= item.titulo %>".<%= item.autor %>
                                                     [<%= item.gradoObtenido %>] - <%= item.centroAcademico %>
                                                 </p> 
                                            <% }); %>
                                        <% } %>

                                        <% if (estancias !== null) { %>
                                            <h4>Estancias de Investigaci&oacute;n</h4>
                                            <table class="table table-condensed table-hover">
                                                <tbody>
                                                    <% estancias.forEach( function( item) { %>
                                                        <tr>
                                                            <td>"<%= item.tema %>".</td>
                                                            <td>
                                                                <strong>Centro visitado: </strong><%= item.centroVisitado %>
                                                                [<%= item.ciudad %>, <%= item.pais %>] (<%= item.desde %> - <%= item.hasta %>)
                                                                 <% if (item.descripcion.length > 0 ) { %>
                                                                    <p><strong>Descripci&oacute;n: </strong><%= item.descripcion %></p>
                                                                 <% } %>
                                                            </td>
                                                        </tr>
                                                     <% }); %>
                                                </tbody>
                                            </table>  
                                        <% } %>

                                        <% if (proyectos !== null) { %>
                                            <h4>Proyectos</h4>
                                            <table class="table table-condensed table-hover">
                                                <tbody>
                                                    <% proyectos.forEach( function( item) { %>
                                                        <tr>
                                                            <td>
                                                                <h5>"<%= item.nombre %>"</h5>
                                                                <strong>Participaci&oacute;n: </strong><%= item.participacion %>
                                                                <strong> / &aacute;rea: </strong><%= item.area %>
                                                                [<%= item.ciudad %>, <%= item.pais %>] (<%= item.desde %> - <% if (item.hastaLaFecha ) { %>
                                                                    Hasta la fecha <% } else { %> <%= item.hasta %><% } %> )
                                                                 <% if (item.financiamiento.length > 0 ) { %>
                                                                    <p><strong>Financiamiento: </strong><%= item.financiamiento %></p>
                                                                 <% } %>
                                                                 <% if (item.descripcion.length > 0 ) { %>
                                                                    <p><strong>Descripci&oacute;n: </strong><%= item.descripcion %></p>
                                                                 <% } %>
                                                            </td>
                                                        </tr>
                                                     <% }); %>
                                                </tbody>
                                            </table> 
                                        <% } %>

                                        <% if (consultoria !== null) { %>
                                            <h4>Consultor&iacute;a en</h4>
                                            <table class="table table-condensed table-hover">
                                                <tbody>
                                                    <% consultoria.forEach( function( item) { %>
                                                        <tr>
                                                            <td>"<%= item.consultoriaEn %>"</td>
                                                            <td>
                                                                <strong>Cliente: </strong><%= item.cliente %>
                                                                <p><strong>Participantes: </strong><%= item.participante %></p>
                                                                [<%= item.ciudad %>, <%= item.pais %>] (<%= item.desde %> - <%= item.hasta %>)
                                                                 <% if (item.descripcion.length > 0 ) { %>
                                                                    <p><strong>Descripci&oacute;n: </strong><%= item.descripcion %></p>
                                                                 <% } %>
                                                            </td>
                                                        </tr>
                                                     <% }); %>
                                                </tbody>
                                            </table> 
                                        <% } %>

                                        <% if (ponencias !== null) { %>
                                            <h4>Ponencias en eventos</h4>
                                            <table class="table table-condensed table-hover">
                                                <tbody>
                                                    <% ponencias.forEach( function( item) { %>
                                                        <tr>
                                                            <td>
                                                                <h5><%= item.tituloPonencia %></h5>
                                                                <strong>Evento: </strong><%= item.nombre %>
                                                                <strong> / Organizado Por: </strong><%= item.organizadoPor %>
                                                                [<%= item.ciudad %>, <%= item.pais %>] (<%= item.fecha %>)
                                                                <p><strong>&aacute;mbito: </strong><%= item.ambito %></p>
                                                                 <% if (item.descripcion.length > 0 ) { %>
                                                                    <p><strong>Descripci&oacute;n: </strong><%= item.descripcion %></p>
                                                                 <% } %>
                                                            </td>
                                                        </tr>
                                                     <% }); %>
                                                </tbody>
                                            </table> 
                                        <% } %>

                                        <% if (reuniones !== null) { %>
                                            <h4>Asistencia a reuniones científicas</h4>
                                            <table class="table table-condensed table-hover">
                                                <tbody>
                                                    <% reuniones.forEach( function( item) { %>
                                                        <tr>
                                                            <td>
                                                                <h5>"<%= item.nombre %>"</h5>
                                                                <strong>Organizado Por: </strong><%= item.organizadoPor %>
                                                                [<%= item.ciudad %>, <%= item.pais %>] (<%= item.anio %>)
                                                                <p><strong>&aacute;mbito: </strong><%= item.ambito %></p>
                                                                 <% if (item.descripcion.length > 0 ) { %>
                                                                    <p><strong>Descripci&oacute;n: </strong><%= item.descripcion %></p>
                                                                 <% } %>
                                                            </td>
                                                        </tr>
                                                     <% }); %>
                                                </tbody>
                                            </table> 
                                        <% } %>
                                    </div>
                            </div>
                    </div>

                    <% if (libros !== null || librosColectivos !== null || indexado !== null || noIndexado !== null || conActa !== null || articuloCV !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-publicaciones">
                                                Publicaciones
                                        </a>
                                </div>
                                <div id="cv-publicaciones" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                            <% if (libros !== null) { %>
                                                <h4>Libros</h4>
                                                <% libros.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> <%= item.investigacionGenerica.autor %>  [<%= item.fechaPublica %>]
                                                         "<%= item.investigacionGenerica.titulo %>"  
                                                        <% if (item.edicion.length > 0 && (item.edicion !== '1' || item.edicion !== 'I')) { %>
                                                                (<%= item.edicion %> ed.)
                                                        <% } %> <%= item.ciudad %> : <%= item.editorial %>
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>

                                            <% if (librosColectivos !== null) { %>
                                                <h4>Capítulos en libros</h4>
                                                <% librosColectivos.forEach( function( item) { %>
                                                    <% if (item.capitulos !== null) { %>
                                                        <% item.capitulos.forEach( function( itemCap) { %>
                                                            <p><i class="icon-bookmark-empty"></i> <%= item.investigacionGenerica.autor %>  [<%= item.fechaPublica %>]
                                                                 "<%= itemCap.nombre %>". En <%= item.coordinador %>  (Ed.)
                                                                 <%= item.investigacionGenerica.titulo %> (pp. <%= itemCap.paginaInicio %> - <%= itemCap.paginaFin %>)                                                             
                                                                <% if (item.edicion.length > 0 && (item.edicion !== '1' || item.edicion !== 'I')) { %>
                                                                        (<%= item.edicion %> ed.)
                                                                <% } %> <%= item.ciudad %> : <%= item.editorial %>
                                                            </p> 
                                                        <% }); %>
                                                    <% } %>
                                                <% }); %>
                                            <% } %>

                                            <% if (indexado !== null) { %>
                                                <h4>Artículos en revistas indexadas o arbitradas</h4>
                                                <% indexado.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> <%= item.investigacionGenerica.autor %>  [<%= item.fecha %>]
                                                         <%= item.investigacionGenerica.titulo %>  <%= item.nombreMedio %>,
                                                        <% if (item.tomoVolumen.length > 0 ) { %>
                                                                Volumen (<%= item.tomoVolumen %>)
                                                        <% } %> (pp. <%= item.inicio %> - <%= item.fin %>)
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>

                                            <% if (noIndexado !== null) { %>
                                                <h4>Artículos en revistas no indexadas</h4>
                                                <% noIndexado.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> <%= item.investigacionGenerica.autor %>  [<%= item.fecha %>]
                                                         <%= item.investigacionGenerica.titulo %>  <%= item.nombreMedio %>,
                                                        <% if (item.tomoVolumen.length > 0 ) { %>
                                                                Volumen (<%= item.tomoVolumen %>)
                                                        <% } %> (pp. <%= item.inicio %> - <%= item.fin %>)
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>

                                            <% if (conActa !== null) { %>
                                                <h4>Ponencias  y comunicaciones en congresos (Actas no indexadas)</h4>
                                                <% conActa.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> <%= item.investigacionGenerica.autor %>  [<%= item.fecha %>]
                                                         <%= item.investigacionGenerica.titulo %>  <%= item.nombreMedio %>,
                                                        <% if (item.tomoVolumen.length > 0 ) { %>
                                                                Volumen (<%= item.tomoVolumen %>)
                                                        <% } %> (pp. <%= item.inicio %> - <%= item.fin %>)
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>

                                            <% if (articuloCV !== null) { %>
                                                <h4>Artículos en otros medios</h4>
                                                <% articuloCV.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> [<%= item.anio %>]
                                                         <%= item.titulo %>  <%= item.medioPublicacion %>, pp. <%= item.pagInicio %> - <%= item.pagFin %>
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (docencia !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-exp-docente">
                                                Experiencia docente
                                        </a>
                                </div>
                                <div id="cv-exp-docente" class="accordion-body collapse">
                                        <div class="accordion-inner">                                        
                                                <% docencia.forEach( function( item) { %>
                                                    (<% if (item.anioInicio !== 0) { %><%= item.anioInicio %> - <% } %>
                                                        <% if (item.hastaLaFecha) { %>
                                                            Actualmente
                                                        <% } else { %> <% if (item.anioFin !== 0) { %><%= item.anioFin %> <% } %><% } %>) - <%= item.centroEstudio %>                                                      
                                                    <% if (item.cursos !== null) { %>                                        
                                                        <table class="table table-condensed table-hover">
                                                            <thead>
                                                                    <tr>
                                                                            <th>Asignatura</th>
                                                                            <th>Periodo</th>
                                                                            <th>categoría</th>
                                                                            <th>Estudio</th>
                                                                    </tr>
                                                            </thead>                                                
                                                            <tbody>
                                                                <% item.cursos.forEach( function( itemDoc) { %>
                                                                    <tr>
                                                                        <td><%= itemDoc.asignatura %></td>
                                                                        <td><%= itemDoc.periodo %></td>
                                                                        <td><%= itemDoc.categoria %></td>
                                                                        <td><%= itemDoc.estudio %></td>
                                                                    </tr>
                                                                 <% }); %>
                                                            </tbody>
                                                        </table>                                        
                                                    <% } %>
                                                <% }); %>                                        
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (actual !== null || directivo !== null || otros !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-exp-profesional">
                                                Experiencia profesional
                                        </a>
                                </div>
                                <div id="cv-exp-profesional" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                            <% if (actual !== null) { %>
                                                <h4>Cargos actuales</h4>
                                                <% actual.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> "<%= item.nombre %>"  [<%= item.empresa %>]
                                                        (<%= item.desde %> -
                                                        <% if (item.hastaLaFecha) { %>
                                                            La actualidad
                                                        <% } else { %> <%= item.hasta %> <% } %>)
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>

                                            <% if (directivo !== null) { %>
                                                <h4>Cargos directivos desempeñados</h4>
                                                <% directivo.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> "<%= item.cargo %>"  [<%= item.institucion %>]
                                                        (<%= item.desde %>  -
                                                        <% if (item.hastaLaFecha) { %>
                                                            La actualidad
                                                        <% } else { %> <%= item.hasta %>  <% } %>)
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>

                                            <% if (otros !== null) { %>
                                                <h4>Otros cargos</h4>
                                                <% otros.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> "<%= item.cargo %>"  [<%= item.centroTrabajo %>]
                                                        (<%= item.desde %>  -
                                                        <% if (item.hastaLaFecha) { %>
                                                            La actualidad
                                                        <% } else { %> <%= item.hasta %>  <% } %>)
                                                    </p>                                            
                                                <% }); %>
                                            <% } %>
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (redes !== null || asociaciones !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-asociaciones">
                                                Asociaciones y redes a las que pertenece
                                        </a>
                                </div>
                                <div id="cv-asociaciones" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                            <% if (redes !== null) { %>
                                                    <h4>Redes académicas</h4>
                                                    <% redes.forEach( function( item) { %>
                                                        <p><i class="icon-bookmark-empty"></i> "<%= item.nombre %>"  [<%= item.url %>] <%= item.especializacion %>
                                                        </p>                                            
                                                    <% }); %>
                                             <% } %>

                                             <% if (asociaciones !== null) { %>
                                                    <h4>Asociaciones profesionales</h4>
                                                    <% asociaciones.forEach( function( item) { %>
                                                        <p><i class="icon-bookmark-empty"></i> "<%= item.nombre %>"  [<%= item.puesto %>]
                                                            (<%= item.desde %> -
                                                            <% if (item.hastaLaFecha) { %>
                                                                La actualidad
                                                            <% } else { %> <%= item.hasta %> <% } %>)
                                                        </p>                                            
                                                    <% }); %>
                                             <% } %>
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (idiomas !== null || idiomaTitulo !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-idiomas">
                                                Idiomas
                                        </a>
                                </div>
                                <div id="cv-idiomas" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                            <% if (idiomas !== null) { %>
                                                <table class="table table-condensed table-hover">
                                                    <tbody>
                                                        <% idiomas.forEach( function( item) { %>
                                                            <tr>
                                                                <td>
                                                                    <h5>"<%= item.nombre %>"  [<%= item.lugar %>]</h5>
                                                                    <strong>Conversaci&oacute;n: </strong><%= item.nivelC %>
                                                                    <strong> / Lectura: </strong><%= item.nivelL %>
                                                                    <strong> / Escritura: </strong><%= item.nivelE %>
                                                                </td>
                                                            </tr>
                                                         <% }); %>
                                                    </tbody>
                                                </table> 
                                            <% } %>
                                            <% if (idiomaTitulo !== null) { %>
                                                    <h4>Títulos y certificados</h4>
                                                    <% idiomaTitulo.forEach( function( item) { %>
                                                        <p><i class="icon-bookmark-empty"></i> <%= item.anioInicio %>. "<%= item.nombre %>"  [<%= item.lugar %>]
                                                        </p>                                            
                                                    <% }); %>
                                             <% } %>
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (patentes !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-patentes">
                                                Patente y/o registros de propiedad
                                        </a>
                                </div>
                                <div id="cv-patentes" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                            <% patentes.forEach( function( item) { %>
                                                <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. "<%= item.tema %>" </p>                                            
                                            <% }); %> 
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (premios !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-honores">
                                                Honores, premios y becas
                                        </a>
                                </div>
                                <div id="cv-honores" class="accordion-body collapse">
                                        <div class="accordion-inner">
                                            <% premios.forEach( function( item) { %>
                                                <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. "<%= item.premio %>" - <%= item.entidad %></p>                                            
                                            <% }); %> 
                                        </div>
                                </div>
                        </div>
                    <% } %>

                    <% if (otrosMeritos !== null) { %>
                        <div class="accordion-group">
                                <div class="accordion-heading">
                                        <a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#cv" href="#cv-otros">
                                                Otros m&eacute;ritos
                                        </a>
                                </div>
                                <div id="cv-otros" class="accordion-body collapse">
                                        <div class="accordion-inner">                                       
                                                <% otrosMeritos.forEach( function( item) { %>
                                                    <p><i class="icon-bookmark-empty"></i> <%= item.anio %>. <%= item.merito %></p>                                            
                                                <% }); %>                                        
                                        </div>
                                </div>
                        </div>
                    <% } %>

    </div>
    <% } else { %>
        <p class="alert alert-info">Aún no se encuentra registrado.</p>
    <% }  %>
<% } else {%>

	<p class="alert alert-error">La información del profesor no est&acute; disponible.</p>

<% }%>