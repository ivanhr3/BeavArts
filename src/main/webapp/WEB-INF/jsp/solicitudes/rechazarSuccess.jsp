<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E" %>
 --%>
<beavarts:layout pageName="errorAceptar">
<div class="minAlto">
    <h3>Su solicitud ha sido rechazada con éxito.</h3>
<br/>
	<a class="btn btn-primary" href="/solicitudes/list">Volver al listado</a>
</div>
</beavarts:layout>