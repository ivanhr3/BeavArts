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


<nav class="navbar navbar-expand-lg navbar-light bg-light">

	<div class="container">
		
		<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggler" data-toggle="collapse"
				data-target="#main-navbar" aria-label="Toggle navigation">
				 <span class="navbar-toggler-icon"></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			
		
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
    		<ul class="navbar-nav mr-auto">
      			<li class="nav-item">
				 <a class="nav-link" href="https://beavartsispp.wixsite.com/home">Sobre nosotros</a>
				  </li>
				<li class="nav-item">
        			<a class="nav-link" href="/beavers/list">Explora</a>
      			</li>
      			
      			<sec:authorize access="isAuthenticated()">
      			<li class="nav-item">
        			<a class="nav-link" href="/solicitudes/list">Mis Solicitudes</a>
      			</li>		
				<li class="nav-item dropdown">
        			<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Mis Publicaciones</a>
					 <div class="dropdown-menu" aria-labelledby="navbarDropdown">
					 <a class="dropdown-item" href="/beavers/${myBeaverId}/encargos/list">Encargos</a>
					 <a class="dropdown-item" href="/beavers/${myBeaverId}/anuncios/new">Anuncios</a>
					</div>
				</li>
				<li class="nav-item">
        			<a class="nav-link" href="/beavers/beaverInfo/${myBeaverId}">Mi perfil</a>
      			</li>	
				</sec:authorize>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar Sesión</a></li>
				</sec:authorize>
				
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">
							<strong><sec:authentication property="name" /></strong>
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
													class="btn btn-primary btn-block btn-sm">Desconectar</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
						</ul></li>
			</sec:authorize>
		</ul>
	</div>
</div>
</nav>
