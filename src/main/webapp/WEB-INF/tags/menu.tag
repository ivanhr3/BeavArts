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
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<%@tag pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">

<nav class="navbar navbar-custom navbar-expand-lg sticky-top">
      <a class="navbar-brand" href="<spring:url value="/" htmlEscape="true" />"><img src="/resources/images/v22.png"  class="d-inline-block align-top" alt="/"></a>
            
      <button class="navbar-toggler" type="button" style="background-color:#ffdb8c;" data-toggle="collapse" data-target="#navbarsExample05" aria-controls="navbarsExample05" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        <span class="icon-bar"></span> 
        <span class="icon-bar"></span> 
        <span class="icon-bar"></span>
      </button>     

      
      <div class="collapse navbar-collapse" id="navbarsExample05">
        <ul class="navbar-nav mr-auto">
        
        
        <li class="nav-item">
			<div class="buttonHeaderImageA aumentoHover">
				<a class="nav-link izqHover" href="/anuncios/list">Anuncios</a>
			</div>	
		</li>
        <li class="nav-item">
        	<div class="buttonHeaderImageE aumentoHover">
        			<a class="nav-link izqHover" href="/beavers/list"><i class="fas fa-users"></i> Explora</a>
        	 </div>	
      	</li>
        <sec:authorize access="isAuthenticated()">
      			<li class="nav-item">
      				<div class="buttonHeaderImageMS aumentoHover">
        				<a class="nav-link izqHover" href="/solicitudes/list"> Mis solicitudes</a>
        			</div>	
      			</li>		
				<li class="nav-item">
					<div class="buttonHeaderImageMP aumentoHover">
        				<a class="nav-link izqHover" href="/beavers/${myBeaverId}/misPublicaciones">Mis publicaciones</a>
        			</div>
				</li>

				</sec:authorize>
        
         <li class="nav-item">
      		<div class="buttonHeaderImageSN aumentoHover">
				        <a class="nav-link izqHover" href="https://beavartsispp.wixsite.com/home">Sobre nosotros</a>
		      </div>	
			   </li>
         
         <sec:authorize access="hasAuthority('admin')">
				  <li class="nav-item dropdown">
					<div class="buttonHeaderImageAdmin">
			        <a class="nav-link dropdown-toggle izqHover" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			          <i class="fas fa-user-cog"></i> Administrador
			        </a>
			        
			        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
			          <a class="dropdown-item" href="/facturas/list">Facturas</a>
			        </div>
			        </div>
      			</li>
      	</sec:authorize>

        </ul>
        
        <ul class="nav navbar-nav ml-auto">
				<sec:authorize access="!isAuthenticated()">
				
					<li>
					<div class="buttonHeaderImagePerfil aumentoHover">
					<a class="nav-link izqHover"style="font-family:SegoePrint; font-weight:bold; color:#000" href="<c:url value="/users/new" />">¡Regístrate!</a>
					</div>
					</li>
					
					<li>
					<div class="buttonHeaderImageLogin aumentoHover">
					<a class="nav-link izqHover"style="font-family:SegoePrint; font-weight:bold; color:#000" href="<c:url value="/login" />">Iniciar Sesión</a>
					</div>
					</li>
					
				</sec:authorize>
				
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item dropdown">
					
        				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"><strong><sec:authentication property="name" /></strong></a>
					 	
					 	<div class="dropdown-menu dropdown-menu-right">
					 		<a class="dropdown-item" href="/beavers/beaverInfo/${myBeaverId}"><i class="fas fa-user"></i> Mi perfil</a>
					 		<a class="dropdown-item" href="/logout">Desconectar</a>
						</div>
					</li>
			</sec:authorize>
		</ul>
		
      </div>
    </nav>

<br/>
<br/>
