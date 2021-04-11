<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	
<beavarts:layout pageName="Detalles de anuncios">

	<spring:url value="/anuncios/{anuncioId}" var="detailUrl">
        <spring:param name="anuncioId" value="${anuncio.id}"/>
    </spring:url>
   
<div class="container justify-content-center" style="display:flex; flex-wrap:wrap;">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	</spring:url>
<ul class="list-group list-group-flush">
		<h1><c:out value="${anuncio.titulo}"/></h1> 
        	<c:if test="${createdByUser== false}">
            <li class="list-group-item">
            	<h3>Publicado por: </h3><h5><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${anuncio.beaver.user.username}"/></strong></a></h5>
            </li>
        	</c:if>
        	<li class="list-group-item">
           		<h3>Precio: </h3><h5 class="descripcionAnuncio"><c:out value="${anuncio.precio} €"/></h5>
        	</li>
	
       		<li class="list-group-item">
           		<h3>Descripción: </h3><h5 class="descripcionAnuncio"><c:out value="${anuncio.descripcion}"/></h5>
        	</li>
        
		<c:if test="${!anuncio.photo.isEmpty()}">
       		<li class="list-group-item">
            	<h3>Imagen de ejemplo</h3>
            	<br/>
            		<img class ="img-thumbnail"src="${anuncio.photo}" width=217px height=250px alt="">
        	</li>
        </c:if>
        <c:if test="${anuncio.photo.isEmpty()}">
       		<li class="list-group-item">
            	<h3>No hay imagen para mostrar</h3>
        	</li>
        </c:if>
        <li class="list-group-item">
        	<h3>Categoría</h3>
        	<div class="text-center">
            	<h5><span class="badge badge-pill badge-categoria"><c:out value="${anuncio.especialidad}"/></span></h5>
        	</div>
        </li>
        <br/>
    </ul>
    </div>
    <div class="text-center">
    <c:if test="${createdByUser == false}">
				<a class="btn btn-primary" href='<spring:url value="#" htmlEscape="true"/>'>Responder al anuncio</a>
	</c:if>
	</div>
    <c:if test="${createdByUser == true}">
        	<a class="btn btn-primary" href='<spring:url value="${anuncio.id}/edit" htmlEscape="true"/>'>Editar anuncio</a>
    </c:if>
</beavarts:layout>