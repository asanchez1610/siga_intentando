<div class="section-subheader">
	<ul class="nav nav-pills">
		<li class="active"><a href="#/perfil/personal">Mi perfil personal</a></li>
                <% if(mostrarDatosPersonales){ %>
                <li><a href="#/perfil/receptor-correspondencia">Datos familiares</a></li> 
                <% }; %>
	</ul>
</div>
<p><a class="btn btn-mini btn-success" href="#/perfil/personal/editar"><i class="icon-edit"></i> Editar perfil</a></p>

<h3><%= alumno.apellidoPaterno %> <%= alumno.apellidoMaterno %>, <%= alumno.nombres %></h3>

<div class="partial-layout">
	<div class="partial-fixed">
		<img id="user-photo" src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= alumno.foto %>" />
	</div>
	<div class="partial-fluid">
		<div class="partial-fluid-content">
			<div class="row-fluid">
				<table class="table table-hover">
					<tr>
						<td class="span3 head">DNI</td>
						<td class="span9"><%= alumno.dni %></td>
					</tr>
                                        <% if(alumno.datoPersonal != null){ %>
                                            <tr>
                                                    <td class="span3 head">Fecha de nacimiento</td>                                                
                                                    <td class="span9">
                                                        <% if(alumno.datoPersonal.fechaNacimiento != null){ %>
                                                            <%= alumno.datoPersonal.fechaNacimiento %>
                                                        <% } %>
                                                    </td>
                                            </tr>
                                            <tr>
                                                    <td class="span3 head">Dep. / Prov. / Dist.</td>
                                                    <td class="span9">
                                                        <% if(alumno.datoPersonal.distrito.id != 0){ %>
                                                            <%= alumno.datoPersonal.distrito.provincia.departamento.nombre %> / <%= alumno.datoPersonal.distrito.provincia.nombre %> / <%= alumno.datoPersonal.distrito.nombre %>
                                                        <% } %>
                                                    </td>
                                            </tr>
                                            <tr>
                                                    <td class="span3 head">Dirección</td>
                                                    <td class="span9"><%= alumno.datoPersonal.direccion %></td>
                                            </tr>
                                            <tr>
                                                    <td class="span3 head">Teléfono</td>
                                                    <td class="span9"><%= alumno.datoPersonal.telefono %></td>
                                            </tr>
                                            <tr>
                                                    <td class="span3 head">Celular</td>
                                                    <td class="span9"><%= alumno.datoPersonal.telefonoMovil %></td>
                                            </tr>
                                        <% } %>
                                        <% emailList.forEach(function(email) { %>
					<tr>
						<td class="span3 head"><%= email.tipoEmail.nombre %></td>
						<td class="span9"><%= email.email %></td>
					</tr>					
                                        <% }); %>
				</table>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
</div>

<% if(alumno.datoPersonal != null){ %>
    <h3>Pensión</h3>
    <div class="row-fluid">
            <table class="table table-hover">
                    <tr>
                            <td class="span3 head">Dep. / Prov. / Dist.</td>
                            <td class="span9">
                                <% if(alumno.datoPersonal.distritoPension.id != 0){ %>
                                    <%= alumno.datoPersonal.distritoPension.provincia.departamento.nombre %> / <%= alumno.datoPersonal.distritoPension.provincia.nombre %> / <%= alumno.datoPersonal.distritoPension.nombre %>
                                <%}%>
                            </td>
                    </tr>
                    <tr>
                            <td class="span3 head">Dirección</td>
                            <td class="span9"><%= alumno.datoPersonal.direccionPension %></td>
                    </tr>
                    <tr>
                            <td class="span3 head">Teléfono</td>
                            <td class="span9"><%= alumno.datoPersonal.telefonoPension %></td>
                    </tr>
            </table>
    </div>

    <h3>Emergencia</h3>
    <p><span class="label label-warning">Persona a contactar en caso de emergencia</span></p>

    <div class="row-fluid">
            <table class="table table-hover">
                    <tr>
                            <td class="span3 head">Nombre</td>
                            <td class="span9"><%= alumno.datoPersonal.contactoEmergencia %></td>
                    </tr>
                    <tr>
                            <td class="span3 head">Teléfono</td>
                            <td class="span9"><%= alumno.datoPersonal.telefonoEmergencia %></td>
                    </tr>
            </table>
    </div>
<% } %>
<p><a class="btn" href="#/perfil/personal/editar"><i class="icon-edit"></i> Editar perfil</a></p>