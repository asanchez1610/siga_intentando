<div id="siga-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="siga-modal-title" aria-hidden="true">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3 id="siga-modal-title">Elección de delegados</h3>
		
	</div>
	
    
        <div class="modal-body">           
            <p>Desea votar por <%= nombreyapellido %> como delegado de <%=estudio%>(NIVEL <%=nivel%>)?.</p>
                
	</div>
	<div class="modal-footer">
            <form action="#/delegados/votacion" method="post" id="votacion">
               <input name="idedicionestudio" id="idedicionestudio" type="hidden" value="<%= idestudio %>" />
                <input name="idcandidato" id="idcandidato" type="hidden" value="<%= idcandidato %>" />
                <input name="idalumnocandidato" id="idalumnocandidato" type="hidden" value="<%= idalumnocandidato %>" />
                <button id="confirmar-votacion"  class="btn" type="submit">Aceptar</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">Cancelar</button>
            </form>
	</div>
    
</div>