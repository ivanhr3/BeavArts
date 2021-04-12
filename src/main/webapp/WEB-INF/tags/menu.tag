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
<link href="//db.onlinewebfonts.com/c/c455d94eee43dc4ceeb83c0c0fd0d4c8?family=Segoe+Print" rel="stylesheet" type="text/css"/>

<nav class="navbar navbar-custom navbar-expand-lg sticky-top">
      <a class="navbar-brand" href="<spring:url value="/" htmlEscape="true" />"><img src="/resources/images/v22.png"  class="d-inline-block align-top" alt="/"></a>
            
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample05" aria-controls="navbarsExample05" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        <span class="icon-bar"></span> 
        <span class="icon-bar"></span> 
        <span class="icon-bar"></span>
      </button>
      
      
      
      
      <div class="collapse navbar-collapse" id="navbarsExample05">
        <ul class="navbar-nav mr-auto">
        
        <li class="nav-item">
        			<a class="nav-link" href="/beavers/list">Explora</a>
      	</li>
      	
      	<li class="nav-item">
				 <a class="nav-link" href="https://beavartsispp.wixsite.com/home">Sobre nosotros</a>
		</li>
		<li class="nav-item">
        			<a class="nav-link" href="/anuncios/list">Anuncios</a>
      	</li>	
        
        <sec:authorize access="isAuthenticated()">
      			<li class="nav-item">
        			<a class="nav-link" href="/solicitudes/list">Mis Solicitudes</a>
      			</li>		
				<li class="nav-item">
        			<a class="nav-link" href="/beavers/${myBeaverId}/misPublicaciones">Mis publicaciones</a>
				</li>
				<li class="nav-item">
        			<a class="nav-link" href="/beavers/beaverInfo/${myBeaverId}">Mi perfil</a>
      			</li>	
				</sec:authorize>
            
        </ul>
        
        <ul class="nav navbar-nav ml-auto">
				<sec:authorize access="!isAuthenticated()">
					<li><a style="font-family:Segoe Print; font-weight:bold; color:#ffbb00" href="<c:url value="/login" />">Iniciar Sesi�n</a></li>
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
		
      </div>
    </nav>
<br/>
<br/>
