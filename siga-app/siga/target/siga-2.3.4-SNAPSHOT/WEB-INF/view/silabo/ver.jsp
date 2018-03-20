<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<center><h3>SÍLABO DE ASIGNATURA <br/>
        ${silabo.asignaturaDictada.nombre} (${silabo.asignaturaDictada.asignaturaSeccion.nombreSeccion})
    </h3>

    <div style="position: relative; width: 197px;">
        <ul class="nav nav-pills">  
            <li id="opt0" class="hidden" onclick="optionsMenuHojas(this.id, 1);
                    return false;"><a href="#"><i class="icon-chevron-sign-left icon-2x"></i></a></li>
            <li id="opt1" class="active" onclick="optionsMenuHojas(this.id, 2);
                    return false;"><a href="#"><strong>1</strong></a></li>
            <li id="opt2" onclick="optionsMenuHojas(this.id, 3);
                    return false;"><a href="#"><strong>2</strong></a></li>
            <li id="opt3" onclick="optionsMenuHojas(this.id, 4);
                    return false;"><a href="#"><strong>3</strong></a></li>
            <li id="opt4" onclick="optionsMenuHojas(this.id, 5);
                    return false;"><a href="#"><i class="icon-chevron-sign-right icon-2x"></i></a></li>
        </ul>
    </div>

    <a href="./descargaSilaboToPDF.htm?id=${param.id}&idee=${param.idee}" target="_blank" 
       rel="tooltip" data-toggle="tooltip" title="Descargar" class="btn btn-mini" style="float:right;">
        Descargar en PDF
        <i class="icon-download-alt"></i>
    </a>
</center>
<div id="page1">
    <p class="head">Datos informativos</p>
    <ul class="map-list">
        <li>
            <strong class="head">Asignatura:</strong>
            ${silabo.asignaturaDictada.nombre}
        </li>
        <li>
            <strong class="head">Sección:</strong>
            ${silabo.asignaturaDictada.asignaturaSeccion.nombreSeccion}
        </li>
        <li>
            <strong class="head">Sigla:</strong>
            ${silabo.asignaturaDictada.sigla}
        </li>
        <li>
            <strong class="head">N° créditos:</strong>
            ${silabo.asignaturaDictada.creditos}
        </li>
        <li>
            <strong class="head">Semestre:</strong>
            ${silabo.asignaturaDictada.periodoAcademico.nombre}
        </li>
        <li>
            <strong class="head">Tipo de asignatura:</strong>
            ${silabo.asignaturaDictada.tipoAsignatura}
        </li>
        <li>
            <strong class="head">Profesor(es):</strong>
            <c:forEach items="${silabo.asignaturaDictada.profesorList}" var="item" varStatus="status">
                <c:choose>
                    <c:when test="${status.last}">
                        ${item.nombres} ${item.apellidoPaterno} 
                    </c:when>
                    <c:otherwise>
                        ${item.nombres} ${item.apellidoPaterno} /
                    </c:otherwise>
                </c:choose>
            </c:forEach>

        </li>
    </ul>
    <p class="head">Sumilla</p>
    <p>${silabo.sumilla}</p>
    <p class="head">Fundamentación</p>
    <p>${silabo.fundamentacion}</p>
    <p class="head">Objetivos</p>
    <ul class="map-list">
        <c:forEach items="${silabo.objetivoList}" var="item" varStatus="status">    
            <li>
                <i class="icon-bookmark"></i>
                ${item.descripcion}
            </li>
        </c:forEach>
    </ul>
