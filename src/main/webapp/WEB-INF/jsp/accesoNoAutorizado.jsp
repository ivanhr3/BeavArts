<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="errorForbidden">
<div class="minAlto">
	<div class="col-12 text-center" >
    	<spring:url value="/resources/images/ForbiddenAccess.png" var="forbiddenImage"/>
    	<img style="margin-bottom:10px" src="${forbiddenImage}"/>


    	<h2>¡ACCESO NO AUTORIZADO!</h2>


    	
     </div>

</div>
</beavarts:layout>

