<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .top-top-top{
        margin-top: 40px;
    }
</style>

<c:if test="${fn:length(mensaje) > 0}">
    <div class="alert alert-error">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>Error!</strong> ${mensaje}
    </div>
</c:if>

<div class="row">
    <div class="span10">
        <h3 id="titulo" class="text-left">${titulo}</h3>
    </div>
    
    <div class="span2">
        <c:if test="${fromlogin}">
            <p class="text-right">
                <a href="./j_spring_security_logout" title="Cerrar sesión" class="btn btn-primary">Cerrar Sesion</a>
            </p>
        </c:if>
    </div>
</div>

<h4><strong>Elige tu especialidad</strong></h4>

<p>
    ${configuracionMensaje}
</p>

<c:set var="action" value="${pageContext.request.contextPath}/planestudio/guardarEspecialidad" />

<c:if test="${fromlogin}">
    <c:set var="action" value="./guardarEspecialidad" />
</c:if>

<form action="${action}" method="POST">
    <input type="hidden" name="idEdicionEstudio" value="${idEdicionEstudio}" />
    <input type="hidden" name="fl" value="${fromlogin}" />
    
    <c:forEach items="${especialidades}" var="especialidad" varStatus="statusesp" >
        <div class="top-top-top">
            
            <label class="radio">
                <input type="radio" name="idEspecialidad" value="${especialidad.idStr}" style="margin-top: 12px;">
                <h3>${especialidad.nombre}</h3>
            </label>
            
            <p>${especialidad.descripcion}</p>

            <div class="malla-especialidad" data-idespecialidad="${especialidad.idStr}">
                <table class="mallaTabla" cellspacing="0" cellpadding="0" border="0">
                    <thead>
                        <tr>
                            <th class="mallaEncabezado primeraColumna" rowspan="2">Área \ Ciclo</th>
                            <c:forEach var="x" begin="1" end="${numPeriodos}">
                                <c:if test="${x%2 == 0}">
                                    <th colspan="2" class="tabla_encabezado mallaEncabezado">
                                        Nivel <fmt:formatNumber type="NUMBER" maxFractionDigits="0" value="${x/2}" />
                                    </th>
                                </c:if>
                            </c:forEach>
                        </tr>
                        <tr class="nivel-romanos"></tr>
                    </thead>

                    <tbody></tbody>
                </table>
            </div>
        </div>
    </c:forEach>
    
    <br/>
    <br/>
    <div class="row">
        <div class="span2">
            <button type="submit" class="btn btn-primary">Registrar especialidad</button>
        </div>
        <div class="span10">
            <c:if test="${!fromlogin}">
                <!--<a class="btn" href="javascript:history.back()">Decidir más tarde</a>-->
                <div class="alert alert-info">
                    Si desea decidir en otro momento por favor cierre la pestaña actual.
                </div>
            </c:if>
        </div>
    </div>
</form>
  
    
<div id="siga-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="siga-modal-title" aria-hidden="true">
</div>
    
