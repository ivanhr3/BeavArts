<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="perfil">
    <h2> Datos de portfolio: </h2>
    <br/>
    <form:form modelAttribute="portfolio" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
               
            <petclinic:inputField label="Sobre mi" name="sobreMi"/>           
            
            <petclinic:inputField label="Portfolio" name="photos"/>
            <p>Para introducir varias fotos separe las url por comas sin utilizar espacios.</p>
             <p style="color:red"><c:out value="${errorUrl}"/></p>
            
    
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-default" type="submit">Actualizar perfil</button>
                 
            </div>
        </div>
    </form:form>
</petclinic:layout>
