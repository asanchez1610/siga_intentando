<%--    
    Created on : 26/04/2013, 05:57:19 PM
    Author     : Wilfredo Atoche
--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=iso-8859-1" %>
<!DOCTYPE html>
<!--[if lt IE 7]>	 <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>		 <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>		 <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <tiles:insertAttribute name="header" ignore="true"  />  
        <script>
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-16343774-15', 'udep.edu.pe');
        </script>
    </head>
    <body>        
        <!--[if lt IE 9]>
            <p class="chromeframe">Estás usando un navegador <strong>obsoleto</strong>. Por favor <a href="http://browsehappy.com/">actualiza tu navegador</a> o <a href="http://www.google.com/chromeframe/?redirect=true">activa Google Chrome Frame</a> para mejorar tu experiencia.</p>
        <![endif]-->
        <div id="stacks">
            <tiles:insertAttribute name="content" ignore="true"  />
        </div>

        <div id="loading-indicator" class="indicator"><i class="icon-spinner icon-spin"></i> Cargando...</div>
        <div id="error-indicator" class="indicator"><i class="icon-meh"></i> Algo salió mal...</div>
        <div id="session-indicator" class="indicator"><a href="login.htm">La sesión finalizó. Haz clic para volver a entrar.</a></div>
      
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/jquery-1.9.1.min.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/bootstrap.min.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/sammy-0.7.4.min.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/sammy.template-0.7.4.min.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/jquery.timer.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/plugins.min.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/jquery.validate.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/form-validation.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/main.js"></script>  
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/common.js"></script>
       
    </body>
</html>
