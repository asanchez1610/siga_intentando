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
         <script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/jquery-1.9.1.min.js"></script>


         
        <tiles:insertAttribute name="header" ignore="true"  />  
        
    </head>
    <body>        
        <!--[if lt IE 9]>
            <p class="chromeframe">Estás usando un navegador <strong>obsoleto</strong>. Por favor <a href="http://browsehappy.com/">actualiza tu navegador</a> o <a href="http://www.google.com/chromeframe/?redirect=true">activa Google Chrome Frame</a> para mejorar tu experiencia.</p>
        <![endif]-->
       <tiles:insertAttribute name="contentheader" ignore="true" />
 
        <div class="main-layout" >
            <div class="container">
                <tiles:insertAttribute name="content" ignore="true" />
            </div>
        </div>
            <tiles:insertAttribute name="contentfooter" ignore="true" />
      
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/vendor/jquery.timer.js"></script>
       <script type="text/javascript" src="<spring:message code="static.path"/>/js/plugins.min.js"></script>  
    </body>
</html>
