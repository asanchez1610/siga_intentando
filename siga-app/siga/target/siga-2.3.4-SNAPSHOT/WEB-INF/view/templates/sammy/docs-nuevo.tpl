<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/docs/pendientes">Pendientes</a></li>
		<li><a href="#/docs/finalizados">Finalizados</a></li>
	</ul>
</div>

<p>Por favor verifica que tus nombres y apellidos se encuentren completos y correctamente escritos, esto es necesario para
la correcta generación del documento que solicitas. Si alguno de estos datos presenta algún error, acércate a tu
Secretaria Académica para solicitar su corrección.</p>

<ul class="map-list">
	<li><span class="head">Nombres</span> <%= nombres %></li>
	<li><span class="head">Apellidos</span> <%= apellidos %></li>
	<li><span class="head">Carné</span> <%= carne %></li>
	<li><span class="head">P.A</span> <%= pa %></li>
</ul>

<p>Si deseas actualizar tu foto envíala a <a href="mailto:documentos.oficiales@udep.pe">documentos.oficiales@udep.pe</a>,
indica en el asunto <strong>"Foto nueva - <em>Nombre y Apellido</em>"</strong> (Ej. "Foto nueva - Carlos Seminario").</p>
<p>Recuerda que la foto que envíes debe tener las siguientes características: </p>

<ul class="map-list">
	<li><span class="head">Ancho x Alto</span> 3.3 x 4cm</li>
	<li><span class="head">Límite</span> 1MB</li>
</ul>

<hr />

<form class="form-horizontal" action="#/docs/nuevo" method="post" id="new-doc">
	<fieldset>
		<input name="idedicionestudio" id="idedicionestudio" type="hidden" value="<%= currentPlan %>" />
		<div class="control-group">
			<label class="control-label" for="cuerpo">Tipo de documento</label>
			<div class="controls">
				<select id="tipo" name="tipo">
					<% tipoSolicitudList.forEach(function(item) { %>
						<option value="<%= item.id %>"><%= item.nombre %></option>
					<% }); %>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="campus-piura">Campus de entrega</label>
			<div class="controls">
				<label class="radio">
					<input type="radio" name="campus" id="campus-piura" value="1" checked>
					Campus Piura
				</label>
				<label class="radio">
					<input type="radio" name="campus" id="campus-lima" value="2">
					Campus Lima
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="idioma-espanol">Idioma</label>
			<div class="controls">
				<label class="radio">
					<input type="radio" name="idioma" id="idioma-espanol" value="1" checked>
					Español
				</label>
				<label class="radio">
					<input type="radio" name="idioma" id="idioma-ingles" value="2">
					Inglés
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="detalle">Detalle</label>
			<div class="controls">
				<textarea class="input-xlarge" id="detalle" name="detalle" rows="5" cols="100" maxlength="199"></textarea>
			</div>
		</div>
		<div class="form-actions">
			<button id="submit-doc" data-loading-text="Cargando..." type="submit" class="btn btn-primary">Enviar</button>
			<a class="btn" href="#/mensajes/inbox">Cancelar</a>
		</div>
	</fieldset>
</form>