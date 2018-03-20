<!--[if lt IE 9]>
	<p class="alert alert-warning">Estás usando un navegador <strong>desactualizado</strong>. Por favor <a href="http://browsehappy.com/">actualiza tu navegador</a> o <a href="http://www.google.com/chromeframe/?redirect=true">activa Google Chrome Frame</a> para mejorar tu experiencia.</p>
<![endif]-->

<div class="section-subheader">
	<ul class="nav nav-pills category-filter">
		<li class="active"><a href="#" data-general="true">Todos los avisos</a></li>
		<li><a href="#" data-general="false">Avisos de facultad</a></li>
	</ul>
</div>

<div class="accordion" id="announcements">
	<% avisos.forEach(function(item) { %>
		<% if ( item.sticky ) { %>
			<div data-general="<%= item.general %>" class="accordion-group sticky">
				<div class="accordion-heading">
					<a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#announcements" href="#announcement-<%= item.id %>">
						<span class="pre-title"><%= item.fecha %></span> <span><%= item.titulo %></span> <span class="pull-right" ><i class="icon-eye-open icon-white"></i></span>
					</a>
				</div>
		<% } else { %>
			<div data-general="<%= item.general %>" class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle collapsed" data-toggle="collapse" data-parent="#announcements" href="#announcement-<%= item.id %>">
						<span class="pre-title"><%= item.fecha %></span> <span><%= item.titulo %></span>
					</a>
			</div>
		<% } %>
                                <div id="announcement-<%= item.id %>" data-id="<%= item.id %>" class="accordion-body collapse <%= item.sticky ? 'stick': '' %>">
					<div class="accordion-inner">
						<ul class="meta">
							<li><i class="meta-icon icon-user"></i><strong>Por:</strong> <%= item.persona %></li>
							<% if (item.rutaArchivo.length != 0 ) { %>
								<li>
									<i class="meta-icon icon-cloud-download"></i>
									<strong>Adjuntos:</strong>
									<a href="json/aviso/download.htm?id=<%= item.id %>"><%= item.rutaArchivo %></a> 
								</li>
							<% } %>
						</ul>
						<div class="content"><%! item.descripcion %></div>
					</div>
				</div>
			</div>
	<% }); %>
</div>
                
<div id="siga-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="siga-modal-title" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 id="siga-modal-title">Elección de especialidad</h3>
    </div>
    <div class="modal-body">
        <p id="especialidadMsj">
            
        </p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    </div>
</div>