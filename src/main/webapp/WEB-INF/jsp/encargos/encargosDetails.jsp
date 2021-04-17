<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
	
<beavarts:layout pageName="Detalles de encargos">

	<spring:url value="/encargoInfo/{encargoId}" var="detailUrl">
        <spring:param name="encargoId" value="${encargo.id}"/>
    </spring:url>
   
<div class="container">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	</spring:url>
            <div class="card-encargos">
                <c:if test="${!encargo.photo.isEmpty()}">
            		<img class="card-img-top" src="${encargo.photo}"  alt="">
        		</c:if>
			<div class="card-body">
			<div class="row">
			<div class="col">
			<div class="card-title"><h1 class="mb-0 SegoeFont"><c:out value="${encargo.titulo}"/>&nbsp;<c:if test="${encargo.disponibilidad == false}">
            				<span class="badge badge-pill badge-danger" style="font-size:24px;" id="badge-noDisponible">No disponible</span>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<span class="badge badge-pill badge-success" style="font-size:24px;"id="badge-disponible">Disponible</span>
        			</c:if></h1>
			</div>
			</div>
			</div>
        	<c:if test="${createdByUser== false}">
            	<h3 class="mb-0 SegoeFont">Publicado por </h3><h5><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${encargo.beaver.user.username}"/></strong></a></h5>
        	</c:if>
           	<br/><h3 class="mb-0 SegoeFont">Precio </h3><h5><c:out value="${encargo.precio} €"/></h5>
           		<br/><h3 class="mb-0 SegoeFont">Descripción </h3><h5><c:out value="${encargo.descripcion}"/></h5>
        	<div class="text-center">
            	<c:if test="${encargo.photo.isEmpty()}">
            	<br/><h3 class="mb-0 SegoeFont">No hay imagen para mostrar</h3>
        		</c:if>
        	</div>
    </div>
    </div>
    </div>
    <br/>
    <c:if test="${createdByUser == false}">
    		<c:if test="${encargo.disponibilidad == true}">
				<a class="btn btn-primary" href='<spring:url value="/solicitudes/${encargo.id}/create" htmlEscape="true"/>'>Solicitar encargo</a>
			</c:if>
     </c:if>
    <br/>
    <c:if test="${createdByUser == true}">
        	<a class="btn btn-primary" href='<spring:url value="${encargo.id}/edit" htmlEscape="true"/>'>Editar encargo</a>
    </c:if>
    
    <security:authorize access="hasAuthority('admin')">
                  
	    &nbsp;              
	    <spring:url value="{encargoId}/delete" var="deleteEncargoUrl">
		<spring:param name="encargoId" value="${encargo.id}"/>              
		</spring:url>
		<a style="color:white"class="btn btn-red" href="${fn:escapeXml(deleteEncargoUrl)}"><i class="fas fa-trash-alt"></i> Borrar Encargo</a>
              
    </security:authorize>
</beavarts:layout>

