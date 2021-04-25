<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
<script src='https://kit.fontawesome.com/a076d05399.js'></script>

<beavarts:layout pageName="succesfulDeletion">
    <div class="container">
        <h1><fmt:message key="borradoExitoso.titulo"/></h1>
        <p><fmt:message key="borradoExitoso.texto"/></p>
        <a class="btn btn-secondary" href='<spring:url value="/" htmlEscape="true"/>'><fmt:message key="borradoExitoso.volverHome"/></a>
    </div>
</beavarts:layout>