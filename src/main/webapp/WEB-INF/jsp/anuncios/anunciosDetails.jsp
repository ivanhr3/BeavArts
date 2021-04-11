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
		<h1 class="mb-0 SegoeFont"><c:out value="${anuncio.titulo}"/></h1> <br/>
        	<c:if test="${createdByUser== false}">
            <li class="list-group-item">
            	<h3 class="mb-0 SegoeFont">Publicado por: </h3><br/><h5><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${anuncio.beaver.user.username}"/></strong></a></h5>
            </li>
        	</c:if>
        	<li class="list-group-item">
           		<h3 class="mb-0 SegoeFont">Precio: </h3><br/><h5 class="descripcionAnuncio"><c:out value="${anuncio.precio} €"/></h5>
        	</li>
	
       		<li class="list-group-item">
           		<h3 class="mb-0 SegoeFont">Descripción: </h3><br/><h5 class="descripcionAnuncio"><c:out value="${anuncio.descripcion}"/></h5>
        	</li>
        
		<c:if test="${!anuncio.photo.isEmpty()}">
       		<li class="list-group-item">
            	<h3 class="mb-0 SegoeFont">Imagen de ejemplo</h3>
            	<br/>
            		<img class ="img-thumbnail"src="${anuncio.photo}" width=217px height=250px alt="">
        	</li>
        </c:if>
        <c:if test="${anuncio.photo.isEmpty()}">
       		<li class="list-group-item">
            	<br/><h3 class="mb-0 SegoeFont">No hay imagen para mostrar</h3>
        	</li>
        </c:if>
        <li class="list-group-item">
        	<h3 class="mb-0 SegoeFont">Categoría</h3>
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