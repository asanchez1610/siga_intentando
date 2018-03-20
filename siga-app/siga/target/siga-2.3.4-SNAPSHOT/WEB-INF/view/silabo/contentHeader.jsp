<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
                    <li><a href="./ver.htm?id=${param.id}&idee=${param.idee}">Sílabo de asignatura</a></li>                    
                </ul>
            </div>
        </div>
    </div>
</div>