<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/buscar/profesores">Buscar</a></li>
		<li><a href="#/buscar/asignaturas">Asignaturas</a></li>
	</ul>
</div>

<p>Ingresa al menos 3 caracteres para empezar la b&uacute;squeda.</p>

<form class="form-horizontal" action="#/buscar/profesores" id="search-people">
	<fieldset>
		<div class="control-group">
			<label class="control-label" for="nombre">Nombres</label>
			<div class="controls">
				<input class="input-xlarge" id="nombre" type="text" name="nombre" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="apellidoP">Apellido paterno</label>
			<div class="controls">
				<input class="input-xlarge" id="apellidoP" type="text" name="paterno" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="apellidoM">Apellido materno</label>
			<div class="controls">
				<input class="input-xlarge" id="apellidoM" type="text" name="materno" />
			</div>
		</div>
		<div class="form-actions">
			<button class="btn btn-primary" id="search-now">Buscar</button>
		</div>
	</fieldset>
</form>

<div class="results"></div>