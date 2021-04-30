<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<link href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.0/jquery.fancybox.min.css" rel="stylesheet" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.0/jquery.fancybox.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.2.0/jquery.fancybox.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src='https://kit.fontawesome.com/a076d05399.js'></script>


	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	<c:set var ="authority" value = ""/>
	<c:forEach items="${beaver.user.authorities}" var="autoridad">
	<c:if test="${autoridad.authority == 'admin'}">
		<c:set var ="authority" value = "admin"/>
	</c:if>
    
</c:forEach>
<beavarts:layout pageName="perfil">
<div class="minAlto">

	<div class="container" style="min-height:800px; height:105%; margin-bottom:30px">
		<div class="mainCard" style="float:left">
			<div class="perfilCard">
			
				
			
			
				<div class="fotoCard text-center">
					<div class="fotoSize">
						<img src="${beaver.urlFotoPerfil}"  class="imgResponsivePerfil rounded-circle sombraPng">
						
						<c:if test="${beaver.user.username == principalUsername}">
								<a style="border-radius: 50%; height: 45px; position: absolute; top: 15%; left: 85%; transform: translate(-50%, -50%);" class="btn btn-primary editBoton sombraPng" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/editPhoto" htmlEscape="true"/>'>
									<i style="margin-left: 2px; margin-top: 7px;" class="fas fa-edit"></i></a>												
	                    </c:if> 
					</div>		      
	                  
	                    <div class="mt-3">
	                      <h4 class="Gagalin tituloPerfil">${beaver.user.username}</h4> 
	                      <security:authorize access="isAuthenticated()">
	                      <c:if test="${beaver.user.username != principalUsername}">      
							  <c:if test="${!usuarioYaValorado}">
								<spring:url value="/beavers/{beaverId}/valoraciones/create" var="valorar">
								<spring:param name="beaverId" value="${beaver.id}"/>              </spring:url>    
								<a class="btn btn-primary" href="${fn:escapeXml(valorar)}">Valorar</a>
							  </c:if>              
	                  	  </c:if>
	                      </security:authorize>
	                      	<c:if test="${beaver.user.username == principalUsername}">
								<a class="btn btn-primary" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/portfolio/edit" htmlEscape="true"/>'>Editar perfil</a>              
	                      </c:if>
	                      
	                      <security:authorize access="hasAuthority('admin')">
	                  
		                  <c:if test="${beaver.user.enabled == true and authority!='admin'}">
		                  
		                  	  <spring:url value="/beavers/beaverInfo/{beaverId}/ban" var="banUserUrl">
							  <spring:param name="beaverId" value="${beaver.id}"/>              
							  </spring:url>
			                  <a style="color:white"class="btn btn-red" href="${fn:escapeXml(banUserUrl)}"><i class="fas fa-ban"></i> Suspender</a>
		                  		
		                  </c:if>
		                  <c:if test="${beaver.user.enabled == true and authority == 'admin'}">
		                  	
		                  </c:if>
		                  <c:if test="${beaver.user.enabled == false}">
		                                    	 
			                  <b class="tituloPerfil" style="color:red">SUSPENDIDO</b>
		                  
		                  </c:if>
	          
		              <br/>  
	            </security:authorize> 
	                    </div>
	                  
				
				</div>
				
				<div class="fotoCard2 text-center">
					<div class ="RobotoLight textoEsp" style="height:20%; color:#3a3a3a; ">
						<h4 class ="Roboto tituloPerfil" style="color:black">Especialidades</h4>
						<c:forEach items="${beaver.especialidades}" var="especialidad">
				                          <c:choose>
							                      	<c:when test="${especialidad == 'TEXTIL'}">
							                      		<i class="fas fa-socks"></i>
							                      	</c:when>				                     
						                      		<c:when test="${especialidad == 'ESCULTURA'}">
							                      		<i class="fas fa-chess-knight"></i>
							                      	</c:when>					                      	
							                      	<c:when test="${especialidad == 'ILUSTRACION'}">
							                      		<i class="fas fa-portrait"></i>
							                      	</c:when>					                      	
							                      	<c:when test="${especialidad == 'ACRILICO'}">
							                      		<i class="fas fa-paint-brush"></i>
							                      	</c:when>				                      	
							                      	<c:when test="${especialidad == 'OLEO'}">
							                      		<i class="fas fa-palette"></i>
							                      	</c:when>					                      	
							                      	<c:when test="${especialidad == 'JOYERIA'}">
							                      		<i class="fas fa-gem"></i>
							                      	</c:when>					                      	
							                      	<c:when test="${especialidad == 'RESINA'}">
							                      		<i class="fas fa-prescription-bottle"></i>
							                      	</c:when>					                      	
							                      	<c:when test="${especialidad == 'FOTOGRAFIA'}">
							                      		<i class="fas fa-camera-retro"></i>
							                      	</c:when>               
						                  </c:choose>                          
			                    		  <c:out value="${especialidad} "/> &nbsp;
						</c:forEach>
					</div>
					
					<div class ="Roboto" style="height:50%">
						<h4 class ="Roboto tituloPerfil">Sobre mi</h4>
						<p class ="RobotoLight textoPerfil" style="font-weight: normal; "><c:out value="${beaver.portfolio.sobreMi}"/></p>
					</div>
					
					<div class ="RobotoLight" style="height:20%; ">
						<h4 class ="Roboto tituloPerfil">Valoración</h4>
							<c:if test = "${puntuacionMedia != null}">
		                    	<c:if test="${puntuacionMedia < 0}">	
		                    	</c:if>
		                    
		                    	<c:if test="${puntuacionMedia > 0 && puntuacionMedia < 1}">
			                    	<span class="fa fa-star-half checked"></span>						
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia >= 1 && puntuacionMedia < 1.5}">
			                    	<span class="fa fa-star checked"></span>					
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia >= 1.5 && puntuacionMedia < 2}">
			                    	<span class="fa fa-star checked"></span>
									<span class="fa fa-star-half checked"></span>							
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia >= 2 && puntuacionMedia < 2.5}">
			                    	<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>													
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia >= 2.5 && puntuacionMedia < 3}">
			                    	<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star-half checked"></span>							
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia >= 3 && puntuacionMedia < 3.5}">
			                    	<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>						
		                    	</c:if>
		                    
		                    
		                     	<c:if test="${puntuacionMedia >= 3.5 && puntuacionMedia < 4}">
			                    	<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star-half checked"></span>							
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia >= 4 && puntuacionMedia < 4.5}">
			                    	<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia >= 4.5 && puntuacionMedia < 5}">
			                    	
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>	
									<span class="fa fa-star checked"></span>						
									<span class="fa fa-star-half checked"></span>
									
		                    	</c:if>
		                    	
		                    	<c:if test="${puntuacionMedia == 5}">
			                    	
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									<span class="fa fa-star checked"></span>
									
		                    	</c:if>
		
		                    	</c:if>
		
		                    	<c:if test = "${sinPuntuacionMedia != null}">
		                    	<c:out value = "${sinPuntuacionMedia}"/>&nbsp;
		                    	</c:if>
			                    	
								<spring:url value="/beavers/${beaverId}/valoraciones/list" var="listValUrl">
		                        <spring:param name="beaverId" value="${beaver.id}"/>
		            			</spring:url>
								<a class="valHover" href="${fn:escapeXml(listValUrl)}">(${numValoraciones})</a>
					</div>
					
					
					
					
				</div>	
			</div>
			<c:if test= "${beaver.user.username == principalUsername}">
				<div class="perfilDataCard text-center">
					<h4 class="Roboto tituloPerfil2">Datos Personales</h4> 
			
	                    <div class="row rowPad" style="margin-left:10px">		                    
		                      <h6 class="mb-0 Roboto textoDatoPerfil">Nombre: &nbsp;<c:out value = "${beaver.firstName}"/></h6>	                    
	                  	</div>
	                  	
	                  	<div class="row rowPad" style="margin-left:10px">		                    
		                      <h6 class="mb-0 Roboto textoDatoPerfil">Apellidos: &nbsp;<c:out value = "${beaver.lastName}"/></h6>		                
	                  	</div>
	                  	
	                    <div class="row rowPad" style="margin-left:10px">
		                      <h6 class="mb-0 Roboto textoDatoPerfil">Email: &nbsp;<c:out value = "${beaver.email}"/></h6>	     
	                  	</div>
	                  	
				</div>
       
			</c:if>
			
		</div>

	<div class="portfolioCard text-center">
		<div class="portfolioCard2">
	<c:if test= "${beaver.portfolio.photos.isEmpty() == true}">
		<h5 class="Roboto"> No hay fotos en el portfolio </h5>
	</c:if>
	
	<c:if test= "${beaver.portfolio.photos.isEmpty() != true}">
	<div style="position: relative; text-align: center; margin-bottom:30px">
		<img class="SignBoardRegister"src="/resources/images/letrero.png"  >
	                                             
	    <h1 class="GagalinLight text-center responsiveFontSignBoard tituloPerfil" style="position: absolute; top: 65%; left: 50%; transform: translate(-50%, -50%);">PORTFOLIO</h1>
	    
    </div>
    <div>
    
    		<spring:url value="/beavers/${beaverId}/encargos/list" var="listUrl">
            	<spring:param name="beaverId" value="${beaver.id}"/>
            </spring:url>
			<b class="Roboto encargosFont">¿Te gusta mi trabajo? Puedes solicitar uno de mis </b><a href="${fn:escapeXml(listUrl)}"><b  class="Roboto encargosFont">Encargos</b></a>
    </div>
    
	  <br/>
	  <br/>
	  <!--Carousel Wrapper-->
	  <div id="multi-item-example" class="carousel carousel-multi-item carouselPerfil" data-ride="carousel">
	  
	    <!--Slides-->
	    <div class="carousel-inner sombraPng" role="listbox" style="margin: 0; position: absolute; top: 50%; -ms-transform: translateY(-50%); transform: translateY(-50%);">		
	     
	      <!--First slide-->
	      <div class="carousel-item active">
	      	<a data-fancybox="gallery" href="${beaver.portfolio.photos[0]}">
	      		<img class="d-bldk w-50" alt="..."  src="${beaver.portfolio.photos[0]}"> 
	      	</a>
	                                    
	      </div>
	      
	      <!--Next slides-->
	      <c:forEach items="${beaver.portfolio.photos}" var="photo" >            	
	    	<c:choose>   	
		    	<c:when test="${photo == beaver.portfolio.photos[0]}"></c:when>
		    	<c:otherwise>
		    		<div class="carousel-item">
						<a data-fancybox="gallery" href="${photo}">
				      		<img class="d-bldk w-50" alt="..."  src="${photo}"> 
				      	</a>	
				      	        	          
			      	</div>  
		    	</c:otherwise>    	
	    	</c:choose>	       
	      </c:forEach>
	   </div>
	   
	   <!--Slide buttons-->
	      <a class="carousel-control-prev" href="#multi-item-example" role="button" data-slide="prev">
	            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	            <span class="sr-only">Previa</span>
	        </a>
	        <a class="carousel-control-next" href="#multi-item-example" role="button" data-slide="next">
	            <span class="carousel-control-next-icon" aria-hidden="true"></span>
	            <span class="sr-only">Siguiente</span>
	        </a> 
	         
	   </div>
	   	
	</c:if> 

	</div>


	</div>


	
</div>






		
</div>

<div class="botonesPerfil">
	<c:if test="${beaver.user.username == principalUsername}">
		<a class="btn btn-secondary espacio" href='<spring:url value="/users/portability" htmlEscape="true"/>'><fmt:message key="portfolio.portabilidad"/></a>
	    <a class="btn btn-danger" href='<spring:url value="/users/delete" htmlEscape="true"/>'><fmt:message key="portfolio.borrarDatos"/></a>
	</c:if>	
 </div>     		
   	  
</beavarts:layout>