<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="solicitudDetails">
<h2>Solicitud: </h2>


    <table class="table table-striped">
        <tr>
            <th>Precio</th>
            <td><c:out value="${solicitud.precio}"/></td>
        </tr>
        
        <tr>
            <th>Usuario</th>
            <td><c:out value="${solicitud.beaver.user}"/></td>
        </tr>
        
        <c:forEach var="encargo" items="${solicitud.encargo}">
        <tr>
            <th>Encargo: </th>
            <td><c:out value="${encargo.titulo}"/></td>
        </tr>
        <tr>
            <th>Descripcion de encargo</th>
            <td><c:out value="${encargo.descripcion}"/></td>
        </tr>
		</c:forEach>
    </table>


	<a class="btn btn-default" href='<spring:url value="/accept/${solicitud.id}" htmlEscape="true"/>'>Aceptar</a>

	<a class="btn btn-default" href='<spring:url value="/decline/${solicitud.id}" htmlEscape="true"/>'>Rechazar</a>
	


</petclinic:layout>