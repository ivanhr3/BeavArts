<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="error">
<div class="minAlto">
	<div class="col-12 text-center" >
		<h2>Ha ocurrido un error. Si este error persiste, es conveniente que avises al administrador del sistema.</h2>
	    <spring:url value="/resources/images/sadbeaver.png" var="sadBeaver"/>
	    <img src="${sadBeaver}"/>
	
	    <p>${exception.message}</p>
	    
	</div>
</div>	
</beavarts:layout>
