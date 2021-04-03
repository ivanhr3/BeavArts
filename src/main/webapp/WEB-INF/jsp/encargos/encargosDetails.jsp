<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	
<beavarts:layout pageName="encargosDetails">

	<spring:url value="/encargoInfo/{encargoId}" var="detailUrl">
        <spring:param name="encargoId" value="${encargo.id}"/>
    </spring:url>
   
<div class="container justify-content-center" style="display:flex; flex-wrap:wrap;">
<h1>Encargo: <c:out value="${encargo.titulo}"/></h1> 
    <table class="table table-borderless">
        <tr>
        	<c:if test="${createdByUser== false}">
            <th><h3>Publicado por: </h3></th>
            <td style="text-align: justify;">
            	<spring:url value="/beavers/beaverInfo/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	</spring:url>
               	<a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${encargo.beaver.user.username}"/></b></a>
               	</td>
        	</c:if>
        
        </tr>

        <tr>
            <th><h3>Precio:</h3></th>
            <td style="text-align: justify;"><c:out value="${encargo.precio} €"/>
            </td>
        </tr>
	
        <tr>
            <th><h3>Descripción: </h3></th>
             
            <td style="text-align: justify;"><c:out value="${encargo.descripcion}"/></td>
        </tr>
        
		<c:if test="${!encargo.photo.isEmpty()}">
       <tr>
            <th><h3>Imágenes de ejemplo:</h3></th>
            <td style="text-align: justify;"><img width=150px height=150px alt="" src="${encargo.photo}"/></td>
        </tr>
        </c:if>
        <tr>
            <th><h3>Disponibilidad:</h3></th>
            <td style="text-align: justify;">
            	<c:if test="${encargo.disponibilidad==true}">
            		<dd>Disponible</dd>
        		</c:if>
            	<c:if test="${encargo.disponibilidad==false}">
            		<dd>No disponible</dd>
        		</c:if>
        	</td>
        </tr>

    </table>
    
    <c:if test="${createdByUser == false}">
    
    		<c:if test="${encargo.disponibilidad == true}">
				<a class="btn btn-default" href='<spring:url value="/solicitudes/${encargo.id}/create" htmlEscape="true"/>'>Solicitar encargo</a>
			</c:if>
     </c:if>
     <c:if test="${createdByUser == true}">
      	  	<a class="btn btn-default" href='<spring:url value="${encargo.id}/delete" htmlEscape="true"/>'>Eliminar encargo</a>
        	<a class="btn btn-default" href='<spring:url value="${encargo.id}/edit" htmlEscape="true"/>'>Editar encargo</a>
    </c:if>
</div>
</beavarts:layout>