<script id="modal-template" type="text/x-handlebars-template">
    <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 id="siga-modal-title">{{asignatura.sigla}} - {{asignatura.nombre}}</h3>
            <h4>Detalles</h4>
    </div>
    <div class="modal-body">
        <table>
            <tbody>
                <tr>
                    <td class="span12" colspan="2">{{asignatura.descripcion}}</td>
                </tr>
                <tr>
                    <td class="span3"><strong>Tipo de asignatura</strong></td>
                    <td class="span9">{{tipoAsignatura.descripcion}}</td>
                </tr>
                <tr>
                    <td><strong>Créditos</strong></td>
                    <td>{{asignatura.creditos}}</td>
                </tr>
                <tr>
                    <td><strong>Ciclo</strong></td>
                    <td>{{numPeriodo}}</td>
                </tr>
                <tr>
                    <td><strong>Nivel</strong></td>
                    <td>{{nivel}}</td>
                </tr>
                
                {{#if gruposr}}
                    <tr>
                        <td colspan="2"><strong>Requisitos</strong></td>
                    </tr>

                    {{#each gruposr as |grupor index|}}
                        <tr>
                            <td>{{grupor.titulo}}</td>
                            <td>
                                {{#each grupor.descripcion as |descrip index|}}
                                    <p>{{descrip}}</p>
                                {{/each}}
                            </td>
                        <tr>
                    {{/each}}
                {{/if}}
        
                
            </tbody>
        <table>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
    </div>
</script>
    
<script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/handlebars-v3.0.3.js"></script>
    
<script type="text/javascript">
    $(document).ready(function(){
        $('div.main-layout').removeClass('login');
        
        $('.nivel-romanos').each(function(){
            var html = '';
            <c:forEach var="x" begin="1" end="${numPeriodos}">
                html += '<th class="mallaEncabezado">' + getRomanos(${x}) + '</th>';
            </c:forEach>
            $(this).html(html);
        });
        
        $('.malla-especialidad').each(function (){
            var idEspecialidad = $(this).data('idespecialidad');
            cargarMallaEspecialidad(idEspecialidad, this);
        });
                
        $('body').on('click', '.asignatura', function (){
            var idAsignatura        = $(this).data('idasignatura');
            var idEspecialidad      = $(this).closest('.malla-especialidad').data('idespecialidad');
            var idAreaConocimiento  = $(this).closest('tr').find(':input[name=idAreaConocimiento]').val();            
            var source              = $("#modal-template").html();
            var template            = Handlebars.compile(source);
            var results             = $("#siga-modal");
            
            $.post(
                '/siga/planestudio/cargarDataAsignatura.json', 
                { idEdicionEstudio : '${idEdicionEstudio}', idPlanEstudio : '${idPlanEstudio}', idEspecialidad : idEspecialidad, idAreaConocimiento: idAreaConocimiento, idAsignatura : idAsignatura },
                function( response ) {
                    if( response.data.requisitosList !== null && response.data.requisitosList.length > 0 ){
                        response.data.gruposr = gruposRequisitos(response.data.requisitosList);
                        delete(response.data.requisitosList);
                    }
                    results.html(template(response.data));
                }
            );
        });
    });
    
    function gruposRequisitos(requisitosList){
        var grupoAc = "";
        var count = 1;
        var count2 = 0;
        var gruposr = [];
        var grupo = {};
        
        for(var i = 0; i < requisitosList.length + 1; i++){
            if( i === 0 ){
                grupoAc = requisitosList[i].grupo;
                                
                grupo = {};
                grupo.nombre = grupoAc;
                grupo.titulo = "Requisito " + count;
                grupo.descripcion = [];
                grupo.descripcion[count2] = requisitosList[i].descripcion;
                
                if( requisitosList.length === 1 ){
                    gruposr.push(grupo);
                    break;
                }
                
                count++;
                count2++;
            }else{
                if( requisitosList[i] === undefined ){
                    gruposr.push(grupo);
                }
                else if( grupoAc !== requisitosList[i].grupo ){                    
                    gruposr.push(grupo);
                    
                    grupo = {};
                    grupo.nombre = grupoAc;
                    grupo.titulo = "Requisito " + count;
                    grupo.descripcion = [];
                    grupo.descripcion[count2] = requisitosList[i].descripcion;

                    count++;
                    count2 = 0;
                }else{
                    grupo.descripcion[count2] = requisitosList[i].descripcion;
                    count2++;
                }
            }
        }
        
        return gruposr;
    }
    
    function cargarMallaEspecialidad(idEspecialidad, div){
        var idEdicionEstudio    = '${idEdicionEstudio}';
        var idPlanEstudio       = '${idPlanEstudio}';
                
        $.post( '/siga/planestudio/cargarMallaEspecialidad.json', 
                {idEdicionEstudio : idEdicionEstudio, idPlanEstudio : idPlanEstudio, idEspecialidad : idEspecialidad}, 
                function( response ) {
            
            if( response.areaConocimientosList !== null ){
                for(var i = 0 ; i < response.areaConocimientosList.length ; i++){
                    var filaTB = "<tr><td style='background:#CCCCCC; width: 120px;' class='areaConocimientoCelda'>" + 
                                 "<input type='hidden' name='idAreaConocimiento' value='"+ response.areaConocimientosList[i].id +"'>" +
                                 response.areaConocimientosList[i].nombre + "</td>";

                    for(var j=1 ; j <= ${numPeriodos} ; j++ ){

                        var molde = "<td align='center' style='padding-bottom: 12px; width: 77px;'>";
                         for(var v=0 ; v < response.areaConocimientosList[i].planEstudioAsignaturaList.length ; v++ ){
                             if(response.areaConocimientosList[i].planEstudioAsignaturaList[v].numPeriodo === j){
                                 var estiloNumLetra = "style='";
                                 if(response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla.length > 5){
                                     estiloNumLetra = "style='font-size:9px;";
                                 }

                                 var divConten = "";
                                 var estado = response.areaConocimientosList[i].planEstudioAsignaturaList[v].estadoEnMalla;
                                 divConten = "<a href='#siga-modal' data-toggle='modal' class='asignatura' data-idasignatura='"+response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id+"' style='color:#FFF; display:block;'><span "+estiloNumLetra+"' >"+cortaSiglaDeLinkMalla(response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla)+"</span></a>";

                                 molde = molde + "<br><div id='id_"+
                                     response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id +
                                     "' class='" + defineNombreClase(estado) + "' onmouseover=\"TagToTip('id_"
                                     + response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id + "_" +
                                     response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla
                                     + "',WIDTH,200);\" onmouseout='UnTip();'> <input type='hidden' id='claseAnt_"+response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id+"' name='claseAnt' value='"+defineNombreClase(estado)+"'>"+ divConten + "</div>"
                                     + armaToolTipCurso(response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id,
                                                       response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla,
                                                       response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.nombre,
                                                       response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.descripcion,
                                                       response.areaConocimientosList[i].planEstudioAsignaturaList[v].tipoAsignatura,
                                                       response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.creditos,
                                                       j,response.areaConocimientosList[i].planEstudioAsignaturaList[v].requisitosList,estado,
                                                       response.areaConocimientosList[i].planEstudioAsignaturaList[v].notaHistorial);

                             }
                         }
                        molde = molde + "</td>";
                        filaTB = filaTB + molde;
                    }

                    filaTB = filaTB + "</tr>";
                    $(div).find('table').find('tbody').append(filaTB);
                }
            }
        });        
    }
    
    function armaToolTipCurso(idCurso, sigla, curso, descripcion, tipoAsignatura, creditos, numeroCol, requisitosList, estado, nota){
        var html = "<div class='mallaToolTipDeCurso' style='display:none;' id='id_" + idCurso + "_" + sigla + "'>";
        html = html + "<div style='text-align:center;font-weight: bold; width: 100%;padding-bottom: 12px;clear:both;'><span align='center' style='text-align:center;'> " + sigla + " - " + curso;
        html = html + "</span></div><div style='clear:both;'><div style='float: left; width: 42%; text-align: left;white-space: nowrap;'>Tipo de<br/> asignatura<br/><br/>Cr&eacute;ditos<br/><br/>Ciclo<br/><br/>Nivel</div>";
        html = html + "<div style='float: right; width: 55%;text-align: left;white-space: nowrap;'><b>" + tipoAsignatura.descripcion + "</b><br/><br/><br/>" + creditos;
        html = html + "<br/><br/>" + numeroCol + "<br/><br/>" + Math.round(numeroCol/2) + "</div></div>";
        if(estado !== 5 && estado !== 6){
            var grupoAc = "";
            var divT = "<div><br/><br/>";
            var pso = 1;
            html = html + "<div>";
            for(var x = 0 ; x < requisitosList.length ; x++){
                if( x === 0){
                    grupoAc = requisitosList[x].grupo;
                    divT = divT + "<br/><b>Requisito " + pso + "</b><br/>";
                    divT = divT + "&nbsp;&nbsp;&nbsp;" +requisitosList[x].descripcion;
                }else{
                    if( grupoAc !== requisitosList[x].grupo){
                        pso = pso + 1;
                        grupoAc = requisitosList[x].grupo;
                        divT = divT + "<br/><b>Requisito " + pso + "</b><br/>";
                        divT = divT + "&nbsp;&nbsp;&nbsp;" +requisitosList[x].descripcion;
                    }else{
                        divT = divT + "<br/>";
                        divT = divT + "&nbsp;&nbsp;&nbsp;" + requisitosList[x].descripcion;
                    }
                }
            }
            divT = divT + "</div>";
            html = html + divT + "</div>";
        }
        if(estado === 4 || estado === 9){
            html = html + "<div><b>Curso desaprobado con " + nota + "</b></div>";
        }
        html = html + "<div style='margin-top: 10px;'><p>Para más detalles, de click en la asignatura.</p></div> </div>";
        return html;
    }
</script>