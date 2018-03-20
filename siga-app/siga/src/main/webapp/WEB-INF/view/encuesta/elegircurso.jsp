<%-- 
    Document   : elegircurso
    Created on : 14/10/2015, 06:16:38 PM
    Author     : AndySanti
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .panel {
                /*border-color: #DDD; */
                margin-bottom: 20px;
                background-color: #FFF;
                border: 1px solid #DDD;
                border-radius: 4px;
                box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.05);
                padding: 10px;
            }
        </style>
    </head>
    <body>
        <div id="siga-main" >
            <div class="row">
                <div class="span1"></div>
                <div class="span10">
                    <form:form action="${pageContext.request.contextPath}/guardarcurso.htm" method="POST" id="Formencuesta"  acceptCharset="UTF-8">
                        <h2>Encuestas de Cursos de Verano ${estudio} ${campus}</h2>
                        <br>
                        <br>
                        <div class="panel">
                            <p> ¿Esta interesado en cursar alguna Asignatura en Verano 2016?</p>


                            <div class="controls">
                                <label class="radio">
                                <input type="radio" name="eleccion" id="radio1" value="1" checked>
                                SÍ
                                </label>
                                <label class="radio">
                                <input type="radio" name="eleccion" id="radio2" value="0">
                                NO
                                </label>
                            </div>
                        </div>  
                      
                        <div class="row" id="asignaturas">
                            <p id="texto">Seleccione 2 cursos de su interés según prioridad
                            </p>
                            <div class="span5">
                                <c:forEach items="${asignaturas1}" var="asignatura">
                                    <label class="checkbox">
                                        <input type="checkbox" name="elegidos"  value="${asignatura.idasignatura}" />
                                        ${asignatura.nombreasignatura}(${asignatura.sigla})
                                    </label>
                                </c:forEach>

                            </div> 
                            <div class="span5">

                                <c:forEach items="${asignaturas2}" var="asignatura">
                                    <label class="checkbox ">
                                        <input type="checkbox" name="elegidos"  value="${asignatura.idasignatura}" />
                                        ${asignatura.nombreasignatura}(${asignatura.sigla})
                                    </label>
                                </c:forEach>

                            </div>

                        </div>
                        <div class="row">
                            <div class="span5"></div>
                            <div class="span5">
                                <a class="btn " href="javascript:history.back()">Cancelar </a>
                                <button type="button" id="boton" class="btn  btn-primary" >Finalizar </button>
                            </div>
                        </div>
                    </form:form> 

                    <br>
                    <br>
                </div>
                <div class="span1"></div>
            </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#radio1").on("click", function () {
                    $('#asignaturas').removeClass("hidden"); //muestro mediante id
                
                });
                $("#radio2").on("click", function () {
                    $('#asignaturas').addClass("hidden"); //oculto mediante id

                });
                $(':button').on('click', function (event) {
                    var doSubmit = true;

                    if($("#radio1").is(':checked')){
                        var lista = $(":checkbox:checked");
                        if (undefined !== lista && null !== lista && lista.length !== 2) {
                            message = ' Debe elegir solo  2 asignaturas.';
                            doSubmit = false;
                            alert(message);
                        } 
                    }

                    if (doSubmit) {
                       // console.log($('form').serializeArray());
                       $('form').submit();
                    }
                });


            });
        </script>
    </body>
</html>


