<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="perfil">
<div class="minAlto">
    <h2 class="Roboto"> Datos de Portfolio: </h2>
    <br/>


    <form:form modelAttribute="portfolio" class="form-horizontal" id="add-owner-form">
    
        <div class="form-group has-feedback">
                 
            <b class="Roboto" style="margin-left:15px; text-align:left"> Sobre mi:</b>
            <petclinic:inputField label="" name="sobreMi"/>
            <br/>      
            <b class="Roboto" style="margin-left:15px; text-align:left"> Portfolio:</b>
            <petclinic:inputField label="" name="photos"/>                       
             <c:if test="${errorUrl != null}">
                        	<div class="alert alert-danger col-sm-10" role="alert">
								<c:out value="${errorUrl}"/>
							</div>
						</c:if>
            <p class="RobotoLight" style="margin-left:15px">Para introducir varias fotos separe las url por comas sin utilizar espacios.</p>
    		<br/>
        </div>
        <div class="form-group" style="margin-left:15px">
           
               
                        <button class="btn btn-primary" type="submit">Actualizar perfil</button>
                 
            
        </div>
    </form:form>
    
</div>
</petclinic:layout>
