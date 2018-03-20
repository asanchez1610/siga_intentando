<div id="select-process">
	<p class="alert alert-info">Selecciona el trámite que desea realizar.</p>

	<% categorias.forEach(function(categoria) { %>
		<h4><%= categoria.nombre %></h4>
		<ul class="nav nav-pills nav-stacked">
			<% categoria.solicitudes.forEach(function(solicitud) { %>
				<li><a href="#" data-id="<%= solicitud.id %>"><%= solicitud.nombre %></a></li>
			<% }); %>
		</ul>
	<% }); %>
</div>

<form class="form-horizontal" action="#/tramites/nuevo" method="post" id="new-process">
	<fieldset>
		<input type="hidden" id="solicitud-id" name="solicitud" value="<%= categorias[0].solicitudes[0].id %>" />
                <input type="hidden" id="idedicionestudio" name="idedicionestudio" value="<%= currentPlan %>" />
		<div class="control-group">
			<label class="control-label">Solicitud</label>
			<div class="controls">
				<div class="input-append">
					<input id="solicitud-nombre" type="text" value="<%= categorias[0].solicitudes[0].nombre %>" disabled />
					<button id="change-process" rel="tooltip" data-toggle="tooltip" data-placement="bottom" title="Cambiar trámite" class="btn" type="button"><i class="icon-caret-up"></i></button>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="cuerpo">Mensaje</label>
			<div class="controls">
				<textarea class="input-xlarge" id="cuerpo" name="cuerpo" rows="10" cols="100" maxlength="500"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<button id="submit-tramite" data-loading-text="Cargando..." type="submit" class="btn btn-primary">Enviar</button>
			<a class="btn" href="#/tramites/pendientes">Cancelar</a>
		</div>
	</fieldset>
</form>