<% if (Object.prototype.toString.call(secciones) === '[object Array]' && secciones.length > 0) { %>

	<div class="btn-group section-subheader" style="display:none;">
		<a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar en PDF</a>
	</div>	
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>
        <p style="float:right;">                
           <a class="btn" href="./silabo/ver.htm?id=<%= id %>&idee=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Ver sílabo completo</a>
        </p>

	<div class="accordion acoordion-mini" id="subject-options-silabo">
		<% secciones.forEach( function( section, index) { %>
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse" href="#subject-silabo-<%= index %>">
						<%= section.titulo %>
					</a>
				</div>
				<div id="subject-silabo-<%= index %>" class="accordion-body collapse">
					<div class="accordion-inner">
						<p><%= section.descripcion %></p>
						<% if (Object.prototype.toString.call(section.items) === '[object Array]' && section.items.length > 0) { %>
							<ul class="map-list">
								<% section.items.forEach(function(item) { %>
									<li><strong class="head"><%= item.titulo %>:</strong> <%= item.contenido %></li>
								<% }); %>
							</ul>
						<% }else if (Object.prototype.toString.call(section.vinetas) === '[object Array]' && section.vinetas.length > 0) { %>
							<ul class="map-list">
								<% section.vinetas.forEach(function(item) { %>
									<li><i class="icon-bookmark"></i> <%= item.descripcion %></li>
								<% }); %>
							</ul>
						<% } %>
                                                
					</div>
				</div>
			</div>
		<% }); %>
	</div>

	<div class="btn-group section-subheader" style="display:none;">
		<a class="btn btn-mini btn-success" href="https://siga.udep.edu.pe/files/silabo-2323powjsm.pdf"><i class="icon-download-alt"></i>Descargar en PDF</a>
	</div>	
	<div class="btn-group section-subheader">
		<button class="btn btn-mini expand-all"><i class="icon-plus"></i></button>
		<button class="btn btn-mini collapse-all"><i class="icon-minus"></i></button>
	</div>
        
        <p style="float:right;">                
           <a class="btn" href="./silabo/ver.htm?id=<%= id %>&idee=<%= currentPlan %>" target="_blank"><i class="icon-external-link-sign"></i> Ver sílabo completo</a>
        </p>
<% } else { %>

	<p class="alert alert-error">A&uacute;n no se encuentra registrado.</p>

<% } %>
