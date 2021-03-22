<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="todoOk">
<h2>¡Encargo eliminado correctamente!</h2>

<a class="btn btn-default" href='<spring:url value="/beavers/${encargo.beaver.id}/encargos/list" htmlEscape="true"/>'>Volver</a>
</petclinic:layout>