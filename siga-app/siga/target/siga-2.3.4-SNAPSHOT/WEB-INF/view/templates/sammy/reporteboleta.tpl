<div id="siga-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="siga-modal-title" aria-hidden="true">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3 id="siga-modal-title">Pagos de cuota <%=ncuota %></h3>
                <h4 >Vence <%=fechav %></h4>
		
	</div>
	
    
        <div class="modal-body">           
           <% if ( pregrado !== null && pregrado.length > 0) { %>
                                <table class="table table-condensed table-hover">
                                    <thead>
                                        <tr>
                                            
                                            <th>Descripcion</th>
                                            <th>Fecha de emisión</th>
                                            <th>Total (S/.)</th>
                                            <th>Descargar recibo</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% pregrado.forEach(function(cuota) { %>
                                            <tr>
                                                
                                                <td><%= cuota.descripcion %></td>
                                                <td><%= cuota.fechaemision %></td>
                                                <td><%= cuota.total %></td>
                                                
                                                 <td>
                                                     <a href="json/pensiones/descargaToPDF/<%= currentPlan %>/<%= cuota.nrecibo %>" class="btn btn-mini" >
                                                      <i class="icon-download-alt"></i></a>
                                                    
                                                 </td>
                                            </tr>
                                        <% }); %>
                                    </tbody>
                                </table>
                            <% } else { %>
                                <p class="alert alert-error">No encontramos pagos.</p>
                            <% } %>
                
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
	</div>	
    
</div>