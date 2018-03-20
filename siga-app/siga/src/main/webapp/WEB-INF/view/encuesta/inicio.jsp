<%-- 
    Document   : inicio
    Created on : 14/10/2015, 05:28:04 PM
    Author     : AndySanti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <div id="siga-main" >
     <div>
         <center>
     <h2>Encuestas de Cursos de Verano ${estudio} ${campus}</h2>
     <br>
     <br>
     <p>Esta es una encuesta para conocer las preferencias de los alumnos en relación a cursar asignaturas en 2016-V.</p><p><br>
         Por favor dé clic en "Iniciar encuesta" o en "Regresar mas tarde" para salir
     </p>
    <br>
     <br>
     <a class="btn " href="./j_spring_security_logout">Regresar mas tarde </a>
     
    <a class="btn  btn-primary" href="./elegircurso.htm">Iniciar Encuesta</a>
    <br>
     <br>
       </center>
    </div>
       </div>
    </body>
</html>
