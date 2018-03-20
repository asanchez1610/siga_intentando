<div id="siga-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="siga-modal-title" aria-hidden="true">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3 id="siga-modal-title"><%= asignatura %></h3>
		<h4><%= evaluacion %></h4>
	</div>
	<div class="modal-body">
		<% if( unidades.length > 0 ) { %>
		<ul>
			<% unidades.forEach( function( unidad ) { %>
				<li>
					<h4><%= unidad.descripcion %></h4>
					<% if(unidad.temaList.length > 0) { %>
						<ul>
							<% unidad.temaList.forEach( function(tema) { %>
								<li><%= tema.tema %></li>
							<% }); %>
						</ul>
					<% } %>
				</li>
			<% }); %>
		</ul>
		<% } else { %>
                    <p class="alert">No hay temas agregados.</p>
                <% } %>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
	</div>	
</div>