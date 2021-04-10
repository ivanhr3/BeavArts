<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<nav class="navbar navbar-custom navbar-expand-lg">
	
		<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggler" data-toggle="collapse"
				data-target="#main-navbar" aria-label="Toggle navigation">
				 <span class="navbar-toggler-icon"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>	

		 
    		<ul class="navbar-nav mr-auto  mt-2 mt-lg-0">
				<li class="nav-item">
        			<a class="nav-link" href="/beavers/list">Explora</a>
      			</li>
      			
      			<sec:authorize access="isAuthenticated()">
      			<li class="nav-item">
        			<a class="nav-link" href="/solicitudes/list">Mis Solicitudes</a>
      			</li>		
				<li class="nav-item dropdown">
        			<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Mis Publicaciones</a>
					 <div class="dropdown-menu">
					 	<a class="dropdown-item" href="/beavers/${myBeaverId}/encargos/list">Encargos</a>
					 	<a class="dropdown-item" href="/beavers/${myBeaverId}/anuncios/new">Anuncios</a>
					</div>
				</li>
				<li class="nav-item">
        			<a class="nav-link" href="/beavers/beaverInfo/${myBeaverId}">Mi perfil</a>
      			</li>	
				</sec:authorize>
				<li class="nav-item">
				 <a class="nav-link" href="https://beavartsispp.wixsite.com/home">Sobre nosotros</a>
				</li>
			</ul>
			
			
			<ul class="nav navbar-nav ml-auto">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar Sesi�n</a></li>
				</sec:authorize>
				
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item dropdown">
        				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"><strong><sec:authentication property="name" /></strong></a>
					 	<div class="dropdown-menu dropdown-menu-right">
					 		<a class="dropdown-item" href="/logout">Desconectar</a>
						</div>
					</li>
			</sec:authorize>
		</ul>
</nav>