<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<h3>${estudioNombre} - Avance de Plan de Estudios: ${planNombre}</h3>
<p>Esta vista muestra el avance del alumno respecto a su plan de estudios vigente 
    y puede diferir de la información registrada en el historial. Para conocer la 
    cantidad de créditos que requiere el alumno para egresar consulte la pestaña "Indicadores".</p>
<h4>Algunas definiciones (extraídas del RFAG ítem IX, artículo 82)</h4>
<ul>
    <li>
        Asignaturas iguales: Son aquellas asignaturas que, independientemente del programa académico al 
        que pertenezcan, son definidas como iguales debido a que se ha establecido que poseen el mismo sílabo.
    </li>
    <li>
        Asignaturas equivalentes (Q): Son aquellas asignaturas para las cuales se ha establecido una 
        relación de contenido similar, tras haber realizado el estudio de convalidaciones correspondiente.
    </li>
</ul>
<br/>
<c:if test="${fn:length(obligatoria['list']) > 0}">
<table class="mallaTabla tablaListaAsignatura">
    <tr class="mallaEncabezado">
        <th colspan="8" style="text-align: left;">Asignaturas obligatorias</th>
    </tr>
    <tr style="border:1px solid #C0C0C0;">
        <th>Nº</th>
        <th>Sigla</th>
        <th>Nombre</th>
        <th>Créditos</th>
        <th>Nota</th>
        <th>Reglas de reconocimiento</th>
        <th>Observaciones</th>
        <th>Estado</th>
    </tr>
    <c:forEach items="${obligatoria['list']}" var="item" varStatus="status" >
        <tr>
            <td>${status.index + 1}</td>
            <td>${item.sigla}</td>
            <td>${item.nombre}</td>
            <td style="text-align: center;">${item.creditos}</td>
            <td style="text-align: center;">${item.nota}</td>
            <td>${item.regla}</td>
            <td>${item.observacion}</td>
            <td style="background-color: ${item.color}; text-align: center;">${item.estado}</td>
        </tr>
    </c:forEach>
</table>
<br/>
<table class="mallaTabla tableResumenTipoAsignatura" style="margin-bottom: 15px;">
    <tr class="mallaEncabezado">
        <th style="text-align: left;" colspan="3">CRÉDITOS OBLIGATORIOS: Resumen</th>
    </tr>
    <tr>
        <td rowspan="5">Créditos del Plan:<br/>${obligatoria['credCumplidos']}/${obligatoria['credRequeridos']}            
            <c:if test="${(otros['obligatoria']) != '0'}">
                <br/><br/>Créditos de Otros Planes: ${otros['obligatoria']}
            </c:if> 
        </td>        
        <td>
            <div style="width: 15px; height: 15px; background-color: #9cd08b; float: left; margin-right: 7px;"></div>
            Créditos cumplidos:
        </td>
        <td style="text-align: right;">${obligatoria['credCumplidos']}</td>
    </tr>
    <tr>
        <td>
            <div style="width: 15px; height: 15px; background-color: #faef94; float: left; margin-right: 7px;"></div>
            Créditos llevados actualmente:
        </td>
        <td style="text-align: right;">${obligatoria['credLlevados']}</td>
    </tr>
    <tr>
        <td>
            <div style="width: 15px; height: 15px; background-color: #f15861; float: left; margin-right: 7px;"></div>
            Créditos desaprobados:
        </td>
        <td style="text-align: right;">${obligatoria['credDesaprobados']}</td>
    </tr>
    <tr>
        <td>
            <div style="width: 15px; height: 15px; background-color: #bbbbbb; float: left; margin-right: 7px;"></div>
            Créditos no llevados:
        </td>
        <td style="text-align: right;">${obligatoria['credNoLlevados']}</td>
    </tr>
    <tr>
        <td>
            <div style="width: 15px; height: 15px; background-color: #fbad56; float: left; margin-right: 7px;"></div>
            Créditos exonerados:
        </td>
        <td style="text-align: right;">${obligatoria['credExonerados']}</td>
    </tr>
</table>
<br/>
</c:if>
<c:if test="${fn:length(electiva['list']) > 0}">
<table class="mallaTabla tablaListaAsignatura">
    <tr class="mallaEncabezado">
        <th colspan="8" style="text-align: left;">Asignaturas electivas</th>
    </tr>
    <tr style="border:1px solid #C0C0C0;">
        <th>Nº</th>
        <th>Sigla</th>
        <th>Nombre</th>
        <th>Créditos</th>
        <th>Nota</th>
        <th>Reglas de reconocimiento</th>
        <th>Observaciones</th>
        <th>Estado</th>
    </tr>
    <c:forEach items="${electiva['list']}" var="item" varStatus="status" >
        <tr>
            <td>${status.index + 1}</td>
            <td>${item.sigla}</td>
            <td>${item.nombre}</td>
            <td style="text-align: center;">${item.creditos}</td>
            <td style="text-align: center;">${item.nota}</td>
            <td>${item.regla}</td>
            <td>${item.observacion}</td>
            <td style="background-color: ${item.color}; text-align: center;">${item.estado}</td>
        </tr>
    </c:forEach>
