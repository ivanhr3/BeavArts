<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="encargosDetails">
<h2>Encargo:<c:out value="${encargo.titulo}"/></h2>

	<spring:url value="/encargoInfo/{encargoId}" var="detailUrl">
        <spring:param name="encargoId" value="${encargo.id}"/>
    </spring:url>

    <table class="table table-striped">
        <tr>
            <th>Publicado por: </th>
            <td><c:out value="${encargo.beaver}"/></td>
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
            <td><c:out value="${encargo.photo}"/></td>
        </tr>

        <c:if test="${encargo.disponibilidad}">
			<a class="btn btn-default">Encargar</a>
		</c:if>
        <c:if test="${!encargo.disponibilidad}">
            <dd>No disponible</dd>
        </c:if>

    </table>
    
    <sec:authorize access="hasAuthority('admin')">
        <a class="btn btn-default" href='<spring:url value="/encargos/delete" htmlEscape="true"/>'>Eliminar encargo</a>
        <a class="btn btn-default" href='<spring:url value="/encargos/{encargoId}/edit" htmlEscape="true"/>'>Editar encargo</a>
    </sec:authorize>
    <br/>
    <br/>


</petclinic:layout>