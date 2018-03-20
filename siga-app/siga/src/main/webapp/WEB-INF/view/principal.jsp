<div id="siga-sidebar" class="stack">
    <div id="siga-intro">
        <header>
            <h1 id="site-logo" class="hide-text">SIGA</h1>
        </header>
        <div id="profile-info">
            <p id="user-info">${nombreAlumno}</p>
            <ul class="user-buttons">
                <li><a href="#/opciones" title="Opciones de cuenta"><i class="icon-cog"></i></a></li>
                <li><a href="./j_spring_security_logout" title="Cerrar sesión"><i class="icon-off"></i></a></li>
            </ul>
        </div>
    </div>
    <div id="siga-sidenav" class="scrollable-container">
        <ul id="siga-mainnav">
            <!--<li class="active"><a href="#/resumen"><i class="item-icon icon-home"></i><i class="icon-chevron-right"></i>Inicio</a></li>-->
            <li><a href="#/avisos"><i class="item-icon icon-flag"></i><i class="icon-chevron-right"></i>Avisos</a></li>
            <li><a href="#/cursos"><i class="item-icon icon-road"></i><i class="icon-chevron-right"></i>Mis asignaturas <span class="notification-asignatura"></span></a></li>
            <li><a href="#/horario"><i class="item-icon icon-time"></i><i class="icon-chevron-right"></i>Mi horario</a></li>
            <li><a href="#/perfil/academico"><i class="item-icon icon-bookmark"></i><i class="icon-chevron-right"></i>Mi perfil acad&eacute;mico</a></li>
            <li><a href="#/perfil/personal"><i class="item-icon icon-user"></i><i class="icon-chevron-right"></i>Mi perfil personal</a></li>
            <li><a href="#/asesor/perfil"><i class="item-icon icon-briefcase"></i><i class="icon-chevron-right"></i>Mi asesor</a></li>
            <li><a href="#/pensiones/cronogramas"><i class="item-icon icon-barcode"></i><i class="icon-chevron-right"></i>Pensiones <span class="notification-pension"></span></a></li>
            <li><a href="#/mensajes/inbox"><i class="item-icon icon-envelope-alt"></i><i class="icon-chevron-right"></i>Mensajería <span class="notification-message badge badge-inverse"></span></a></li>
            <li><a href="#/tramites/pendientes"><i class="item-icon icon-exchange"></i><i class="icon-chevron-right"></i>Trámites acad&eacute;micos</a></li>
            <li><a href="#/docs/pendientes"><i class="item-icon icon-cloud-download"></i><i class="icon-chevron-right"></i>Documentos oficiales</a></li>
            <li><a href="#/ambientes"><i class="item-icon icon-search"></i><i class="icon-chevron-right"></i>Ambientes</a></li>
            <li><a href="#/buscar/profesores"><i class="item-icon icon-search"></i><i class="icon-chevron-right"></i>Buscar</a></li>
        </ul>
        <ul id="siga-altnav">
            <li><a href="#/universidad">Universidad</a></li>
            <li><a href="#/intranet">Mapa de sitio</a></li>
            <li><a href="#/consultas">Reclamos, consultas y sugerencias</a></li>
            <li><a href="http://beta.udep.edu.pe/guias/siga/presentacion/" target="_blank">Guía de usuario</a></li>
        </ul>
    </div>
</div>
   
<div id="siga-main" class="stack">

	<div class="scrollable-container">
		<div class="main-header">
			<a id="toggle-sidebar" class="btn btn-primary">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<h1 class="section-title"><span id="page-title">SIGA</span></h1>
		</div>

		<div class="container-fluid">
			<div id="main-app"></div>
		</div>
	</div>

</div>
