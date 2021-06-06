<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<script src='https://kit.fontawesome.com/a076d05399.js'></script>
<beavarts:layout pageName="home">
<div class="minAlto text-center">
    
    	<h1 class="Roboto responsiveFontSmartphoneTitulo">¡Bienvenido al portal BeavArts!</h1>
    	<br>
    	<sec:authorize access="!isAuthenticated()">
	        <h2 class="RobotoLight responsiveFontSmartphoneTituloMediano">Para ver todas las funcionalidades, <i>inicia sesión.</i></h2>
	        <h2 class="RobotoLight responsiveFontSmartphoneTituloMediano">Y si aún no formas parte de esta gran comunidad, <i>¡regístrate!</i></h2>
       	</sec:authorize>
        
   
            <img style="max-width:100%"class="imagenResponsive"src="<spring:url value="/resources/images/emblema23.png" htmlEscape="true" />" alt=""/>
            <img class="homeDisplay imagenResponsive" src="<spring:url value="/resources/images/HomeFondo.png" htmlEscape="true" />" alt=""/>
            <img class="homeDisplay2 imagenResponsive" src="<spring:url value="/resources/images/HomeFondo3.png" htmlEscape="true" />"  alt=""/>
 		
  

    
</div>
</beavarts:layout>