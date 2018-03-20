<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="logo-wrapper">
    <h1 id="logo">SIGA</h1>
    <p id="description" class="label" style="font-size:14px;">Sistema Integrado de Gesti&oacute;n Acad&eacute;mica</p>
</div>
<form id="login-form" class="form-horizontal" accept-charset="UTF-8" action="${pageContext.request.contextPath}/j_spring_security_check" method="POST">
    <fieldset>        
        <c:set var="messageLength" value="${fn:length(fn:trim(SPRING_SECURITY_LAST_EXCEPTION.message))}" />
        <c:choose> 
            <c:when test="${param.state=='failure'}">   
                <div class="alert alert-error">
                    ${fn:replace(SPRING_SECURITY_LAST_EXCEPTION.message, 'Bad credentials', '<i class="icon-exclamation-sign"></i> Los datos ingresados son incorrectos.')}
                </div>
            </c:when> 
            <c:when test="${messageLength > 0}">
                <div class="alert alert-error">                                       
                    Error: 
                    <c:if test="${messageLength > 50}">
                        ${fn:substring(SPRING_SECURITY_LAST_EXCEPTION.message, 0, 50)}...
                    </c:if>
                    <c:if test="${messageLength <= 50}">
                        ${SPRING_SECURITY_LAST_EXCEPTION.message}
                    </c:if>
                </div>
            </c:when> 
        </c:choose>
        <div class="control-group">
            <label class="control-label" for="j_username">Usuario</label>
            <div class="controls">
                <input type="text" id="j_username" name="j_username" placeholder="" class="input-medium">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="j_password">Contrase&ntilde;a</label>
            <div class="controls">
                <input type="password" id="j_password" name="j_password" placeholder="" class="input-medium">
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button class="btn btn-primary"><i class="icon-ok-sign"></i> Iniciar sesi&oacute;n</button>
            </div>
        </div>
    </fieldset>
</form>
<div id="verisign">    
	<script src="https://seal.verisign.com/getseal?host_name=siga.udep.edu.pe&amp;size=L&amp;use_flash=YES&amp;use_transparent=YES&amp;lang=es"></script>
	<p><small>This site chose VeriSign <a href="http://www.verisign.es/products-services/security-services/ssl/ssl-information-center/" target="_blank">SSL</a> for secure e-commerce and confidential communications.</small></p>
</div>
