<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${fn:length(mensaje) > 0}">
    <div class="alert alert-success">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>Bien!</strong> ${mensaje}
    </div>
</c:if>

<h3 id="titulo"></h3>
<table id="idMalla" class="mallaTabla" cellspacing="0" cellpadding="0" border="0">
    <thead></thead>
    <tbody></tbody>
</table>
<br/>
<table class="mallaTabla" cellpadding="5" cellspacing="0" align="center">
    <tr>
        <td rowspan="2" width="25%" style="vertical-align: middle;">
            <b>Leyenda de colores del plan de estudios</b>
        </td>
        <td style="text-align:left;">
            <div style="width: 20px; height: 20px; background-color: #5AB336;float:left;margin-right:8px;"></div>
            Aprobada / Convalidada</td>
        <td style="text-align:left;">
            <div style="width: 20px; height: 20px; background-color: red;float:left;margin-right:8px;"></div>
            Desaprobada</td>
        <td style="text-align:left;">
            <div style="width: 20px; height: 20px; background-color: #9E872B;float:left;margin-right:8px;"></div>
            Exonerada</td>
    </tr>
    <tr>
        <td style="text-align:left;">
            <div style="width: 20px; height: 20px; background-color: #fff703;float:left;margin-right:8px;"></div>
            Matriculada</td>
        <td style="text-align:left;">
            <div style="width: 20px; height: 20px; background-color: #AAAAAA;float:left;margin-right:8px;"></div>
            No cursada</td>
        <td style="text-align:left;">
            <div style="width: 20px; height: 20px; background-color: #385084;float:left;margin-right:8px;"></div>
            Puede llevar</td>
    </tr>
</table>

<script type="text/javascript">
    $(document).ready(function () {
        $('div.main-layout').removeClass('login');
        cargarMalla();
    });

    function cargarMalla() {
        var idEdicionestudio = '${param.id}';
        $.post('${pageContext.request.contextPath}/planestudio/cargarMalla.json',
                {idEdicionestudio: idEdicionestudio}, function (response) {
            $("#titulo").text(response.estudioNombre + ' - Plan de Estudios: ' + response.planNombre);
            $("#idMalla tbody").empty();
            $("#idMalla thead").empty();
            var filaNivel = "<tr><th class='mallaEncabezado primeraColumna' rowspan='2'>Área \\ Ciclo</th>";
            var filaCiclo = "<tr>";
            for (var x = 1; x <= response.numPeriodos; x++) {
                if (x % 2 == 0) {
                    filaNivel = filaNivel + "<th colspan='2' class='tabla_encabezado mallaEncabezado'>Nivel " + (x / 2) + "</th>";
                }
                filaCiclo = filaCiclo + "<th class='mallaEncabezado'>" + getRomanos(x) + "</th>";
            }
            filaNivel = filaNivel + "</tr>";
            filaCiclo = filaCiclo + "</tr>";
            $("#idMalla thead").append(filaNivel);
            $("#idMalla thead").append(filaCiclo);

            for (var i = 0; i < response.areaConocimientosList.length; i++) {
                var filaTB = "<tr><td style='background:#CCCCCC;' class='areaConocimientoCelda'>"
                        + response.areaConocimientosList[i].nombre + "</td>";

                for (var j = 1; j <= response.numPeriodos; j++) {

                    var molde = "<td align='center' style='padding-bottom: 12px;'>";
                    for (var v = 0; v < response.areaConocimientosList[i].planEstudioAsignaturaList.length; v++) {
                        if (response.areaConocimientosList[i].planEstudioAsignaturaList[v].numPeriodo == j) {
                            var estiloNumLetra = "style='";
                            if (response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla.length > 5) {
                                estiloNumLetra = "style='font-size:9px;";
                            }

                            var divConten = "";
                            var estado = response.areaConocimientosList[i].planEstudioAsignaturaList[v].estadoEnMalla;
                            divConten = "<span " + estiloNumLetra + "' >" + response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla + "</span>";

                            molde = molde + "<br><div id='id_" +
                                    response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id +
                                    "' class='" + defineNombreClase(estado) + "' onmouseover=\"TagToTip('id_"
                                    + response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id + "_" +
                                    response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla
                                    + "',WIDTH,200);\" onmouseout='UnTip();'  ";

                            if (response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.desdeEspecialidad) {
                                molde = molde + " style='border:2px solid #2193ea'";
                            }

                            molde = molde + "> <input type='hidden' id='claseAnt_" + response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id + "' name='claseAnt' value='" + defineNombreClase(estado) + "'>" + divConten + "</div>"

                                    + armaToolTipCurso(response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.id,
                                            response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.sigla,
                                            response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.nombre,
                                            response.areaConocimientosList[i].planEstudioAsignaturaList[v].tipoAsignatura,
                                            response.areaConocimientosList[i].planEstudioAsignaturaList[v].asignatura.creditos,
                                            j, response.areaConocimientosList[i].planEstudioAsignaturaList[v].requisitosList, estado,
                                            response.areaConocimientosList[i].planEstudioAsignaturaList[v].notaHistorial);

                        }
                    }
                    molde = molde + "</td>";
                    filaTB = filaTB + molde;
                }

                filaTB = filaTB + "</tr>";
                $("#idMalla tbody").append(filaTB);
            }
        });
    }

    function armaToolTipCurso(idCurso, sigla, curso, tipoAsignatura, creditos, numeroCol, requisitosList, estado, nota) {
        var html = "<div class='mallaToolTipDeCurso' style='display:none;' id='id_" + idCurso + "_" + sigla + "'>";
        html = html + "<div style='text-align:center;font-weight: bold; width: 100%;padding-bottom: 12px;clear:both;'><span align='center' style='text-align:center;'> " + sigla + " - " + curso;
        html = html + "</span></div><div style='clear:both;'><div style='float: left; width: 42%; text-align: left;white-space: nowrap;'>Tipo de<br/> asignatura<br/><br/>Cr&eacute;ditos<br/><br/>Ciclo<br/><br/>Nivel</div>";
        html = html + "<div style='float: right; width: 55%;text-align: left;white-space: nowrap;'><b>" + tipoAsignatura.descripcion + "</b><br/><br/><br/>" + creditos;
        html = html + "<br/><br/>" + numeroCol + "<br/><br/>" + Math.round(numeroCol / 2) + "</div></div>";
        if (estado != 5 && estado != 6) {
            var grupoAc = "";
            var divT = "<div><br/><br/>";
            var pso = 1;
            html = html + "<div>";
            for (var x = 0; x < requisitosList.length; x++) {
                if (x == 0) {
                    grupoAc = requisitosList[x].grupo;
                    divT = divT + "<br/><b>Requisito " + pso + "</b><br/>";
                    divT = divT + "&nbsp;&nbsp;&nbsp;" + requisitosList[x].descripcion;
                } else {
                    if (grupoAc != requisitosList[x].grupo) {
                        pso = pso + 1;
                        grupoAc = requisitosList[x].grupo;
                        divT = divT + "<br/><b>Requisito " + pso + "</b><br/>";
                        divT = divT + "&nbsp;&nbsp;&nbsp;" + requisitosList[x].descripcion;
                    } else {
                        divT = divT + "<br/>";
                        divT = divT + "&nbsp;&nbsp;&nbsp;" + requisitosList[x].descripcion;
                    }
                }

            }
            divT = divT + "</div>";
            html = html + divT + "</div>";
        }
        if (estado == 4 || estado == 9) {
            html = html + "<div><b>Curso desaprobado con " + nota + "</b></div>";
        }
        html = html + "</div>";
        return html;
    }
</script>