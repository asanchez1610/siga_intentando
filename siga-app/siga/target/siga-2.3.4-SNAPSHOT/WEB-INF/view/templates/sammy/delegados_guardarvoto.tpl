       
        <h3 id="siga-modal-title">Elección de delegados</h3>
        <div>           
            <p>¿Desea votar por <%= nombreyapellido %> como delegado de <%=estudio%>(NIVEL <%=nivel%>)?</p>
                
	</div>
	<div >
            <form action="#/delegados/votacion" method="post" id="votacion">
               <input name="idedicionestudio" id="idedicionestudio" type="hidden" value="<%= idestudio %>" />
                <input name="idcandidato" id="idcandidato" type="hidden" value="<%= idcandidato %>" />
                <input name="idalumnocandidato" id="idalumnocandidato" type="hidden" value="<%= idalumnocandidato %>" />
                <button id="confirmar-votacion"  class="btn" type="submit">Aceptar</button>
               <a class="btn" href="#/delegados">Cancelar</a>
            </form>
	</div>
    
