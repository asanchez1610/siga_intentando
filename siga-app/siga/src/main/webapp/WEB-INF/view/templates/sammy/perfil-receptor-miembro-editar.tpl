<form action="#/perfil/receptor-correspondencia/editar" method="post" id="edit-miembro">
<input name="id" id="id" type="hidden" value="<%= miembro.id %>" />
<input name="idEdicionestudio" id="idEdicionestudio" type="hidden" value="<%= currentPlan %>" />
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
                                                    <input type="text" id="direccion" name="direccion" value="<%= miembro.direccion %>" />                                                   
                                                </td>
                                        </tr>  
                                        <tr>
                                                <td class="span3 head">Teléfono fijo</td>
                                                <td class="span9">
                                                    <input type="tel" id="telefonofijo" name="telefonofijo" value="<%= miembro.telefonofijo %>" class="textoNumero" maxlength="9"/><span class="input_legend beforeError">Debe incluir el c&oacute;digo de la ciudad</span>                                                   
                                                </td>
                                        </tr> 
                                        <tr>
                                                <td class="span3 head">Celular</td>
                                                <td class="span9">
                                                    <input type="tel" id="telefonocelular" name="telefonocelular" value="<%= miembro.telefonocelular %>"  class="textoNumero" maxlength="9"/><span class="input_legend beforeError">Debe ingresar s&oacute;lo d&iacute;gitos</span>
                                                </td>
                                        </tr>
                                        <tr>
                                                <td class="span3 head">Correo</td>
                                                <td class="span9">
                                                    <input type="text" id="email" name="email" value="<%= miembro.email %>" />                                                    
                                                </td>
                                        </tr> 
				</table>                            
			</div>
		</div>
	</div>
        <div class="clearfix"></div>
</div>
<p>
    <a class="btn" href="#/perfil/receptor-correspondencia">Cancelar</a>
    <button id="perfil-miembro-editar-submit" data-loading-text="Cargando..." class="btn btn-primary" >Guardar</button>
</p>
</form>