</table>
<br/>
<table class="mallaTabla tableResumenTipoAsignatura" style="margin-bottom: 15px;">
    <tr class="mallaEncabezado">
        <th style="text-align: left;" colspan="3">CRÉDITOS ALTERNATIVOS Resumen</th>
    </tr>
    <tr>
        <td rowspan="4">Créditos del Plan:<br/>${electiva['credCumplidos']}/${electiva['credRequeridos']}
            <c:if test="${(otros['electiva']) != '0'}">
                <br/>Créditos de Otros Planes: ${otros['electiva']}
            </c:if> 
            <br/>
            Créditos ofrecidos<br/>en el Plan: ${electiva['credOfrecidos']}
        </td>        
        <td>
            <div style="width: 15px; height: 15px; background-color: #9cd08b; float: left; margin-right: 7px;"></div>
            Créditos cumplidos:
        </td>
        <td style="text-align: right;">${electiva['credCumplidos']}</td>
    </tr>
    <tr>
        <td>
            <div style="width: 15px; height: 15px; background-color: #faef94; float: left; margin-right: 7px;"></div>
            Créditos llevados actualmente:
        </td>
        <td style="text-align: right;">${electiva['credLlevados']}</td>
    </tr>
    <tr>
        <td>
            <div style="width: 15px; height: 15px; background-color: #f15861; float: left; margin-right: 7px;"></div>
            Créditos desaprobados:
        </td>
        <td style="text-align: right;">${electiva['credDesaprobados']}</td>
    </tr>
    <tr>
        <td>
            <div style="width: 15px; height: 15px; background-color: #bbbbbb; float: left; margin-right: 7px;"></div>
            Créditos no llevados:
        </td>
        <td style="text-align: right;">${electiva['credNoLlevados']}</td>
    </tr>    
</table>
<br/>
</c:if>
<c:if test="${fn:length(libre['list']) > 0}">
<table class="mallaTabla tablaListaAsignatura">
    <tr class="mallaEncabezado">
        <th colspan="8" style="text-align: left;">Asignaturas libres</th>
    </tr>
    <tr style="border:1px solid #C0C0C0;">
        <th>Nº</th>
        <th>Sigla</th>
        <th>Nombre</th>
        <th>Créditos</th>
        <th>Nota</th>
        <th>Tipo de reconocimiento</th>
        <th>Observaciones</th>
        <th>Estado</th>
    </tr>
    <c:forEach items="${libre['list']}" var="item" varStatus="status" >
        <tr>
            <td>${status.index + 1}</td>
            <td>${item.sigla}</td>
            <td>${item.nombre}</td>
            <td style="text-align: center;">${item.creditos}</td>
            <td style="text-align: center;">${item.nota}</td>
            <td>${item.regla}</td>
            <td>${item.observacion}</td>
            <td style="background-color: ${item.color}; text-align: center;">${item.estado}</td>
        </tr>
    </c:forEach>
</table>
<br/>
</c:if>
<table class="mallaTabla tablaListaAsignatura">
    <tr class="mallaEncabezado">
        <th colspan="4" style="text-align: left;">Total de créditos aprobados</th>
    </tr>
    <tr style="border:1px solid #C0C0C0;">
        <th>Tipos</th>
        <th>Créditos cumplidos del plan</th>    
        <th>Créditos cumplidos de otros planes</th> 
        <th>Total</th> 
    </tr>
    <tr>
        <td>Obligatorios</td>
        <td style="text-align: right; padding-right: 20px">${obligatoria['credCumplidos']}</td>
        <td style="text-align: right; padding-right: 20px">${otros['obligatoria']}</td>
        <td style="text-align: right; padding-right: 20px">${obligatoria['credCumplidos'] + otros['obligatoria']}</td>
    </tr>
    <tr>
        <td>Electivos</td>
        <td style="text-align: right; padding-right: 20px">${electiva['credCumplidos']}</td>
        <td style="text-align: right; padding-right: 20px">${otros['electiva']}</td>
        <td style="text-align: right; padding-right: 20px">${electiva['credCumplidos'] + otros['electiva']}</td>
    </tr>
    <tr>
        <td>Libres</td>
        <td style="text-align: right; padding-right: 20px">${libre['credCumplidos']}</td>
        <td style="text-align: right; padding-right: 20px">${otros['libre']}</td>
        <td style="text-align: right; padding-right: 20px">${libre['credCumplidos'] + otros['libre']}</td>
    </tr>
    <tr style="border:1px solid #C0C0C0;">
        <th>Total</th>
        <th style="text-align: right; padding-right: 20px">${obligatoria['credCumplidos'] + electiva['credCumplidos'] + libre['credCumplidos']}</th>
        <th style="text-align: right; padding-right: 20px">${otros['obligatoria'] + otros['electiva'] + otros['libre']}</th>
        <th style="text-align: right; padding-right: 20px">${obligatoria['credCumplidos'] + electiva['credCumplidos'] + libre['credCumplidos'] 
                                                             + otros['obligatoria'] + otros['electiva'] + otros['libre']}</th>        
    </tr>
</table>
<script type="text/javascript">
    $(document).ready(function(){
        $('div.main-layout').removeClass( 'login' );        
    });
</script>