<p><a class="btn btn-link btn-small btn-back hidden" href="javascript:history.back();"><i class="icon-angle-left"></i> Volver al perfil del profesor</a></p>

<h3><%= investigacion.titulo %></h3>

<table class="table table-hover">
	<tbody>
		<tr>
			<td class="head">Autor(es)</td>
			<td><%= investigacion.autores %></td>
		</tr>
		<tr>
			<td class="head">Campo</td>
			<td><%= investigacion.campoInvestigacion %></td>
		</tr>
		<tr>
			<td class="head">Resultado</td>
			<td><%= investigacion.resultado %></td>
		</tr>
		<tr>
			<td class="head">Inicio</td>
			<td><%= investigacion.fechaInicio %></td>
		</tr>
		<tr>
			<td class="head">Fin</td>
			<td><%= investigacion.fechaFin %></td>
		</tr>
		<tr>
			<td class="head">Descripción</td>
			<td><%= investigacion.descripcion %></td>
		</tr>
		<% if (Object.prototype.toString.call(archivos) === '[object Array]' && archivos.length > 0) { %>
		<tr>
			<td class="head">Archivos</td>
			<td>
				<% if (archivos !== null) { %>                                        
                                        <table class="table table-condensed table-hover">
                                            <thead>
                                                    <tr>
                                                            <th>Nombre</th>
                                                            <th>Descripcion</th>                                                            
                                                            <th>Fecha Registro</th>
                                                    </tr>
                                            </thead>                                                
                                            <tbody>
                                                <% archivos.forEach( function( itemDoc) { %>
                                                    <tr>
                                                        <td><%= itemDoc.nombre %></td>
                                                        <td><%= itemDoc.descripcion %></td>
                                                        <td><%= itemDoc.fecha %></td>
                                                    </tr>
                                                 <% }); %>
                                            </tbody>
                                        </table>                                        
                                    <% } else { %>
                                        <p class="alert alert-info">No hay documentos disponibles.</p>
                                    <% } %>
			</td>
		</tr>
		<% } %>
	</tbody>
</table>