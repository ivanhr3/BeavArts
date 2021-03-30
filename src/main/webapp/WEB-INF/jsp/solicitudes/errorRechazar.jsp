<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%-- <%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags%22%%3E" %>
 --%>
<petclinic:layout pageName="errorRechazar">

    <h2>Ha ocurrido un error al intentar rechazar la solicitud</h2>

	<a class="btn btn-default" href="/">Volver al inicio</a>


</petclinic:layout>