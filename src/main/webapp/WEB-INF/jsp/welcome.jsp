<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="home">
    <div class="text-center">
    	<h1 class="SegoeFont responsiveFontSmartphoneTitulo">¡Bienvenido al portal BeavArts!</h1>
    	<br>
        <h2 class="SegoeFont responsiveFontSmartphoneTituloMediano">Para ver todas las funcionalidades, <i>inicia sesión.</i></h2>
        <h2 class="SegoeFont responsiveFontSmartphoneTituloMediano">Y si aún no formas parte de esta gran comunidad, <i> <a href="/users/new"><b  class="SegoeFont" style="color:#5e8a58"> ¡regístrate!</b></a></i></h2>
       
        <div class="col-12 text-center imagenResponsive">
            <img src="<spring:url value="/resources/images/emblema.png" htmlEscape="true" />"
                                             alt=""/>
 		</div>
    </div>
</beavarts:layout>