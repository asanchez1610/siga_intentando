<div id="select-process">
	<p class="alert alert-info">Selecciona una categoría.</p>

	<% categorias.forEach(function(categoria) { %>
		<ul class="nav nav-pills nav-stacked">			
			<li><a href="#" data-id="<%= categoria.id %>"><%= categoria.nombre %></a></li>			
		</ul>
	<% }); %>
</div>

<form class="form-horizontal" action="#/consultas/nuevo" method="post" id="new-suggestion">
	<fieldset>
		<input type="hidden" id="solicitud-id" name="categoria" value="<%= categorias[0].id %>" />
		<div class="control-group">
			<label class="control-label">Solicitud</label>
			<div class="controls">
				<div class="input-append">
					<input id="solicitud-nombre" type="text" value="<%= categorias[0].nombre %>" disabled />
					<button id="change-process" rel="tooltip" data-toggle="tooltip" data-placement="bottom" title="Cambiar categoria" class="btn" type="button"><i class="icon-caret-up"></i></button>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="cuerpo">Mensaje</label>
			<div class="controls">
				<textarea class="input-xlarge" id="cuerpo" name="cuerpo" rows="5" cols="100"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<button id="submit-consulta" data-loading-text="Cargando..." type="submit" class="btn btn-primary">Enviar</button>
			<a class="btn" href="#/consultas/pendientes">Cancelar</a>
		</div>
	</fieldset>
</form>