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

<beavarts:layout pageName="deletion">
<div class="minAlto">
    <form:form modelAttribute="user" class="form-horizontal" id="delete-user-data">
    <div class="container">
        <h1 class="Roboto text-center fontDeleteDataTitulo"><fmt:message key="confirmacionBorrado.titulo"/></h1>
        <br/>
    
        <p class="RobotoLight fontDeleteDataTexto"><fmt:message key="confirmacionBorrado.texto"/></p>

        <br/>
        <div class="text-center">
            <a style="margin-bottom: 5%;" class="btn btn-secondary fontDeleteDataTexto" href='<spring:url value="/beavers/beaverInfo/${myBeaverId}" htmlEscape="true"/>'><fmt:message key="confirmacionBorrado.cancelar"/></a>
            <button style="margin-bottom: 5%;" class="btn btn-danger fontDeleteDataTexto" type="submit"><fmt:message key="confirmacionBorrado.eliminar"/></button>
        </div>
    </div>
    </form:form>

</div>
</beavarts:layout>