</div>
<div id="page2" class="hidden">
    <p class="head">PROGRAMACIÓN DE CONTENIDOS</p>
    <div style="padding-left: 25px;">        
        <c:forEach items="${silabo.unidadList}" var="unidad">
            <ul class="sublist-blue"><li><strong>${unidad.numero} ${unidad.descripcion}</strong></li></ul>
            <c:if test="${fn:length(unidad.temaList) > 0}">
            <table class="table table-condensed">
                <thead>
                    <tr>
                        <th>N°</th>
                        <th style="width:50%;">Tema</th>
                        <th>Semana</th>
                        <th>Fecha de la sesión</th>
                        <th>Horas de <br/>sesiones teóricas</th>
                        <th>Horas de <br/>sesiones práctica</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${unidad.temaList}" var="tema">
                    <tr>
                        <td>${tema.numero}</td>
                        <td>${tema.tema}</td>
                        <td>${tema.semana}</td>
                        <td>
                            <c:forEach items="${tema.fechasSesion}" var="fecha" varStatus="status">
                                <c:choose>
                                    <c:when test="${status.last}">
                                        <fmt:formatDate value="${fecha}" pattern="dd-MMM-yyyy" />
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:formatDate value="${fecha}" pattern="dd-MMM-yyyy" /> <br/>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </td>
                        <td>${tema.horasTeoricas}</td>
                        <td>${tema.horasPracticas}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </c:if>
            <c:if test="${fn:length(unidad.temaList) == 0}">
                <p class="alert">Aún no hay temas registrados.</p>
            </c:if>
        </c:forEach>        
    </div>
</div>
<div id="page3" class="hidden">
    <p class="head">ESTRATEGIAS METODOLÓGICAS</p>
    <div style="padding-left: 25px;">
        <ul class="map-list">
        <c:forEach items="${silabo.estrategiaList}" var="estrategia">
            <li>
                <i class="icon-bookmark"></i>
                ${estrategia.titulo}
            </li>
        </c:forEach>
        </ul>
    </div>
    <p class="head">EVALUACIÓN</p>
    <div id="popup-temas" style="padding-left: 25px;">
        <ul class="sublist-blue"><li><strong>Descripción</strong></li></ul>
        <p>${silabo.descripcionEvaluacion}</p>
        <ul class="sublist-blue"><li><strong>Formas de evaluación</strong></li></ul>
        <c:if test="${fn:length(silabo.tipoEvaluacionList) > 0}">
            <table class="table table-condensed">
                <thead>
                    <tr>
                        <th>N°</th>
                        <th>Descripción</th>
                        <th>Tipo evaluación</th>
                        <th>Peso</th>
                        <th>Anulable</th>
                        <th>Fecha</th>
                        <th>Temas a <br/>evaluar</th>
                    </tr>
                </thead>
                <tbody>
                <c:set var="indexEval" value="${0}"></c:set>
                <c:forEach items="${silabo.tipoEvaluacionList}" var="tipo">
                    <c:forEach items="${tipo.evaluacionList}" var="item">
                        <c:set var="indexEval" value="${indexEval + 1}"></c:set>
                    <tr>
                        <td>${indexEval}</td>
                        <td>${item.nombre}</td>
                        <td>${tipo.nombre}</td>
                        <td>${item.peso}</td>
                        <td>${item.anulable? 'Sí' : 'No'}</td>
                        <td><fmt:formatDate value="${item.fechaEvaluacion}" pattern="dd-MMM-yyyy" /></td>
                        <td>
                            <button data-id="${item.idRandom}" title="Temas" class="btn btn-link btn-link-inline open-modal"><i class="icon-search"></i></button>
                        </td>
                    </tr>
                    </c:forEach>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
    
    <p class="head">BIBLIOGRAFÍA</p>
    <div style="padding-left: 25px;">
        <ul class="sublist-blue"><li><strong>Bibliografía básica</strong></li></ul>
        <ul class="map-list">
        <c:forEach items="${silabo.bibliografiaBasica}" var="item">
            <li>
                <i class="icon-bookmark"></i>
                ${item}
            </li>
        </c:forEach>
        </ul>
        <ul class="sublist-blue"><li><strong>Bibliografía especializada</strong></li></ul>
        <ul class="map-list">
        <c:forEach items="${silabo.bibliografiaavanzada}" var="item">
            <li>
                <i class="icon-bookmark"></i>
                ${item}
            </li>
        </c:forEach>
        </ul>
    </div>
