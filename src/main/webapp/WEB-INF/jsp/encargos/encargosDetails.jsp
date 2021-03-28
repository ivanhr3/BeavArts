<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
<petclinic:layout pageName="encargosDetails">
<h2>Encargo:<c:out value="${encargo.titulo}"/></h2>

	<spring:url value="/encargoInfo/{encargoId}" var="detailUrl">
        <spring:param name="encargoId" value="${encargo.id}"/>
    </spring:url>

    <table class="table table-striped">
        <tr>
            <th>Publicado por: </th>
            <td><c:out value="${encargo.beaver.user.username}"/></td>
        </tr>

        <tr>
            <th>Precio</th>
            <td><c:out value="${encargo.precio}"/></td>
        </tr>

        <tr>
            <th>Descripción: </th>
            <td><c:out value="${encargo.descripcion}"/></td>
        </tr>

       <tr>
            <th>Fotos</th>
            <td><img width=200px height= auto src="/resources/images/imagenes/${encargo.photo}" alt ="Foto" /></td>
        </tr>
        
        <tr>
            <th>Disponibilidad</th>
            <td>
            	<c:if test="${encargo.disponibilidad == false}">
            		<dd>No disponible</dd>
        		</c:if>
        		<c:if test="${encargo.disponibilidad == true}">
            		<dd>Disponible</dd>
        		</c:if>
        	</td>
        </tr>
        
    </table>
    
    <c:if test="${encargo.beaver.user.username != principalUsername}">
    
    		<c:if test="${encargo.disponibilidad == true}">
				<a class="btn btn-default">Encargar</a>
			</c:if>
     </c:if>
     <c:if test="${encargo.beaver.user.username == principalUsername}">
      	  	<a class="btn btn-default" href='<spring:url value="${encargo.id}/delete" htmlEscape="true"/>'>Eliminar encargo</a>
        	<a class="btn btn-default" href='<spring:url value="${encargo.id}/edit" htmlEscape="true"/>'>Editar encargo</a>
	
    </c:if>
    
    <br/>
    <br/>


</petclinic:layout>