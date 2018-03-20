<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

<div class="modal-body">
<p id="mensaje"></p>
</div>
<div class="modal-footer">
<button class="btn" data-dismiss="modal" aria-hidden="true">Aceptar</button>

</div>
</div>
<div id="siga-main" >
    <style>
        .table .head, .accordion .row-fluid .head {
  color: #fb5252;
  text-transform: uppercase;
  font-weight: bold;
}
       
    </style>
    <form action="./guardarinfodatospersonales.htm" method="post" id="edit-profile">
	<fieldset>
             <p> Estimado alumno,</p>
             
             <p>Antes de continuar por favor actualiza tus Datos Personales. 
          </p>
          <p>Los campos marcados con * son obligatorios.</p>
		<p class="text-right">
                        <a href="./j_spring_security_logout" title="Cerrar sesión" class="btn btn-primary">Cerrar Sesion</a>
                        
		</p>

		

		<h3>General</h3>
		<div class="row-fluid">
			<table class="table table-condensed table-hover">
				<tr>
					<td class="span3 head">Nombre</td>
					<td class="span9"><input type="text" name="nombre" readonly="true" value="${alumno.nombres}" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Apellido Paterno</td>
					<td class="span9"><input type="text" name="appaterno" readonly="true" value="${alumno.apellidoPaterno}" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Apellido Materno</td>
					<td class="span9"><input type="text" name="apmaterno" readonly="true" value="${alumno.apellidoMaterno}" required /></td>
				</tr>
				<tr>
					<td class="span3 head">DNI</td>
					<td class="span9"><input type="text" name="dni" readonly="true" value="${alumno.dni}" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Fecha de nacimiento</td>
					<td class="span9">
                                <c:if test="${alumno.datoPersonal.fechaNacimiento != null}"> 
                                                <input type="text" name="fechaNacimiento" readonly value="${alumno.datoPersonal.fechaNacimiento}" />
                                </c:if>          
                                 <c:if test="${alumno.datoPersonal.fechaNacimiento == null}">
                                <input type="text" name="fechaNacimiento" readonly value="" />
                                </c:if>
                                        </td>
				</tr>
				
                                <tr>
					<td class="span3 head">Departamento *</td>
					<td class="span9">
                                            <c:choose>
                                            <c:when test="${alumno.datoPersonal.distrito.nombre != null && alumno.datoPersonal.distrito.provincia.nombre != null && alumno.datoPersonal.distrito.provincia.departamento.nombre != null}">
							<select id="departamento" name="departamento" data-origin="${alumno.datoPersonal.distrito.provincia.departamento.id}">
								<option value="${alumno.datoPersonal.distrito.provincia.departamento.id}">${alumno.datoPersonal.distrito.provincia.departamento.nombre}</option>
							</select>
                                            </c:when>
					     <c:otherwise>
							<select id="departamento" name="departamento">
								<option value="0">Seleccione...</option>
							</select>
                                             </c:otherwise>
                                            </c:choose>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Provincia *</td>
					<td class="span9">
                                            <c:choose>
						 <c:when test="${alumno.datoPersonal.distrito.nombre != null && alumno.datoPersonal.distrito.provincia.nombre != null}">
							<select id="provincia" name="provincia" data-origin="${alumno.datoPersonal.distrito.provincia.id}">
								<option value="${alumno.datoPersonal.distrito.provincia.id}">${alumno.datoPersonal.distrito.provincia.nombre}</option>
							</select>
						  </c:when>
                                                   <c:otherwise >
							<select id="provincia" name="provincia">
								<option value="0">Seleccione...</option>
							</select>
                                                   </c:otherwise>
                                            </c:choose>     
					</td>
				</tr>
				<tr>
					<td class="span3 head">Distrito *</td>
					<td class="span9">
                                            <c:choose>
						<c:when test="${alumno.datoPersonal.distrito.nombre != null}">
							<select id="distrito" name="distrito" data-origin="${alumno.datoPersonal.distrito.id}">
								<option value="${alumno.datoPersonal.distrito.id}">${alumno.datoPersonal.distrito.nombre}</option>
							</select>
                                                </c:when>
                                                <c:otherwise>
							<select id="distrito" name="distrito">
								<option value="0">Seleccione...</option>
							</select>
						 </c:otherwise>
                                            </c:choose>   
					</td>
				</tr>
				<tr>
					<td class="span3 head">Direcci&oacute;n *</td>
					<td class="span9"><input type="text" name="direccion" value="${alumno.datoPersonal.direccion}" required /></td>
				</tr>
				<tr>
					<td class="span3 head">Teléfono</td>
					<td class="span9"><input type="tel" name="telefonoCasa" value="${alumno.datoPersonal.telefono}" /></td>
				</tr>
				<tr>
					<td class="span3 head">Celular</td>
					<td class="span9"><input type="tel" name="telefonoCelular" value="${alumno.datoPersonal.telefonoMovil}" /></td>
				</tr>
				<tr>
					<td class="span3 head">Correo UDEP</td>
					<td class="span9"><input type="email" name="emailUdep" value="${oficial}" readonly="true" /></td>
				</tr>             
				<tr>
					<td class="span3 head">Correo personal</td>
					<td class="span9"><input type="email" name="emailPersonal" value="${personal}" /></td>
				</tr>
				
			</table>
		</div>
              <h3>Pensión</h3>
		<div class="row-fluid">
			<table class="table table-condensed table-hover">
				<tr>
					<td class="span3 head">Departamento</td>
					<td class="span9">
                                            <c:choose>
                                             <c:when test="${alumno.datoPersonal.distritoPension.nombre != null && alumno.datoPersonal.distritoPension.provincia.nombre != null && alumno.datoPersonal.distritoPension.provincia.departamento.nombre != null}">
							<select id="pension_departamento" name="pension_departamento" data-origin="${alumno.datoPersonal.distritoPension.provincia.departamento.id}">
								<option value="${alumno.datoPersonal.distritoPension.provincia.departamento.id}">${alumno.datoPersonal.distritoPension.provincia.departamento.nombre}</option>
							</select>
                                            </c:when>
					     <c:otherwise>
							<select id="pension_departamento" name="pension_departamento">
								<option value="0">Seleccione...</option>
							</select>
                                             </c:otherwise>
                                            </c:choose>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Provincia</td>
					<td class="span9">
                                            <c:choose>
                                               <c:when test="${alumno.datoPersonal.distritoPension.nombre != null && alumno.datoPersonal.distritoPension.provincia.nombre != null}">
							<select id="pension_provincia" name="pension_provincia" data-origin="${alumno.datoPersonal.distritoPension.provincia.id}">
								<option value="${alumno.datoPersonal.distritoPension.provincia.id}">${alumno.datoPersonal.distritoPension.provincia.nombre}</option>
							</select>
                                               </c:when>
					       <c:otherwise>
							<select id="pension_provincia" name="pension_provincia">
								<option value="0">Seleccione...</option>
							</select>
                                              </c:otherwise>
                                            </c:choose>	
					</td>
				</tr>
				<tr>
					<td class="span3 head">Distrito</td>
					<td class="span9">
                                            <c:choose>
                                              <c:when test="${alumno.datoPersonal.distritoPension.nombre != null}">
							<select id="pension_distrito" name="pension_distrito" data-origin="${alumno.datoPersonal.distritoPension.id}">
								<option value="${alumno.datoPersonal.distritoPension.id}">${alumno.datoPersonal.distritoPension.nombre}</option>
							</select>
                                              </c:when>
					     <c:otherwise>
							<select id="pension_distrito" name="pension_distrito">
								<option value="0">Seleccione...</option>
							</select>
                                             </c:otherwise>
                                            </c:choose>
					</td>
				</tr>
				<tr>
					<td class="span3 head">Dirección</td>
					<td class="span9"><input type="text" name="pensionDireccion" value="${alumno.datoPersonal.direccionPension}" /></td>
				</tr>
				<tr>
					<td class="span3 head">Teléfono</td>
					<td class="span9"><input type="tel" name="pensionTelefono" value="${alumno.datoPersonal.telefonoPension}" /></td>
				</tr>
			</table>
		</div>                  
               <h3>Emergencia</h3>
		<p><span class="label label-warning">Persona a contactar en caso de emergencia</span></p>
		<div class="row-fluid">
			<table class="table table-condensed table-hover">
				<tr>
					<td class="span3 head">Nombre</td>
					<td class="span9"><input type="text" name="emergenciaNombre" value="${alumno.datoPersonal.contactoEmergencia}" /></td>
				</tr>
				<tr>
					<td class="span3 head">Teléfono</td>
					<td class="span9"><input type="tel" name="emergenciaTelefono" value="${alumno.datoPersonal.telefonoEmergencia}" /></td>
				</tr>
			</table>
		</div>
		<p>
		<button id="perfil-personal-editar-submit" data-loading-text="Cargando..." class="btn btn-primary" type="submit">Guardar</button>
                
		</p>
	</fieldset>
