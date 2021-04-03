<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


<beavarts:layout pageName="home">
    <div class="text-center">
        <h2>Para ver todas las funcionalidades, <a href="<c:url value="/login" />inicia sesión.</a> </h2>

        <h2>Y si aún no formas parte de esta gran comunidad, <a href="<c:url value="/users/new" />¡registrate!</a></h2>
    </div>  

    <br>
    <br>
    

    <div class="row text-center">
        <h1><fmt:message key="welcome"/></h1>
        <div class="col-12 text-center imagenResponsive">
            <img src="<spring:url value="/resources/images/emblema.png" htmlEscape="true" />"
                                             alt=""/>
        </div>

        
    </div>
</beavarts:layout>


