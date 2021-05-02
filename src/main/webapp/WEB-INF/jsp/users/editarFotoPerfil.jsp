<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="FotoPerfil">
    
    <br/>    
    <form:form modelAttribute="beaver" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
         <b class="RobotoLight" style="margin-left:15px"> Foto de perfil:</b>
             <petclinic:inputField label="" name= "urlFotoPerfil"/> 
        </div>
        <c:if test="${errorUrl != null}">
                        	<div class="alert alert-danger col-sm-10" role="alert">
								<c:out value="${errorUrl}"/>
							</div>
						</c:if>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-primary" type="submit">Actualizar foto</button>           
            </div>
        </div>
        <div class="control-group hidden" style="display: none">
        	
        	<input name="firstName" value="${beaver.firstName}"><br>
        	<input name="lastName" value="${beaver.lastName}"><br>
        	<input name="dni" value="${beaver.dni}"><br>
        </div>
    </form:form>
</petclinic:layout>
