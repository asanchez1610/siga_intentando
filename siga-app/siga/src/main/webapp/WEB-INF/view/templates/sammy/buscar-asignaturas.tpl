<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/buscar/profesores">Buscar</a></li>
		<li class="active"><a href="#/buscar/asignaturas">Asignaturas</a></li>
	</ul>
</div>

<form class="form-horizontal" action="#/buscar/asignaturas" method="post" id="search-subjects">
	<fieldset>
		<div class="control-group">
			<label class="control-label" for="campus">Campus *</label>
			<div class="controls">
				<select id="campus" name="campus" class="input-xlarge">
					<option value="1">Piura</option>
                                        <option value="2">Lima</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="facultad">Programa Académico</label>
			<div class="controls">
				<select id="facultad" name="facultad" class="input-xlarge">
                                        <option value="0">Todos</option>				
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="pa">Periodo Académico</label>
			<div class="controls">
				<select id="pa" name="pa" class="input-xlarge">
                                        <option value="0">Todos</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="sigla">Sigla</label>
			<div class="controls">
				<input class="input-xlarge" id="sigla" type="text" name="sigla" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="nombre">Nombre</label>
			<div class="controls">
				<input class="input-xlarge" id="nombre" type="text" name="nombre" />
			</div>
		</div>
		<div class="form-actions">
			<button class="btn btn-primary" id="search-now">Buscar</button>
		</div>
	</fieldset>
</form>

<div class="results"></div>