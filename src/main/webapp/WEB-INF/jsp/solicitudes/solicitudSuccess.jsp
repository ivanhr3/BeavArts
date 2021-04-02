<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E" %> --%>

<beavarts:layout pageName="successSolicitud">

    <h2>Su solicitud ha sido creada con éxito</h2>

	<a class="btn btn-default" href='<spring:url value="/solicitudes/list" htmlEscape="true"/>'>Mis solicitudes</a>


</beavarts:layout>