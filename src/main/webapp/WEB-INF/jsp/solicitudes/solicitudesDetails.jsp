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
            <th>Usuario</th>
            <td><c:out value="${solicitud.beaver.user.username}"/></td>
        </tr>
    </table>


    <a class="btn btn-default" href='<spring:url value="/accept/${solicitud.id}" htmlEscape="true"/>'>Aceptar</a>

    <a class="btn btn-default" href='<spring:url value="/decline/${solicitud.id}" htmlEscape="true"/>'>Rechazar</a>



</petclinic:layout>