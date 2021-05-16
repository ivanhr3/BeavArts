<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> 
<%-- Para  tildes, ñ y caracteres especiales como el € --%>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
	
<beavarts:layout pageName="Detalles de encargos">

	<spring:url value="/encargoInfo/{encargoId}" var="detailUrl">
        <spring:param name="encargoId" value="${encargo.id}"/>
    </spring:url>
   
<div class="container">
<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	</spring:url>
           <div class="card" style="box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;">
                <c:if test="${!encargo.photo.isEmpty()}">
            	<div id="multi-item-example" class="carousel carousel-multi-item carouselPerfil text-center" data-ride="carousel">
            	<div class="carousel-inner sombraPng" role="listbox">		
            	<div class="carousel-item active">
            		<img class ="d-bldk w-75"src="${encargo.photo}" alt="">
            		</div>
            		</div>
            		</div>
        		</c:if>
			<div class="card-body">
			<div class="row">
			<div class="col">
			<div class="card-title text-center"><h2 class="mb-0 Roboto encargoDetailsFont"><c:out value="${encargo.titulo}"/>&nbsp;<c:if test="${encargo.disponibilidad == false}">
            				<span class="badge badge-pill badge-danger" id="badge-noDisponible">No disponible</span>
        				</c:if>
        				<c:if test="${encargo.disponibilidad == true}">
            				<span class="badge badge-pill badge-success" id="badge-disponible">Disponible</span>
        			</c:if></h2>
			</div>
			</div>
			</div>
        	<c:if test="${createdByUser== false}">
            <h4 class="encargoDetailsPrecio">Publicado por&nbsp;</h4><h4 class="RobotoLight"><a href="${fn:escapeXml(beaverUrl)}"><strong><c:out value="${encargo.beaver.user.username}"/></strong></a></h4>
        	</c:if>
        	
        	<h4 class="col mb-1 encargoDetailsPrecio"><c:out value="${encargo.descripcion}"/></h4>
           	<h4 class="col mt-4 mb-1 encargoDetailsPrecio">Precio: <c:out value="${encargo.precio} €"/></h4>
        	<div class="text-center">
            	<c:if test="${encargo.photo.isEmpty()}">
            	<br/><h5 class="mb-0 encargoDetailsPrecio">No hay imagen para mostrar</h5>
        		</c:if>
        	</div>
    </div>
    </div>
    </div>
    <br/>
    <c:if test="${createdByUser == false}">
    		<c:if test="${encargo.disponibilidad == true}">
				<a class="btn btn-primary encargoDetailsFontBody" href='<spring:url value="/solicitudes/${fn:escapeXml(encargo.id)}/create" htmlEscape="true"/>'>Solicitar encargo</a>
			</c:if>
     </c:if>
    <br/>
    <c:if test="${createdByUser == true}">
        	<a class="btn btn-primary encargoDetailsFontBody" href='<spring:url value="${encargo.id}/edit" htmlEscape="true"/>'>Editar encargo</a>
    </c:if>
    
    <security:authorize access="hasAuthority('admin')">
                  
	    &nbsp;              
	    <spring:url value="{encargoId}/delete" var="deleteEncargoUrl">
		<spring:param name="encargoId" value="${encargo.id}"/>              
		</spring:url>
		<a style="color:white"class="btn btn-red encargoDetailsFontBody" href="${fn:escapeXml(deleteEncargoUrl)}"><p class="fas fa-trash-alt"></p> Borrar Encargo</a>
              
    </security:authorize>
</beavarts:layout>

