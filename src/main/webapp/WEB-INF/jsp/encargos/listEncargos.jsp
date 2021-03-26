<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	<c:set var = "SI" value = "Si"/>
	<c:set var = "NO" value = "No"/>

<petclinic:layout pageName="encargosList">
    <h2>Mis Encargos</h2>

    <table id="encargosTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Disponibilidad</th>
            <th style="width: 150px;">Título</th>
            <th style="width: 200px;">Precio</th>
            <th style="width: 120px">Descripción</th>
            <th style="width: 120px">Fotos</th>
            <th style="width: 120px">Publicado por</th>
        </tr>
        </thead>
        
        <tbody>
        <c:forEach items="${encargos}" var="encargo">
        
            <tr>
            	<td>
                    <c:if test="${encargo.disponibilidad == NO}">
            		<dd>No disponible</dd>
        		</c:if>
        		<c:if test="${encargo.disponibilidad == SI}">
            		<dd>Disponible</dd>
        		</c:if>
                </td>
                <td>
                <spring:url value="/beavers/{beaverId}/encargos/{encargoId}" var="encargoUrl">
                        <spring:param name="encargoId" value="${encargo.id}"/>
                        <spring:param name="beaverId" value="${encargo.beaver.id}"/>
                </spring:url>  
                        <a href="${fn:escapeXml(encargoUrl)}"><b><c:out value="${encargo.titulo}"/></b></a>
                         
                </td>
                <td>
                    <c:out value="${encargo.precio}"/>
                </td>
                <td>
                	<c:out value="${encargo.descripcion}"/>
                </td>
                <td>
                	<img width=100px height= auto src="/resources/images/imagenes/${encargo.photo}" alt ="Foto" />
                </td>
                <td>
                <spring:url value="/perfil/{username}" var="usernameUrl">
                        <spring:param name="username" value="${encargo.beaver.user.username}"/>
                </spring:url>  
                        <a href="${fn:escapeXml(usernameUrl)}"><b><c:out value="${encargo.beaver.user.username}"/></b></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <a class="btn btn-default" href='<spring:url value="new" htmlEscape="true"/>'>Crear encargo</a>
</petclinic:layout>