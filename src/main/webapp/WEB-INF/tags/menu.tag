<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

		</div>
		
			<div class="nav navbar-nav navbar-right">
			
			<div style="margin:25px" class="navbar-left">
				<form action="/action_page.php">
	      			<input type="text" placeholder="Buscar..." name="search">
	      			<button type="submit"><i class="fa fa-search"></i></button>
	    		</form>
			</div>
			
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li><a href="<c:url value="/users/new" />">Register</a></li>
				</sec:authorize>
				
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
						</ul></li>
			</sec:authorize>
		</div>
	</div>

	<nav class="navbar2">		
			<div class="marginLeft">
	    		<spring:url value="" var=""></spring:url>
	    		<a   href="" class="btn btn-header-home">Explora</a>
	    		
	    		<spring:url value="" var=""></spring:url>
	    		<a   href="" class="btn btn-header-home">Anuncios</a>
	    		
	    		<sec:authorize access="isAuthenticated()">
		    		<spring:url value="/solicitudes/list" var="misSolicitudesUrl"></spring:url>
		    		<a   href="${fn:escapeXml(misSolicitudesUrl)}" class="btn btn-header-home">Mis Solicitudes</a>
		    		
		    		<spring:url value="/beavers/${beaverId}/encargos/list" var="misEncargosUrl"></spring:url>
		    		<a   href="${fn:escapeXml(misEncargosUrl)}" class="btn btn-header-home"> Mis Publicaciones</a>
		    		
		    		<spring:url value="/beavers/beaverInfo/miPerfil" var="perfilUrl"></spring:url>
		    		<a   href="${fn:escapeXml(perfilUrl)}" class="btn btn-header-home"> Mi Perfil</a>
	    		</sec:authorize>
    		</div>
    	
	</nav>


</nav>