</div>    
<center>
    <div style="position: relative; width: 197px;">
        <ul class="nav nav-pills">  
            <li id="opt00" class="hidden" onclick="optionsMenuHojas(this.id, 1);
                    return false;"><a href="#"><i class="icon-chevron-sign-left icon-2x"></i></a></li>
            <li id="opt10" class="active" onclick="optionsMenuHojas(this.id, 2);
                    return false;"><a href="#"><strong>1</strong></a></li>
            <li id="opt20" onclick="optionsMenuHojas(this.id, 3);
                    return false;"><a href="#"><strong>2</strong></a></li>
            <li id="opt30" onclick="optionsMenuHojas(this.id, 4);
                    return false;"><a href="#"><strong>3</strong></a></li>
            <li id="opt40" onclick="optionsMenuHojas(this.id, 5);
                    return false;"><a href="#"><i class="icon-chevron-sign-right icon-2x"></i></a></li>
        </ul>
    </div>
</center>
<div id="popupAll">
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $('div.main-layout').removeClass('login');

        var $inner = $('#popup-temas');
        $inner.on( 'click', '.open-modal', function() {
            var id = $(this).data( 'id' );
            var templateUrl = '../templates/evaluaciones-silabo.tpl';
            $.ajax({
                url: templateUrl,
                type: "GET",
                dataType: "html",
                cache: true
            }).done(function( html ) {
                $("#popupAll").append(html);
                var jsonUrl = '../json/asignatura/evaluaciones-temas.json';
                $.post(jsonUrl, {idEvaluacion : id}, function( response ) {
                    $('#siga-modal-title').html(response.asignatura);
                    $('#siga-modal-subtitle').html(response.evaluacion);
                    var bodyModal = '';
                    if( response.unidades.length > 0 ) {
                        bodyModal = '<ul>';
                        response.unidades.forEach( function( unidad ) {
                            bodyModal += '<li><h4>' + unidad.descripcion + '</h4>';
                            if(unidad.temaList.length > 0) {
                                bodyModal += '<ul>';
                                unidad.temaList.forEach( function(tema) {
                                    bodyModal += '<li>' + tema.tema + '</li>';
                                });
                                bodyModal += '</ul>';
                            }
                            bodyModal += '</li>';
                        });
                        bodyModal += '</ul>';
                    } else {
                        bodyModal = '<p class="alert">No hay temas agregados.</p>';
                    }
                    $('#siga-modal .modal-body').html(bodyModal);

                    $('#siga-modal').modal().on('hidden', function() {
                        $(this).remove();
                    });
                });
            });
        });
    });

    function optionsMenuHojas(id, opt) {
        if (opt === 1 || opt === 5) {
            var itemSelect = $('ul li.active')[0].id;
            if (itemSelect === 'opt1' || itemSelect === 'opt10') {
                if (opt === 1) {
                    opt = 2;
                    id = 'opt1';
                } else {
                    opt = 3;
                    id = 'opt2';
                }
            } else if (itemSelect === 'opt2' || itemSelect === 'opt20') {
                if (opt === 1) {
                    opt = 2;
                    id = 'opt1';
                } else {
                    opt = 4;
                    id = 'opt3';
                }
            } else if (itemSelect === 'opt3' || itemSelect === 'opt30') {
                if (opt === 1) {
                    opt = 3;
                    id = 'opt2';
                } else {
                    opt = 4;
                    id = 'opt3';
                }
            }
        }
        $('ul li').removeClass('active');
        $('#' + id).addClass('active');
        $('#' + id + '0').addClass('active');
        if (opt === 2) {
            $('#page1').removeClass('hidden');
            $('#page2').addClass('hidden');
            $('#page3').addClass('hidden');

            $('#opt0').addClass('hidden');
            $('#opt00').addClass('hidden');
            $('#opt4').removeClass('hidden');
            $('#opt40').removeClass('hidden');
        } else if (opt === 3) {
            $('#page1').addClass('hidden');
            $('#page2').removeClass('hidden');
            $('#page3').addClass('hidden');

            $('#opt0').removeClass('hidden');
            $('#opt00').removeClass('hidden');
            $('#opt4').removeClass('hidden');
            $('#opt40').removeClass('hidden');
        } else if (opt === 4) {
            $('#page1').addClass('hidden');
            $('#page2').addClass('hidden');
            $('#page3').removeClass('hidden');

            $('#opt0').removeClass('hidden');
            $('#opt00').removeClass('hidden');
            $('#opt4').addClass('hidden');
            $('#opt40').addClass('hidden');
        }
    }
</script>