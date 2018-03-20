<% if ( profesor !== null ) { %>

	<h2><%= profesor.apellidoPaterno %> <%= profesor.apellidoMaterno %>, <%= profesor.nombres %></h2>

	<p><a class="btn btn-link btn-small" href="#/buscar/profesores"><i class="icon-angle-left"></i> Ver otros profesores</a></p>

	<div class="section-subheader">
		<ul class="nav nav-pills category-filter">
			<li><a href="#/profesores/<%= profesor.id %>/perfil">Perfil</a></li>
			<li class="active"><a href="#/profesores/<%= profesor.id %>/investigacion">Trabajos de investigaci&oacute;n</a></li>
			<li><a href="#/profesores/<%= profesor.id %>/cv">CV</a></li>
		</ul>
	</div>

	<% if ( enCurso.length > 0 || articulos.length > 0 || libros.length > 0 || tesis.length > 0 ) { %>

	<div class="accordion acoordion-mini" id="research-docs">

		<% if (Object.prototype.toString.call(enCurso) === '[object Array]' && enCurso.length > 0) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-encurso">
						En curso (<%= enCurso.length %>)
					</a>
				</div>
				<div id="research-docs-encurso" class="accordion-body collapse">
					<div class="accordion-inner">
						<ul class="nav nav-tabs nav-stacked">
							<% enCurso.forEach( function( item) { %>
								<li><a href="#/investigacionesCurso/<%= item.id %>"><%= item.titulo %></a></li>
							<% }); %>
						</ul>
					</div>
				</div>
			</div>
		<% } %>
		
		<% if (Object.prototype.toString.call(articulos) === '[object Array]' && articulos.length > 0) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-articulos">
						Art&iacute;culos (<%= articulos.length %>)
					</a>
				</div>
				<div id="research-docs-articulos" class="accordion-body collapse">
					<div class="accordion-inner">
						<ul class="nav nav-tabs nav-stacked">
							<% articulos.forEach( function( item) { %>
								<li><a href="#/investigacionArt/<%= item.id %>"><%= item.titulo %></a></li>
							<% }); %>
						</ul>
					</div>
				</div>
			</div>
		<% } %>
		
		<% if (Object.prototype.toString.call(libros) === '[object Array]' && libros.length > 0) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-libros">
						Libros (<%= libros.length %>)
					</a>
				</div>
				<div id="research-docs-libros" class="accordion-body collapse">
					<div class="accordion-inner">
						<ul class="nav nav-tabs nav-stacked">
							<% libros.forEach( function( item) { %>
								<li><a href="#/investigacionLibro/<%= item.id %>"><%= item.titulo %> 
																<% if( item.isCapitulo ) { %> - <strong>(Cap&iacute;tulos de Libro)</strong><% } %>
																</a></li>
							<% }); %>
						</ul>
					</div>
				</div>
			</div>
		<% } %>
		
		<% if (Object.prototype.toString.call(tesis) === '[object Array]' && tesis.length > 0) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" data-parent="#research-docs" href="#research-docs-tesis">
						Tesis (<%= tesis.length %>)
					</a>
				</div>
				<div id="research-docs-tesis" class="accordion-body collapse">
					<div class="accordion-inner">
						<ul class="nav nav-tabs nav-stacked">
							<% tesis.forEach( function( item) { %>
								<li><a href="#/investigacionTesis/<%= item.id %>"><%= item.titulo %> - <strong><%= item.tipo %></strong></a></li>
							<% }); %>
						</ul>
					</div>
				</div>
			</div>
		<% } %>

	</div>

	<% } else { %>

			<p class="alert alert-info">No hay investigaciones registradas.</p>

	<% } %>
<% } else { %>

	<p class="alert alert-error">La información del profesor no est&aacute; disponible.</p>

<% } %>