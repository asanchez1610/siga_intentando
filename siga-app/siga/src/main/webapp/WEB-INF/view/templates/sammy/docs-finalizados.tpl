<% if(moroso){ %>
    <p class="alert">Se ha bloqueado el acceso a nueva solicitud debido a que presenta deuda de pensiones.</p>
<% } %>
<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/docs/pendientes">Pendientes</a></li>
		<li class="active"><a href="#/docs/finalizados">Finalizados</a></li>
	</ul>
</div>

<% if (Object.prototype.toString.call(documentos) === '[object Array]' && documentos.length > 0) { %>
        <% if(!moroso){ %>
            <div class="btn-group section-subheader">
                    <a href="#/docs/nuevo" class="btn btn-mini btn-success"><i class="icon-pencil"></i>Solicitar documento</a>
            </div>
        <% } %>
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>
        
        <div class="accordion acoordion-mini" id="docs-documento">
        <% documentos.forEach(function(documento, nro) { %>
                <div class="accordion-group">
                        <div class="accordion-heading">
                                <a class="accordion-toggle" data-toggle="collapse" href="#docs-<%= nro %>">
                                        <span class="pre-title"><%= documento.fecha %></span> <%= documento.tipoSolicitud.nombre %> <small class="pull-right"><%= documento.estadoDocOficial.nombre %></small>
                                </a>
                        </div>
                        <div id="docs-<%= nro %>" class="accordion-body collapse">
                                <div class="accordion-inner">

                                        <div class="row-fluid">
                                                <div class="span3 head">Campus de Entrega</div>
                                                <div class="span9"><%= documento.campusEntrega %></div>
                                        </div>
                                        <div class="row-fluid">
                                                <div class="span3 head">Idioma</div>
                                                <div class="span9"><%= documento.idioma %></div>
                                        </div>
                                        <div class="row-fluid">
                                                <div class="span3 head">Voucher</div>
                                                <div class="span9">
                                                    <% if(documento.urlVoucher !== null){ %>
                                                    <a href="json/documentooficial/downloadvoucher/<%= documento.urlVoucher %>" class="btn btn-mini btn-link view-voucher" data-id="<%= documento.id %>"><i class="icon-paper-clip"></i>Ver</a>
                                                    <% } %>
                                                </div>
                                        </div>
                                        <div class="row-fluid">
                                                <div class="span3 head">Detalle de la solicitud</div>
                                                <div class="span9"><%= documento.obsAlumno %></div>
                                        </div>
                                        <div class="row-fluid">
                                                <div class="span3 head">Observación</div>
                                                <div class="span9"><%= documento.respuesta %></div>
                                        </div>
                                        <div class="row-fluid">
                                                <div class="span3 head">Código</div>
                                                <div class="span9"><%= documento.nroSolicitud %></div>
                                        </div>
                                </div>
                        </div>
                </div>
        <% }); %>
	</div>
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>

<% } else { %>

	<p class="alert">No hay documentos tramitados registrados.</p>
        <% if(!moroso){ %>
            <div class="btn-group section-subheader">
                    <a href="#/docs/nuevo" class="btn btn-success"><i class="icon-pencil"></i>Solicitar documento</a>
            </div>
        <% } %>
<% } %>