<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="beavarts" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<beavarts:layout pageName="beavers">
    <h2>
        <c:if test="${beaver['new']}">Registrarse </c:if>
    </h2>
    
    
    <form:form modelAttribute="beaver" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <beavarts:inputField label="Nombre" name="firstName"/>
            <beavarts:inputField label="Apellido" name="lastName"/>
            
             
            	<p style="margin-left:210px">Para seleccionar varias especialidades mantenga la tecla 'ctrl' y seleccione sus especialidades.</p>
           
            
            <div class="control-group" style="padding: 10px">
            	<beavarts:selectField name="especialidades" label="Especialidades" names="${types}" size="8"/>
            </div>
            
            <beavarts:inputField label="DNI" name="dni"/>
            <beavarts:inputField label="Email" name="email"/>
            <beavarts:inputField label="Username" name="user.username"/>
            <beavarts:inputField label="Password" name="user.password"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${beaver['new']}">
                        <button class="btn btn-default" type="submit">�Convertirme en Beaver!</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Actualizar Beaver</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</beavarts:layout>
