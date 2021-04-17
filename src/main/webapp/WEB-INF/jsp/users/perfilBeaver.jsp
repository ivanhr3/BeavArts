<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
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

<div class="container">
    <div class="main-body">
    
          <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
              <div class="card">
                <div class="card-body">
                
                  <div class="d-flex flex-column align-items-center text-center">
                  
                  <security:authorize access="hasAuthority('admin')">
                  
	                  <c:if test="${beaver.user.enabled == true and authority!='admin'}">
	                  
	                  	  <spring:url value="/beavers/beaverInfo/{beaverId}/ban" var="banUserUrl">
						  <spring:param name="beaverId" value="${beaver.id}"/>              
						  </spring:url>
		                  <a style="color:white"class="btn btn-red" href="${fn:escapeXml(banUserUrl)}"><i class="fas fa-ban"></i> Suspender Usuario</a>
	                  		
	                  </c:if>
	                  <c:if test="${beaver.user.enabled == true and authority == 'admin'}">
	                  	
	                  </c:if>
	                  <c:if test="${beaver.user.enabled == false}">
	                                    	 
		                  <b style="color:red">USUARIO SUSPENDIDO</b>
	                  
	                  </c:if>
          
	              <br/>  
                  </security:authorize>
                  
                    <img width="150px" height="150px" src="${beaver.urlFotoPerfil}" alt="Admin" class="rounded-circle" width="150">
                    <div class="mt-3">
                      <h4 class="SegoeFont">${beaver.user.username}</h4> 
                      <c:if test="${beaver.user.username != principalUsername}">                    
                  	
                      	<spring:url value="/beavers/{beaverId}/valoraciones/create" var="valorar">
						<spring:param name="beaverId" value="${beaver.id}"/>              </spring:url>    
                      	<a class="btn btn-primary" href="${fn:escapeXml(valorar)}">Valorar</a>
 	
                      </c:if>
                      	<c:if test="${beaver.user.username == principalUsername}">
							<a class="btn btn-primary" href='<spring:url value="/beavers/beaverInfo/${beaver.id}/portfolio/edit" htmlEscape="true"/>'>Editar perfil</a>						
                      </c:if> 
                    </div>
                  </div>
                </div>
              </div>             
            </div>
            <div class="col-md-8">
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Especialidades</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      
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
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Sobre mi</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.portfolio.sobreMi}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Valoración</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                    	
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
                    	<c:out value = "${sinPuntuacionMedia}"/>
                    	</c:if>
	                    	
						(${numValoraciones})
                    </div>
                  </div>
        
                </div>
                
              </div> 
 
	      		<spring:url value="/beavers/${beaverId}/valoraciones/list" var="listValUrl">
	                        <spring:param name="beaverId" value="${beaver.id}"/>
	            </spring:url>
				<b class="SegoeFont" style="font-size:20px">Ir a  </b><a class="customHover" href="${fn:escapeXml(listValUrl)}"><b  class="SegoeFont" style="color:b14900; font-size:20px"> Valoraciones</b></a>

            </div>
       
 <c:if test="${beaver.user.username == principalUsername}"> 
	<div class="container my-4">
		<hr class="mb-5"/>
        <div class="col-md-8">
            <h2 class="SegoeFont">Datos Personales:</h2> 
            <br/>
              <div class="card mb-3">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Nombre</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.firstName}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Apellidos</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.lastName}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">Email</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.email}
                    </div>
                  </div>
                  <hr>
                  <div class="row">
                    <div class="col-sm-3">
                      <h6 class="mb-0 SegoeFont">DNI</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                      ${beaver.dni}
                    </div>
                  </div>               
                </div>
              </div>              
           </div>
        </div>
      </c:if> 
    </div>
  </div>
</div>

<c:if test= "${beaver.portfolio.photos.isEmpty() != true}">
	<div class="container my-4">
	
	  <hr class="mb-5"/>
	  <h1 class="SegoeFont">Portfolio:</h1>
	  <br/>
	  <br/>
	  <!--Carousel Wrapper-->
	  <div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">
	  
	    <!--Slides-->
	    <div class="carousel-inner" role="listbox">		
	    
	    
	  
	    
	      <!--First slide-->
	      <div class="carousel-item active">
	      	<a data-fancybox="gallery" href="${beaver.portfolio.photos[0]}">
	      		<img class="d-bldk w-50 imgCustom" alt="..."  src="${beaver.portfolio.photos[0]}"> 
	      	</a>
	                                    
	      </div>
	      
	      <!--Next slides-->
	      <c:forEach items="${beaver.portfolio.photos}" var="photo" >            	
	    	<c:choose>   	
		    	<c:when test="${photo == beaver.portfolio.photos[0]}"></c:when>
		    	<c:otherwise>
		    		<div class="carousel-item">
						<a data-fancybox="gallery" href="${photo}">
				      		<img class="d-bldk w-50 imgCustom" alt="..."  src="${photo}"> 
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
	</div>
</c:if> 

<c:if test= "${beaver.portfolio.photos.isEmpty() == true}">
	<h5 class="SegoeFont"> No hay fotos en el portfolio </h5>
</c:if>


<security:authorize access="isAuthenticated()">
   		
	
 		<c:if test="${beaver.user.username != principalUsername}">
      		<spring:url value="/beavers/${beaverId}/encargos/list" var="listUrl">
                        <spring:param name="beaverId" value="${beaver.id}"/>
            </spring:url>
			<b class="SegoeFont" style="font-size:20px">¿Te gusta mi trabajo? Puedes solicitar uno de mis </b><a class="customHover" href="${fn:escapeXml(listUrl)}"><b  class="SegoeFont" style="color:b14900; font-size:20px">Encargos</b></a>
   	  	</c:if>
</security:authorize>
		

</beavarts:layout>