</form>    
 </div><script type="text/javascript">
      $(document).ready(function(){
          
      cargarLista();
      $('#edit-profile').submit(function(event){
           if($('#distrito option:selected').val()==='0')
               {
                   
               event.preventDefault();
               $('#mensaje').html('Por favor seleccione  un Distrito');
                   $('#myModal').modal('show');
                   $(document).load();
               }
               return ;
       }
   );
      });
      function cargarLista(){
          var emptyOption = "<option value=0>Seleccione</option>";
          var $departamento = $( '#departamento' ),
                                $provincia = $( '#provincia' ),
                                $distrito = $( '#distrito' ),
                                $departamentoPension = $( '#pension_departamento' ),
                                $provinciaPension = $( '#pension_provincia' ),
                                $distritoPension = $( '#pension_distrito' );

                        var departamentoOrigin = $departamento.data( 'origin' ),
                                provinciaOrigin = $provincia.data( 'origin' ),
                                distritoOrigin = $distrito.data( 'origin' ),
                                departamentoPensionOrigin = $departamentoPension.data( 'origin' ),
                                provinciaPensionOrigin = $provinciaPension.data( 'origin' ),
                                distritoPensionOrigin = $distritoPension.data( 'origin' );
           $.post( 'json/personal/departamentoList.json', function( response ) {
            var aux = "";
             
               for(var i = 0 ; i < response.departamentoList.length ; i++){  
                   
                       
                         aux = aux + "<option value="+response.departamentoList[i].id+">"+response.departamentoList[i].nombre+"</option>";         
               }
               //listar departamentos
              // $departamento.append(aux);
              $departamento.html(aux);
               $departamentoPension.append(aux);
               
               $departamento.val( departamentoOrigin );
                // seleccionar el departamento de la pension
               $departamentoPension.val( departamentoPensionOrigin );
        });  
        // si hay un departamento seleccionado
                        departamentoOrigin = typeof departamentoOrigin !== 'undefined' && departamentoOrigin !== null
                                ? departamentoOrigin
                                // sino toma el primer departamento mostrado
                                : $departamento.val();

                        // cargar lista de provincias
                        $.post( 'json/personal/provinciaList.json?idDepartamento=' + departamentoOrigin )
                                .done( function( response ) {
                                  var aux = "";
             
                                  for(var i = 0 ; i < response.provinciaList.length ; i++){  
          
                                  aux = aux + "<option value="+response.provinciaList[i].id+">"+response.provinciaList[i].nombre+"</option>";         
                                  }
                                       // listar provincias
                                      $provincia.html(aux).val( provinciaOrigin );
                                });
                      // si hay una provincia seleccionada
                        if ( typeof provinciaOrigin !== 'undefined' && provinciaOrigin !== null ) {
                                // cargar lista de distritos
                                $.post( 'json/personal/distritoList.json?idProvincia=' + provinciaOrigin )
                                        .done( function( response ) {
                                    var aux = "";
             
                                  for(var i = 0 ; i < response.distritoList.length ; i++){  
          
                                  aux = aux + "<option value="+response.distritoList[i].id+">"+response.distritoList[i].nombre+"</option>";         
                                  }
                                      
                                             // listar distritos
                                                $distrito.html(aux).val( distritoOrigin );
                                        });
                        }
                 // si hay un departamento_pension seleccionado
                        departamentoPensionOrigin = typeof departamentoPensionOrigin !== 'undefined' && departamentoPensionOrigin !== null
                                ? departamentoPensionOrigin
                                // sino toma el primer departamento mostrado
                                : $departamentoPension.val();

                        // cargar lista de provincias
                        if(departamentoPensionOrigin>0)
                            {
                        $.post( 'json/personal/provinciaList.json?idDepartamento=' + departamentoPensionOrigin )
                                .done( function( response ) {
                                     var aux = "";
             
                                  for(var i = 0 ; i < response.provinciaList.length ; i++){  
          
                                  aux = aux + "<option value="+response.provinciaList[i].id+">"+response.provinciaList[i].nombre+"</option>";         
                                  }
                                        // listar provincias
                                        $provinciaPension.html(aux).val( provinciaPensionOrigin );
                                });
                            }
                        // si hay una provincia_pension seleccionada
                        if ( typeof provinciaPensionOrigin !== 'undefined' && provinciaPensionOrigin !== null ) {
                                // cargar lista de distritos
                                $.post( 'json/personal/distritoList.json?idProvincia=' + provinciaPensionOrigin )
                                        .done( function( response ) {
                                     var aux = "";
             
                                  for(var i = 0 ; i < response.distritoList.length ; i++){  
          
                                  aux = aux + "<option value="+response.distritoList[i].id+">"+response.distritoList[i].nombre+"</option>";         
                                  }
                                                     // listar distritos
                                                $distritoPension.html(aux).val( distritoPensionOrigin );
                                        });
                        } 
                        
                        // al cambiar departamento
                        $departamento.on( 'change', function() {
                                //var provinciaPromise = $.post( 'json/personal/provinciaList.json?idDepartamento=' + $departamento.val() );

                                // eliminar inmediatamente
                                $provincia.html( emptyOption );
                                $distrito.html( emptyOption );

                                $.post( 'json/personal/provinciaList.json?idDepartamento=' + $departamento.val()).done( function( response ) {
                                     var aux = "";
             
                                  for(var i = 0 ; i < response.provinciaList.length ; i++){  
          
                                  aux = aux + "<option value="+response.provinciaList[i].id+">"+response.provinciaList[i].nombre+"</option>";         
                                  }
                                         $provincia.append(aux);
                                });
                        });
                        // al cambiar provincia
                        $provincia.on( 'change', function() {
                                //var distritoPromise = $.post( 'json/personal/distritoList.json?idProvincia=' + $provincia.val() );

                                // eliminar inmediatamente
                                $distrito.html( emptyOption );

                                $.post( 'json/personal/distritoList.json?idProvincia=' + $provincia.val()).done( function( response ) {
                                      var aux = "";
             
                                  for(var i = 0 ; i < response.distritoList.length ; i++){  
          
                                  aux = aux + "<option value="+response.distritoList[i].id+">"+response.distritoList[i].nombre+"</option>";         
                                  }
                                         $distrito.append(aux);
                                });
                        });
                        
                         // al cambiar departamento pension
                        $departamentoPension.on( 'change', function() {
                                //var provinciaPromise = $.post( 'json/personal/provinciaList.json?idDepartamento=' + $departamento.val() );

                                // eliminar inmediatamente
                               $provinciaPension.html( emptyOption );
                                $distritoPension.html( emptyOption );

                                $.post( 'json/personal/provinciaList.json?idDepartamento=' + $departamentoPension.val()).done( function( response ) {
                                     var aux = "";
             
                                  for(var i = 0 ; i < response.provinciaList.length ; i++){  
          
                                  aux = aux + "<option value="+response.provinciaList[i].id+">"+response.provinciaList[i].nombre+"</option>";         
                                  }
                                         $provinciaPension.append(aux);
                                });
                        });
                        // al cambiar provincia pension
                        $provinciaPension.on( 'change', function() {
                                //var distritoPromise = $.post( 'json/personal/distritoList.json?idProvincia=' + $provincia.val() );

                                // eliminar inmediatamente
                                $distritoPension.html( emptyOption );

                                $.post( 'json/personal/distritoList.json?idProvincia=' + $provinciaPension.val()).done( function( response ) {
                                      var aux = "";
             
                                  for(var i = 0 ; i < response.distritoList.length ; i++){  
          
                                  aux = aux + "<option value="+response.distritoList[i].id+">"+response.distritoList[i].nombre+"</option>";         
                                  }
                                         $distritoPension.append(aux);
                                });
                        });
                             
    }
     </script>

     
	


              
               
