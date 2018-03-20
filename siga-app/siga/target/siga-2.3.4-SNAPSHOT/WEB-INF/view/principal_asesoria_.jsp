<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-body">
        <div class="row-fluid">
            <div class="span3">
                <i class="icon-3x icon-check "></i>
            </div>
            <div class="span9">
                <h3>Elección exitosa</h3>
                <h5>La opción seleccionada ha sido registrada exitosamente para el periodo ${periodo}.</h5>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary pull-right" data-dismiss="modal" aria-hidden="true" onclick="window.location.reload()">Aceptar</button>
    </div>
</div>
<div id="siga-main">
    <div class="row-fluid">
        <div class="span12 text-center">
            <h1>Asesoramiento ${periodo}</h1>
        </div>
    </div>
    
    <div class="row-fluid text-center">
     <p> Estimado alumno, a través de esta sección puedes seleccionar a tu asesor para el presente periodo ${periodo}.</p>
    </div>
    <br/><br/>
    <div class="row-fluid">
        <div class="span12 text-center">
            <h3>Asesor actual</h3>
            
        </div>
        <div class="span4"></div>
        <div class="span4 text-center">
            <img src="https://sigadocentes.udep.edu.pe/fotossiga/persona/${fotoAsesor}" alt="" width = "150px" height="224"/>
            <br/>
            <h4>${nombreAsesor}</h4>
        </div>
        <div class="span4"></div>
    </div>
    <br/>
    <br/>
    <div class="row-fluid">
        <div class="span12  text-center">
            <button class="btn btn-primary" id="mantenerAsesor">Mantener mi asesor</button>&nbsp;
            <button class="btn btn-default" onclick="window.location='${pageContext.request.contextPath}/asesoria/elegirAsesor.htm'">Elegir otro</button>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('#mantenerAsesor').click(function(){
            $.post('${pageContext.request.contextPath}/asesoria/mantenerAsesor.json',{},function(response){
                if(response.status===true){
                    $('#myModal').modal({
                        backdrop: 'static'
                        ,keyboard: false
                    });
                }else{
                    window.location= '${pageContext.request.contextPath}/login.htm';
                }
            });
        });
    });
</script>