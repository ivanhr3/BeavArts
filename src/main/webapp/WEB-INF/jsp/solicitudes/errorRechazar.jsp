<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E" %>
 --%>
<beavarts:layout pageName="errorRechazar">
<div class="minAlto Roboto">
    <h3>Ha ocurrido un error al intentar rechazar la solicitud</h3>
<br/>
	<a class="btn btn-primary" href="/">Volver al inicio</a>

</div>
</beavarts:layout>