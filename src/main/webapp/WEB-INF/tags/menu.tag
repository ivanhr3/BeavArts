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
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul style="margin-left:50px" class="nav navbar-nav">
		
				<beavarts:menuItem active="${name eq 'sobre'}" url="https://beavartsispp.wixsite.com/home">
					<span>Sobre nosotros</span>
				</beavarts:menuItem>

				<beavarts:menuItem active="${name eq 'explora'}" url="/beavers/list">
					<span>Explora</span>
				</beavarts:menuItem>
				
				<sec:authorize access="isAuthenticated()">
					<beavarts:menuItem active="${name eq 'mis solicitudes'}" url="/solicitudes/list">
						<span>Mis Solicitudes</span>
					</beavarts:menuItem>
					
					<beavarts:menuItem active="${name eq 'mis publicaciones'}" url="/beavers/${myBeaverId}/encargos/list">
						<span>Mis Publicaciones</span>
					</beavarts:menuItem>
					
					<beavarts:menuItem active="${name eq 'mi perfil'}" url="/beavers/beaverInfo/${myBeaverId}">
						<span>Mi Perfil</span>
					</beavarts:menuItem>
				</sec:authorize>
				
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Iniciar Sesi�n</a></li>
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
