<form action="#/perfil/personal/editar" method="post" id="edit-profile">
	<fieldset>
		<p>
			<a class="btn" href="#/perfil/personal">Cancelar</a>
			<button id="perfil-personal-editar-submit2" data-loading-text="Cargando..." class="btn btn-primary" type="submit">Guardar</button>
		</p>

		<p>Los campos marcados con * son obligatorios.</p>

		<h3>General</h3>
		<div class="row-fluid">
			<table class="table table-condensed table-hover">
				<tr>
					<td class="span3 head">Nombre</td>
					<td class="span9"><input type="text" name="nombre" readonly="true" value="<%= alumno.nombres %>" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Apellido Paterno</td>
					<td class="span9"><input type="text" name="appaterno" readonly="true" value="<%= alumno.apellidoPaterno %>" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Apellido Materno</td>
					<td class="span9"><input type="text" name="apmaterno" readonly="true" value="<%= alumno.apellidoMaterno %>" required /></td>
				</tr>
				<tr>
					<td class="span3 head">DNI</td>
					<td class="span9"><input type="text" name="dni" readonly="true" value="<%= alumno.dni %>" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Fecha de nacimiento</td>
					<td class="span9">
                                            <% if(alumno.datoPersonal.fechaNacimiento != null){ %>
                                                <input type="text" name="fechaNacimiento" readonly value="<%= alumno.datoPersonal.fechaNacimiento %>" />
                                            <% } else { %>
                                                <input type="text" name="fechaNacimiento" readonly value="" />
                                            <% } %>
                                        </td>
				</tr>
				<tr>
					<td class="span3 head">Departamento *</td>
					<td class="span9">
						<% if ( alumno.datoPersonal.distrito.nombre !== null && alumno.datoPersonal.distrito.provincia.nombre !== null && alumno.datoPersonal.distrito.provincia.departamento.nombre !== null ) { %>
							<select id="departamento" name="departamento" data-origin="<%= alumno.datoPersonal.distrito.provincia.departamento.id %>">
								<option value="<%= alumno.datoPersonal.distrito.provincia.departamento.id %>"><%= alumno.datoPersonal.distrito.provincia.departamento.nombre %></option>
							</select>
						<% } else { %>
							<select id="departamento" name="departamento">
								<option value="0">Seleccione...</option>
							</select>
						<% } %>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Provincia *</td>
					<td class="span9">
						<% if ( alumno.datoPersonal.distrito.nombre !== null && alumno.datoPersonal.distrito.provincia.nombre !== null ) { %>
							<select id="provincia" name="provincia" data-origin="<%= alumno.datoPersonal.distrito.provincia.id %>">
								<option value="<%= alumno.datoPersonal.distrito.provincia.id %>"><%= alumno.datoPersonal.distrito.provincia.nombre %></option>
							</select>
						<% } else { %>
							<select id="provincia" name="provincia">
								<option value="0">Seleccione...</option>
							</select>
						<% } %>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Distrito *</td>
					<td class="span9">
						<% if ( alumno.datoPersonal.distrito.nombre !== null ) { %>
							<select id="distrito" name="distrito" data-origin="<%= alumno.datoPersonal.distrito.id %>">
								<option value="<%= alumno.datoPersonal.distrito.id %>"><%= alumno.datoPersonal.distrito.nombre %></option>
							</select>
						<% } else { %>
							<select id="distrito" name="distrito">
								<option value="0">Seleccione...</option>
							</select>
						<% } %>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Direcci&oacute;n *</td>
					<td class="span9"><input type="text" name="direccion" value="<%= alumno.datoPersonal.direccion %>" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Teléfono</td>
					<td class="span9"><input type="tel" name="telefonoCasa" value="<%= alumno.datoPersonal.telefono %>" /></td>
				</tr>
				<tr>
					<td class="span3 head">Celular</td>
					<td class="span9"><input type="tel" name="telefonoCelular" value="<%= alumno.datoPersonal.telefonoMovil %>" /></td>
				</tr>
				<tr>
					<td class="span3 head">Correo UDEP</td>
					<td class="span9"><input type="email" name="emailUdep" value="<%= oficial %>" readonly="true" /></td>
				</tr>             
				<tr>
					<td class="span3 head">Correo personal</td>
					<td class="span9"><input type="email" name="emailPersonal" value="<%= personal %>" /></td>
				</tr>
			</table>
		</div>

		<h3>Pensión</h3>
		<div class="row-fluid">
			<table class="table table-condensed table-hover">
				<tr>
					<td class="span3 head">Departamento</td>
					<td class="span9">
						<% if ( alumno.datoPersonal.distritoPension.nombre !== null && alumno.datoPersonal.distritoPension.provincia.nombre !== null && alumno.datoPersonal.distritoPension.provincia.departamento.nombre !== null ) { %>
							<select id="pension_departamento" name="pension_departamento" data-origin="<%= alumno.datoPersonal.distritoPension.provincia.departamento.id %>">
								<option value="<%= alumno.datoPersonal.distritoPension.provincia.departamento.id %>"><%= alumno.datoPersonal.distritoPension.provincia.departamento.nombre %></option>
							</select>
						<% } else { %>
							<select id="pension_departamento" name="pension_departamento">
								<option value="0">Seleccione...</option>
							</select>
						<% } %>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Provincia</td>
					<td class="span9">
						<% if ( alumno.datoPersonal.distritoPension.nombre !== null && alumno.datoPersonal.distritoPension.provincia.nombre !== null ) { %>
							<select id="pension_provincia" name="pension_provincia" data-origin="<%= alumno.datoPersonal.distritoPension.provincia.id %>">
								<option value="<%= alumno.datoPersonal.distritoPension.provincia.id %>"><%= alumno.datoPersonal.distritoPension.provincia.nombre %></option>
							</select>
						<% } else { %>
							<select id="pension_provincia" name="pension_provincia">
								<option value="0">Seleccione...</option>
							</select>
						<% } %>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Distrito</td>
					<td class="span9">
						<% if ( alumno.datoPersonal.distritoPension.nombre !== null ) { %>
							<select id="pension_distrito" name="pension_distrito" data-origin="<%= alumno.datoPersonal.distritoPension.id %>">
								<option value="<%= alumno.datoPersonal.distritoPension.id %>"><%= alumno.datoPersonal.distritoPension.nombre %></option>
							</select>
						<% } else { %>
							<select id="pension_distrito" name="pension_distrito">
								<option value="0">Seleccione...</option>
							</select>
						<% } %>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Dirección</td>
					<td class="span9"><input type="text" name="pensionDireccion" value="<%= alumno.datoPersonal.direccionPension %>" /></td>
				</tr>
				<tr>
					<td class="span3 head">Teléfono</td>
					<td class="span9"><input type="tel" name="pensionTelefono" value="<%= alumno.datoPersonal.telefonoPension %>" /></td>
				</tr>
			</table>
		</div>

		<h3>Emergencia</h3>
		<p><span class="label label-warning">Persona a contactar en caso de emergencia</span></p>
		<div class="row-fluid">
			<table class="table table-condensed table-hover">
				<tr>
					<td class="span3 head">Nombre</td>
					<td class="span9"><input type="text" name="emergenciaNombre" value="<%= alumno.datoPersonal.contactoEmergencia %>" /></td>
				</tr>
				<tr>
					<td class="span3 head">Teléfono</td>
					<td class="span9"><input type="tel" name="emergenciaTelefono" value="<%= alumno.datoPersonal.telefonoEmergencia %>" /></td>
				</tr>
			</table>
		</div>

		<p>
			<a class="btn" href="#/perfil/personal">Cancelar</a>
			<button id="perfil-personal-editar-submit" data-loading-text="Cargando..." class="btn btn-primary" type="submit">Guardar</button>
		</p>
	</fieldset>
</form>