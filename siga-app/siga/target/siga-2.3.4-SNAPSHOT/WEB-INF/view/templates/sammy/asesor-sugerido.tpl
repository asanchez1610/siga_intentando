<% if(isSugeridos) { %>
    <div class="section-subheader">
            <ul class="nav nav-pills">
                    <li><a href="#/asesor/perfil">Mi asesor</a></li>
                    <li class="active"><a href="#/asesor/sugeridos">Selección de asesores</a></li>
            </ul>
    </div>
    
    <div>
     <p> Estimado alumno, a través de esta sección puedes seleccionar a tu asesor para el presente periodo <%= periodo %>. 
         Debes seleccionar de manera obligatoria dos opciones de asesor; Recuerda que si deseas mantener a tu asesor actual,
         debes seleccionar su nombre en la primera opción.</p>
    </div>

    <table width = "80%" >
        <tr>
            <td>
                <table class="table table-hover">
                    <tr>
                            <td class="head span3">Asesor actual</td>
                            <td class="span9"><%= nombreAsesor %></td>    
                    </tr>
                </table>
             </td>
             <td><img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= fotoAsesor %>" alt="<%= nombreAsesor %>" width = "150px"/></td>                 
        </tr>
        <tr>
            <td colspan = "2">

            </td>
        </tr>
    </table>
    <form class="form-horizontal" action="#/asesor/sugeridos" method="post" id="new-sugeridos">
        <fieldset>
                <input name="idedicionestudio" id="idedicionestudio" type="hidden" value="<%= currentPlan %>" />		
                <div class="control-group">
                    <table><tr><td>
                        <table class="table table-hover"><tr><td class="head span3">
                            OPCIÓN 1* <div class="controls">
                                    <select id="sugerido_1" name="sugerido_1"> 
                                        <option value="0">Seleccione asesor</option>
                                                <% sugeridosList.forEach(function(sugeridos) { %>
                                                    <% sugeridos.asesorList.forEach(function(item) { %>
                                                    <%if(item.id==sugeridosList[0].idasesor1){ %>
                                                    
                                                    <option selected value="<%= item.id %>"><%= item.nombres %></option>
                                                    <% } else { %>
                                                    <option value="<%= item.id %>"><%= item.nombres %></option>
                                                    <% }%>
                                                        
                                                    <% }); %>
                                                <% }); %>
                                    </select>
                            </div></td></tr></table></td><td>
                        <div class="img_Asesor_1">
                                <img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= sugeridosList[0].fotoasesor1 %>" alt="<%= nombreAsesor %>" width = "100px"/>
                        </div></td></tr>
                    </table>
                </div>
                <div class="control-group">
                    <table><tr><td>
                        <table class="table table-hover"><tr><td class="head span3">
                            OPCIÓN 2* <div class="controls">
                                    <select id="sugerido_2" name="sugerido_2"> 
                                        <option value="0">Seleccione asesor</option>
                                                <% sugeridosList.forEach(function(sugeridos) { %>
                                                    <% sugeridos.asesorList.forEach(function(item) { %>
                                                   <%if(item.id==sugeridosList[0].idasesor2){ %>
                                                    
                                                    <option selected value="<%= item.id %>"><%= item.nombres %></option>
                                                    <% } else { %>
                                                    <option value="<%= item.id %>"><%= item.nombres %></option>
                                                    <% }%>
                                                    <% }); %>
                                                <% }); %>
                                    </select>
                            </div></td></tr></table></td><td>
                        <div class="img_Asesor_2">
                                <img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/<%= sugeridosList[0].fotoasesor2 %>" alt="<%= nombreAsesor %>" width = "100px"/>
                        </div></td></tr>
                    </table>
                </div>	
                <div class="form-actions">
                        <button id="submit-asesores" data-loading-text="Cargando..." type="submit" class="btn btn-primary">Enviar</button>
                </div>
        </fieldset>
    </form>
<% } else {%>
    <p class="alert alert-error">Esta opción solo está disponible para pregrado.</p>
<% }%>


                    