<div class="section-subheader">
	<ul class="nav nav-pills category-filter">
		<li><a href="#/mensajes/inbox">Recibidos <span class="badge message-inbox-count"></span></a></li>
		<li><a href="#/mensajes/outbox">Enviados</a></li>
	</ul>
</div>

<% if (asesor == null){ %>

	<p class="alert alert-warning">Aún no tienes un asesor asignado. Debes esperar un poco para poder enviar mensajes.</p>

<% } else { %>

	<form class="form-horizontal" action="#/mensajes/nuevo" method="post" id="new-message">
		<fieldset>
			<input id="idasesor" type="hidden" name="idasesor" value="<%= asesor.id %>" />
			<input id="idperiodoacademicoasesor" type="hidden" name="idperiodoacademicoasesor" value="<%= asesor.periodoAcademicoAsesor.id %>" />
			<div class="control-group">
				<label class="control-label" for="from">Para</label>
				<div class="controls">
					<input class="input-xlarge" id="from" type="text" name="de" 
					value="<%= asesor.nombres %> <%= asesor.apellidoPaterno %>" disabled />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="subject">Asunto</label>
				<div class="controls">
					<input class="input-xlarge" id="aubject" type="text" name="asunto" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="body">Mensaje</label>
				<div class="controls">
					<textarea class="input-xlarge" id="body" name="cuerpo" rows="5" cols="100"></textarea>
				</div>
			</div>
			<div class="form-actions">
				<button id="submit-mensaje" data-loading-text="Cargando..." type="submit" class="btn btn-primary">Enviar</button>
				<a class="btn" href="#/mensajes/inbox">Cancelar</a>
			</div>
		</fieldset>
	</form>

<% } %>