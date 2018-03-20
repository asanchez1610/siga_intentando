<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" src="<spring:message code="static.path"/>/js/wz_tooltip.js"></script>

<div id="main-navbar" class="navbar navbar-inverse navbar-static-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="http://udep.edu.pe/?utm_source=siga&utm_medium=barra_global&utm_campaign=logo">UDEP</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="hidden-desktop">SIGA</span><i class="visible-desktop icon-th-large icon-white"></i></a>
                        <ul class="dropdown-menu">
                            <li><a href="./malla.htm?id=${param.id}"><i class="item-icon icon-align-left"></i> Plan de estudios</a></li>
                            <!--<li><a href="./avancePlan.htm?id=${param.id}"><i class="item-icon icon-align-justify"></i> Avance del plan</a></li> -->                    
                        </ul>
                    </li>
                </ul>
                <ul class="nav">
                    <li <tiles:getAsString name="activeMalla" ignore="true" />><a href="./malla.htm?id=${param.id}">Plan de estudios</a></li>
                   <!-- <li <tiles:getAsString name="activeAvance" ignore="true" />><a href="./avancePlan.htm?id=${param.id}">Avance del plan</a></li>-->
                </ul>
            </div>
        </div>
    </div>
</div>