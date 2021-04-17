<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="Detalles de anuncios">

	<spring:url value="/anuncios/{anuncioId}" var="detailUrl">
        <spring:param name="anuncioId" value="${anuncio.id}"/>
    </spring:url>
   
<div class="container">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${anuncio.beaver.id}"/>
               	</spring:url>
     <h1 class="mb-0 SegoeFont"><c:out value="${anuncio.titulo}"/>&nbsp;
     							<c:if test="${anuncio.especialidad == 'TEXTIL'}">
					                      		<i class="fas fa-socks"></i>
					                      	</c:if>
				                     
				                      		<c:if test="${anuncio.especialidad == 'ESCULTURA'}">
					                      		<i class="fas fa-chess-knight"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'ILUSTRACION'}">
					                      		<i class="fas fa-portrait"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'ACRILICO'}">
					                      		<i class="fas fa-paint-brush"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'OLEO'}">
					                      		<i class="fas fa-palette"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'JOYERIA'}">
					                      		<i class="fas fa-gem"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'RESINA'}">
					                      		<i class="fas fa-prescription-bottle"></i>
					                      	</c:if>
					                      	
					                      	<c:if test="${anuncio.especialidad == 'FOTOGRAFIA'}">
					                      		<i class="fas fa-camera-retro"></i>
					                      	</c:if>
	                    				<c:out value="${anuncio.especialidad} "/>
	                				</h1>
	 <br/>
	 <div class="card">
	 	<div class="card-body">
        	<c:if test="${createdByUser== false}">
            	<h3 class="mb-0 SegoeFont">Publicado por: </h3><br/><h5><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${anuncio.beaver.user.username}"/></strong></a></h5><br/>
            </c:if>
           		<h3 class="mb-0 SegoeFont">Precio: </h3><br/><h5 class="descripcionAnuncio"><c:out value="${anuncio.precio} €"/></h5>
        	<br/>
           		<h3 class="mb-0 SegoeFont">Descripción: </h3><br/><h5 class="descripcionAnuncio"><c:out value="${anuncio.descripcion}"/></h5>
        	
		<c:if test="${!anuncio.photo.isEmpty()}">
            	<h3 class="mb-0 SegoeFont">Imagen de ejemplo</h3>
            	<br/>
            		<img class ="img-thumbnail"src="${anuncio.photo}" width=217px height=250px alt="">
        </c:if>
        <c:if test="${anuncio.photo.isEmpty()}">
       		
            	<br/><h3 class="mb-0 SegoeFont">No hay imagen para mostrar</h3>
        	
        </c:if>
  </div>
  </div>
  </div>
  <br/>
    <div class="text-center">
    <c:if test="${createdByUser == false}">
				<a class="btn btn-primary" href='<spring:url value="/solicitudes/${anuncio.id}/new" htmlEscape="true"/>'>Responder al anuncio</a>
	</c:if>
	</div>
    <c:if test="${createdByUser == true}">
        	<a class="btn btn-primary" href='<spring:url value="/beavers/${anuncio.beaver.id}/anuncios/${anuncio.id}/edit" htmlEscape="true"/>'>Editar anuncio</a>
        	<c:if test="${urlEdit == true}">
        	<div class="alert alert-danger" role="alert">
			<c:out value="${errorEditarSolicitudesAceptadas}"/>
			</div>
			</c:if>
			
        	<a class="btn btn-primary" href='<spring:url value="/beavers/${anuncio.beaver.id}/anuncios/${anuncio.id}/delete" htmlEscape="true"/>'>Eliminar anuncio</a>
        	<c:if test="${urlEliminar == true}">
        	<div class="alert alert-danger" role="alert">
			<c:out value="${errorEliminarSolicitudesAceptadas}"/>
			</div>
			</c:if>
    </c:if>
    
    <security:authorize access="hasAuthority('admin')">
	    &nbsp;              
	    <spring:url value="{anuncioId}/delete" var="deleteAnuncioUrl">
		<spring:param name="anuncioId" value="${anuncio.id}"/>              
		</spring:url>
		<a style="color:white"class="btn btn-red" href="${fn:escapeXml(deleteAnuncioUrl)}"><i class="fas fa-trash-alt"></i>Borrar Anuncio</a>
    </security:authorize>
</beavarts:layout>