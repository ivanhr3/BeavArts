<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E "%> --%>

<petclinic:layout pageName="solicitudDetails">
<h2>Solicitud: </h2>


    <table class="table table-striped">
        <tr>
            <th>Precio</th>
            <td><c:out value="${solicitud.precio}"/></td>
        </tr>
		<tr>
            <th>Estado</th>
            <td><c:out value="${solicitud.estado}"/></td>
        </tr>
        <tr>
            <th>Solicitud de</th>
            <td><c:out value="${solicitud.beaver.user.username}"/></td>
        </tr>
        <c:if test="${solicitud.estado eq 'ACEPTADO'}">
        	<th>Encargo de: </th>
            <td><c:out value="${solicitud.encargo.beaver.user.username}"/></td>
        </c:if>

    </table>


   <spring:url value="/solicitudes/accept/${solicitud.id}" var="aceptarUrl">
   </spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(aceptarUrl)}" >Aceptar Solicitud</a>
	
	<spring:url value="/solicitudes/decline/${solicitud.id}" var="rechazarUrl">
	</spring:url>
    <a class="btn btn-default" href="${fn:escapeXml(rechazarUrl)}" >Rechazar Solicitud</a>


</petclinic:layout>