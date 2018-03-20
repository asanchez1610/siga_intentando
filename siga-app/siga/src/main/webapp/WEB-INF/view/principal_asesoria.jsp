<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
        <button class="btn btn-primary pull-right" data-dismiss="modal" aria-hidden="true" onclick="window.location='${pageContext.request.contextPath}/'">Aceptar</button>
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
            <h3>Asesor actual : ${nombreAsesor}</h3>
            
        </div>
       <br/>
       <br/>
       <div class="span12 text-center">
            <h4>Elige 2 opciones</h4>
            
        </div>
    </div>
    <br/>
    <br/>
    <div class="row-fluid">
        <div class="span2"></div>
        <form class="form-horizontal span8" action="${pageContext.request.contextPath}/asesoria/guardarinfoasesor.htm" method="post" id="new-sugeridos" >
                <input name="idedicionestudio" id="idedicionestudio" type="hidden" value="${currentPlan}" />		
                <div class="control-group">
                    <table width="100%">
                        <tr>
                            <td>
                                <table class="table table-hover"><tr><td class="head span3">
                                            OPCIÓN 1* <div class="controls">
                                                <select id="sugerido_1" name="sugerido_1"> 
                                                    <option value="0">Seleccione asesor</option>


                                                </select>
                                            </div></td></tr>
                                </table>
                            </td>
                            <td>
                                <div  id="img_Asesor_1" class="img_Asesor_1">

                                </div>
                            </td></tr>
                    </table>
                </div>
                <div class="control-group">
                    <table width="100%"><tr><td>
                                <table class="table table-hover"><tr><td class="head span3">
                                            OPCIÓN 2* <div class="controls">
                                                <select id="sugerido_2" name="sugerido_2"> 
                                                    <option value="0">Seleccione asesor</option>


                                                </select>
                                            </div></td></tr></table></td><td>
                                <div  id="img_Asesor_2" class="img_Asesor_2">

                                </div>
                            </td></tr>
                    </table>
                </div>
                <div class="row-fluid text-center" style="background-color: #F3F3F3;">
                    <br/>
                    <button data-loading-text="Cargando..." type="button" onclick="window.location='${pageContext.request.contextPath}/'" class="btn btn-default">Regresar</button>&nbsp;
                    <button id="submit-asesores" data-loading-text="Cargando..." type="button" class="btn btn-primary">Enviar</button>
                    <br/>
                </div>
        </form>
        <div class="span2"></div>
    </div>
</div>
                    
<script type="text/javascript" src="<spring:message code="static.path"/>/js/jquery.form.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        $('#new-sugeridos').submit(function (event) {
            if ($('#sugerido_1 option:selected').val() === '0' || $('#sugerido_2 option:selected').val() === '0' || ($('#sugerido_1 option:selected').val() === $('#sugerido_2 option:selected').val()))
            {

                event.preventDefault();
                $('#mensaje').html('Por favor seleccione  2 opciones de asesor diferentes');
                $('#myModal').modal('show');
                $(document).load();
            }
            return;
        }
        );
        $('#sugerido_2').change(function (event)
        {
            var select1 = $('#sugerido_1 option:selected').val();
            if ($(this).val() === select1)
            {

                $('#mensaje').html('Debe elegir asesores diferentes');
                $('#myModal').modal('show');
            }
            else
            {
                $.post('${pageContext.request.contextPath}/json/asesor/getFoto.json', {idAsesorSugerido: $(this).val()}, function (data)
                {
                    var foto = "<img src='https://sigadocentes.udep.edu.pe/fotossiga/persona/" + data.fotoAsesor + "'" + " width = '100px'/>";
                    $("#img_Asesor_2").html(foto);
                });
            }
        });



        cargarLista();

        var sugerido1 = $('#sugerido_1');
        sugerido1.on('change', function () {
            //app.log( 'cambio ' +  sugerido1.val()); 
            $.post('${pageContext.request.contextPath}/json/asesor/getFoto.json', {idAsesorSugerido: sugerido1.val()}, function (data)
            {
                var foto = "<img src='https://sigadocentes.udep.edu.pe/fotossiga/persona/" + data.fotoAsesor + "'" + " width = '100px'/>";
                $("#img_Asesor_1").html(foto);
            });

        });

        
        
        $('#submit-asesores').click(function(e){
            $.post('${pageContext.request.contextPath}/asesoria/guardarinfoasesor.json'
                    ,{sugerido_1:$('#sugerido_1').val(),sugerido_2:$('#sugerido_2').val()
                    ,idedicionestudio: $('#idedicionestudio').val()}
                    ,function(response){
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


    function cargarLista() {
        var currentPlan = '${currentPlan}';
        $.post('${pageContext.request.contextPath}/json/asesor/sugeridos.json', {idEdicionestudio: currentPlan}, function (data) {

            var aux = "";
            var fotodefault = "";
            for (var i = 0; i < data.sugeridosList.length; i++) {
                fotodefault = data.sugeridosList[i].fotoDefault;
                for (var j = 0; j < data.sugeridosList[i].asesorList.length; j++) {
                    if(data.sugeridosList[i].asesorList[j].id !== '${idAsesor}'){
                        aux = aux + "<option value=" + data.sugeridosList[i].asesorList[j].id + ">" + data.sugeridosList[i].asesorList[j].nombres + "</option>";
                    }
                }
            }
            var foto = "<img src='https://sigadocentes.udep.edu.pe/fotossiga/persona/" + fotodefault + "'" + " width = '100px'/>";
            $("#img_Asesor_1").append(foto);
            $("#sugerido_1").append(aux);
            $("#img_Asesor_2").append(foto);
            $("#sugerido_2").append(aux);
        });
    }


</script>






