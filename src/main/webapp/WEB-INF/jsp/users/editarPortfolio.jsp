<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="perfil">
    <h2 class="SegoeFont"> Datos de portfolio: </h2>
    <br/>
    <form:form modelAttribute="portfolio" class="form-horizontal" id="add-owner-form">
        <div class="form-group has-feedback">
            <b class="SegoeFont" style="margin-left:15px"> Sobre mi:</b>
            <petclinic:inputField label="" name="sobreMi"/>           
            
            <b class="SegoeFont" style="margin-left:15px"> Portfolio:</b>
            <petclinic:inputField label="" name="photos"/>                       
             <c:if test="${errorUrl != null}">
                        	<div class="alert alert-danger col-sm-10" role="alert">
								<c:out value="${errorUrl}"/>
							</div>
						</c:if>
            <p class="SegoeFont">Para introducir varias fotos separe las url por comas sin utilizar espacios.</p>
    
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
               
                        <button class="btn btn-primary" type="submit">Actualizar perfil</button>
                 
            </div>
        </div>
    </form:form>
</petclinic:layout>
