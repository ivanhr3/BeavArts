<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <%-- Para  tildes, ñ y caracteres especiales como el € --%>

	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

<beavarts:layout pageName="Mis publicaciones">
<div class="minAlto">
<div class="mb-3 centerContainerVal">
<div class="container row" style="display:flex; flex-wrap: wrap;">
<div class="col">
<c:if test="${beaver.user.username == principalUsername}">
		<c:if test="${noHayEspecialidades == false}">
		<a class="btn btn-primary" href='<spring:url value="/beavers/${beaverId}/encargos/new" htmlEscape="true"/>'>Nuevo encargo</a>
		</c:if>
		<c:if test="${noHayEspecialidades == true}">
    	<div class="alert alert-danger" role="alert">
			<c:out value="${errorEspecialidades}"/>
		</div>
		</c:if>
		<a class="btn btn-primary" href='<spring:url value="/beavers/${beaverId}/anuncios/new" htmlEscape="true"/>'>Nuevo anuncio</a>
		<br/>
    	
</c:if>
</div>
</div>
<div class="card" style="width:43rem;">
<div class="card-header">
<ul class="nav nav-tabs card-header-tabs" id="publicaciones-list">
    			<li class="nav-item">
        			<a class="nav-link active enlaceEncargos RobotoLight" href="#listAnuncios" data-toggle="tab">Anuncios</a>
      			</li>
      			<li class="nav-item">
              		<a class="nav-link enlaceEncargos RobotoLight" href="#listEncargos" data-toggle="tab">Encargos</a></li>
    		</ul></div>
    		
    <div class="card-body tab-content justify-content-center">	
  	<div class="tab-pane fade show active" id="listAnuncios">
  	 	<c:if test="${hayAnuncios == false}">
  			<h2>¡AÚN NO HAY ANUNCIOS!</h2></c:if>
  	<c:if test="${hayAnuncios != false}">		
  	<c:forEach items="${beaver.anuncios}" var="anuncio">
  					<spring:url value="/beavers/${beaverId}/anuncios/{anuncioId}" var="anuncioUrl">
                        <spring:param name="anuncioId" value="${anuncio.id}"/>
                	</spring:url>   	
  			<div class="card-header-publicaciones"><h4><c:out value="${anuncio.titulo}"/></h4></div>
            <div class="card-body card-body-anuncios">
            	<h5><c:out value="${anuncio.descripcion}"/></h5>
            	<div class="row justify-content-center">
            	<h5>Categoría:&nbsp;</h5>
            	<h5 style="color:grey">  
            								<c:if test="${fn:escapeXml(anuncio.especialidad) == 'TEXTIL'}">
					                      		<i class="fas fa-socks"></i>
					                      	</c:if>
				                     
				                      		<c:if test="${fn:escapeXml(anuncio.especialidad) == 'ESCULTURA'}">
					                      		<p class="fas fa-chess-knight"></p>
					                      	</c:if>
					                      	
					                      	<c:if test="${fn:escapeXml(anuncio.especialidad) == 'ILUSTRACION'}">
					                      		<p class="fas fa-portrait"></p>
					                      	</c:if>
					                      	
					                      	<c:if test="${fn:escapeXml(anuncio.especialidad) == 'ACRILICO'}">
					                      		<p class="fas fa-paint-brush"></p>
					                      	</c:if>
					                      	
					                      	<c:if test="${fn:escapeXml(anuncio.especialidad) == 'OLEO'}">
					                      		<p class="fas fa-palette"></p>
					                      	</c:if>
					                      	
					                      	<c:if test="${fn:escapeXml(anuncio.especialidad) == 'JOYERIA'}">
					                      		<p class="fas fa-gem"></p>
					                      	</c:if>
					                      	
					                      	<c:if test="${fn:escapeXml(anuncio.especialidad) == 'RESINA'}">
					                      		<p class="fas fa-prescription-bottle"></p>
					                      	</c:if>
					                      	
					                      	<c:if test="${fn:escapeXml(anuncio.especialidad) == 'FOTOGRAFIA'}">
					                      		<p class="fas fa-camera-retro"></p>
					                      	</c:if>
	                    				<c:out value="${fn:escapeXml(anuncio.especialidad)} "/>
	                    				<c:if test="${fn:escapeXml(anuncio.especialidad)==null}">
					                      		Sin categoría
					                      	</c:if>
				</h5>
				</div>
        		<br/>
        		<a href="${fn:escapeXml(anuncioUrl)}" class="btn btn-primary" id="verMas">Ver más</a>
        	</div>
        	</c:forEach>
      			</c:if>
        	</div>
      		
  	<div class="tab-pane fade" id="listEncargos">
  		<c:if test="${hayEncargos == false}">
  			<h2>¡AÚN NO HAY ENCARGOS!</h2></c:if>
  			<c:if test="${hayEncargos != false}">
    			<c:forEach items="${encargos}" var="encargo">
    			 <div class="encargosCard" style="width: auto;" id="encargosCard">
            		<spring:url value="/beavers/${beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                	</spring:url>
    	
                	<c:if test="${!encargo.photo.isEmpty()}">
            		<img class="card-img-top-publicacion rounded" src="${encargo.photo}" alt="Card image cap">
            		<div class="card-body">
            		<h4 class="card-title"><c:out value="${encargo.titulo}"/></h4>
                    	<p><c:if test="${encargo.disponibilidad == false}">
            				<h5><span class="badge badge-danger">No disponible</span></h5>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<h5><span class="badge badge-success">Disponible</span></h5>
        				</c:if></p>
        				<a href="${fn:escapeXml(encargoUrl)}" class="btn btn-primary" id="verMas">Ver más</a>
        			</div>
            		</c:if>
            		
            		<c:if test="${encargo.photo.isEmpty()}">
            		<img class="card-img-top-publicacion rounded" src="/resources/images/no-pictures.png" alt="No hay imagen">
            			<div class="card-body" id ="card-body">
            			<h4 class="card-title"><c:out value="${encargo.titulo}"/></h4>
                    	<p><c:if test="${encargo.disponibilidad == false}">
            				<h5><span class="badge badge-pill badge-danger" id="badge-noDisponible">No disponible</span></h5>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<h5><span class="badge badge-pill badge-success" id="badge-disponible">Disponible</span></h5>
        				</c:if></p>
        					<a href="${encargoUrl}" class="btn btn-primary" id="verMas">Ver más</a>
        			</div>
            		</c:if>
            		</div>
            		<hr/>
  			</c:forEach>
  			</c:if>
  			</div>
  </div>
  </div>
  </div>
</div>
</beavarts:layout>