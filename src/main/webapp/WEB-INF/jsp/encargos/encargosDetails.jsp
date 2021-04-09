<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	
<beavarts:layout pageName="Detalles de encargos">

	<spring:url value="/encargoInfo/{encargoId}" var="detailUrl">
        <spring:param name="encargoId" value="${encargo.id}"/>
    </spring:url>
   
<div class="container justify-content-center" style="display:flex; flex-wrap:wrap;">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	</spring:url>
<ul class="list-group list-group-flush">
		<h1><c:out value="${encargo.titulo}"/></h1> 
        	<c:if test="${createdByUser== false}">
            <li class="list-group-item">
            	<h3>Publicado por: </h3><h5><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${encargo.beaver.user.username}"/></strong></a></h5>
            </li>
        	</c:if>
        	<li class="list-group-item">
           <h3>Precio: </h3><h5><c:out value="${encargo.precio} €"/></h5>
        	</li>
	
       		<li class="list-group-item">
           		<h3>Descripción: </h3><h5><c:out value="${encargo.descripcion}"/></h5>
        	</li>
        
		<c:if test="${!encargo.photo.isEmpty()}">
       		<li class="list-group-item">
            	<h3>Imágenes de ejemplo</h3>
            	<br/>
            		<img class ="img-thumbnail"src="${encargo.photo}" width=217px height=250px alt="">
        	</li>
        </c:if>
        <li class="list-group-item">
        	<div class="text-center">
            	<p><c:if test="${encargo.disponibilidad == false}">
            				<h5><span class="badge badge-pill badge-danger" id="badge-noDisponible">No disponible</span></h5>
        				</c:if>
        			<c:if test="${encargo.disponibilidad == true}">
            				<h5><span class="badge badge-pill badge-success" id="badge-disponible">Disponible</span></h5>
        			</c:if></p>
        	</div>
        </li>
    </ul>
    <br/>
    <c:if test="${createdByUser == false}">
    		<c:if test="${encargo.disponibilidad == true}">
				<a class="btn btn-primary" href='<spring:url value="/solicitudes/${encargo.id}/create" htmlEscape="true"/>'>Solicitar encargo</a>
			</c:if>
     </c:if>
    </div>
    <c:if test="${createdByUser == true}">
        	<a class="btn btn-primary" href='<spring:url value="${encargo.id}/edit" htmlEscape="true"/>'>Editar encargo</a>
    </c:if>
</beavarts:layout>