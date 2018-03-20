<div class="section-subheader">
	<ul class="nav nav-pills">
		<li><a href="#/perfil/personal">Mi perfil personal</a></li>
                <li class="active"><a href="#/perfil/receptor-correspondencia">Datos familiares</a></li>
	</ul>
</div>

<h3>DATOS DEL RECEPTOR DE CORRESPONDENCIA</h3>
<p>Por favor verifica que los datos no editables se encuentren completos y correctamente escritos. Si alguno de estos
datos presenta algún error, acércate a Oficina de pensiones para solicitar su corrección.
</p>
<div class="partial-layout">
	<div class="partial-fluid">
		<div class="partial-fluid-content">
			<div class="row-fluid">
				<table class="table table-hover">
					<tr>
						<td class="span3 head">Nombres y Apellidos</td>
						<td class="span9"><%= receptor.nombres %> <%= receptor.apellidos %></td>
					</tr>                                       
                                        <tr>
                                                <td class="span3 head">Dirección de correspondencia</td>
                                                <td class="span9"><%= receptor.direccion %> - <%= receptor.ciudad %></td>
                                        </tr>                                        
				</table>
			</div>
		</div>
	</div>
        <div class="clearfix"></div>
</div>
<h3>DATOS DE LA FAMILIA</h3>
<ul id="subject-nav-tabs" class="nav nav-tabs">
    <% receptor.listTipoMiembro.forEach(function(item) { %>
	<li><a href="#/perfil/receptor-correspondencia/<%=item.codigo %>" data-target="#subject-tab-<%=item.codigo %>" data-toggle="tab" data-id="<%=item.cpac %>" id="nav-tab-<%=item.codigo %>"><%=item.parentesco %></a></li>
    <% }); %>
</ul>
<div id="subject-tab-content" class="tab-content">
    <% receptor.listTipoMiembro.forEach(function(item) { %>
	<div class="tab-pane" id="subject-tab-<%=item.codigo %>"></div>
    <% }); %>
</div>