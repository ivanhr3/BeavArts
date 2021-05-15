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
					
						<c:choose>
							<c:when test="${not empty beaver.urlFotoPerfil}">
								<img src="${beaver.urlFotoPerfil}"  class="imgResponsivePerfil rounded-circle sombraPng">
							</c:when>
							<c:when test="${empty beaver.urlFotoPerfil}">
								<img src="/resources/images/FotoBasePerfil.png"  class="imgResponsivePerfil rounded-circle sombraPng">
							</c:when>
						</c:choose>
						
						<c:if test="${beaver.user.username == principalUsername}">
								<a style="border-radius: 50%; height: 45px; position: absolute; top: 15%; left: 85%; transform: translate(-50%, -50%);" class="btn btn-primary sombraPng" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/editPhoto" htmlEscape="true"/>'>
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
                        
	                      	<c:if test="${beaver.user.username == principalUsername}">
								<a class="btn btn-primary" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/portfolio/edit" htmlEscape="true"/>'>Editar perfil</a>              
	                      	</c:if>
							</security:authorize> 
	                      <security:authorize access="hasAuthority('admin')">
							  
		                  <c:if test="${beaver.user.enabled == true and authority!='admin'}">
						
		                  	  <spring:url value="/beavers/beaverInfo/{beaverId}/ban" var="banUserUrl">
							  <spring:param name="beaverId" value="${beaver.id}"/>              
							  </spring:url>
			                  <a style="color:white"class="btn btn-red" href="${fn:escapeXml(banUserUrl)}"><i class="fas fa-ban"></i> Suspender</a>
		                  		
		                  </c:if>

						  <c:if test="${beaver.user.enabled == false and authority!='admin'}">
						
							<spring:url value="/beavers/beaverInfo/{beaverId}/unban" var="unbanUserUrl">
						  <spring:param name="beaverId" value="${beaver.id}"/>              
						  </spring:url>
						  <a style="color:white"class="btn btn-success" href="${fn:escapeXml(unbanUserUrl)}"><i class="fas fa-check"></i> Reactivar Cuenta</a>
							  
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
					<div class ="Roboto textoEsp" style="height:35%; color:#3a3a3a; ">

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
					
						<c:if test="${beaver.user.username == principalUsername}">
						<div class="container">
								<a class="btn btn-primary espButton" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/editEspecialidades" htmlEscape="true"/>'>Cambiar</a>              
	                     </div> </c:if>
					</div>
					
					<div class ="Roboto" style="height:50%">
						<h4 class ="Roboto tituloPerfil">Sobre mi</h4>
						<p class ="RobotoLight textoPerfil" style="font-weight: normal; "><c:out value="${beaver.portfolio.sobreMi}"/></p>
					</div>
					
					<div class ="RobotoLight" style="height:5%; ">
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
		                    	<p class="textoPerfil text-center"><c:out value = "${sinPuntuacionMedia}"/>&nbsp;</p>
		                    	</c:if>
			                    	
								<spring:url value="/beavers/${beaverId}/valoraciones/list" var="listValUrl">
		                        <spring:param name="beaverId" value="${beaver.id}"/>
		            			</spring:url>
								<a class="valHover textoPerfil" href="${fn:escapeXml(listValUrl)}">(${numValoraciones})</a>
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
<c:if test= "${beaver.user.username != principalUsername}">
<c:if test= "${beaver.encargos.isEmpty() != true}">
	<div class="encCard">
		
		<div class="encCard2 text-center">
		
			<div style="position: relative; text-align: center; margin-bottom:60px">
				<img class="SignBoardRegister"src="/resources/images/letrero.png"  >
			                                             
			    <h1 class="GagalinLight text-center responsiveFontSignBoard tituloPerfil" style="position: absolute; top: 65%; left: 50%; transform: translate(-50%, -50%);">ENCARGOS</h1>
			    
    		</div>
			<div style=" width:100%; height:100%; padding:2% ; display: flex; flex-wrap: wrap;">
					
			<c:forEach begin="0" end="5" items="${beaver.encargos}" var="encargo">
			<c:if test="${!encargo.photo.isEmpty()}">
				<c:if test="${encargo.disponibilidad == true}">
				
				
					<div class="cartaEncargo">
				
	                  
	                  
	                 <div class="cuerpoCarta" style="background-image: url(${encargo.photo}); background-position: center; ">
							<div class="text-center" style="height:50%;">
								<div class="letrero Roboto">
									<c:out value="${encargo.titulo}"/>
								</div>
		
							</div>
	                        <div class="cuerpoCartaFoto">
	                        
	                        	
	                            <a data-fancybox="gallery" href="${encargo.photo}">
					      		<img class="imgResponsiveCarta sombraImg" src="${encargo.photo}" alt="EncargosTienda">
					      		</a>
	                            
	
		                        
	
	                  		</div>
					</div>
	
	                  
		
					  <div class="cuerpoFooter text-center">
					  		<a  class="customHoverEncargos" href="#myModal${encargo.id}" data-toggle="modal"><b class="card-title Roboto">Ver más</b></a>
					  </div>
					</div>
					</c:if>
	                <div class="modal fade" id="myModal${encargo.id}">
	  						<div class="modal-dialog modal-dialog-centered modal-lg">
	    					<div class="modal-content">
	    					
	    					<!-- Modal Header -->
	    					<div class="modal-header">
	        					<h4 class="modal-title col-11 text-center">${encargo.titulo}</h4>
	        					<button type="button" class="close" data-dismiss="modal">&times;</button>
	     						 </div>
	     						 
	     						 <!-- Modal Body -->
	     						 <div class="modal-body">
	        						<h5>${encargo.descripcion}</h5>
	        						<h5>Precio: ${encargo.precio}€</h5>
					     		<c:if test="${!encargo.photo.isEmpty()}">
	            					<img class ="img-thumbnail center" src="${encargo.photo}" alt="">
	        					</c:if>
	        					
	        					<c:if test="${encargo.photo.isEmpty()}">
	            				<h6 class="mb-0">No hay imagen para mostrar</h6>
	        					</c:if>	
	      						</div>
	      						
	      						<!-- Modal Footer -->
	     						 <div class="modal-footer justify-content-center">
                                     <security:authorize access="isAuthenticated()">
                                         <a class="btn btn-primary" href='<spring:url value="/solicitudes/${encargo.id}/create" htmlEscape="true"/>'>Solicitar encargo</a>
                                     </security:authorize>
	        					<security:authorize access="hasAuthority('admin')">

	    							&nbsp;              
	    						<spring:url value="/beavers/{beaverId}/encargos/{encargoId}/delete" var="deleteEncargoUrl">
									<spring:param name="encargoId" value="${encargo.id}"/>  
									<spring:param name="beaverId" value="${encargo.beaver.id}"/>         
								</spring:url>
								<a style="color:white"class="btn btn-red" href="${fn:escapeXml(deleteEncargoUrl)}"><i class="fas fa-trash-alt"></i> Borrar Encargo</a>
              
    						</security:authorize>
	      							</div>
	    					</div>
	    				</div>
	    			</div> 
    			</c:if>       
   			</c:forEach>
   			
			</div>
			<spring:url value="/beavers/${beaverId}/encargos/list" var="listUrl">
            	<spring:param name="beaverId" value="${beaver.id}"/>
            </spring:url>
            <security:authorize access="isAuthenticated()">
            	<div class="row text-center">
            	<div class="col">
				<p style="color: white" class="RobotoLight encargosFont">Mira el resto de mis encargos&nbsp;<a href="${fn:escapeXml(listUrl)}"><b  class="RobotoLight encargosFont">aquí</b></a> </p>
           		</div>
           		</div>
            </security:authorize>
            <c:if test="${principalUsername == null}">
                <spring:url value="/login" var="loginURL">
                </spring:url>
                <p style="color: white" class="RobotoLight encargosFont"><a href="${fn:escapeXml(loginURL)}">Inicia sesión</a> para ver más encargos</p>
            </c:if>
			</div>
	
		
		</div>
				
	</c:if>		
</c:if>	
</div>

<c:if test="${beaver.user.username == principalUsername}">
		<div class="botonesPerfil">			
				<a class="btn btn-secondary espacio" href='<spring:url value="/users/portability" htmlEscape="true"/>'><fmt:message key="portfolio.portabilidad"/></a>
			    <a class="btn btn-danger" href='<spring:url value="/users/delete" htmlEscape="true"/>'><fmt:message key="portfolio.borrarDatos"/></a>
			
		 </div>  
</c:if>	
		
</div>
</beavarts:layout>
