<p><a class="btn btn-mini btn-success" href="#/perfil/receptor-correspondencia/editar/<%= miembro.codigo %>"><i class="icon-edit"></i> Editar</a></p>
<div class="partial-layout">
	<div class="partial-fluid">
		<div class="partial-fluid-content">
			<div class="row-fluid">
				<table class="table table-hover">
					<tr>
						<td class="span3 head">Nombres</td>
						<td class="span9"><%= miembro.nombres %></td>
					</tr> 
                                        <tr>
						<td class="span3 head">Apellidos</td>
						<td class="span9"><%= miembro.apellidos %></td>
					</tr> 
                                        <tr>
                                                <td class="span3 head">Dirección</td>
                                                <td class="span9">
                                                     <% if(miembro.direccion === '') { %> 
                                                           <i>No registrado</i>
                                                     <% } else { %> 
                                                           <%= miembro.direccion %>
                                                     <% } %>
                                                </td>
                                        </tr>  
                                        <tr>
                                                <td class="span3 head">Teléfono fijo</td>
                                                <td class="span9">
                                                    <% if(miembro.telefonofijo === '') { %> 
                                                           <i>No registrado</i>
                                                    <% } else { %> 
                                                           <%= miembro.telefonofijo %>
                                                    <% } %>
                                                </td>
                                        </tr> 
                                        <tr>
                                                <td class="span3 head">Celular</td>
                                                <td class="span9">
                                                    <% if(miembro.telefonocelular === '') { %>
                                                           <i>No registrado</i>
                                                    <% } else { %> 
                                                           <%= miembro.telefonocelular %>
                                                    <% } %>
                                                </td>
                                        </tr>
                                        <tr>
                                                <td class="span3 head">Correo</td>
                                                <td class="span9">
                                                    <% if(miembro.email === '') { %>
                                                           <i>No registrado</i>
                                                    <% } else { %> 
                                                           <%= miembro.email %>
                                                    <% } %>
                                                </td>
                                        </tr> 
				</table>
			</div>
		</div>
	</div>
        <div class="clearfix"></div>
</div>
<p><a class="btn btn-mini btn-success" href="#/perfil/receptor-correspondencia/editar/<%= miembro.codigo %>"><i class="icon-edit"></i> Editar</a></p>