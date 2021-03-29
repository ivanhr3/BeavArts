<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
<beavarts:layout pageName="encargosDetails">
<h1>Encargo: <c:out value="${encargo.titulo}"/></h1>

	<spring:url value="/encargoInfo/{encargoId}" var="detailUrl">
        <spring:param name="encargoId" value="${encargo.id}"/>
    </spring:url>
    
	<div class="table-responsive">
	
    <table class="table table-borderless">
 
        <tr>
        	<c:if test="${encargo.beaver.user.username != principalUsername}">
            <th >Publicado por: </th>
            <td style="text-align: left">
            	<spring:url value="/beavers/{beaverId}" var="beaverUrl">
                	<spring:param name="beaverId" value="${encargo.beaver.id}"/>
               	</spring:url>
               	<a href="${fn:escapeXml(beaverUrl)}"><b><c:out value="${encargo.beaver.user.username}"/></b></a></td>
        	</c:if>
        </tr>

        <tr>
            <th>Precio:</th>
            <td style="text-align: left"><c:out value="${encargo.precio} €"/></td>
        </tr>
	
        <tr>
            <th><h3>Descripción: </h3></th>
            <td style="text-align: left"><c:out value="${encargo.descripcion}"/></td>
        </tr>

       <tr>
            <th>Imágenes de ejemplo:</th>
            <td style="text-align: left"><a href="${encargo.photo}"><c:out value="${encargo.photo}"/></a></td>
        </tr>
        
        <tr>
            <th>Disponibilidad:</th>
            <td style="text-align: left">
            	<c:if test="${encargo.disponibilidad==true}">
            		<dd>Disponible</dd>
        		</c:if>
            	<c:if test="${encargo.disponibilidad==false}">
            		<dd>No disponible</dd>
        		</c:if>
        	</td>
        </tr>
        
    </table>
    </div>
    
    <c:if test="${createdByUser== false}">
    
    		<c:if test="${encargo.disponibilidad == true}">
				<a class="btn btn-default">Encargar</a>
			</c:if>
     </c:if>
     <c:if test="${createdByUser == true}">
      	  	<a class="btn btn-default" href='<spring:url value="${encargo.id}/delete" htmlEscape="true"/>'>Eliminar encargo</a>
        	<a class="btn btn-default" href='<spring:url value="${encargo.id}/edit" htmlEscape="true"/>'>Editar encargo</a>
	
    </c:if>
    
    <br/>
    <br/>


</beavarts:layout>